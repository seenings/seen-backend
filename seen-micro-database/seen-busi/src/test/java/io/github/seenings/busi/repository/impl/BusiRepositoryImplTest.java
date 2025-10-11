package io.github.seenings.busi.repository.impl;

import io.github.seenings.busi.model.Busi;
import io.github.seenings.coin.enumeration.BusiType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureRestDocs
class BusiRepositoryImplTest {

    @Autowired
    private BusiRepositoryImpl busiRepository;

    @Test
    void insert() {
        var modal = new Busi()
                .setBusiTime(LocalDateTime.now())
                .setBusiTypeId(BusiType.SIGN_UP.getIndex());
        Assertions.assertTrue(busiRepository.insert(modal) >= 1);
    }
}