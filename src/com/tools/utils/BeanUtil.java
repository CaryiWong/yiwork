package com.tools.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentSet;

/**
 * 
 * @author Lee.J.Eric
 *
 */
public class BeanUtil {
	
	/**
	 * 获取单个属性值对应的map
	 * @param bean
	 * @param key
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午1:05:04
	 */
	private static Map<String, Object> getFieldValueMap(Object bean,String key,Map<String, Object> valueMap,Set<String> keySet){
		try {
			if(bean!=null){
				if(key.indexOf(".")==-1){//没有下一级
					Class<?> clazz = bean.getClass();
					PropertyDescriptor pd = new PropertyDescriptor(key, clazz);
	            	Method readMethod = pd.getReadMethod();//获得读方法
	            	Object fieldVal = readMethod.invoke(bean);
	            	if("date".equalsIgnoreCase(pd.getPropertyType().getSimpleName())){
	            		try {
	            			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            			valueMap.put(key, format.format(fieldVal));
						} catch (Exception e) {
							e.printStackTrace();
						}
	            	}
	            	else
	            		valueMap.put(key, fieldVal);
				}else {//有下一级
					String key0 = key.substring(0, key.indexOf("."));
					String key1 = key.substring(key.indexOf(".")+1);
					Class<?> clazz = bean.getClass();
					PropertyDescriptor pd = new PropertyDescriptor(key0, clazz);
	            	Method readMethod = pd.getReadMethod();//获得读方法
	            	Object fieldVal = readMethod.invoke(bean);
					if(fieldVal instanceof List) {//集合形式
						Set<String> set = new HashSet<String>();
						for (String string : keySet) {
							if(string.startsWith(key0)&&string.length()>key0.length())
								set.add(string.substring(key0.length()+1));
						}
						valueMap.put(key0, getFieldValueMapForList((List)fieldVal,set));
					}else if (fieldVal instanceof Set) {
						Set<String> set = new HashSet<String>();
						for (String string : keySet) {
							if(string.startsWith(key0)&&string.length()>key0.length()&&string.indexOf(".")>0)
								set.add(string.substring(key0.length()+1));
						}
						valueMap.put(key0, getFieldValueMapForSet((Set)fieldVal,set));
					}else {
						Map<String, Object> map = (Map<String, Object>)valueMap.get(key0);
						if(map==null||map.isEmpty())
							map = new HashMap<String,Object>();
						valueMap.put(key0, getFieldValueMap(fieldVal,key1,map,keySet));//递归
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return valueMap;
	}
	
	/**
	 * 取Bean的属性和值对应关系的map
	 * @param beans
	 * @param set
	 * @return
	 * Lee.J.Eric
	 * 2014年6月17日 下午4:45:36
	 */
	public static <T> List<Map<String, Object>> getFieldValueMapForList(List<T> beans,Set<String> set){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Object bean : beans) {
			list.add(getFieldValueMap(bean,set));
		}
		return list;
	}
	
	/**
	 * 取Bean的属性和值对应关系的map
	 * @param beans
	 * @param set
	 * @return
	 * Lee.J.Eric
	 * 2014年9月9日 下午2:09:28
	 */
	public static <T> Set<Map<String, Object>> getFieldValueMapForSet(Set<T> beans,Set<String> set){
		Set<Map<String, Object>> value = new HashSet<Map<String,Object>>();
		for (Object bean : beans) {
			value.add(getFieldValueMap(bean, set));
		}
		return value;
	}
	
	/**
	 * 取Bean的属性和值对应关系的map
	 * @param bean
	 * @param set
	 * @return
	 * Lee.J.Eric
	 * 2014年6月17日 下午4:40:33
	 */
    public static Map<String, Object> getFieldValueMap(Object bean,Set<String> set) {  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
        for (String key : set) {
        	getFieldValueMap(bean,key,valueMap,set);
		}
        return valueMap;  
  
    } 
    
	
	/**
	 * 取Bean的属性和值对应关系的map 
	 * @param bean
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午2:13:06
	 */
    public static Map<String, Object> getFieldValueMap(Object bean) {  
        Class<?> cls = bean.getClass();  
        Map<String, Object> valueMap = new HashMap<String, Object>();  
        // 取出bean里的所有方法  
        Field[] fields = cls.getDeclaredFields();  
        for (Field field : fields) {  
            try {  
            	PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
            	Method readMethod = pd.getReadMethod();//获得读方法
            	Object fieldVal = readMethod.invoke(bean);
                valueMap.put(field.getName(), fieldVal);  
            } catch (Exception e) {  
                continue;  
            }  
        }  
        return valueMap;  
  
    }  
  
    /**
     * set属性的值到Bean  
     * @param bean
     * @param valMap
     * Lee.J.Eric
     * 2014年6月18日 下午2:13:15
     */
    public static void setFieldValue(Object bean, Map<String, Object> valMap) {  
        Class<?> cls = bean.getClass();  
        Set<Entry<String, Object>> entries = valMap.entrySet();
        for (Entry<String, Object> entry : entries) {
        	try {
        		PropertyDescriptor pd = new PropertyDescriptor(entry.getKey(),cls);
        		Method writeMethod = pd.getWriteMethod();//得到写方法
        		Field field = cls.getDeclaredField(entry.getKey());
    			Object value = entry.getValue();
    			String fieldType = field.getType().getSimpleName();  
				if ("String".equals(fieldType)) {
					writeMethod.invoke(bean, value);
				} else if ("Date".equals(fieldType)) {
					Date temp = parseDate(value.toString());
					writeMethod.invoke(bean, temp);
				} else if ("Integer".equals(fieldType)|| "int".equals(fieldType)) {
					Integer intval = Integer.parseInt(value.toString());
					writeMethod.invoke(bean, intval);
				} else if ("Long".equalsIgnoreCase(fieldType)) {
					Long temp = Long.parseLong(value.toString());
					writeMethod.invoke(bean, temp);
				} else if ("Double".equalsIgnoreCase(fieldType)) {
					Double temp = Double.parseDouble(value.toString());
					writeMethod.invoke(bean, temp);
				} else if ("Boolean".equalsIgnoreCase(fieldType)) {
					Boolean temp = Boolean.parseBoolean(value.toString());
					writeMethod.invoke(bean, temp);
				} else if ("Float".equalsIgnoreCase(fieldType)) {
					Float temp = Float.parseFloat(value.toString());
					writeMethod.invoke(bean, temp);
				} else if ("Short".equalsIgnoreCase(fieldType)) {
					Short temp = Short.parseShort(value.toString());
					writeMethod.invoke(bean, temp);
				} else {
					writeMethod.invoke(bean, value);
				}
			} catch (Exception e) {
				// TODO: handle exception
				continue;
			}
        	
		}
    }  
  
    /**
     * 格式化string为Date 
     * @param datestr
     * @return
     * Lee.J.Eric
     * 2014年6月18日 下午2:13:23
     */
    private static Date parseDate(String datestr) {  
        if (null == datestr || "".equals(datestr)) {  
            return null;  
        }  
        try {  
            String fmtstr = null;  
            if (datestr.indexOf(':') > 0) {  
                fmtstr = "yyyy-MM-dd HH:mm:ss";  
            } else {  
  
                fmtstr = "yyyy-MM-dd";  
            }  
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);  
            return sdf.parse(datestr);  
        } catch (Exception e) {  
            return null;  
        }  
    }  
  
    /**
     * 日期转化为String
     * @param date
     * @return
     * Lee.J.Eric
     * 2014年6月18日 下午2:13:31
     */
    private static String fmtDate(Date date) {  
        if (null == date) {  
            return null;  
        }  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",  
                    Locale.US);  
            return sdf.format(date);  
        } catch (Exception e) {  
            return null;  
        }  
    }
}
