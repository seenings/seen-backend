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
import java.time.ZoneOffset;
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
        public static Table<Record> TABLE_NAME = table("busi_register");
        public static Field<Long> ID = field("id", SQLDataType.BIGINT.identity(true));
        public static Field<Long> USER_ID = field("user_id", Long.class);
        public static Field<Long> BUSI_ID = field("busi_id", Long.class);
        public static Field<Timestamp> REGISTER_TIME = field("register_time", Timestamp.class);
        public static Field<Timestamp> CREATE_TIME = field("create_time", Timestamp.class);
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
                .fetch()
                .stream()
                .map(record -> new BusiRegister()
                        .setId(record.get(ID))
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
     * @param busiRegister 用户注册信息
     * @return 用户注册ID
     */
    @Override
    public Long insert(BusiRegister busiRegister) {
        return dslContext.insertInto(TABLE_NAME)
                .set(USER_ID, busiRegister.getUserId())
                .set(BUSI_ID, busiRegister.getBusiId())
                .set(REGISTER_TIME, Timestamp.valueOf(busiRegister.getRegisterTime()))
                .set(CREATE_TIME, Timestamp.valueOf(LocalDateTime.now()))
                .returningResult(ID).fetch().map(n -> n.getValue(ID)).getFirst();
    }

}
