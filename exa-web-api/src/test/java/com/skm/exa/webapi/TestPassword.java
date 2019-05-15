package com.skm.exa.webapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestPassword {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("wangwu"));
        String i = "hk34_.@";
        String s = "^[\\w_.@]{6,20}$";

        boolean b = i.matches(s);
        System.out.println(b);

        String content = "I am noob " +
                "from runoob.com.";

        String pattern = ".*runoob.*";

        boolean isMatch = Pattern.matches(pattern, content);
        System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);

    }
}
