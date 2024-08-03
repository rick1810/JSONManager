package com.dutch_computer_technology.JSONManager.data;

import java.util.ArrayList;
import java.util.List;

import com.dutch_computer_technology.JSONManager.JSONUtils;
import com.dutch_computer_technology.JSONManager.exception.JSONParseException;

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
	 * Create/Copy a JSONArray from a JSONArray.
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
		if (str == null) throw new JSONParseException("Null");
		str = JSONUtils.sanitize(str);
		if (!(str.startsWith("[") && str.endsWith("]"))) throw new JSONParseException("Not a JSONArray");
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
				if (open.length() == 0) continue;
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
		for (String oValue : objs) {
			if (oValue.startsWith("\"") && oValue.endsWith("\"") && oValue.length() > 1) {
				if (oValue.length() == 2) {
					data.add("");
				} else {
					data.add(JSONUtils.unescape(oValue.substring(1, oValue.length()-1)));
				};
				continue;
			};
			if (oValue.startsWith("{") && oValue.endsWith("}")) {
				JSONObject json = new JSONObject();
				json.parse(oValue);
				data.add(json);
				continue;
			};
			if (oValue.startsWith("[") && oValue.endsWith("]")) {
				JSONArray json = new JSONArray();
				json.parse(oValue);
				data.add(json);
				continue;
			};
			if (oValue.equals("true")) {
				data.add(true);
				continue;
			};
			if (oValue.equals("false")) {
				data.add(false);
				continue;
			};
			if (oValue.equals("null")) {
				data.add(null);
				continue;
			};
			if (oValue.matches("(-|)\\d+L")) { //Long
				try {
					data.add(Long.parseLong(oValue.substring(0, oValue.length()-1)));
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException");
				}
			};
			if (oValue.matches("(-|)\\d+\\.?\\d*D")) { //Double
				try {
					data.add(Double.parseDouble(oValue.substring(0, oValue.length()-1)));
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException");
				}
			};
			if (oValue.matches("(-|)\\d+\\.?\\d*F")) { //Float
				try {
					data.add(Float.parseFloat(oValue.substring(0, oValue.length()-1)));
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException");
				}
			};
			if (oValue.matches("(-|)\\d+\\.\\d+")) { //Double
				try {
					data.add(Double.parseDouble(oValue.substring(0, oValue.length()-1)));
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException");
				}
			};
			if (oValue.matches("(-|)\\d+")) { //Int
				try {
					data.add(Integer.parseInt(oValue));
					continue;
				} catch(NumberFormatException e) {
					throw new JSONParseException("NumberFormatException");
				}
			};
			throw new JSONParseException("Unexpected Object");
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
		String str = "[";
		for (int i = 0; i < data.size(); i++) {
			Object oValue = data.get(i);
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
			} else if (oValue instanceof Double) {
				str += Double.toString((double) oValue) + ((!safeMode) ? "D" : "");
			} else if (oValue instanceof Float) {
				str += Float.toString((float) oValue) + ((!safeMode) ? "F" : "");
			} else if (oValue instanceof Boolean) {
				if ((boolean) oValue) {
					str += "true";
				} else {
					str += "false";
				};
			} else {
				if (oValue == null) {
					str += null;
				} else {
					str += "\"" + JSONUtils.escape(oValue.toString()) + "\"";
				};
			};
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
	 * Add a Object to the JSONArray,
	 * at the given position.
	 * 
	 * When the given postion is less then 0,
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
	 * Get Object at given position,
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
	 * Get String at given position,
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
	 * Get Integer at given position,
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
	 * Get Long at given position,
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
	 * Get Double at given position,
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
	 * Get Float at given position,
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
	 * Get Boolean at given position,
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
	 * Get JSONObject at given position,
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
	 * Get JSONArray at given position,
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