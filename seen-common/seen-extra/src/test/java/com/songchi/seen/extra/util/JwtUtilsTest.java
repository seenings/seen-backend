package com.songchi.seen.extra.util;

import org.junit.jupiter.api.Test;


/**
 * JwtUtilsTest
 *
 * @author chixuehui
 * @since 2023-12-03
 */
class JwtUtilsTest {

    @Test
    void createToken() {

        String token = JwtUtils.createToken("10000000", 1000 * 3600 * 24 * 7L);
        System.out.println(token);
    }
}