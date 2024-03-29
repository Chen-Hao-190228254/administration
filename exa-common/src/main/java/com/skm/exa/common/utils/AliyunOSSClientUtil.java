package com.skm.exa.common.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;

public class AliyunOSSClientUtil {

    //阿里云API的外网域名
    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
    //阿里云API的密钥Access Key ID
    public static final String ACCESS_KEY_ID = "LTAIb7tQ7p7SwbwU";
    //阿里云API的密钥Access Key Secret
    public static final String ACCESS_KEY_SECRET = "DpenmJOdFQy2rIW0QVZlsyLtf8PqDJ";
    //阿里云API的bucket名称
    public static final String BACKET_NAME = "cd-skm";
    //阿里云API的文件夹名称
    public static final String FOLDER="admin/";



    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static  OSSClient getOSSClient(){
        return new OSSClient(ENDPOINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

//    /**
//     * 创建存储空间
//     * @param ossClient      OSS连接
//     * @param bucketName 存储空间
//     * @return
//     */
//    public  static String createBucketName(OSSClient ossClient,String bucketName){
//        //存储空间
//        final String bucketNames=bucketName;
//        if(!ossClient.doesBucketExist(bucketName)){
//            //创建存储空间
//            Bucket bucket=ossClient.createBucket(bucketName);
//            System.out.println("创建存储空间成功");
//            return bucket.getName();
//        }
//        return bucketNames;
//    }
//
//    /**
//     * 删除存储空间buckName
//     * @param ossClient  oss对象
//     * @param bucketName  存储空间
//     */
//    public static  void deleteBucket(OSSClient ossClient, String bucketName){
//        ossClient.deleteBucket(bucketName);
//        System.out.println("删除" + bucketName + "Bucket成功");
//    }

//    /**
//     * 创建模拟文件夹
//     * @param ossClient oss连接
//     * @param bucketName 存储空间
//     * @param folder   模拟文件夹名如"qj_nanjing/"
//     * @return  文件夹名
//     */
//    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
//        //文件夹名
//        final String keySuffixWithSlash =folder;
//        //判断文件夹是否存在，不存在则创建
//        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
//            //创建文件夹
//            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
//            System.out.println("创建文件夹成功");
//            //得到文件夹名
//            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
//            String fileDir=object.getKey();
//            return fileDir;
//        }
//        return keySuffixWithSlash;
//    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param filename Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(String filename){
        OSSClient ossClient = getOSSClient();
        ossClient.deleteObject(BACKET_NAME, FOLDER + filename);
    }

    /**
     * 文件批量删除
     * @param filenames 需要删除的文件名称   Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static Boolean deleteFile(List<String> filenames){
        int index = 0;
        int deleteSize = 0;
        int forSize = filenames.size()/1000+1;
        for (int i = 0; i < forSize; i++){
            List<String> f = new ArrayList<>();
            for(int j = index;j < filenames.size(); j++){
                f.add(filenames.get(j));
                index++;
                if(j == filenames.size() || j == 1000){
                    OSSClient ossClient = getOSSClient();
                    deleteSize += ossClient.deleteObjects(new DeleteObjectsRequest(BACKET_NAME).withKeys(f)).getDeletedObjects().size();
                    ossClient.shutdown();
                    f = new ArrayList<>();
                }
            }
        }
        return deleteSize == filenames.size()? true:false;
    }

    /**
     * 上传图片至OSS 传出File类型
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @return String 返回的唯一MD5数字签名
     * */
    public static Map<String, String> uploadObject2OSS(File file) {
        OSSClient ossClient = getOSSClient();
        try {
            //以输入流的形式上传文件
            InputStream is = new FileInputStream(file);
            //上传后文件名称，随机生成
            String fileName = generateRandomNames(file.getName());
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + fileName, is, metadata);
            //解析结果
            String md5key = putResult.getETag();




            // 设置URL过期时间为100年 3600l* 1000*24*365*10*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(BACKET_NAME, FOLDER+fileName, expiration);


            Long size = fileSize/1024/1024;
            Map<String,String> map = new HashMap<>();
            map.put("name",fileName);
            map.put("md5key",md5key);
            map.put("filesize",size.toString());
            map.put("url",url.toString());
            return map;

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("上传阿里云OSS服务器异常." + e.getMessage());
        }
        Map<String,String> map = new HashMap<>();
        map.put("error","上传失败");
        return map;
    }








    /**
     * 上传图片至OSS 传入MultipartFile类型
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @return String 返回的唯一MD5数字签名
     * */
    public static Map<String, String> uploadObject2OSS(MultipartFile file) {
        OSSClient ossClient = getOSSClient();
        try {
            //以输入流的形式上传文件
            InputStream is = file.getInputStream();
            //上传后文件名称，随机生成
            String fileName = generateRandomNames(file.getOriginalFilename());
            //文件大小
            Long fileSize = file.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + fileName, is, metadata);
            //解析结果
            String md5key = putResult.getETag();




            // 设置URL过期时间为100年 3600l* 1000*24*365*10*10
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(BACKET_NAME, FOLDER+fileName, expiration);


            Map<String,String> map = new HashMap<>();
            map.put("name",fileName);
            map.put("md5key",md5key);
            map.put("filesize",fileSize.toString());
            map.put("url",url.toString());
            return map;

        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println("上传阿里云OSS服务器异常." + e.getMessage());
        }
        Map<String,String> map = new HashMap<>();
        map.put("error","上传失败");
        return map;
    }




    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }





    /**
     * 原文件名传过来后生成随机名称
     * @param     filename
     * @return
     */
    public static String generateRandomNames(String filename){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String r = str + rannum;
        String name = r+filename.substring(filename.lastIndexOf("."));
        return name;
    }


    /**
     * 获取所有文件
     * @return
     */
    public static List<String> GetFileAllContent() {
        OSSClient ossClient = getOSSClient();

        final int maxKeys = 200;
        String nextMarker = null;
        ObjectListing objectListing;
        List<String> list = new ArrayList<>();
        do {
            objectListing = ossClient.listObjects(new ListObjectsRequest(BACKET_NAME).withPrefix(FOLDER).withMarker(nextMarker).withMaxKeys(maxKeys));
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                list.add(s.getKey());
            }
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());
        ossClient.shutdown();
        return list;
    }

}


