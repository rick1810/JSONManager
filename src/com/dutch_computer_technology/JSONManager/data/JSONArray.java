package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.List;

import com.dutch_computer_technology.JSONManager.exception.JSONParseException;
import com.dutch_computer_technology.JSONManager.utils.JSONParser;
import com.dutch_computer_technology.JSONManager.utils.JSONStringify;
import com.dutch_computer_technology.JSONManager.utils.JSONUtils;

public class JSONArray {
	
	private List<Object> data;
	
	/**
	 * Create a empty JSONArray.
	 */
	public JSONArray() {
		data = new ArrayList<Object>();
	};
	
	/**
	 * Create &amp; Parse a JSONArray from a Byte array.
	 * 
	 * @param bytes Byte array to be parsed.
	 * @throws JSONParseException
	 */
	public JSONArray(byte[] bytes) throws JSONParseException {
		data = new ArrayList<Object>();
		parse(new String(bytes));
	};
	
	/**
	 * Create &amp; Parse a JSONArray from a String.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public JSONArray(String str) throws JSONParseException {
		data = new ArrayList<Object>();
		parse(str);
	};
	
	/**
	 * Create &amp; Copy a JSONArray from a JSONArray.
	 * 
	 * @param json JSONArray to be copied.
	 */
	public JSONArray(JSONArray json) {
		data = new ArrayList<Object>(json.data);
	};
	
	/**
	 * Parse a String to a JSONArray.
	 * 
	 * @param str String to be parsed.
	 * @throws JSONParseException
	 */
	public void parse(String str) throws JSONParseException {
		new JSONParser(data, str);
	};
	
	/**
	 * Create a stringified JSON String.
	 * @return Returns a stringified JSON String.
	 */
	public String stringify() {
		return toString();
	};
	
	/**
	 * Create a stringified JSON String.
	 * 
	 * @return Returns a stringified JSON String.
	 */
	@Override
	public String toString() {
		
		if (data.isEmpty()) return "[]";
		
		StringBuilder str = new StringBuilder("[");
		int tabs = JSONUtils.beautifyTab();
		JSONUtils.currentTabs += tabs;
		
		for (int i = 0; i < data.size(); i++) {
			
			if (tabs > 0) str.append("\n").append(JSONUtils.beautifyTabs(JSONUtils.currentTabs));
			
			Object oValue = data.get(i);
			str.append(JSONStringify.Stringify(oValue));
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		JSONUtils.currentTabs -= tabs;
		if (tabs > 0) str.append("\n").append(JSONUtils.beautifyTabs(JSONUtils.currentTabs));
		return str.append("]").toString();
		
	};
	
	/**
	 * Create a byte array from JSON.<br>
	 * ignores beautify rules
	 * 
	 * @return Returns a stringified JSON byte array.
	 */
	public byte[] toBytes() {
		
		if (data.isEmpty()) return "[]".getBytes();
		
		StringBuilder str = new StringBuilder("[");
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			str.append(JSONStringify.Stringify(oValue));
			
			if (i < data.size()-1) str.append(",");
			
		};
		
		return str.append("]").toString().getBytes();
		
	};
	
	/**
	 * Check if the JSONArray contains a Object.
	 * 
	 * @param key Object to be searched
	 * @return {@code true} if key found, {@code false} if nothing found.
	 */
	public boolean contains(Object key) {
		return data.contains(key);
	};
	
	/**
	 * Add a Object to the JSONArray.
	 * 
	 * @param value Object to be added.
	 */
	public void add(Object value) {
		add(value, -1);
	};
	
	/**
	 * Add a Object to the JSONArray,<br>
	 * at the given position.<br>
	 * <br>
	 * When the given postion is less then 0,<br>
	 * the Object will be added at the end of the JSONArray.
	 * 
	 * @param value Object to be added.
	 * @param i Position of Object, must be greater or equal to 0.
	 */
	public void add(Object value, int i) {
		if (i > -1) {
			if (i > data.size()) i = data.size();
			data.add(i, value);
			return;
		};
		data.add(value);
	};
	
	/**
	 * Remove a Object from the JSONArray, at the given position.
	 * 
	 * @param i Position of Object.
	 */
	public void delete(int i) {
		if (i < 0 || i > data.size() || data.size() == 0) return;
		data.remove(i);
	};
	
	/**
	 * Remove a Object from the JSONArray.
	 * 
	 * @param value Object to be removed.
	 */
	public void delete(Object value) {
		data.remove(value);
	};
	
	/**
	 * Get ammount of Object in the JSONArray.
	 * 
	 * @return Size/Ammount of Object's/keys.
	 */
	public int size() {
		return data.size();
	};
	
	/**
	 * Create a copy List of the JSONArray Object's.
	 * 
	 * @return A new List of Object's inside of the JSONArray.
	 */
	public List<Object> objs() {
		return new ArrayList<Object>(data);
	};
	
	/**
	 * Create a copy List of the JSONArray Object's.
	 * 
	 * @return A new List of Object's inside of the JSONArray.
	 */
	public List<Object> getObjects() {
		return new ArrayList<Object>(data);
	};
	
	/**
	 * Create a copy List of the JSONArray Object's,
	 * that are part of the given Class.
	 * 
	 * @param cls Class to make a List of.
	 * @return {@code null} When cls is null, A new List of {@code cls} Object's inside of the JSONArray.
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getObjects(Class<?> cls) {
		
		if (cls == null) return null;
		
		List<T> objs = new ArrayList<>();
		if (data.isEmpty()) return objs;
		
		for (Object oValue : data) {
			
			if (oValue == null) continue;
			if (cls.isInstance(oValue)) {
				
				try {
					objs.add((T) cls.cast(oValue));
				} catch(ClassCastException ignore) {};
				
			};
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of String's from JSONArray.
	 * 
	 * @return A new List of String's inside of the JSONArray.
	 */
	public List<String> getStrings() {
		return getObjects(String.class);
	};
	
	/**
	 * Create a List of Boolean's from JSONArray.
	 * 
	 * @return A new List of Boolean's inside of the JSONArray.
	 */
	public List<Boolean> getBooleans() {
		return getObjects(Boolean.class);
	};
	
	/**
	 * Create a List of Integer's from JSONArray.
	 * 
	 * @return A new List of Integer's inside of the JSONArray.
	 */
	public List<Integer> getInts() {
		return getObjects(Integer.class);
	};
	
	/**
	 * Create a List of Long's from JSONArray.
	 * 
	 * @return A new List of Long's inside of the JSONArray.
	 */
	public List<Long> getLongs() {
		return getObjects(Long.class);
	};
	
	/**
	 * Create a List of Float's from JSONArray.
	 * 
	 * @return A new List of Float's inside of the JSONArray.
	 */
	public List<Float> getFloats() {
		return getObjects(Float.class);
	};
	
	/**
	 * Create a List of Double's from JSONArray.
	 * 
	 * @return A new List of Double's inside of the JSONArray.
	 */
	public List<Double> getDoubles() {
		return getObjects(Double.class);
	};
	
	/**
	 * Create a List of JSONObject's from JSONArray.
	 * 
	 * @return A new List of JSONObject's inside of the JSONArray.
	 */
	public List<JSONObject> getJSONObjects() {
		return getObjects(JSONObject.class);
	};
	
	/**
	 * Create a List of JSONArray's from JSONArray.
	 * 
	 * @return A new List of JSONArray's inside of the JSONArray.
	 */
	public List<JSONArray> getJSONArrays() {
		return getObjects(JSONArray.class);
	};
	
	/**
	 * Get Object at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or not found, Object when found.
	 */
	public Object get(int i) {
		return get(i, null, null);
	};
	
	/**
	 * Get Object at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @param def Default return when not found.
	 * @return {@code def} when out of bounds or not found, Object when found.
	 */
	public Object get(int i, Object def) {
		return get(i, def, null);
	};
	
	/**
	 * Get Object from key.
	 * 
	 * @param i Postion of Object.
	 * @param def Default return when not found.
	 * @param cls Class to check for.
	 * @return {@code def} when out of bounds or not found, {@code cls} Object when found.
	 */
	public Object get(int i, Object def, Class<?> cls) {
		if (i < 0 || i > data.size()) return def;
		Object oValue = data.get(i);
		if (oValue == null) return def;
		if (cls == null) return oValue;
		if (cls.isInstance(oValue)) return oValue;
		return def;
	};
	
	/**
	 * Check if Object at given position,<br>
	 * is part of the given Class.<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @param cls Class to check for.
	 * @return {@code false} when out of bounds or not the given Class, {@code true} when the given Class.
	 */
	public boolean isValue(int i, Class<?> cls) {
		if (i < 0 || i > data.size()) return false;
		if (cls == null) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return cls.isInstance(oValue);
	};
	
	/**
	 * Get String at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when not a String, String when found.
	 */
	public String getString(int i) {
		return (String) get(i, null, String.class);
	};
		
	/**
	 * Check if Object at given position is a String,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a String, {@code true} when a String.
	 */
	public boolean isString(int i) {
		return isValue(i, String.class);
	};
	
	/**
	 * Get Integer at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0} when out of bounds or when not a Integer, Integer when found.
	 */
	public int getInt(int i) {
		return (int) get(i, 0, Integer.class);
	};
	
	/**
	 * Check if Object at given position is a Integer,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a Integer, {@code true} when a Integer.
	 */
	public boolean isInt(int i) {
		return isValue(i, Integer.class);
	};
	
	/**
	 * Get Long at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0} when out of bounds or when not a Long, Long when found.
	 */
	public long getLong(int i) {
		return (long) get(i, 0, Long.class);
	};
	
	/**
	 * Check if Object at given position is a Long,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a Long, {@code true} when a Long.
	 */
	public boolean isLong(int i) {
		return isValue(i, Long.class);
	};
	
	/**
	 * Get Double at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0.0} when out of bounds or when not a Double, Double when found.
	 */
	public double getDouble(int i) {
		return (double) get(i, 0.0D, Double.class);
	};
	
	/**
	 * Check if Object at given position is a Double,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a Double, {@code true} when a Double.
	 */
	public boolean isDouble(int i) {
		return isValue(i, Double.class);
	};
	
	/**
	 * Get Float at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0.0} when out of bounds or when not a Float, Float when found.
	 */
	public float getFloat(int i) {
		return (float) get(i, 0.0F, Float.class);
	};
	
	/**
	 * Check if Object at given position is a Float,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a Float, {@code true} when a Float.
	 */
	public boolean isFloat(int i) {
		return isValue(i, Float.class);
	};
	
	/**
	 * Get Boolean at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or when not a Boolean, Boolean when found.
	 */
	public boolean getBoolean(int i) {
		return (boolean) get(i, false, Boolean.class);
	};
	
	/**
	 * Check if Object at given position is a Boolean,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a Boolean, {@code true} when a Boolean.
	 */
	public boolean isBoolean(int i) {
		return isValue(i, Boolean.class);
	};
	
	/**
	 * Get JSONObject at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when not a JSONObject, JSONObject when found.
	 */
	public JSONObject getJSONObject(int i) {
		return (JSONObject) get(i, null, JSONObject.class);
	};
	
	/**
	 * Check if Object at given position is a JSONObject,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a JSONObject, {@code true} when a JSONObject.
	 */
	public boolean isJSONObject(int i) {
		return isValue(i, JSONObject.class);
	};
	
	/**
	 * Get JSONArray at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when not a JSONArray, JSONArray when found.
	 */
	public JSONArray getJSONArray(int i) {
		return (JSONArray) get(i, null, JSONArray.class);
	};
	
	/**
	 * Check if Object at given position is a JSONArray,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or not a JSONArray, {@code true} when a JSONArray.
	 */
	public boolean isJSONArray(int i) {
		return isValue(i, JSONArray.class);
	};
	
};