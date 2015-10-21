package com.tools.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 深度克隆实现类
 * 
 * @author Lee.J.Eric
 * 
 */
public class DepthClone {

	/**
	 * 深度克隆
	 * 
	 * @param src
	 *            样本(实现<code>Serializable</code>接口的类)
	 * @return 克隆体(Object)
	 */
	public static Object deepClone(Object src) {
		Object o = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(src);// 源对象

			ByteArrayInputStream in = new ByteArrayInputStream(
					out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			o = oi.readObject();// 目标对象
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return o;
	}
}
