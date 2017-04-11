package com.yzz.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptionUtil {

	/**
	 * SHA1加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeBySha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 对字符进行排序
	 * 
	 * @param str
	 * @return
	 */
	public static String sortingStr(String str) {
		char[] arr = str.toCharArray();
		Arrays.sort(arr);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			buf.append(arr[i]);
		}
		return buf.toString();
	}

	/**
	 * Base64加密
	 * 
	 * @param str
	 * @return
	 */
	/*public static String encodeByBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}*/

	/**
	 * Base64解密
	 * 
	 * @param str
	 * @return
	 */
	/*public static String decodeByBase64(String str) {
		byte[] b = null;
		String result = null;
		if (str != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(str);
				result = new String(b, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}*/

	/**
	 * MD5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeByMD5(String str) {
		String resutl = "";
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");// 获取MD5算法对象
			byte[] digest = instance.digest(str.getBytes());// 对字符串加密,返回字节数组
			StringBuffer strBuffer = new StringBuffer();
			for (byte b : digest) {
				int i = b & 0xff;
				String hexString = Integer.toHexString(i);// 将整数转为16进制
				if (hexString.length() < 2) {
					hexString = "0" + hexString;// 如果是1位的话,补0
				}
				strBuffer.append(hexString);
			}
			resutl = strBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return resutl;
	}

	public static String encodePassword(String password) {

		return encodeBySha1(encodeByMD5(password));
	}

}