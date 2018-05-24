package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.utils.ZimgClient;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * tupianshangchuan
 *
 * @author yjy
 */
@RestController
@RequestMapping("/upload/v1/")
public class UploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("uploadHeadImage")
    public Result uploadImage(MultipartFile multipartFile, HttpServletRequest request) {
        String imgUrl = null;
        // 上传到图片服务器
        MultipartFile f = multipartFile;

        String tmpFileName = ZimgClient.tmpPath + "/"
                + f.getOriginalFilename();
        // mkdir("./tmp")
        File tmp = new File(ZimgClient.tmpPath);
        tmp.mkdir();
        tmp = new File(tmpFileName);
        try {
            // tmp.delete();
            f.transferTo(tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ZimgClient.ZimgResult ret = this.uploadImg(tmpFileName);
        LOGGER.debug(new Gson().toJson(ret));
        if (ret != null && ret.isRet())
            imgUrl = ret.getImageUrl();


        return Result.succeed();
    }

    /**
     * 指定文件名，上传到zimg
     *
     * @param fileName
     * @return
     */
    public ZimgClient.ZimgResult uploadImg(String fileName) {
        String ext = "jpeg";
        int inx = fileName.lastIndexOf(".");
        if (inx > 0)
            ext = fileName.substring(inx + 1);
        String resp = this.Send(ZimgClient.zimgUrl + "upload", fileName, ext);
        return new Gson().fromJson(resp, ZimgClient.ZimgResult.class);
    }

    /**
     * 将图片文件上传到zimg服务器
     *
     * @param url
     * @param fileName
     * @param ext
     * @return
     */
    protected String Send(String url, String fileName, String ext) {

        if (ext.toLowerCase().compareTo("jpg") == 0)
            ext = "jpeg";
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
            LOGGER.debug("zimg set header");
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Connection", "Keep-Alive");
            uc.setRequestProperty("Cache-Control", "no-cache");
            uc.setRequestProperty("Content-Type", ext.toLowerCase());// "jpeg");//
            uc.setRequestProperty("COOKIE", "william");
            uc.setDoOutput(true);
            uc.setDoInput(true);

            LOGGER.debug("zimg connect server.");
            // 与建立服务器连接
            uc.connect();
            // 设置传输模式为二进制
            LOGGER.debug("zimg upload image in binary.");
            OutputStream om = uc.getOutputStream();
            // 循环读取图片，发送到zimg服务器
            FileInputStream in = new FileInputStream(fileName);
            byte[] buf = new byte[8192];
            while (true) {
                int len = in.read(buf);
                if (len <= 0)
                    break;
                om.write(buf, 0, len);
            }

            // 到开输入（返回信息）流
            InputStreamReader im = new InputStreamReader(uc.getInputStream(),
                    "UTF-8");
            // 循环读取，知道结束，获取返回信息
            LOGGER.debug("zimg get response text.");
            char[] bb = new char[8192];
            while (true) {
                int length = im.read(bb);
                if (length == -1)
                    break;
                char[] bc = new char[length];
                for (int i = 0; i < length; i++)
                    bc[i] = bb[i];
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
