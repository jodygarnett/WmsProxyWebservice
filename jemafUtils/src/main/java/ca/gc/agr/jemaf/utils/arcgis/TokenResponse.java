/**
 * Produced by the AGS Team
 *
 * Crown Copyright 2013. All rights reserved.
 * (c) Her Majesty the Queen in Right of Canada,
 * represented by the Minister of Agriculture &
 * Agri-Food Canada, 2013
 *
 * Droits d'auteur 2013. Tous droits réservés.
 * (C) Sa Majesté la Reine du chef du Canada,
 * représentée par le ministre d'agriculture et
 * agroalimentaire, 2013
 *
 */
package ca.gc.agr.jemaf.utils.arcgis;

import com.google.gson.JsonObject;

import ca.gc.agr.jemaf.gson.JeMafGsonUtils;

/**
 * @author Roberto
 *
 */
public class TokenResponse  {

	/**
	 *  Logger - no need
	 */

	/** static variables ========================================================================
	 */

	/** instance variables ======================================================================
	 */
	
	/** class variables =========================================================================
	 */
	
	// Serialize and Deserialize these fields only
	private Boolean 	success = false;
	private Boolean 	isSSL = false;
	private String  	message = "";
	private String  	url = "";
	private String  	webmap = "";
	private String  	errorMessage = "";
	private String 	 	token = "";
	private Long 		expires = 0l;
	private JsonObject 	info = null;
	private JsonObject 	data = null;

	
	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */

	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToPrettyString(TokenResponse error) {
		return serializeObjectToPrettyJsonString(error);
	}
	
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
	}

	/**
	 * @return the isSSL
	 */
	public Boolean getIsSSL() {
		return isSSL;
	}

	/**
	 * @param isSSL the isSSL to set
	 */
	public void setIsSSL(Boolean isSSL) {
		this.isSSL = isSSL;
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
		this.errorMessage = message;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the webmap
	 */
	public String getWebmap() {
		return webmap;
	}

	/**
	 * @param webmap the webmap to set
	 */
	public void setWebmap(String webmap) {
		this.webmap = webmap;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		this.message = errorMessage;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the expires
	 */
	public Long getExpires() {
		return expires;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(Long expires) {
		this.expires = expires;
	}

	/**
	 * @return the info
	 */
	public JsonObject getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(JsonObject info) {
		this.info = info;
	}

	/**
	 * @return the data
	 */
	public JsonObject getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(JsonObject data) {
		this.data = data;
	}

	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToString(TokenResponse error) {
		return serializeObjectToJsonString(error);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public TokenResponse deserializeJson(String jsonInput) {
		return deserializeJsonToObject(jsonInput);
	}

	/** methods =================================================================================
	 */
	
	// Factories to support automatic serialization and deserialization without knowing Class Type
	public static String serializeObjectToPrettyJsonString(TokenResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonString(error, TokenResponse.class);
	}
	
	public static String serializeObjectToJsonString(TokenResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonString(error, TokenResponse.class);
	}
	
	public static TokenResponse deserializeJsonToObject(String jsonInput) {
		return (TokenResponse)JeMafGsonUtils.deserializeJsonToObject(jsonInput, TokenResponse.class);
	}


	/** inner class ================================================================= 
	 */
	
}
