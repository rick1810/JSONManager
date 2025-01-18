package com.dutch_computer_technology.JSONManager.utils;

import java.lang.reflect.Method;

import com.dutch_computer_technology.JSONManager.data.JSONArray;
import com.dutch_computer_technology.JSONManager.data.JSONObject;

public class JSONStringify {
	
	//JSONObject
	/**
	 * Returns a stringified Value from a Object
	 * 
	 * @param obj To be stringified.
	 * @return The stringified Value
	 */
	public static String Stringify(Object obj) {
		
		//Null
		if (obj == null) return "null";
		
		//String
		if (obj instanceof String) return "\"" + JSONUtils.escape((String) obj) + "\"";
		
		//Object
		if (obj instanceof JSONObject) return ((JSONObject) obj).stringify();
		
		//Array
		if (obj instanceof JSONArray) return ((JSONArray) obj).stringify();
		
		//Boolean
		if (obj instanceof Boolean) return obj.toString();
		
		//Numbers
			
			boolean suffix = JSONUtils.suffix();
			
			//Integer
			if (obj instanceof Integer) return Integer.toString((int) obj) + (suffix ? "I" : "");
			
			//Long
			if (obj instanceof Long) return Long.toString((long) obj) + (suffix ? "L" : "");
			
			//Double
			if (obj instanceof Double) {
				
				double dob = (double) obj;
				if (!Double.isFinite(dob)) return "null";
				return Double.toString(dob) + (suffix ? "D" : "");
				
			};
			
			//Float
			if (obj instanceof Float) {
				
				float flo = (float) obj;
				if (!Float.isFinite(flo)) return "null";
				return Float.toString(flo) + (suffix ? "F" : "");
			};
		
		//Object
		Class<?> cls = obj.getClass();
		try {
			
			Method method = cls.getMethod("toJSON");
			Object ret = method.invoke(obj);
			if (ret instanceof JSONObject) return ((JSONObject) ret).stringify();
			
		} catch (NoSuchMethodException ignore) {
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return "\"" + JSONUtils.escape(obj.toString()) + "\"";
		
	};
	
};