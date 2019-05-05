package com.skm.exa.webapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestPassword {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("wangwu"));
    }
}
