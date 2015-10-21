package com.tools.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author LeeJEric
 * 
 */
public class MD5Util {

	/**
	 * 三重MD5带移位加密
	 * 
	 * @param password
	 * @return
	 */
	public static String md5For3Encrypt(String password) {
		return encrypt(StringUtil.rotateRight(
				encrypt(StringUtil.rotateLeft(encrypt(password), 16)), 16));
	}

	/**
	 * MD5加密明文
	 * 
	 * @param str
	 *            明文
	 * @return 密文
	 */
	public static String encrypt(String str) {
		StringBuffer buf = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;

			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buf.toString().toLowerCase();
	}

}
