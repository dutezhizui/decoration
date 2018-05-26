package com.darkcom.decoration.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.darkcom.decoration.common.Result;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * tupianshangchuan
 *
 * @author yjy
 */
@RestController
@RequestMapping("/upload/v1/")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
    @Value("${zimgServer}")
    private String zimServer;

    @PostMapping(value = "uploadHeadImage")
    public Result uploadImage(@RequestParam("headerImage") String base64Data, HttpServletRequest request) throws Exception {
        String imgurl = null;
        try {
            String dataPrix = "";
            String data = "";
            if(base64Data == null || "".equals(base64Data)){
                throw new Exception("上传失败，上传图片数据为空");
            }else{
                String [] d = base64Data.split("base64,");
                if(d != null && d.length == 2){
                    dataPrix = d[0];
                    data = d[1];
                }else{
                    throw new Exception("上传失败，数据不合法");
                }
            }
            String suffix = "";
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                suffix = "jpg";
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                suffix = "ico";
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                suffix = "gif";
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                suffix = "png";
            }else{
                throw new Exception("上传图片格式不合法");
            }
            //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs = Base64Utils.decodeFromString(data);

            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bs);

            String info = this.send(zimServer,byteArrayInputStream,suffix);
            JSONObject jsonObject = JSON.parseObject(info);
            imgurl = jsonObject.getJSONObject("info").getString("md5");
            } catch (Exception e) {
            LOGGER.error("图片文件上传失败,原因：" + e.getMessage());
        }
        return Result.succeed(zimServer + imgurl);
    }

    @PostMapping("uploadMultiImage")
    public Result uploadMultiImage(@RequestParam("fieldName") MultipartFile file[], HttpServletRequest request) {
        String imgurl = null;
        try {
            //调取ZimgUploadUtil工具类来上传图片
            MultipartFile multipartFile = ((MultipartHttpServletRequest) request).getFile("fieldName");
            String info = this.send(zimServer, (FileInputStream) multipartFile.getInputStream(),"png");
            JSONObject jsonObject = JSON.parseObject(info);
            imgurl = jsonObject.getJSONObject("info").getString("md5");
        } catch (Exception e) {
            LOGGER.error("图片文件上传失败,原因：" + e.getMessage());
        }
        return Result.succeed(zimServer + imgurl);
    }

    /**
     * 将图片文件上传到zimg服务器
     *
     * @param url
     * @param inputStream
     * @return
     */
    protected String send(String url, InputStream inputStream,String ext) {
        String respXML = "";
        try {
            // 获得connection对象
            LOGGER.debug("zimg server url:" + url);
            URL ul = new URL(url);
            URLConnection connection = ul.openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(25000);
            HttpURLConnection uc = (HttpURLConnection) connection;

            // 设置HTTP协议的消息头
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Connection", "Keep-Alive");
            uc.setRequestProperty("Cache-Control", "no-cache");
            uc.setRequestProperty("Content-Type",ext);
            uc.setRequestProperty("COOKIE", "william");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.connect();
            OutputStream om = uc.getOutputStream();
            // 循环读取图片，发送到zimg服务器
            InputStream in = inputStream;
            byte[] buf = new byte[8192];
            while (true) {
                int len = in.read(buf);
                if (len <= 0) {
                    break;
                }
                om.write(buf, 0, len);
            }

            // 到开输入（返回信息）流
            InputStreamReader im = new InputStreamReader(uc.getInputStream(),
                    "UTF-8");
            // 循环读取，直到结束，获取返回信息
            char[] bb = new char[8192];
            while (true) {
                int length = im.read(bb);
                if (length == -1) {
                    break;
                }

                char[] bc = new char[length];
                for (int i = 0; i < length; i++) {
                    bc[i] = bb[i];
                }

                respXML += new String(bc);
            }
            LOGGER.debug("zimg response:" + respXML);
            // 关闭上下行
            im.close();
            uc.disconnect();
        } catch (Exception e) {
            LOGGER.debug("zimg exception :" + e.getMessage());
            e.printStackTrace();
        }

        return respXML;

    }
}
