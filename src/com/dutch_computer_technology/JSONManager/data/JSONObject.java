package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dutch_computer_technology.JSONManager.JSONUtils;
import com.dutch_computer_technology.JSONManager.exception.JSONParseException;

public class JSONObject {
	
	private Map<String, Object> data;
	
	/**
	 * Create a empty JSONObject.
	 */
	public JSONObject() {
		data = new HashMap<String, Object>();
	};
	
	/**
	 * Create &amp; Parse a JSONObject from a String.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public JSONObject(String str) throws JSONParseException {
		data = new HashMap<String, Object>();
		parse(str);
	};
	
	/**
	 * Create/Copy a JSONObject from a JSONObject.
	 * 
	 * @param json JSONObject to be copied.
	 */
	public JSONObject(JSONObject json) {
		data = new HashMap<String, Object>(json.data);
	};
	
	/**
	 * Parse a String to a JSONObject.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public void parse(String str) throws JSONParseException {
		if (str == null) throw new JSONParseException("Null");
		str = JSONUtils.sanitize(str);
		if (!(str.startsWith("{") && str.endsWith("}"))) throw new JSONParseException("Not a JSONObject");
		if (str.length() == 2) return;
		str = str.substring(1, str.length()-1);
		List<String> objs = new ArrayList<String>();
		String open = "";
		int o = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '[' || c == '{') {
				open += c;
				continue;
			};
			if (c == ']' || c == '}') {
				if (open.length() == 0) throw new JSONParseException("Illegal bracket at " + (i+2));
				char b = open.charAt(open.length()-1);
				if (!((c == ']' && b == '[') || (c == '}' && b == '{'))) throw new JSONParseException("Illegal bracket at " + (i+2));
				open = open.substring(0, open.length()-1);
			};
			if (c == '"') {
				if (open.length() > 0 && open.charAt(open.length()-1) == '"') {
					if (open.length() == 0) continue;
					open = open.substring(0, open.length()-1);
				} else {
					open += '"';
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
		for (String obj : objs) {
			int closeKey = obj.indexOf("\"", 1);
			if (closeKey == -1) throw new JSONParseException("Key is not a String at " + o);
			String key = obj.substring(obj.startsWith("\"") ? 1 : 0, closeKey);
			if (key.length() == 0) throw new JSONParseException("Key is empty at " + o); //Key should always have a value
			int separatorIndex = obj.indexOf(":", closeKey);
			if (separatorIndex == -1) throw new JSONParseException("No Separator at " + o);
			String oValue = obj.substring(separatorIndex+1, obj.length());
			if (oValue.startsWith("\"") && oValue.endsWith("\"") && oValue.length() > 1) {
				if (oValue.length() == 2) {
					data.put(key, "");
				} else {
					data.put(key, JSONUtils.unescape(oValue.substring(1, oValue.length()-1)));
				};
				continue;
			};
			if (oValue.startsWith("{") && oValue.endsWith("}")) {
				data.put(key, new JSONObject(oValue));
				o += key.length()+oValue.length()+5;
				continue;
			};
			if (oValue.startsWith("[") && oValue.endsWith("]")) {
				data.put(key, new JSONArray(oValue));
				o += key.length()+oValue.length()+5;
				continue;
			};
			if (oValue.equals("true")) {
				data.put(key, true);
				o += key.length()+oValue.length();
				continue;
			};
			if (oValue.equals("false")) {
				data.put(key, false);
				o += key.length()+oValue.length();
				continue;
			};
			if (oValue.equals("null")) {
				data.put(key, null);
				o += key.length()+oValue.length();
				continue;
			};
			if (oValue.matches("\\d+L")) {
				try {
					data.put(key, Long.parseLong(oValue.substring(0, oValue.length()-1)));
					o += key.length()+oValue.length();
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException at " + o);
				}
			};
			if (oValue.matches("\\d+")) {
				try {
					data.put(key, Integer.parseInt(oValue));
					o += key.length()+oValue.length();
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException at " + o);
				}
			};
			throw new JSONParseException("Unexpected Object at " + o);
		};
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * By default will save Long's with a L behind it,
	 * when safeMode is {@code true} the Long's will be saved without a L.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	@Override
	public String toString() {
		return toString(false);
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * By default will save Long's with a L behind it,
	 * when safeMode is {@code true} the Long's will be saved without a L.
	 * 
	 * @param safeMode Whenever to save Long's with or without a L behind.
	 * @return Returns a stringified JSON String.
	 */
	public String toString(boolean safeMode) {
		String str = "{";
		Object[] keys = data.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			str += "\"" + ((String) keys[i]) + "\":";
			Object oValue = data.get(((String) keys[i]));
			if (oValue instanceof JSONObject) {
				str += ((JSONObject) oValue).toString(safeMode);
			} else if (oValue instanceof JSONArray) {
				str += ((JSONArray) oValue).toString(safeMode);
			} else if (oValue instanceof String) {
				str += "\"" + JSONUtils.escape((String) oValue) + "\"";
			} else if (oValue instanceof Long) {
				str += Long.toString((long) oValue) + ((!safeMode) ? "L" : "");
			} else if (oValue instanceof Integer) {
				str += Integer.toString((int) oValue);
			} else if (oValue instanceof Boolean) {
				if ((boolean) oValue) {
					str += "true";
				} else {
					str += "false";
				};
			} else {
				if (oValue == null) {
					str += "null";
				} else {
					str += "\"" + JSONUtils.escape(oValue.toString()) + "\"";
				};
			};
			if (i < data.size()-1) str += ",";
		};
		return str + "}";
	};
	
	/**
	 * Check if the JSONObject contains a key.
	 * 
	 * @param key Key to be searched
	 * @return {@code true} if key found, {@code false} if nothing found.
	 */
	public boolean hasKey(String key) {
		return data.containsKey(key);
	};
	
	/**
	 * Check if the JSONObject contains a key.
	 * 
	 * @param key Key to be searched
	 * @return {@code true} if key found, {@code false} if nothing found.
	 */
	public boolean contains(String key) {
		return data.containsKey(key);
	};
	
	/**
	 * Check if the JSONObject contains a Object.
	 * 
	 * @param value Value to be searched
	 * @return {@code true} if value found, {@code false} if nothing found.
	 */
	public boolean hasValue(Object value) {
		return data.containsValue(value);
	};
	
	/**
	 * Get keys from JSONObject.
	 * 
	 * @return All the key's inside of JSONObject.
	 */
	public Set<String> keySet() {
		return data.keySet();
	};
	
	/**
	 * Add a key with value to the JSONObject.
	 * 
	 * @param key key to be added.
	 * @param value Object of the key to be added.
	 */
	public void put(String key, Object value) {
		if (key == null) return;
		data.put(key, value);
	};
	
	/**
	 * Remove a key with it's value from the JSONObject.
	 * 
	 * @param key key to be removed.
	 */
	public void delete(String key) {
		if (data.containsKey(key)) data.remove(key);
	};
	
	/**
	 * Get Object from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, Object when found.
	 */
	public Object get(String key) {
		if (key == null) return null;
		return data.get(key);
	};
	
	/**
	 * Get String from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, String when found.
	 */
	public String getString(String key) {
		if (key == null) return null;
		Object oValue = data.get(key);
		if (oValue == null) return null;
		if (oValue instanceof String) return (String) oValue;
		return null;
	};
	
	/**
	 * Get Integer from key.
	 * 
	 * @param key key.
	 * @return {@code 0} when not found, Integer when found.
	 */
	public int getInt(String key) {
		if (key == null) return 0;
		Object oValue = data.get(key);
		if (oValue == null) return 0;
		if (oValue instanceof Integer) return (int) oValue;
		return 0;
	};
	
	/**
	 * Get Long from key.
	 * 
	 * @param key key.
	 * @return {@code 0} when not found, Long when found.
	 */
	public long getLong(String key) {
		if (key == null) return 0;
		Object oValue = data.get(key);
		if (oValue == null) return 0;
		if (oValue instanceof Long) return (long) oValue;
		return 0;
	};
	
	/**
	 * Get Boolean from key.
	 * 
	 * @param key key.
	 * @return {@code false} when not found, Boolean when found.
	 */
	public boolean getBoolean(String key) {
		if (key == null) return false;
		Object oValue = data.get(key);
		if (oValue == null) return false;
		if (oValue instanceof Boolean) return (boolean) oValue;
		return false;
	};
	
	
	
	/**
	 * Get JSONObject from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, JSONObject when found.
	 */
	public JSONObject getJSONObject(String key) {
		if (key == null) return null;
		Object oValue = data.get(key);
		if (oValue == null) return null;
		if (oValue instanceof JSONObject) return (JSONObject) oValue;
		return null;
	};
	
	/**
	 * Get JSONArray from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, JSONArray when found.
	 */
	public JSONArray getJSONArray(String key) {
		if (key == null) return null;
		Object oValue = data.get(key);
		if (oValue == null) return null;
		if (oValue instanceof JSONArray) return (JSONArray) oValue;
		return null;
	};
	
};