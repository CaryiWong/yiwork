package com.tools.utils;

import java.lang.reflect.Field;

/**
 * 反射交换
 * 
 * @author LeeJEric
 * 
 */
public class ReflectSwap {

	/**
	 * 交换两个Integer
	 * 
	 * @param a
	 * @param b
	 */
	public static void swap(Integer a, Integer b) {
		Class<?> type = a.getClass();
		Integer c = new Integer(a);
		try {
			Field value = type.getDeclaredField("value");
			value.setAccessible(true);
			value.set(a, b);
			value.set(b, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 交换两个Double
	 * 
	 * @param a
	 * @param b
	 */
	public static void swap(Double a, Double b) {
		Class<?> type = a.getClass();
		Double c = new Double(a);
		try {
			Field value = type.getDeclaredField("value");
			value.setAccessible(true);
			value.set(a, b);
			value.set(b, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 交换两个Float
	 * 
	 * @param a
	 * @param b
	 */
	public static void swap(Float a, Float b) {
		Class<?> type = a.getClass();
		Float c = new Float(a);
		try {
			Field value = type.getDeclaredField("value");
			value.setAccessible(true);
			value.set(a, b);
			value.set(b, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 交换两个Long
	 * 
	 * @param a
	 * @param b
	 */
	public static void swap(Long a, Long b) {
		Class<?> type = a.getClass();
		Long c = new Long(a);
		try {
			Field value = type.getDeclaredField("value");
			value.setAccessible(true);
			value.set(a, b);
			value.set(b, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
