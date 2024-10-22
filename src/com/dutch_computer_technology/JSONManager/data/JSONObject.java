package com.dutch_computer_technology.JSONManager.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.dutch_computer_technology.JSONManager.exception.JSONParseException;
import com.dutch_computer_technology.JSONManager.utils.JSONParser;
import com.dutch_computer_technology.JSONManager.utils.JSONStringify;

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
	 * Create &amp; Copy a JSONObject from a JSONObject.
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
		new JSONParser(data, str);
	};
	
	/**
	 * Create a stringified JSON String.<br>
	 * <br>
	 * By default will save Numbers with a suffix behind it,<br>
	 * when safeMode is {@code true} Numbers will not be saved with a suffix behind it.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	public String stringify() {
		return toString(false);
	};
	
	/**
	 * Create a stringified JSON String.<br>
	 * <br>
	 * By default will save Numbers with a suffix behind it,<br>
	 * when safeMode is {@code true} Numbers will not be saved with a suffix behind it.
	 * 
	 * @param safeMode Whenever to save Number suffixes.
	 * @return Returns a stringified JSON String.
	 */
	public String stringify(boolean safeMode) {
		return toString(safeMode);
	};
	
	/**
	 * Create a stringified JSON String.<br>
	 * <br>
	 * By default will save Numbers with a suffix behind it,<br>
	 * when safeMode is {@code true} Numbers will not be saved with a suffix behind it.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	@Override
	public String toString() {
		return toString(false);
	};
	
	/**
	 * Create a stringified JSON String.<br>
	 * <br>
	 * By default will save Numbers with a suffix behind it,<br>
	 * when safeMode is {@code true} Numbers will not be saved with a suffix behind it.
	 * 
	 * @param safeMode Whenever to save Number suffixes.
	 * @return Returns a stringified JSON String.
	 */
	public String toString(boolean safeMode) {
		String str = "{";
		Object[] keys = data.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			str += "\"" + ((String) keys[i]) + "\":";
			Object oValue = data.get(((String) keys[i]));
			str += JSONStringify.Stringify(oValue, safeMode);
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
	 * Get Double from key.
	 * 
	 * @param key key.
	 * @return {@code 0.0} when not found, Double when found.
	 */
	public double getDouble(String key) {
		if (key == null) return 0.0D;
		Object oValue = data.get(key);
		if (oValue == null) return 0.0D;
		if (oValue instanceof Double) return (double) oValue;
		return 0.0D;
	};
	
	/**
	 * Get Float from key.
	 * 
	 * @param key key.
	 * @return {@code 0.0} when not found, Float when found.
	 */
	public float getFloat(String key) {
		if (key == null) return 0.0F;
		Object oValue = data.get(key);
		if (oValue == null) return 0.0F;
		if (oValue instanceof Float) return (float) oValue;
		return 0.0F;
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