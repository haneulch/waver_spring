package com.mybury.waver.security;

import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JwtTokenParserTest {

    @Autowired
    private JwtTokenParser jwtTokenParser;

    @Test
    void generateToken() {
        Long username = 1212L;
        String testToken = jwtTokenParser.generateToken(username);
        log.info(testToken);

        Long result = jwtTokenParser.getUserIdFromToken(testToken);
        assertThat(result).isEqualTo(username);
    }
}