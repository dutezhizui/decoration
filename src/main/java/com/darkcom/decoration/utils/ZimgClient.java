package com.darkcom.decoration.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

public class ZimgClient {
    public static String zimgUrl = "http://123.57.241.67:4869/";
    public static String zimgShareUrl = "http://123.57.241.67:4869/";
    public static String tmpPath = "";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        // 从文件上传图片
        ZimgResult ret0 = new ZimgClient()
                .uploadImg("/var/guttv/logs/tmp/gophercolor.png");
        System.out.println(ret0.isRet() + "\r\n" + ret0.getImageUrl());
        // 从URL上传图片
        ZimgResult ret = new ZimgClient()
                .uploadImgFromUrl("http://creatives.ftchinese.com/ads/beijing/201509/20150914_cartier_33660.gif");
        System.out.println(ret.isRet() + "\r\n" + ret.getImageUrl());
        // Send("http://192.168.1.221:4869/upload",
        // "c:/4c422e03jw1ejoqm5ghm0j20nl0fb76x.jpg", "jpg");
    }

    /**
     * 从页面提交图片，上传到zimg
     *
     * @param request
     * @param fileTag
     * @return
     */
    public String uploadImgToZimg(HttpServletRequest request, String fileTag) {
        String imgUrl = "";
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = mhs.getFiles(fileTag);
        if (files != null && files.size() > 0) {
            // 上传到图片服务器
            MultipartFile f = files.get(0);
            if (f.getSize() == 0)
                return "";
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

            ZimgResult ret = this.uploadImg(tmpFileName);
            logger.debug(new Gson().toJson(ret));
            if (ret != null && ret.isRet())
                imgUrl = ret.getImageUrl();

            // 删除文件
            if (tmp != null) {
                tmp.setWritable(true);
                // try {
                // new FileOutputStream(tmp).close();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                System.gc();// java'bug，must be gc before delete
                tmp.delete();
            }
        }

        return imgUrl;
    }

    /**
     * 指定文件名，上传到zimg
     *
     * @param fileName
     * @return
     */
    public ZimgResult uploadImg(String fileName) {
        String ext = "jpeg";
        int inx = fileName.lastIndexOf(".");
        if (inx > 0)
            ext = fileName.substring(inx + 1);
        String resp = this.Send(ZimgClient.zimgUrl + "upload", fileName, ext);
        return new Gson().fromJson(resp, ZimgResult.class);
    }

    public ZimgResult uploadImgFromUrl(String url) {
        String resp = this.SendFromUrl(url);
        return new Gson().fromJson(resp, ZimgResult.class);
    }

    /**
     * 从指定的URL下载图片并上传到zimg服务器
     *
     * @param zimgUrl
     * @param imgUrl
     * @return
     */
    protected String SendFromUrl(String imgUrl) {

        // 设置文件类型默认值
        String ext = "jpeg";
        String respXML = "";
        try {
            // 获得connection对象
            logger.debug("zimg server url:" + ZimgClient.zimgUrl);
            URL zimgUL = new URL(ZimgClient.zimgUrl);
            URLConnection zimgConnection = zimgUL.openConnection();
            zimgConnection.setReadTimeout(50000);
            zimgConnection.setConnectTimeout(25000);
            HttpURLConnection zimgUC = (HttpURLConnection) zimgConnection;

            // 设置HTTP协议的消息头
            logger.debug("zimg set header");
            zimgUC.setRequestMethod("POST");
            zimgUC.setRequestProperty("Connection", "Keep-Alive");
            zimgUC.setRequestProperty("Cache-Control", "no-cache");
            zimgUC.setRequestProperty("Content-Type", ext.toLowerCase());// "jpeg");//
            zimgUC.setRequestProperty("COOKIE", "william");
            zimgUC.setDoOutput(true);
            zimgUC.setDoInput(true);

            logger.debug("zimg connect server.");
            // 与建立服务器连接
            zimgUC.connect();
            // 设置传输模式为二进制
            logger.debug("zimg upload image in binary.");
            OutputStream om = zimgUC.getOutputStream();
            // 循环读取图片，发送到zimg服务器

            ext = this.writeImage(imgUrl, om);
            logger.debug("image type=" + ext);
            // byte[] buf = new byte[8192];
            // while (true) {
            // int len = in.read(buf);
            // if (len <= 0)
            // break;
            // om.write(buf, 0, len);
            // }

            // 到开输入（返回信息）流
            InputStreamReader im = new InputStreamReader(
                    zimgUC.getInputStream(), "UTF-8");
            // 循环读取，知道结束，获取返回信息
            logger.debug("zimg get response text.");
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
            logger.debug("zimg response:" + respXML);
            // 关闭上下行
            im.close();
            zimgUC.disconnect();
        } catch (Exception e) {
            logger.debug("zimg exception :" + e.getMessage());
            e.printStackTrace();
        }

        return respXML;

    }

    /**
     * 返货图片类型
     *
     * @param data
     * @return
     */
    protected String getImageType(byte[] data) {
        String type = null;
        // Png test:
        if (data[1] == 'P' && data[2] == 'N' && data[3] == 'G') {
            type = "PNG";
            return type;
        }
        // Gif test:
        if (data[0] == 'G' && data[1] == 'I' && data[2] == 'F') {
            type = "GIF";
            return type;
        }
        // JPG test:
        if (data[6] == 'J' && data[7] == 'F' && data[8] == 'I'
                && data[9] == 'F') {
            type = "JPG";
            return type;
        }
        return type;
    }

    /**
     * 获取URL的输入流
     *
     * @param imgUrl
     * @return
     */
    private String writeImage(String imgUrl, OutputStream om) {
        long totalBytes = 0;
        String imgType = "jpeg";
        try {
            // 获得connection对象
            URL imgUL = new URL(imgUrl);
            URLConnection imgConnection = imgUL.openConnection();
            imgConnection.setReadTimeout(50000);
            imgConnection.setConnectTimeout(25000);
            HttpURLConnection imgUC = (HttpURLConnection) imgConnection;

            // 设置HTTP协议的消息头
            logger.debug("set header");
            imgUC.setRequestMethod("GET");
            imgUC.setRequestProperty("Connection", "Keep-Alive");
            imgUC.setRequestProperty("Cache-Control", "no-cache");
            // imgUC.setRequestProperty("Content-Type", ext.toLowerCase());//
            // "jpeg");//
            imgUC.setRequestProperty("COOKIE", "GostLiu程序员老刘");
            imgUC.setDoOutput(true);
            imgUC.setDoInput(true);
            InputStream in = imgUC.getInputStream();

            byte[] buf = new byte[8192];
            boolean GotType = false;
            while (true) {
                int len = in.read(buf);
                if (len <= 0)
                    break;
                if (!GotType) {
                    imgType = this.getImageType(buf);
                    GotType = true;
                }
                totalBytes += len;
                om.write(buf, 0, len);
            }
            in.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
        if (totalBytes > 0)
            return imgType;
        else
            return "";
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
            logger.debug("zimg server url:" + url);
            URL ul = new URL(url);
            URLConnection connection = ul.openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(25000);
            HttpURLConnection uc = (HttpURLConnection) connection;

            // 设置HTTP协议的消息头
            logger.debug("zimg set header");
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Connection", "Keep-Alive");
            uc.setRequestProperty("Cache-Control", "no-cache");
            uc.setRequestProperty("Content-Type", ext.toLowerCase());// "jpeg");//
            uc.setRequestProperty("COOKIE", "william");
            uc.setDoOutput(true);
            uc.setDoInput(true);

            logger.debug("zimg connect server.");
            // 与建立服务器连接
            uc.connect();
            // 设置传输模式为二进制
            logger.debug("zimg upload image in binary.");
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
            logger.debug("zimg get response text.");
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
            logger.debug("zimg response:" + respXML);
            // 关闭上下行
            im.close();
            uc.disconnect();
        } catch (Exception e) {
            logger.debug("zimg exception :" + e.getMessage());
            e.printStackTrace();
        }

        return respXML;

    }

    /********** zimg 服务器返回消息定义 ***********************************/
    public class ZimgError {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    public class ZimgInfo {
        private String md5;

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        private int size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public class ZimgResult {
        private boolean ret;
        private ZimgInfo info;
        private ZimgError error;

        public ZimgError getError() {
            return error;
        }

        public void setError(ZimgError error) {
            this.error = error;
        }

        public String getImageUrl() {
            if (this.isRet()) {
                return ZimgClient.zimgShareUrl + this.info.getMd5();
            }
            return "";
        }

        public boolean isRet() {
            return ret;
        }

        public void setRet(boolean ret) {
            this.ret = ret;
        }

        public ZimgInfo getInfo() {
            return info;
        }

        public void setInfo(ZimgInfo info) {
            this.info = info;
        }

    }
}
