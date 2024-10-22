package com.dutch_computer_technology.JSONManager.utils;

import com.dutch_computer_technology.JSONManager.data.JSONArray;
import com.dutch_computer_technology.JSONManager.data.JSONObject;

public class JSONStringify {
	
	//JSONObject
	public static String Stringify(Object obj, boolean safeMode) {

		//Null
		if (obj == null) return "null";
		
		//String
		if (obj instanceof String) {
			return "\"" + JSONUtils.escape((String) obj) + "\"";
		};
		
		//Object
		if (obj instanceof JSONObject) {
			return ((JSONObject) obj).toString(safeMode);
		};
		
		//Array
		if (obj instanceof JSONArray) {
			return ((JSONArray) obj).toString(safeMode);
		};
		
		//Boolean
		if (obj instanceof Boolean) {
			return obj.toString();
		};
		
		//Integer
		if (obj instanceof Integer) {
			return Integer.toString((int) obj);
		};
		
		//Long
		if (obj instanceof Long) {
			return Long.toString((long) obj) + ((!safeMode) ? "L" : "");
		};
		
		//Double
		if (obj instanceof Double) {
			return Double.toString((double) obj) + ((!safeMode) ? "D" : "");
		};
		
		//Float
		if (obj instanceof Float) {
			return Float.toString((float) obj) + ((!safeMode) ? "F" : "");
		};
		
		return "\"" + JSONUtils.escape(obj.toString()) + "\"";
		
	};
	
};