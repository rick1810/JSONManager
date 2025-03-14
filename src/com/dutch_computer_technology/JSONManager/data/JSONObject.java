package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dutch_computer_technology.JSONManager.exception.JSONParseException;
import com.dutch_computer_technology.JSONManager.utils.JSONParser;
import com.dutch_computer_technology.JSONManager.utils.JSONStringify;
import com.dutch_computer_technology.JSONManager.utils.JSONUtils;

public class JSONObject {
	
	private Map<String, Object> data;
	
	/**
	 * Create a empty JSONObject.
	 */
	public JSONObject() {
		
		data = new HashMap<String, Object>();
		
	};
	
	/**
	 * Create &amp; Parse a JSONObject from a Byte array.
	 * 
	 * @param bytes Byte array to be parsed.
	 * @throws JSONParseException
	 */
	public JSONObject(byte[] bytes) throws JSONParseException {
		
		data = new HashMap<String, Object>();
		parse(new String(bytes));
		
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
	 * Create a stringified JSON String.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	public String stringify() {
		
		return stringify(JSONUtils.suffix(), JSONUtils.beautifyTabs(), 0);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @return Returns a stringified JSON String.
	 */
	public String stringify(boolean suffix, boolean tabs) {
		
		return stringify(suffix, tabs, 0);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @param myTabs The ammount of tabs, Should be 0.
	 * @return Returns a stringified JSON String.
	 */
	public String stringify(boolean suffix, boolean tabs, int myTabs) {
		
		return JSONStringify.Stringify(data, suffix, tabs, myTabs);
		
	};
	
	/**
	 * Create a stringified JSON String,<br>
	 * ignores beautify rules.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	@Override
	public String toString() {
		
		return toString(JSONUtils.suffix());
		
	};
	
	/**
	 * Create a stringified JSON String,<br>
	 * ignores beautify rules.
	 * 
	 * @param suffix To use suffixes.
	 * @return Returns a stringified JSON String.
	 */
	public String toString(boolean suffix) {
		
		return toString(suffix, false);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @return Returns a stringified JSON String.
	 */
	public String toString(boolean suffix, boolean tabs) {
		
		return stringify(suffix, tabs);
		
	};
	
	/**
	 * Create a byte array from JSON,<br>
	 * ignores beautify rules.
	 * 
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes() {
		
		return toBytes(JSONUtils.suffix(), false);
		
	};
	
	/**
	 * Create a byte array from JSON,<br>
	 * ignores beautify rules.
	 * 
	 * @param suffix To use suffixes.
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes(boolean suffix) {
		
		return toBytes(suffix, false);
		
	};
	
	/**
	 * Create a byte array from JSON.
	 * 
	 * @param suffix To use suffixes.
	 * @param tabs To use tabs.
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes(boolean suffix, boolean tabs) {
		
		return stringify(suffix, tabs).getBytes();
		
	};
	
	/**
	 * Check if the JSONObject contains a key.
	 * 
	 * @param key Key to be searched
	 * @return {@code true} if key found, {@code false} if nothing found.
	 */
	public boolean hasKey(String key) {
		return contains(key);
	};
	
	/**
	 * Check if the JSONObject contains a key.
	 * 
	 * @param key Key to be searched
	 * @return {@code true} if key found, {@code false} if nothing found.
	 */
	public boolean contains(String key) {
		if (key == null) return false;
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
	 * Add a key with value to the JSONObject,<br>
	 * when this key doesn't exist.
	 * 
	 * @param key key to be added.
	 * @param value Object of the key to be added.
	 */
	public void putWhenAbsent(String key, Object value) {
		if (key == null) return;
		if (data.containsKey(key)) return;
		data.put(key, value);
	};
	
	/**
	 * Remove a key with it's value from the JSONObject.
	 * 
	 * @param key key to be removed.
	 */
	public void delete(String key) {
		remove(key);
	};
	
	/**
	 * Remove a key with it's value from the JSONObject.
	 * 
	 * @param key key to be removed.
	 */
	public void remove(String key) {
		if (key == null) return;
		if (data.containsKey(key)) data.remove(key);
	};
	
	/**
	 * Get Object from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, Object when found.
	 */
	public Object get(String key) {
		return get(key, null, null);
	};
	
	/**
	 * Get Object from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Object when found.
	 */
	public Object get(String key, Object def) {
		return get(key, def, null);
	};
	
	/**
	 * Get Object from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @param cls Class to check for.
	 * @return {@code def} when not found, {@code cls} Object when found.
	 */
	public Object get(String key, Object def, Class<?> cls) {
		if (key == null) return def;
		Object oValue = data.get(key);
		if (oValue == null) return def;
		if (cls == null) return oValue;
		if (cls.isInstance(oValue)) return oValue;
		return def;
	};
	
	/**
	 * Check if value from key is a given Class.
	 * 
	 * @param key key.
	 * @param cls Class to check for.
	 * @return {@code false} when not the given Class, {@code true} when the given Class.
	 */
	public boolean isValue(String key, Class<?> cls) {
		if (key == null) return false;
		if (cls == null) return false;
		Object oValue = data.get(key);
		if (oValue == null) return false;
		return cls.isInstance(oValue);
	};
	
	/**
	 * Get String from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, String when found.
	 */
	public String getString(String key) {
		return (String) get(key, null, String.class);
	};
	
	/**
	 * Check if value from key is a String.
	 * 
	 * @param key key.
	 * @return {@code false} when not a String, {@code true} when a String.
	 */
	public boolean isString(String key) {
		return isValue(key, String.class);
	};
	
	/**
	 * Get Integer from key.
	 * 
	 * @param key key.
	 * @return {@code 0} when not found, Integer when found.
	 */
	public int getInt(String key) {
		return (int) get(key, 0, Integer.class);
	};
	
	/**
	 * Check if value from key is a Integer.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Integer, {@code true} when a Integer.
	 */
	public boolean isInt(String key) {
		return isValue(key, Integer.class);
	};
	
	/**
	 * Get Long from key.
	 * 
	 * @param key key.
	 * @return {@code 0} when not found, Long when found.
	 */
	public long getLong(String key) {
		return (long) get(key, 0L, Long.class);
	};
	
	/**
	 * Check if value from key is a Long.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Long, {@code true} when a Long.
	 */
	public boolean isLong(String key) {
		return isValue(key, Long.class);
	};
	
	/**
	 * Get Double from key.
	 * 
	 * @param key key.
	 * @return {@code 0.0} when not found, Double when found.
	 */
	public double getDouble(String key) {
		return (double) get(key, 0.0D, Double.class);
	};
	
	/**
	 * Check if value from key is a Double.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Double, {@code true} when a Double.
	 */
	public boolean isDouble(String key) {
		return isValue(key, Double.class);
	};
	
	/**
	 * Get Float from key.
	 * 
	 * @param key key.
	 * @return {@code 0.0} when not found, Float when found.
	 */
	public float getFloat(String key) {
		return (float) get(key, 0.0F, Float.class);
	};
	
	/**
	 * Check if value from key is a Float.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Float, {@code true} when a Float.
	 */
	public boolean isFloat(String key) {
		return isValue(key, Float.class);
	};
	
	/**
	 * Get Boolean from key.
	 * 
	 * @param key key.
	 * @return {@code false} when not found, Boolean when found.
	 */
	public boolean getBoolean(String key) {
		return (boolean) get(key, false, Boolean.class);
	};
	
	/**
	 * Check if value from key is a Boolean.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Boolean, {@code true} when a Boolean.
	 */
	public boolean isBoolean(String key) {
		return isValue(key, Boolean.class);
	};
		
	/**
	 * Get JSONObject from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, JSONObject when found.
	 */
	public JSONObject getJSONObject(String key) {
		return (JSONObject) get(key, null, JSONObject.class);
	};
	
	/**
	 * Check if value from key is a JSONObject.
	 * 
	 * @param key key.
	 * @return {@code false} when not a JSONObject, {@code true} when a JSONObject.
	 */
	public boolean isJSONObject(String key) {
		return isValue(key, JSONObject.class);
	};
	
	/**
	 * Get JSONArray from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, JSONArray when found.
	 */
	public JSONArray getJSONArray(String key) {
		return (JSONArray) get(key, null, JSONArray.class);
	};
	
	/**
	 * Check if value from key is a JSONArray.
	 * 
	 * @param key key.
	 * @return {@code false} when not a JSONArray, {@code true} when a JSONArray.
	 */
	public boolean isJSONArray(String key) {
		return isValue(key, JSONArray.class);
	};
	
	/**
	 * Get List&lt;?&gt; from key.
	 * 
	 * @param key key.
	 * @return {@code null} when not found, List&lt;?&gt; when found.
	 */
	public List<?> getList(String key) {
		return getList(key, null);
	};
	
	/**
	 * Get List&lt;{@code cls}&gt; from key.
	 * 
	 * @param key key.
	 * @param cls Class to check for.
	 * @return {@code null} when not found, List&lt;{@code cls}&gt; when found.
	 */
	public List<?> getList(String key, Class<?> cls) {
		List<?> list = (List<?>) get(key, null, List.class);
		if (cls == null || list == null) return list;
		List<Object> clsList = new ArrayList<>();
		for (Object obj : list) {
			if (cls.isInstance(obj)) clsList.add(obj);
		};
		return clsList;
	};
	
	/**
	 * Check if value from key is a List.
	 * 
	 * @param key key.
	 * @return {@code false} when not a List, {@code true} when a List.
	 */
	public boolean isList(String key) {
		return isValue(key, List.class);
	};
	
};