package com.tools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author Lee.J.Eric
 * 
 */
public class StringUtil {

	/**
	 * 字符串循环右移
	 * 
	 * @param str
	 *            源字符串
	 * @param n
	 *            移动位数(大于零的整数)
	 * @return 源串str右移n位后的字符串
	 */
	public static String rotateRight(String str, int n)
			throws ArrayIndexOutOfBoundsException {
		try {
			char[] chars = str.toCharArray();
			int length = chars.length;
			char[] temp = new char[length];
			int k = n % length;// 对串长度求模
			System.arraycopy(chars, length - k, temp, 0, k);
			System.arraycopy(chars, 0, temp, k, length - k);
			return new String(temp);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * 循环左移
	 * 
	 * @param str
	 *            源字符串
	 * @param n
	 *            移动位数(大于零的整数)
	 * @return 源串str左移n位后的字符串
	 */
	public static String rotateLeft(String str, int n)
			throws ArrayIndexOutOfBoundsException {
		try {
			char[] chars = str.toCharArray();
			int length = chars.length;
			char[] temp = new char[length];
			int k = n % length; // 对串长度求模
			System.arraycopy(chars, k, temp, 0, length - k);
			System.arraycopy(chars, 0, temp, length - k, k);
			return new String(temp);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return true:是；false:否
	 */
	public static Boolean isEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return true:是；false:否
	 */
	public static Boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern
					.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
