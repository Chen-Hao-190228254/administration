package com.skm.exa.webapi;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.skm.exa.common.object.FileUpdateObject;
import com.skm.exa.common.utils.AliyunOSSClientUtil;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.service.biz.EnterpriseService;
import com.skm.exa.service.biz.impl.EnterpriseServiceImpl;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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




//    //测试
//    @Test
//    public void test1() {
//        //上传文件
//        String file="C:\\Users\\Chen Hao\\Pictures\\Camera Roll\\02_elephant_a_resized.jpg";
//        File filess=new File(file);
//        Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(filess);
//        for(String s:map.keySet()){
//            System.out.println(map.get(s));
//        }
//
//    }
//
//
//    @Autowired
//    EnterpriseService enterpriseService;
//


//    @Test
//    public void test2(){
//        String file="C:\\Users\\Chen Hao\\Pictures\\Camera Roll\\02_elephant_a_resized.jpg";
//        File files=new File(file);
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(files);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            MultipartFile multipartFile = new MockMultipartFile(files.getName(), files.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//            enterpriseService.addImage(1L,multipartFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }








}
