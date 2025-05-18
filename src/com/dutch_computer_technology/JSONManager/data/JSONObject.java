package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dutch_computer_technology.JSONManager.exception.JSONParseException;
import com.dutch_computer_technology.JSONManager.utils.JSONConfig;
import com.dutch_computer_technology.JSONManager.utils.JSONParser;
import com.dutch_computer_technology.JSONManager.utils.JSONStringify;

/**
 * Get/Put a {@code Object} using a {@code String} key
 */
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
	 * @throws JSONParseException When parsing fails.
	 */
	public JSONObject(byte[] bytes) throws JSONParseException {
		
		data = new HashMap<String, Object>();
		parse(new String(bytes));
		
	};
	
	/**
	 * Create &amp; Parse a JSONObject from a Byte array.
	 * 
	 * @param bytes Byte array to be parsed.
	 * @param config JSONConfig config for Parsing.
	 * @throws JSONParseException When parsing fails.
	 */
	public JSONObject(byte[] bytes, JSONConfig config) throws JSONParseException {
		
		data = new HashMap<String, Object>();
		parse(new String(bytes), config);
		
	};
	
	/**
	 * Create &amp; Parse a JSONObject from a String.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException When parsing fails.
	 */
	public JSONObject(String str) throws JSONParseException {
		
		data = new HashMap<String, Object>();
		parse(str);
		
	};
	
	/**
	 * Create &amp; Parse a JSONObject from a String.
	 * 
	 * @param str String to be parsed.
	 * @param config JSONConfig config for Parsing.
	 * @throws JSONParseException When parsing fails.
	 */
	public JSONObject(String str, JSONConfig config) throws JSONParseException {
		
		data = new HashMap<String, Object>();
		parse(str, config);
		
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
	 * Copy the JSONObject.
	 * 
	 * @return Returns a clone of this JSONObject.
	 */
	public JSONObject clone() {
		
		return new JSONObject(this);
		
	};
	
	/**
	 * Parse a String to a JSONObject.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException When parsing fails.
	 */
	public void parse(String str) throws JSONParseException {
		
		parse(str, null);
		
	};
	
	/**
	 * Parse a String to a JSONObject.
	 * 
	 * @param str String to be parsed.
	 * @param config JSONConfig config for Parsing.
	 * @throws JSONParseException When parsing fails.
	 */
	public void parse(String str, JSONConfig config) throws JSONParseException {
		
		new JSONParser(data, str, config);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	public String stringify() {
		
		return _stringify(null, 0);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param config JSONConfig config for Stringifying.
	 * @return Returns a stringified JSON String.
	 */
	public String stringify(JSONConfig config) {
		
		return _stringify(config, 0);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param config JSONConfig config for Stringifying.
	 * @param myTabs The ammount of tabs, Should be 0.
	 * @return Returns a stringified JSON String.
	 */
	public String _stringify(JSONConfig config, int myTabs) {
		
		return JSONStringify.Stringify(data, config, myTabs);
		
	};
	
	/**
	 * Create a stringified JSON String,<br>
	 * ignores beautify rules.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	@Override
	public String toString() {
		
		JSONConfig config = new JSONConfig();
		config.tabs(false);
		return toString(config);
		
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @param config JSONConfig config for Stringifying.
	 * @return Returns a stringified JSON String.
	 */
	public String toString(JSONConfig config) {
		
		return stringify(config);
		
	};
	
	/**
	 * Create a byte array from JSON,<br>
	 * ignores beautify rules.
	 * 
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes() {
		
		JSONConfig config = new JSONConfig();
		config.tabs(false);
		return toBytes(config);
		
	};
	
	/**
	 * Create a byte array from JSON.
	 * 
	 * @param config JSONConfig config for Stringifying.
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes(JSONConfig config) {
		
		return stringify(config).getBytes();
		
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
	 * @return All the key's inside the JSONObject.
	 */
	public Set<String> keySet() {
		return new HashSet<String>(data.keySet());
	};
	
	/**
	 * Get Object's from JSONObject.
	 * 
	 * @return All the values inside the JSONObject.
	 */
	public List<Object> values() {
		return new ArrayList<Object>(data.values());
	};
	
	/**
	 * Get number of Keys from JSONObject.
	 * 
	 * @return Total Keys inside the JSONObject.
	 */
	public int size() {
		return data.keySet().size();
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
	 * check if Object from key is a given Class.
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
	 * Get String from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, String when found.
	 */
	public String getString(String key, String def) {
		return (String) get(key, def, String.class);
	};
	
	/**
	 * check if Object from key is a String.
	 * 
	 * @param key key.
	 * @return {@code false} when not a String, {@code true} when a String.
	 */
	public boolean isString(String key) {
		return isValue(key, String.class);
	};
	
	/**
	 * 
	 * check if Object from key is a number,<br>
	 * Integer, Long, Double, Float.
	 * 
	 * @param key key.
	 * @return {@code false} when not a number, {@code true} when a number.
	 */
	public boolean isNumber(String key) {
		return isValue(key, Number.class);
	};
	
	/**
	 * 
	 * check if Object from key is a Long or Integer.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Long or Integer, {@code true} when a Long or Integer.
	 */
	public boolean isWholeNumber(String key) {
		Object oValue = get(key);
		if (oValue == null) return false;
		if (oValue instanceof Long) return true;
		if (oValue instanceof Integer) return true;
		return false;
	};
	
	/**
	 * 
	 * check if Object from key is a Double or Float.
	 * 
	 * @param key key.
	 * @return {@code false} when not a Double or Float, {@code true} when a Double or Float.
	 */
	public boolean isFractionalNumber(String key) {
		Object oValue = get(key);
		if (oValue == null) return false;
		if (oValue instanceof Double) return true;
		if (oValue instanceof Float) return true;
		return false;
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
	 * Get Integer from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Integer when found.
	 */
	public int getInt(String key, int def) {
		return (int) get(key, def, Integer.class);
	};
	
	/**
	 * Get Integer from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @param convert Allow to convert a Long to Integer.
	 * @return {@code def} when not found, Integer when found.
	 */
	public int getInt(String key, int def, boolean convert) {
		Object oValue = get(key, def);
		if (oValue instanceof Integer) return (int) oValue;
		if (convert && (oValue instanceof Long)) return Long.valueOf((long) oValue).intValue();
		return def;
	};
	
	/**
	 * check if Object from key is a Integer.
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
	 * Get Long from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Long when found.
	 */
	public long getLong(String key, long def) {
		return (long) get(key, def, Long.class);
	};
	
	/**
	 * Get Long from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @param convert Allow to convert a Integer to Long.
	 * @return {@code def} when not found, Long when found.
	 */
	public long getLong(String key, long def, boolean convert) {
		Object oValue = get(key, def);
		if (oValue instanceof Long) return (long) oValue;
		if (convert && (oValue instanceof Integer)) return Integer.valueOf((int) oValue).longValue();
		return def;
	};
	
	/**
	 * check if Object from key is a Long.
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
	 * Get Double from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Double when found.
	 */
	public double getDouble(String key, double def) {
		return (double) get(key, def, Double.class);
	};
	
	/**
	 * Get Double from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @param convert Allow to convert a Float to Double.
	 * @return {@code def} when not found, Double when found.
	 */
	public double getDouble(String key, long def, boolean convert) {
		Object oValue = get(key, def);
		if (oValue instanceof Double) return (double) oValue;
		if (convert && (oValue instanceof Float)) return Float.valueOf((float) oValue).doubleValue();
		return def;
	};
	
	/**
	 * check if Object from key is a Double.
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
	 * Get Float from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Float when found.
	 */
	public float getFloat(String key, float def) {
		return (float) get(key, def, Float.class);
	};
	
	/**
	 * Get Float from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @param convert Allow to convert a Double to Float.
	 * @return {@code def} when not found, Float when found.
	 */
	public float getFloat(String key, float def, boolean convert) {
		Object oValue = get(key, def);
		if (oValue instanceof Float) return (float) oValue;
		if (convert && (oValue instanceof Double)) return Double.valueOf((double) oValue).floatValue();
		return def;
	};
	
	/**
	 * check if Object from key is a Float.
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
	 * Get Boolean from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, Boolean when found.
	 */
	public boolean getBoolean(String key, boolean def) {
		return (boolean) get(key, def, Boolean.class);
	};
	
	/**
	 * check if Object from key is a Boolean.
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
	 * Get JSONObject from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, JSONObject when found.
	 */
	public JSONObject getJSONObject(String key, JSONObject def) {
		return (JSONObject) get(key, def, JSONObject.class);
	};
	
	/**
	 * check if Object from key is a JSONObject.
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
	 * Get JSONArray from key.
	 * 
	 * @param key key.
	 * @param def Default return when not found.
	 * @return {@code def} when not found, JSONArray when found.
	 */
	public JSONArray getJSONArray(String key, JSONArray def) {
		return (JSONArray) get(key, def, JSONArray.class);
	};
	
	/**
	 * check if Object from key is a JSONArray.
	 * 
	 * @param key key.
	 * @return {@code false} when not a JSONArray, {@code true} when a JSONArray.
	 */
	public boolean isJSONArray(String key) {
		return isValue(key, JSONArray.class);
	};
	
};