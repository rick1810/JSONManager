package com.dutch_computer_technology.JSONManager.exception;

/**
 * When parsing fails.
 */
public class JSONParseException extends Exception {
	
	private static final long serialVersionUID = 522505121917328076L;
	
	/**
	 * Create a JSONParseException
	 * 
	 * @param e Failmessage
	 */
	public JSONParseException(String e) {
		super(e);
	};
	
};