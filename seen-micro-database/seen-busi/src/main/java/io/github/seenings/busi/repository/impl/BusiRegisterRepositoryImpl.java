package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.controller.BusiRegisterController;
import io.github.seenings.busi.model.BusiRegister;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.SQLDataType;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static io.github.seenings.busi.repository.impl.BusiRegisterRepositoryImpl.Meta.*;

/**
 * 注册
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BusiRegisterRepositoryImpl implements BusiRegisterController {
    /**
     * 数据表元数据
     */
    public static class Meta {
        public static Table<Record> TABLE_NAME = table("BUSI_REGISTER");
        public static Field<Long> REGISTER_ID = field("REGISTER_ID", SQLDataType.BIGINT.identity(true));
        public static Field<Long> USER_ID = field("USER_ID", Long.class);
        public static Field<Long> BUSI_ID = field("BUSI_ID", Long.class);
        public static Field<Timestamp> REGISTER_TIME = field("REGISTER_TIME", Timestamp.class);
        public static Field<Timestamp> CREATE_TIME = field("CREATE_TIME", Timestamp.class);
    }

    /**
     * 数据库操作上下文
     */
    private DSLContext dslContext;

    /**
     * 查询所有
     *
     * @return 查询所有
     */
    @Override
    public List<BusiRegister> select() {
        return dslContext.selectFrom(TABLE_NAME)
                .stream().map(record -> new BusiRegister()
                        .setRegisterId(record.get(REGISTER_ID))
                        .setUserId(record.get(USER_ID))
                        .setBusiId(record.get(BUSI_ID))
                        .setRegisterTime(record.get(REGISTER_TIME).toLocalDateTime())
                        .setCreateTime(record.get(CREATE_TIME).toLocalDateTime())
                )
                .collect(Collectors.toList());
    }

    /**
     * 增加用户注册
     *
     * @param entity 用户注册信息
     * @return 用户注册ID
     */
    @Override
    public Long insert(BusiRegister entity) {
        return dslContext.insertInto(TABLE_NAME)
                .set(USER_ID, entity.getUserId())
                .set(BUSI_ID, entity.getBusiId())
                .set(REGISTER_TIME, Timestamp.valueOf(entity.getRegisterTime()))
                .set(CREATE_TIME, Timestamp.valueOf(LocalDateTime.now()))
                .returningResult(REGISTER_ID)
                .fetchOptional().map(n -> n.getValue(REGISTER_ID)).orElse(null);
    }

}
