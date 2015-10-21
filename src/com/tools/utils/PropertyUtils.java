package com.tools.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertyUtils {
	private static Properties properties = new Properties();

	private PropertyUtils() {
	}

	public static void loadFile(String file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static String get(String sql) {
		return properties.getProperty(sql);
	}

	public static Set<Object> getKeys() {
		return properties.keySet();
	}

}
