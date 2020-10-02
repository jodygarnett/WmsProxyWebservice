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
public class ArcPortalServiceResponse extends ErrorResponse {

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
	private Boolean mapExist = true;

	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */

	/**
	 * @return the mapExist
	 */
	public Boolean getMapExist() {
		return mapExist;
	}

	/**
	 * @param mapExist the mapExist to set
	 */
	public void setMapExist(Boolean mapExist) {
		this.mapExist = mapExist;
	}



	
	/** methods =================================================================================
	 */
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToPrettyString(ArcPortalServiceResponse error) {
		return serializeObjectToPrettyJsonString(error);
	}

	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToString(ArcPortalServiceResponse error) {
		return serializeObjectToJsonString(error);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public ArcPortalServiceResponse deserializeJson(String jsonInput) {
		return deserializeJsonToObject(jsonInput);
	}
	
	// Factories to support automatic serialization and deserialization without knowing Class Type
	public static String serializeObjectToPrettyJsonString(ArcPortalServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonString(error, ArcPortalServiceResponse.class);
	}
	
	public static String serializeObjectToJsonString(ArcPortalServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonString(error, ArcPortalServiceResponse.class);
	}

	public static byte[] serializeObjectToPrettyJsonByte(ArcPortalServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonByte(error, ArcPortalServiceResponse.class);
	}
	
	public static byte[] serializeObjectToJsonByte(ArcPortalServiceResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonByte(error, ArcPortalServiceResponse.class);
	}
	
	public static ArcPortalServiceResponse deserializeJsonToObject(String jsonInput) {
		return (ArcPortalServiceResponse)JeMafGsonUtils.deserializeJsonToObject(jsonInput, ArcPortalServiceResponse.class);
	}


	/** inner class ================================================================= 
	 */
	
}
