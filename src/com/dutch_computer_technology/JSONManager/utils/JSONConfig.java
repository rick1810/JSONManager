package com.dutch_computer_technology.JSONManager.utils;

/**
 * Config for Stringifying &amp; Parsing
 */
public class JSONConfig {
	
	private boolean threaded = JSONUtils.threaded();
	private boolean suffix = JSONUtils.suffix();
	private boolean tabs = JSONUtils.tabs();
	private boolean className = JSONUtils.className();
	
	/**
	 * Create a config with Default settings
	 */
	public JSONConfig() {};
	
	/**
	 * Create a config and set it's settings
	 * 
	 * @param suffix {@code true}/{@code false}, Add suffix's behind Value's when stringified
	 */
	public JSONConfig(boolean suffix) {
		
		this.suffix = suffix;
		
	};
	
	/**
	 * Create a config and set it's settings
	 * 
	 * @param suffix {@code true}/{@code false}, Add suffix's behind Value's when stringified
	 * @param tabs {@code true}/{@code false}, Add tabs when stringified
	 */
	public JSONConfig(boolean suffix, boolean tabs) {
		
		this.suffix = suffix;
		this.tabs = tabs;
		
	};
	
	/**
	 * Create a config and set it's settings
	 * 
	 * @param suffix {@code true}/{@code false}, Add suffix's behind Value's when stringified
	 * @param tabs {@code true}/{@code false}, Add tabs when stringified
	 * @param threaded {@code true}/{@code false}, Use Threads to Parse/Stringify JSON
	 */
	public JSONConfig(boolean suffix, boolean tabs, boolean threaded) {
		
		this.suffix = suffix;
		this.tabs = tabs;
		this.threaded = threaded;
		
	};
	
	/**
	 * Create a config and set it's settings
	 * 
	 * @param suffix {@code true}/{@code false}, Add suffix's behind Value's when stringified
	 * @param tabs {@code true}/{@code false}, Add tabs when stringified
	 * @param threaded {@code true}/{@code false}, Use Threads to Parse/Stringify JSON
	 * @param className {@code true}/{@code false}, Add className to Stringified class using toJSON()
	 */
	public JSONConfig(boolean suffix, boolean tabs, boolean threaded, boolean className) {
		
		this.suffix = suffix;
		this.tabs = tabs;
		this.threaded = threaded;
		this.className = className;
		
	};
	
	/**
	 * Enable/Disable Threading for Parsing/Stringifying JSON
	 * 
	 * @param threaded {@code true}/{@code false}
	 */
	public void threaded(boolean threaded) {
		
		this.threaded = threaded;
		
	};
	
	/**
	 * Use Threads to Parse/Stringify JSON
	 * 
	 * @return {@code true} When Threading enabled, {@code false} When using single Thread.
	 */
	public boolean threaded() {
		
		return this.threaded;
		
	};
	
	/**
	 * Add suffix's behind Value's when stringified
	 * 
	 * @param suffix {@code true}/{@code false}
	 */
	public void suffix(boolean suffix) {
		
		this.suffix = suffix;
		
	};
	
	/**
	 * Get if suffix's are added behind Value's when stringified
	 * 
	 * @return {@code true} When adding suffix's, {@code false} When none are added.
	 */
	public boolean suffix() {
		
		return this.suffix;
		
	};
	
	/**
	 * Add tabs when stringified
	 * 
	 * @param tabs {@code true}/{@code false}
	 */
	public void tabs(boolean tabs) {
		
		this.tabs = tabs;
		
	};
	
	/**
	 * Get if tabs are added when stringified
	 * 
	 * @return {@code true} When tabs are added, {@code false} When tabs will not be added.
	 */
	public boolean tabs() {
		
		return this.tabs;
		
	};
	
	/**
	 * Add className when a Class with toJSON is stringified
	 * 
	 * @param className {@code true}/{@code false}
	 */
	public void className(boolean className) {
		
		this.className = className;
		
	};
	
	/**
	 * Get if className is added when a Class with toJSON is stringified
	 * 
	 * @return {@code true} When className is added, {@code false} When className will not be added.
	 */
	public boolean className() {
		
		return this.className;
		
	};
	
};