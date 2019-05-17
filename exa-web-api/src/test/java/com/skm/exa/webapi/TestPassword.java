package com.skm.exa.webapi;

import com.aliyun.oss.OSSClient;
import com.skm.exa.common.object.AliyunOSSClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
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


    //测试
    @Test
    public void test1() {
        //上传文件
        String file="C:\\Users\\Chen Hao\\Pictures\\Camera Roll\\02_elephant_a_resized.jpg";
        File filess=new File(file);
        Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(filess);
        for(String s:map.keySet()){
            System.out.println(map.get(s));
        }

    }




    @Test
    public void delete(){
        Long s = 1000L;
        System.out.println(s);
    }





}
