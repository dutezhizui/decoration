package com.darkcom.decoration.wechat.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 
 * 类名:com.beauty.wechat.WechatCoreController
 * 描述:微信核心处理类
 * 编写者:陈骑元
 * 创建时间:2017年4月29日 上午9:54:35
 * 修改说明:
 */
@Controller
@RequestMapping(value = "/wechat/wechatCode")
public class WechatCoreController {
	
	@Value("${wechat.token}")
	private String token;
	/**
	 * 
	 * 简要说明：微信登陆调用验证接口
	 * 编写者：陈骑元
	 * 创建时间：2017年4月29日 上午10:10:07
	 * @param 说明
	 * @return 说明
	 */
	@RequestMapping(value="/subscribe", method = RequestMethod.GET)
	public void subscribeGet(@RequestParam(value = "signature") String signature,
			@RequestParam(value = "timestamp") String timestamp,
			@RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "echostr") String echostr,HttpServletRequest request,HttpServletResponse response) {
		//获取token
		//String token= IMSPropertiesUtil.getString("wechat.token");
		//微信验证登陆
		if(checkSignature(token,signature,
				timestamp, nonce)){
			
			writeRaw(response,echostr);
			
		}
		
	}
	public static void writeRaw(HttpServletResponse response, String outString) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		try {
			response.getWriter().write(outString == null ? "" : outString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean checkSignature(String token, String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

}
