/**
 * Produced by the AGS Team
 * 
 * Crown Copyright 2012. All rights reserved.
 * (c) Her Majesty the Queen in Right of Canada,
 * represented by the Minister of Agriculture &
 * Agri-Food Canada, 2012
 * 
 * Droits d'auteur 2012. Tous droits réservés.
 * (C) Sa Majesté la Reine du chef du Canada,
 * représentée par le ministre d'agriculture et 
 * agroalimentaire, 2012
 * 
 */
package ca.gc.agr.jemaf.gson;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import ca.gc.agr.jemaf.utils.JeMafUtils;

/**
 * If you plan to extend this class, you will have to override these 3 methods to make sure to 
 * get all getters/setters read/write to the JSON
 * 
 *  serializeToPrettyString
 *  serializeToString
 *  deserializeJson
 *  
 * @author Roberto
 *
 */
@Controller
public class ErrorResponse  {

	/**
	 *  Logger - no need
	 */

	/** static variables ========================================================================
	 */

	/** instance variables ======================================================================
	 */
	// The following will NOT be Serialize and Deserialize 
	@Autowired
	private transient MessageSource  messageSource;
	
	/** class variables =========================================================================
	 */
	
	// Serialize and Deserialize these fields only
	private Boolean success = false;
	private Boolean isError = true;
	private String  message = "";

	// The following will NOT be Serialize and Deserialize 
	private transient Locale  locale = Locale.ENGLISH;
	
	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
		this.isError = !success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the isError
	 */
	public Boolean getIsError() {
		return isError;
	}

	/**
	 * @param isError the isError to set
	 */
	public void setIsError(Boolean isError) {
		this.isError = isError;
		this.success = !isError;
	}

	/**
	 * NB: It will not be used for JSON
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * NB: It will not be used for JSON
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setLocale(String lang) {
		this.locale = JeMafUtils.convertLang(lang);
	}


	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	
	/** methods =================================================================================
	 */
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToPrettyString(ErrorResponse error) {
		return serializeObjectToPrettyJsonString(error);
	}
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToString(ErrorResponse error) {
		return serializeObjectToJsonString(error);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public ErrorResponse deserializeJson(String jsonInput) {
		return deserializeJsonToObject(jsonInput);
	}
	
	// Factories to support automatic serialization and deserialization without knowing Class Type
	public static String serializeObjectToPrettyJsonString(ErrorResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonString(error, ErrorResponse.class);
	}
	
	public static String serializeObjectToJsonString(ErrorResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonString(error, ErrorResponse.class);
	}

	public static byte[] serializeObjectToPrettyJsonByte(ErrorResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonByte(error, ErrorResponse.class);
	}
	
	public static byte[] serializeObjectToJsonByte(ErrorResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonByte(error, ErrorResponse.class);
	}
	
	public static ErrorResponse deserializeJsonToObject(String jsonInput) {
		return (ErrorResponse)JeMafGsonUtils.deserializeJsonToObject(jsonInput, ErrorResponse.class);
	}


	/** inner class ================================================================= 
	 */
	
}
