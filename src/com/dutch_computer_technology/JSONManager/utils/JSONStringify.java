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
		if (obj instanceof String) return new StringBuilder("\"").append(JSONUtils.escape((String) obj)).append("\"").toString();
		
		//Object
		if (obj instanceof JSONObject) return ((JSONObject) obj).stringify();
		
		//Array
		if (obj instanceof JSONArray) return ((JSONArray) obj).stringify();
		
		//Boolean
		if (obj instanceof Boolean) return obj.toString();
		
		//Numbers
			
			boolean suffix = JSONUtils.suffix();
			
			//Integer
			if (obj instanceof Integer) return new StringBuilder(Integer.toString((int) obj)).append(suffix ? "I" : "").toString();
			
			//Long
			if (obj instanceof Long) return new StringBuilder(Long.toString((long) obj)).append(suffix ? "L" : "").toString();
			
			//Double
			if (obj instanceof Double) {
				
				double dob = (double) obj;
				if (!Double.isFinite(dob)) return "null";
				return new StringBuilder(Double.toString(dob)).append(suffix ? "D" : "").toString();
				
			};
			
			//Float
			if (obj instanceof Float) {
				
				float flo = (float) obj;
				if (!Float.isFinite(flo)) return "null";
				return new StringBuilder(Float.toString(flo)).append(suffix ? "F" : "").toString();
				
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
		
		return new StringBuilder("\"").append(JSONUtils.escape(obj.toString())).append("\"").toString();
		
	};
	
};