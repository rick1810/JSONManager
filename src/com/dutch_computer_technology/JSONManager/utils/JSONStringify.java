package com.dutch_computer_technology.JSONManager.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dutch_computer_technology.JSONManager.data.JSONArray;
import com.dutch_computer_technology.JSONManager.data.JSONObject;
import com.dutch_computer_technology.JSONManager.exception.JSONParseException;

public class JSONStringify {
	
	/**
	 * Stringify a JSONObject
	 * 
	 * @param data The Map to stringify.
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @param myTabs The ammount of tabs.
	 * @throws JSONParseException
	 */
	public static String Stringify(Map<String, Object> data, boolean suffix, boolean tabs, int myTabs) {
		
		if (data.isEmpty()) return "{}";
		
		StringBuilder str = new StringBuilder("{");
		myTabs++;
		
		Object[] keys = data.keySet().toArray();
		List<Unloader> unloaders = new ArrayList<Unloader>();
		for (int i = 0; i < keys.length; i++) {
			
			String key = (String) keys[i];
			
			Unloader unloader = new Unloader(key, data.get(key), suffix, tabs, myTabs);
			unloaders.add(unloader);
			unloader.start();
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Unloader unloader : unloaders) {
				
				if (!unloader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (int i = 0; i < unloaders.size(); i++) {
			
			if (tabs) str.append("\n").append(JSONUtils.beautifyTabs(myTabs));
			
			Unloader unloader = unloaders.get(i);
			
			str.append("\"").append(unloader.getKey()).append("\":");
			
			str.append(unloader.getStringified());
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		myTabs--;
		if (tabs) str.append("\n").append(JSONUtils.beautifyTabs(myTabs));
		return str.append("}").toString();
		
	};
	
	/**
	 * Stringify a JSONArray
	 * 
	 * @param data The List to stringify.
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @param myTabs The ammount of tabs.
	 * @throws JSONParseException
	 */
	public static String Stringify(List<Object> data, boolean suffix, boolean tabs, int myTabs) {
		
		if (data.isEmpty()) return "[]";
		
		StringBuilder str = new StringBuilder("[");
		myTabs++;
		
		List<Unloader> unloaders = new ArrayList<Unloader>();
		for (int i = 0; i < data.size(); i++) {
			
			Unloader unloader = new Unloader(data.get(i), suffix, tabs, myTabs);
			unloaders.add(unloader);
			unloader.start();
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Unloader unloader : unloaders) {
				
				if (!unloader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (int i = 0; i < unloaders.size(); i++) {
			
			if (tabs) str.append("\n").append(JSONUtils.beautifyTabs(myTabs));
			
			str.append(unloaders.get(i).getStringified());
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		myTabs--;
		if (tabs) str.append("\n").append(JSONUtils.beautifyTabs(myTabs));
		return str.append("]").toString();
		
	};
	
	private static class Unloader extends Thread {
		
		private String key;
		private Object value;
		
		private boolean suffix;
		private boolean tabs;
		private int myTabs;
		
		private String stringified;
		private boolean ready;
		
		public Unloader(String key, Object value, boolean suffix, boolean tabs, int myTabs) {
			
			this.key = key;
			this.value = value;
			
			this.suffix = suffix;
			this.tabs = tabs;
			this.myTabs = myTabs;
			
			this.stringified = null;
			this.ready = false;
			
		};
		
		public Unloader(Object value, boolean suffix, boolean tabs, int myTabs) {
			
			this.key = null;
			this.value = value;
			
			this.suffix = suffix;
			this.tabs = tabs;
			this.myTabs = myTabs;
			
			this.stringified = null;
			this.ready = false;
			
		};
		
		public String getKey() {
			
			return (String) this.key;
			
		};
		
		public String getStringified() {
			
			return this.stringified;
			
		};
		
		public boolean isReady() {
			
			return this.ready;
			
		};
		
		@Override
		public void run() {
			
			this.stringified = Stringify(this.value, this.suffix, this.tabs, this.myTabs);
			this.ready = true;
			
		};
		
	};
	
	/**
	 * Returns a stringified Value from a Object
	 * 
	 * @param obj To be stringified.
	 * @return The stringified Value
	 */
	public static String Stringify(Object obj) {
		
		return Stringify(obj, JSONUtils.suffix(), JSONUtils.beautifyTabs(), 0);
		
	};
	
	/**
	 * Returns a stringified Value from a Object
	 * 
	 * @param obj To be stringified.
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @param myTabs The ammount of tabs.
	 * @return The stringified Value
	 */
	public static String Stringify(Object obj, boolean suffix, boolean tabs, int myTabs) {
		
		//Null
		if (obj == null) return "null";
		
		//String
		if (obj instanceof String) return new StringBuilder("\"").append(JSONUtils.escape((String) obj)).append("\"").toString();
		
		//Object
		if (obj instanceof JSONObject) return ((JSONObject) obj).stringify(suffix, tabs, myTabs);
		
		//Array
		if (obj instanceof JSONArray) return ((JSONArray) obj).stringify(suffix, tabs, myTabs);
		
		//Boolean
		if (obj instanceof Boolean) return obj.toString();
		
		//Numbers
			
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
		
		//List
		if (obj instanceof List) {
			
			List<?> list = (List<?>) obj;
			JSONArray arr = new JSONArray();
			for (Object o : list) arr.add(o);
			JSONObject json = new JSONObject();
			json.put("values", arr);
			json.put("__class", List.class.getName());
			return json.stringify(suffix, tabs, myTabs);
			
		};
		
		//Object
		Class<?> cls = obj.getClass();
		try {
			
			Method method = cls.getMethod("toJSON");
			Object ret = method.invoke(obj);
			if (ret instanceof JSONObject) {
				JSONObject json = (JSONObject) ret;
				json.put("__class", cls.getName());
				return ((JSONObject) ret).stringify();
			};
			
		} catch (NoSuchMethodException ignore) {
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return new StringBuilder("\"").append(JSONUtils.escape(obj.toString())).append("\"").toString();
		
	};
	
};