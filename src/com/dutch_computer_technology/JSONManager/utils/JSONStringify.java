package com.dutch_computer_technology.JSONManager.utils;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dutch_computer_technology.JSONManager.data.JSONArray;
import com.dutch_computer_technology.JSONManager.data.JSONObject;

/**
 * Stringifier for JSONObject's &amp; JSONArray's
 */
public class JSONStringify {
	
	/**
	 * Stringifier for JSONObject's &amp; JSONArray's
	 */
	public JSONStringify() {};
	
	/**
	 * Stringify a JSONObject
	 * 
	 * @param data The Map to stringify.
	 * @param config JSONConfig config for Stringifying.
	 * @param myTabs The ammount of tabs.
	 * @return The stringified JSONObject
	 */
	public static String Stringify(Map<String, Object> data, JSONConfig config, int myTabs) {
		
		if (data.isEmpty()) return "{}";
		if (config == null) config = new JSONConfig();
		
		StringBuilder str = new StringBuilder("{");
		myTabs++;
		
		Object[] keys = data.keySet().toArray();
		List<Unloader> unloaders = new ArrayList<Unloader>();
		for (int i = 0; i < keys.length; i++) {
			
			String key = (String) keys[i];
			Object value = data.get(key);
			
			Unloader unloader = new Unloader(key, value, config, myTabs);
			unloaders.add(unloader);
			
			if (config.threaded()) {
				
				unloader.start();
				
			} else {
				
				unloader.run();
				
			};
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Unloader unloader : unloaders) {
				
				if (!unloader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (int i = 0; i < unloaders.size(); i++) {
			
			if (config.tabs()) str.append("\n").append(JSONUtils.tabs(myTabs));
			
			Unloader unloader = unloaders.get(i);
			
			str.append("\"").append(unloader.getKey()).append("\":");
			
			str.append(unloader.getStringified());
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		myTabs--;
		if (config.tabs()) str.append("\n").append(JSONUtils.tabs(myTabs));
		return str.append("}").toString();
		
	};
	
	/**
	 * Stringify a JSONArray
	 * 
	 * @param data The List to stringify.
	 * @param config JSONConfig config for Stringifying.
	 * @param myTabs The ammount of tabs.
	 * @return The stringified JSONArray
	 */
	public static String Stringify(List<Object> data, JSONConfig config, int myTabs) {
		
		if (data.isEmpty()) return "[]";
		if (config == null) config = new JSONConfig();
		
		StringBuilder str = new StringBuilder("[");
		myTabs++;
		
		List<Unloader> unloaders = new ArrayList<Unloader>();
		for (int i = 0; i < data.size(); i++) {
			
			Unloader unloader = new Unloader(null, data.get(i), config, myTabs);
			unloaders.add(unloader);
			
			if (config.threaded()) {
				
				unloader.start();
				
			} else {
				
				unloader.run();
				
			};
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Unloader unloader : unloaders) {
				
				if (!unloader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (int i = 0; i < unloaders.size(); i++) {
			
			if (config.tabs()) str.append("\n").append(JSONUtils.tabs(myTabs));
			
			str.append(unloaders.get(i).getStringified());
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		myTabs--;
		if (config.tabs()) str.append("\n").append(JSONUtils.tabs(myTabs));
		return str.append("]").toString();
		
	};
	
	private static class Unloader extends Thread {
		
		private String key;
		private Object value;
		
		private JSONConfig config;
		private int myTabs;
		
		private String stringified;
		private boolean ready;
		
		public Unloader(String key, Object value, JSONConfig config, int myTabs) {
			
			this.key = key;
			this.value = value;
			
			this.config = config;
			this.myTabs = myTabs;
			
			this.stringified = null;
			this.ready = false;
			
		};
		
		public String getKey() {
			
			return this.key;
			
		};
		
		public String getStringified() {
			
			return this.stringified;
			
		};
		
		public boolean isReady() {
			
			return this.ready;
			
		};
		
		@Override
		public void run() {
			
			this.stringified = Stringify(this.value, this.config, this.myTabs);
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
		
		return Stringify(obj, null, 0);
		
	};
	
	/**
	 * Returns a stringified Value from a Object
	 * 
	 * @param obj To be stringified.
	 * @param config JSONConfig config for Stringifying.
	 * @param myTabs The ammount of tabs.
	 * @return The stringified Value
	 */
	public static String Stringify(Object obj, JSONConfig config, int myTabs) {
		
		if (config == null) config = new JSONConfig();
		
		//Null
		if (obj == null) return "null";
		
		//String
		if (obj instanceof String) return new StringBuilder("\"").append(JSONUtils.escape((String) obj)).append("\"").toString();
		
		//Object
		if (obj instanceof JSONObject) return ((JSONObject) obj)._stringify(config, myTabs);
		
		//Array
		if (obj instanceof JSONArray) return ((JSONArray) obj)._stringify(config, myTabs);
		
		//Boolean
		if (obj instanceof Boolean) return obj.toString();
		
		boolean suffix = config.suffix();
		
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
		
		//Object
		Class<?> cls = obj.getClass();
		try {
			
			Method method = cls.getMethod("toJSON");
			Object ret = method.invoke(obj);
			if (ret instanceof JSONObject) {
				JSONObject json = (JSONObject) ret;
				if (config.className()) json.put("__class", cls.getName());
				return ((JSONObject) ret).stringify(config);
			};
			
		} catch (NoSuchMethodException ignore) {
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		return new StringBuilder("\"").append(JSONUtils.escape(obj.toString())).append("\"").toString();
		
	};
	
};