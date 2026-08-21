package io.github.seenings.school.util;

import io.github.seenings.school.model.School;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SchoolExcelReaderUtil {

    // 匹配省份分组行，如 "北京市（92所）"
    private static final Pattern PROVINCE_PATTERN = Pattern.compile("^(.+?)（\\d+所）$");

    // 表头行关键字
    private static final String HEADER_KEYWORD = "学校名称";

    /**
     * 从输入流读取（兼容 .xls 和 .xlsx）
     */
    public static List<School> read(InputStream inputStream) throws IOException {
        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            return readSheet(workbook.getSheetAt(0));
        }
    }

    private static List<School> readSheet(Sheet sheet) {
        List<School> list = new ArrayList<>();
        boolean headerFound = false;

        for (Row row : sheet) {
            String firstCell = getCellString(row.getCell(0));

            // 跳过空行
            if (firstCell == null || firstCell.isBlank()) {
                continue;
            }

            // 找到表头行后才开始解析数据
            if (!headerFound) {
                if (containsHeader(row)) {
                    headerFound = true;
                }
                continue;
            }

            // 跳过省份分组行，如 "北京市（92所）"
            Matcher matcher = PROVINCE_PATTERN.matcher(firstCell.trim());
            if (matcher.matches()) {
                continue;
            }

            // 序号必须是数字，否则跳过（防止异常行）
            Integer serialNo = parseInteger(firstCell);
            if (serialNo == null) {
                continue;
            }

            // 解析数据行（列索引：0序号 1学校名称 2标识码 3主管部门 4所在地 5办学层次 6备注）
            School school = new School();
            school.setSerialNo(serialNo);
            school.setSchoolName(getCellString(row.getCell(1)));
            school.setSchoolCode(getCellString(row.getCell(2)));
            school.setAuthority(getCellString(row.getCell(3)));
            school.setLocation(getCellString(row.getCell(4)));
            school.setEducationLevel(getCellString(row.getCell(5)));
            school.setRemark(getCellString(row.getCell(6)));

            list.add(school);
        }
        return list;
    }

    // ---------- 辅助方法 ----------

    private static boolean containsHeader(Row row) {
        for (Cell cell : row) {
            if (HEADER_KEYWORD.equals(getCellString(cell))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统一把单元格转成 String；
     * 数值类型去掉小数尾（如 4111010001.0 → "4111010001"，1.0 → "1"）
     */
    private static String getCellString(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val)) {
                    return String.valueOf((long) val);
                }
                return String.valueOf(val);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf((long) cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return null;
        }
    }

    private static Integer parseInteger(String s) {
        if (s == null) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
