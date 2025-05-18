package com.dutch_computer_technology.JSONManager.utils;

/**
 * Utils &amp; Settings
 */
public class JSONUtils {
	
	/**
	 * Utils &amp; Settings
	 */
	public JSONUtils() {};
	
	private static boolean threaded = false;
	private static boolean suffix = false;
	private static boolean tabs = false;
	
	/**
	 * Returns the current version of the JSONManager
	 * 
	 * @return Version of the JSONManager
	 */
	public static String version() {
		
		return "3.0.0";
		
	};
	
	/**
	 * Enable/Disable Threading for Parsing/Stringifying JSON
	 * 
	 * @param threaded {@code true}/{@code false}
	 */
	public static void threaded(boolean threaded) {
		
		JSONUtils.threaded = threaded;
		
	};
	
	/**
	 * Use Threads to Parse/Stringify JSON
	 * 
	 * @return {@code true} When Threading enabled, {@code false} When using single Thread.
	 */
	public static boolean threaded() {
		
		return threaded;
		
	};
	
	/**
	 * Add suffix's behind Value's when stringified
	 * 
	 * @param suffix {@code true}/{@code false}
	 */
	public static void suffix(boolean suffix) {
		
		JSONUtils.suffix = suffix;
		
	};
	
	/**
	 * Get if suffix's are added behind Value's when stringified
	 * 
	 * @return {@code true} When adding suffix's, {@code false} When none are added.
	 */
	public static boolean suffix() {
		
		return suffix;
		
	};
	
	/**
	 * Add tabs when stringified
	 * 
	 * @param tabs {@code true}/{@code false}
	 */
	public static void tabs(boolean tabs) {
		
		JSONUtils.tabs = tabs;
		
	};
	
	/**
	 * Get if tabs are added when stringified
	 * 
	 * @return {@code true} When tabs are added, {@code false} When tabs will not be added.
	 */
	public static boolean tabs() {
		
		return tabs;
		
	};
	
	/**
	 * Returns ammount of tabs
	 * 
	 * @param tabs The ammount needed.
	 * @return The ammount of tabs needed.
	 */
	public static String tabs(int tabs) {
		
		if (tabs <= 0) return "";
		
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < tabs; i++) str.append("\t");
		return str.toString();
		
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
		StringBuilder strOut = new StringBuilder();
		boolean open = false;
		for (int i = 0; i < str.length(); i++) {
			char chr = str.charAt(i);
			if (chr == '"') open = !open;
			if (open) {
				strOut.append(chr);
			} else if (chr != ' ') {
				strOut.append(chr);
			};
		};
		return strOut.toString();
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