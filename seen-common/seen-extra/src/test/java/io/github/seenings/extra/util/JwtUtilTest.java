package io.github.seenings.extra.util;

import org.junit.jupiter.api.Test;


/**
 * JwtUtilsTest
 *
 * @author chixuehui
 * @since 2023-12-03
 */
class JwtUtilTest {

    @Test
    void createToken() {

        String token = JwtUtil.createToken("10000000", 1000 * 3600 * 24 * 7L);
        System.out.println(token);
    }
}
