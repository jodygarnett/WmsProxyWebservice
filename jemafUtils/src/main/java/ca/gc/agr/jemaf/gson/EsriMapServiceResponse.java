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

import org.springframework.stereotype.Controller;

import ca.gc.agr.jemaf.gson.ErrorResponse;
import ca.gc.agr.jemaf.gson.JeMafGsonUtils;

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
public class EsriMapServiceResponse extends ErrorResponse {

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
	private Boolean ssl = false;

	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */
	
	/**
	 * @return the ssl
	 */
	public Boolean getSsl() {
		return ssl;
	}

	/**
	 * @param ssl the ssl to set
	 */
	public void setSsl(Boolean ssl) {
		this.ssl = ssl;
	}

	
	/** methods =================================================================================
	 */
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToPrettyString(EsriMapServiceResponse error) {
		return serializeObjectToPrettyJsonString(error);
	}

	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToString(EsriMapServiceResponse error) {
		return serializeObjectToJsonString(error);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public EsriMapServiceResponse deserializeJson(String jsonInput) {
		return deserializeJsonToObject(jsonInput);
	}
	
	// Factories to support automatic serialization and deserialization without knowing Class Type
	public static String serializeObjectToPrettyJsonString(EsriMapServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonString(error, EsriMapServiceResponse.class);
	}
	
	public static String serializeObjectToJsonString(EsriMapServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonString(error, EsriMapServiceResponse.class);
	}

	public static byte[] serializeObjectToPrettyJsonByte(EsriMapServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonByte(error, EsriMapServiceResponse.class);
	}
	
	public static byte[] serializeObjectToJsonByte(EsriMapServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonByte(error, EsriMapServiceResponse.class);
	}
	
	public static EsriMapServiceResponse deserializeJsonToObject(String jsonInput) {
		return (EsriMapServiceResponse)JeMafGsonUtils.deserializeJsonToObject(jsonInput, EsriMapServiceResponse.class);
	}


	/** inner class ================================================================= 
	 */
	
}
