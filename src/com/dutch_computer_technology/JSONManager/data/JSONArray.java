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
	public List<Object> getObjects() {
		return new ArrayList<Object>(data);
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
	 * Create a List of String's from JSONArray.
	 * 
	 * @return A new List of String's inside of the JSONArray.
	 */
	public List<String> getStrings() {
		
		List<String> objs = new ArrayList<String>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof String) objs.add((String) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of Boolean's from JSONArray.
	 * 
	 * @return A new List of Boolean's inside of the JSONArray.
	 */
	public List<Boolean> getBooleans() {
		
		List<Boolean> objs = new ArrayList<Boolean>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof Boolean) objs.add((boolean) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of Integer's from JSONArray.
	 * 
	 * @return A new List of Integer's inside of the JSONArray.
	 */
	public List<Integer> getInts() {
		
		List<Integer> objs = new ArrayList<Integer>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof Integer) objs.add((int) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of Long's from JSONArray.
	 * 
	 * @return A new List of Long's inside of the JSONArray.
	 */
	public List<Long> getLongs() {
		
		List<Long> objs = new ArrayList<Long>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof Long) objs.add((long) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of Float's from JSONArray.
	 * 
	 * @return A new List of Float's inside of the JSONArray.
	 */
	public List<Float> getFloats() {
		
		List<Float> objs = new ArrayList<Float>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof Float) objs.add((float) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of Double's from JSONArray.
	 * 
	 * @return A new List of Double's inside of the JSONArray.
	 */
	public List<Double> getDoubles() {
		
		List<Double> objs = new ArrayList<Double>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof Double) objs.add((double) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of JSONObject's from JSONArray.
	 * 
	 * @return A new List of JSONObject's inside of the JSONArray.
	 */
	public List<JSONObject> getJSONObjects() {
		
		List<JSONObject> objs = new ArrayList<JSONObject>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof JSONObject) objs.add((JSONObject) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Create a List of JSONArray's from JSONArray.
	 * 
	 * @return A new List of JSONArray's inside of the JSONArray.
	 */
	public List<JSONArray> getJSONArrays() {
		
		List<JSONArray> objs = new ArrayList<JSONArray>();
		if (data.isEmpty()) return objs;
		
		for (int i = 0; i < data.size(); i++) {
			
			Object oValue = data.get(i);
			if (oValue == null) continue;
			if (oValue instanceof JSONArray) objs.add((JSONArray) oValue);
			
		};
		
		return objs;
		
	};
	
	/**
	 * Get Object at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds, Object when in bounds.
	 */
	public Object get(int i) {
		if (i < 0 || i > data.size()) return null;
		return data.get(i);
	};
	
	/**
	 * Get String at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when Object is not instanceof String, String when in bounds.
	 */
	public String getString(int i) {
		if (i < 0 || i > data.size()) return null;
		Object oValue = data.get(i);
		if (oValue == null) return null;
		if (oValue instanceof String) return (String) oValue;
		return null;
	};
	
	/**
	 * Check if value from key is a String.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a String, {@code true} when a String.
	 */
	public boolean isString(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof String);
	};
	
	/**
	 * Get Integer at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0} when out of bounds or when Object is not instanceof Integer, Integer when in bounds.
	 */
	public int getInt(int i) {
		if (i < 0 || i > data.size()) return 0;
		Object oValue = data.get(i);
		if (oValue == null) return 0;
		if (oValue instanceof Integer) return (int) oValue;
		return 0;
	};
	
	/**
	 * Check if value from key is a Integer.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a Integer, {@code true} when a Integer.
	 */
	public boolean isInt(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof Integer);
	};
	
	/**
	 * Get Long at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0} when out of bounds or when Object is not instanceof Long, Long when in bounds.
	 */
	public long getLong(int i) {
		if (i < 0 || i > data.size()) return 0;
		Object oValue = data.get(i);
		if (oValue == null) return 0;
		if (oValue instanceof Long) return (long) oValue;
		return 0;
	};
	
	/**
	 * Check if value from key is a Long.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a Long, {@code true} when a Long.
	 */
	public boolean isLong(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof Long);
	};
	
	/**
	 * Get Double at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0.0} when out of bounds or when Object is not instanceof Double, Double when in bounds.
	 */
	public double getDouble(int i) {
		if (i < 0 || i > data.size()) return 0.0D;
		Object oValue = data.get(i);
		if (oValue == null) return 0.0D;
		if (oValue instanceof Double) return (double) oValue;
		return 0.0D;
	};
	
	/**
	 * Check if value from key is a Double.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a Double, {@code true} when a Double.
	 */
	public boolean isDouble(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof Double);
	};
	
	/**
	 * Get Float at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code 0.0} when out of bounds or when Object is not instanceof Float, Float when in bounds.
	 */
	public float getFloat(int i) {
		if (i < 0 || i > data.size()) return 0.0F;
		Object oValue = data.get(i);
		if (oValue == null) return 0.0F;
		if (oValue instanceof Float) return (float) oValue;
		return 0.0F;
	};
	
	/**
	 * Check if value from key is a Float.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a Float, {@code true} when a Float.
	 */
	public boolean isFloat(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof Float);
	};
	
	/**
	 * Get Boolean at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when out of bounds or when Object is not instanceof Boolean, Boolean when in bounds.
	 */
	public boolean getBoolean(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		if (oValue instanceof Boolean) return (Boolean) oValue;
		return false;
	};
	
	/**
	 * Check if value from key is a Boolean.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a Boolean, {@code true} when a Boolean.
	 */
	public boolean isBoolean(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof Boolean);
	};
	
	/**
	 * Get JSONObject at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when Object is not instanceof JSONObject, JSONObject when in bounds.
	 */
	public JSONObject getJSONObject(int i) {
		if (i < 0 || i > data.size()) return null;
		Object oValue = data.get(i);
		if (oValue == null) return null;
		if (oValue instanceof JSONObject) return (JSONObject) oValue;
		return null;
	};
	
	/**
	 * Check if value from key is a JSONObject.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a JSONObject, {@code true} when a JSONObject.
	 */
	public boolean isJSONObject(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof JSONObject);
	};
	
	/**
	 * Get JSONArray at given position,<br>
	 * must be inbound 0-size.
	 * 
	 * @param i Postion of Object.
	 * @return {@code null} when out of bounds or when Object is not instanceof JSONArray, JSONArray when in bounds.
	 */
	public JSONArray getJSONArray(int i) {
		if (i < 0 || i > data.size()) return null;
		Object oValue = data.get(i);
		if (oValue == null) return null;
		if (oValue instanceof JSONArray) return (JSONArray) oValue;
		return null;
	};
	
	/**
	 * Check if value from key is a JSONArray.
	 * 
	 * @param i Postion of Object.
	 * @return {@code false} when not a JSONArray, {@code true} when a JSONArray.
	 */
	public boolean isJSONArray(int i) {
		if (i < 0 || i > data.size()) return false;
		Object oValue = data.get(i);
		if (oValue == null) return false;
		return (oValue instanceof JSONArray);
	};
	
};