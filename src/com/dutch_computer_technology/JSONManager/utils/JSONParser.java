package com.dutch_computer_technology.JSONManager.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dutch_computer_technology.JSONManager.data.JSONArray;
import com.dutch_computer_technology.JSONManager.data.JSONObject;
import com.dutch_computer_technology.JSONManager.exception.JSONParseException;

public class JSONParser {
	
	//JSONObject
	/**
	 * Parse a JSONObject from a String.
	 * 
	 * @param data The Map to store the parsed data.
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public JSONParser(Map<String, Object> data, String str) throws JSONParseException {
		
		if (data == null) throw new JSONParseException("Unexpected Null"); //Should never be Null.
		if (str == null) throw new JSONParseException("Null"); //No String to parse.
		
		str = JSONUtils.sanitize(str); //Remove any unneeded charaters.
		if (!(str.startsWith("{") && str.endsWith("}"))) throw new JSONParseException("Not a JSONObject");
		
		if (str.length() == 2) return; //Just open & close, no need to progress.
		
		str = str.substring(1, str.length()-1);
		
		List<String> objs = new ArrayList<String>();
		
		StringBuilder open = new StringBuilder();
		int o = 0;
		
		for (int i = 0; i < str.length(); i++) {
			
			char c = str.charAt(i);
			if (c == '[' || c == '{') {
				open.append(c);
				continue;
			};
			
			if (c == ']' || c == '}') {
				if (open.length() == 0) throw new JSONParseException("Illegal bracket at " + i);
				char b = open.charAt(open.length()-1);
				if (!((c == ']' && b == '[') || (c == '}' && b == '{'))) throw new JSONParseException("Illegal bracket at " + i);
				open.setLength(open.length()-1);
			};
			
			if (c == '"') {
				if (open.length() > 0 && open.charAt(open.length()-1) == '"') {
					if (open.length() == 0) continue;
					open.setLength(open.length()-1);
				} else {
					open.append('"');
				};
			};
			
			if (open.length() > 0) continue;
			
			if (c == ',') {
				String buf = str.substring(str.charAt(o) == ',' ? o+1 : o, i);
				objs.add(buf);
				o = i;
				continue;
			};
			
			if (i == str.length()-1) {
				String buf = str.substring(str.charAt(o) == ',' ? o+1 : o);
				objs.add(buf);
			};
			
		};
		
		o = 1; //Offset for { of init
		List<Loader> loaders = new ArrayList<Loader>();
		for (String obj : objs) {
			
			int closeKey = obj.indexOf("\"", 1);
			if (closeKey == -1) throw new JSONParseException("Key is not a String at " + o);
			
			String key = obj.substring(obj.startsWith("\"") ? 1 : 0, closeKey);
			if (key.length() == 0) throw new JSONParseException("Key is empty at " + o); //Key should always have a value
			
			int separatorIndex = obj.indexOf(":", closeKey);
			if (separatorIndex == -1) throw new JSONParseException("No Separator at " + o);
			
			String oValue = obj.substring(separatorIndex+1, obj.length());
			
			Loader loader = new Loader(key, oValue);
			loaders.add(loader);
			loader.start();
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Loader loader : loaders) {
				
				if (loader.getErr() != null) throw loader.getErr();
				if (!loader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (Loader loader : loaders) {
			
			data.put(loader.getKey(), loader.getValue());
			
		};
		
	};
	
	//JSONArray
	/**
	 * Parse a JSONParser from a String.
	 * 
	 * @param data The List to store the parsed data.
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public JSONParser(List<Object> data, String str) throws JSONParseException {
		
		if (data == null) throw new JSONParseException("Unexpected Null"); //Should never be Null.
		if (str == null) throw new JSONParseException("Null"); //No String to parse.
		
		str = JSONUtils.sanitize(str); //Remove any unneeded charaters.
		if (!(str.startsWith("[") && str.endsWith("]"))) throw new JSONParseException("Not a JSONArray");
		
		if (str.length() == 2) return; //Just open & close, no need to progress.
		
		str = str.substring(1, str.length()-1);
		
		List<String> objs = new ArrayList<String>();
		
		StringBuilder open = new StringBuilder();
		int o = 0;
		
		for (int i = 0; i < str.length(); i++) {
			
			char c = str.charAt(i);
			
			if (c == '[' || c == '{') {
				open.append(c);
				continue;
			};
			
			if (c == ']' || c == '}') {
				if (open.length() == 0) continue;
				open.setLength(open.length()-1);
			};
			
			if (c == '"') {
				if (open.length() > 0 && open.charAt(open.length()-1) == '"') {
					if (open.length() == 0) continue;
					open.setLength(open.length()-1);
				} else {
					open.append('"');
				};
			};
			
			if (open.length() > 0) continue;
			
			if (c == ',') {
				String buf = str.substring(str.charAt(o) == ',' ? o+1 : o, i);
				objs.add(buf);
				o = i;
				continue;
			};
			
			if (i == str.length()-1) {
				String buf = str.substring(str.charAt(o) == ',' ? o+1 : o);
				objs.add(buf);
			};
			
		};
		
		List<Loader> loaders = new ArrayList<Loader>();
		for (String oValue : objs) {
			
			Loader loader = new Loader(oValue);
			loaders.add(loader);
			loader.start();
			
		};
		
		while (true) { //In order
			
			boolean allReady = true;
			for (Loader loader : loaders) {
				
				if (loader.getErr() != null) throw loader.getErr();
				if (!loader.isReady()) allReady = false;
				
			};
			if (allReady) break;
			
		};
		
		for (Loader loader : loaders) {
			
			data.add(loader.getValue());
			
		};
		
	};
	
	private class Loader extends Thread {
		
		private String key;
		private String value;
		
		private JSONParseException e;
		private Object obj;
		private boolean ready;
		
		public Loader(String key, String value) {
			
			this.key = key;
			this.value = value;
			
			this.e = null;
			this.obj = null;
			this.ready = false;
			
		};
		
		public Loader(String value) {
			
			this.key = null;
			this.value = value;
			
			this.e = null;
			this.obj = null;
			this.ready = false;
			
		};
		
		public String getKey() {
			
			return (String) this.key;
			
		};
		
		public Object getValue() {
			
			return this.obj;
			
		};
		
		public JSONParseException getErr() {
			
			return this.e;
			
		};
		
		public boolean isReady() {
			
			return this.ready;
			
		};
		
		@Override
		public void run() {
			
			try {
				
				this.obj = parseValue(this.value);
				
			} catch(JSONParseException e) {
				
				this.e = e;
				
			};
			this.ready = true;
			
		};
		
	};
	
	/**
	 * Parse a Value from a String.
	 * 
	 * @param str String to be parsed.
	 * @return Returns a Object value.
	 * @throws JSONParseException
	 */
	private Object parseValue(String str) throws JSONParseException {
		
		//String
		if (str.startsWith("\"") && str.endsWith("\"") && str.length() > 1) {
			if (str.length() == 2) return "";
			return JSONUtils.unescape(str.substring(1, str.length()-1));
		};
		
		//JSONObject
		if (str.startsWith("{") && str.endsWith("}")) {
			
			JSONObject json = new JSONObject(str);
			if (!json.isString("__class")) return json; //Check if __class exists & is a string.
			
			//Get class.
			Class<?> cls = null;
			try {
				cls = Class.forName(json.getString("__class")); //Get the class.
			} catch(Exception ignore) {};
			if (cls == null) return json; //No class, return json.
			
			//Try to create a List
			if (cls.equals(List.class)) {
				
				if (!json.isJSONArray("values")) return json;
				
				JSONArray arr = json.getJSONArray("values");
				return arr.getObjects();
				
			};
			
			//Try to create a custom class
			try {
				Constructor<?> con = cls.getConstructor(JSONObject.class); //Try constructor of class.
				return con.newInstance(json); //New instance.
			} catch(Exception ignore) {};
			
			return json; //No constructor, return json.
			
		};
		
		//Array
		if (str.startsWith("[") && str.endsWith("]")) return new JSONArray(str);
		
		//Boolean
		if (str.equals("true")) return true;
		if (str.equals("false")) return false;
		
		//Null
		if (str.equals("null")) return null;
		
		//Numbers
		if (str.matches("^-?\\d+(\\.\\d*)?([eE]-?\\d+)?[ILDF]?$")) {
			
			try {
				
				char suffix = str.charAt(str.length() - 1);
				String num = str;
				if (suffix == 'I' || suffix == 'L' || suffix == 'D' || suffix == 'F') num = str.substring(0, str.length()-1);
				
				switch(suffix) {
					//Integer
					case 'I':
						return Integer.parseInt(num);
					//Long
					case 'L':
						return Long.parseLong(num);
					//Double
					case 'D':
						return Double.parseDouble(num);
					//Float
					case 'F':
						return Float.parseFloat(num);
					//default, Long/Double
					default:
						if (num.contains(".")) return Double.parseDouble(num);
						return Long.parseLong(num);
				}
				
			} catch(NumberFormatException e) {
				throw new JSONParseException("NumberFormatException");
			}
			
		};
		
		throw new JSONParseException("Unexpected Object");
		
	};
	
};