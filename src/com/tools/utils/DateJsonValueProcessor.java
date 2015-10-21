package com.tools.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 转化JSON对象时间格式处理
 * 
 * @author Lee.J.Eric
 * 
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	public static final String Default_DATE_PATTERN = "yyyy-MM-dd";
    private DateFormat dateFormat;
    
    public DateJsonValueProcessor(String datePattern) {
        try {
            dateFormat = new SimpleDateFormat(datePattern);
        } catch (Exception e) {
            dateFormat = new SimpleDateFormat(Default_DATE_PATTERN); 
        }
    }

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		// TODO Auto-generated method stub
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		// TODO Auto-generated method stub
		return process(value);
	}
	
	 private Object process(Object value) {
	        if (value == null) {
	            return "";
	        } else {
	            return dateFormat.format((Timestamp) value);
	        }
	    }

}
