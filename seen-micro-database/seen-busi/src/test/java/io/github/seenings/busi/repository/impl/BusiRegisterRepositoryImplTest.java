package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.model.BusiRegister;
import io.github.seenings.coin.enumeration.BusiType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

//@JooqTest
//@WebMvcTest
@SpringBootTest
//@AllArgsConstructor
@AutoConfigureRestDocs
class BusiRegisterRepositoryImplTest {
    @Autowired
    private BusiRegisterRepositoryImpl busiRegisterController;

    @Test
    void insert() {
        BusiRegister busiRegister;
        busiRegister = new BusiRegister().setBusiId((long) BusiType.SIGN_UP.getIndex())
                .setUserId(1000000L)
                .setRegisterTime(LocalDateTime.now())
                .setCreateTime(LocalDateTime.now());
        Assertions.assertTrue(busiRegisterController.insert(busiRegister) >= 1);
    }

    @Test
    void select() {
        Assertions.assertTrue(busiRegisterController.select().size() > 1);
    }
}