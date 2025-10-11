package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.model.Busi;
import io.github.seenings.coin.enumeration.BusiType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.TruncateIdentityStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
@AutoConfigureRestDocs
class BusiRepositoryImplTest {

    @Autowired
    private BusiRepositoryImpl busiRepository;
    /**
     * 数据库操作上下文
     */
    @Autowired
    private DSLContext dslContext;

    @Test
    void insert() {
        var modal = new Busi()
                .setBusiTime(LocalDateTime.now())
                .setBusiTypeId(BusiType.SIGN_UP.getIndex());
        long result = busiRepository.insert(modal);
        log.info("{}", result);
        Assertions.assertEquals(1, result);
        //清理
        TruncateIdentityStep<Record> truncateIdentityStep =
                dslContext.truncate(BusiRegisterRepositoryImpl.Meta.TABLE_NAME);
        Assertions.assertNotNull(truncateIdentityStep);
    }
}