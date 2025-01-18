package com.dutch_computer_technology.JSONManager.utils;

public class JSONUtils {
	
	private static boolean suffix = false;
	private static int tab = 0;
	
	public static int currentTabs = 0;
	
	/**
	 * Returns the current version of the JSONManager
	 * 
	 * @return Version of the JSONManager
	 */
	public static String version() {
		return "1.0.9";
	};
	
	/**
	 * Add suffix's behind Value's when Stringified
	 * 
	 * @param suffix {@code true}/{@code false}
	 */
	public static void suffix(boolean suffix) {
		
		JSONUtils.suffix = suffix;
		
	};
	
	/**
	 * Get if suffix's are added behind Value's when Stringified
	 * 
	 * @return {@code true} When adding suffix's, {@code false} When none are added.
	 */
	public static boolean suffix() {
		
		return suffix;
		
	};
	
	/**
	 * Set how many Tab's per JSONObject when Stringified
	 * 
	 * @param tab Tabs per JSONObject, >=0
	 */
	public static void beautifyTab(int tab) {
		
		if (tab < 0) tab = 0;
		JSONUtils.tab = tab;
		
	};
	
	/**
	 * Get how many Tab's per JSONObject when Stringified
	 * 
	 * @return Tab's per JSONObject
	 */
	public static int beautifyTab() {
		
		return tab;
		
	};
	
	/**
	 * Returns Tab's
	 * 
	 * @param tabs Ammount of Tab's
	 * @return Tab's
	 */
	public static String beautifyTabs(int tabs) {
		
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