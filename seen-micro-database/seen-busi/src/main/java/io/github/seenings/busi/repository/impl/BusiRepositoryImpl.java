package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.controller.BusiController;
import io.github.seenings.busi.model.Busi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.ZoneOffset;

import static io.github.seenings.busi.repository.impl.BusiRepositoryImpl.Meta.*;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * 业务
 */
@Slf4j
@RestController
@AllArgsConstructor
public class BusiRepositoryImpl implements BusiController {
    /**
     * 数据表元数据
     */
    public static class Meta {
        public static Table<Record> TABLE_NAME = table("busi");
        public static Field<Long> BUSI_ID = field("busi_id", SQLDataType.BIGINT.identity(true));
        public static Field<Integer> BUSI_TYPE_ID = field("busi_type_id", Integer.class);
        public static Field<Timestamp> BUSI_TIME = field("busi_time", Timestamp.class);
    }

    /**
     * 数据库操作上下文
     */
    private DSLContext dslContext;

    /**
     * 增加业务信息
     *
     * @param modal 业务信息
     * @return 业务ID
     */
    @Override
    public Long insert(Busi modal) {
        return dslContext.insertInto(TABLE_NAME)
                .set(BUSI_TYPE_ID, modal.getBusiTypeId())
                .set(BUSI_TIME, Timestamp.valueOf(modal.getBusiTime()))
                .returningResult(BUSI_ID)
                .fetchOptional().map(n -> n.getValue(BUSI_ID)).orElse(null);
    }
}
