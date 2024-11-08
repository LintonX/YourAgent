package com.youragent.youragent;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DAOTests {

    @Test
    public void test() {
        String test = "hi";

        assertThat(test).isEqualTo("hid");
    }
}
