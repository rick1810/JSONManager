package com.dutch_computer_technology.JSONManager.utils;

public class JSONUtils {
	
	/**
	 * Returns the current version of the JSONManager
	 * 
	 * @return Version of the JSONManager
	 */
	public static String version() {
		return "1.0.8";
	};
	
	/**
	 * Remove special characters before parsing,<br>
	 * Tabs Spaces Newlines
	 * 
	 * @param str String to be sanitized
	 * @return Sanitized String ready to parse
	 */
	public static String sanitize(String str) {
		str = str.replace("\n", "");
		str = str.replace("\b", "");
		str = str.replace("\f", "");
		str = str.replace("\r", "");
		str = str.replace("\t", "");
		String strOut = "";
		boolean open = false;
		for (int i = 0; i < str.length(); i++) {
			char chr = str.charAt(i);
			if (chr == '"') open = !open;
			if (open) {
				strOut += chr;
			} else if (chr != ' ') {
				strOut += chr;
			};
		};
		return strOut;
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