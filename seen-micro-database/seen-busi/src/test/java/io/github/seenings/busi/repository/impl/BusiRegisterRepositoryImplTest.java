package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.model.BusiRegister;
import io.github.seenings.coin.enumeration.BusiType;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TruncateIdentityStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

//@JooqTest
//@WebMvcTest
@SpringBootTest
@AutoConfigureRestDocs
class BusiRegisterRepositoryImplTest {
    @Autowired
    private BusiRegisterRepositoryImpl busiRegisterController;

    /**
     * 数据库操作上下文
     */
    @Autowired
    private DSLContext dslContext;

    @Test
    void insert() {
        BusiRegister busiRegister;
        busiRegister = new BusiRegister().setBusiId((long) BusiType.SIGN_UP.getIndex())
                .setUserId(1000000L)
                .setRegisterTime(LocalDateTime.now())
                .setCreateTime(LocalDateTime.now());
        Assertions.assertEquals(1, (long) busiRegisterController.insert(busiRegister));
        //清理
        TruncateIdentityStep<Record> truncateIdentityStep =
                dslContext.truncate(BusiRegisterRepositoryImpl.Meta.TABLE_NAME);
        Assertions.assertNotNull(truncateIdentityStep);
    }

    @Test
    void select() {
        busiRegisterController.insert(new BusiRegister().setBusiId((long) BusiType.SIGN_UP.getIndex())
                .setUserId(1000000L)
                .setRegisterTime(LocalDateTime.now())
                .setCreateTime(LocalDateTime.now()));
        Assertions.assertFalse(busiRegisterController.select().isEmpty());
    }
}