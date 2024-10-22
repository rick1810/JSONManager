package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.List;

import com.dutch_computer_technology.JSONManager.exception.JSONParseException;
import com.dutch_computer_technology.JSONManager.utils.JSONParser;
import com.dutch_computer_technology.JSONManager.utils.JSONStringify;

public class JSONArray {
	
	private List<Object> data;
	
	/**
	 * Create a empty JSONArray.
	 */
	public JSONArray() {
		data = new ArrayList<Object>();
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
		String str = "[";
		for (int i = 0; i < data.size(); i++) {
			Object oValue = data.get(i);
			str += JSONStringify.Stringify(oValue, safeMode);
			if (i < data.size()-1) str += ",";
		};
		return str + "]";
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
	
};