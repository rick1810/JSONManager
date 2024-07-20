package com.dutch_computer_technology.JSONManager;

public class JSONUtils {
	
	/**
	 * Returns the current version of the JSONManager
	 * 
	 * @return Version of the JSONManager
	 */
	public static String version() {
		return "1.0.5";
	};
	
	/**
	 * Escapes special characters for use in JSON
	 * 
	 * @param str String to be escaped
	 * @return Escaped String
	 */
	public static String escape(String str) {
		str = str.replace("\\", "\\\\");
		str = str.replace("/", "\\/");
		str = str.replace("\"", "\\\"");
		str = str.replace("\n", "\\n");
		str = str.replace("\b", "\\b");
		str = str.replace("\f", "\\f");
		str = str.replace("\r", "\\r");
		str = str.replace("\t", "\\t");
		return str;
	};
	
	/**
	 * Unescapes special characters after loaded from JSON
	 * 
	 * @param str String to be unescaped
	 * @return Unescaped String
	 */
	public static String unescape(String str) {
		str = str.replace("\\\\", "\\");
		str = str.replace("\\/", "/");
		str = str.replace("\\\"", "\"");
		str = str.replace("\\n", "\n");
		str = str.replace("\\b", "\b");
		str = str.replace("\\f", "\f");
		str = str.replace("\\r", "\r");
		str = str.replace("\\t", "\t");
		return str;
	};
	
};