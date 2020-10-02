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
public class LayerResponse  {

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
	private String 		serviceType = "";
	private Boolean		serviceTypeWMS = false;
	private String 		serviceTypeExtraMessage = "";
	private Boolean 	success = false;
	private Boolean 	isSSL = false;
	private	String 		serviceUrl = "";
	private String		mapInfo = "";
	private JsonObject	layerInfo = null;

	
	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceTypeWMS
	 */
	public Boolean getServiceTypeWMS() {
		return serviceTypeWMS;
	}

	/**
	 * @param serviceTypeWMS the serviceTypeWMS to set
	 */
	public void setServiceTypeWMS(Boolean serviceTypeWMS) {
		this.serviceTypeWMS = serviceTypeWMS;
	}

	/**
	 * @return the serviceTypeExtraMessage
	 */
	public String getServiceTypeExtraMessage() {
		return serviceTypeExtraMessage;
	}

	/**
	 * @param serviceTypeExtraMessage the serviceTypeExtraMessage to set
	 */
	public void setServiceTypeExtraMessage(String serviceTypeExtraMessage) {
		this.serviceTypeExtraMessage = serviceTypeExtraMessage;
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
	 * @return the serviceUrl
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * @param serviceUrl the serviceUrl to set
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * @return the layerInfo
	 */
	public JsonObject getLayerInfo() {
		return layerInfo;
	}

	/**
	 * @param layerInfo the layerInfo to set
	 */
	public void setLayerInfo(JsonObject layerInfo) {
		this.layerInfo = layerInfo;
	}


	/**
	 * @return the mapInfo
	 */
	public String getMapInfo() {
		return mapInfo;
	}

	/**
	 * @param mapInfo the mapInfo to set
	 */
	public void setMapInfo(String mapInfo) {
		this.mapInfo = mapInfo;
	}


	/** methods =================================================================================
	 */
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToPrettyString(LayerResponse error) {
		return serializeObjectToPrettyJsonString(error);
	}
	
	/**
	 * 
	 * @param error
	 * @return
	 */
	public String serializeToString(LayerResponse error) {
		return serializeObjectToJsonString(error);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 */
	public LayerResponse deserializeJson(String jsonInput) {
		return deserializeJsonToObject(jsonInput);
	}
	
	// Factories to support automatic serialization and deserialization without knowing Class Type
	public static String serializeObjectToPrettyJsonString(LayerResponse error) {
		return JeMafGsonUtils.serializeObjectToPrettyJsonString(error, LayerResponse.class);
	}
	
	public static String serializeObjectToJsonString(LayerResponse error) {
		return JeMafGsonUtils.serializeObjectToJsonString(error, LayerResponse.class);
	}
	
	public static LayerResponse deserializeJsonToObject(String jsonInput) {
		return (LayerResponse)JeMafGsonUtils.deserializeJsonToObject(jsonInput, LayerResponse.class);
	}


	/** inner class ================================================================= 
	 */
	
}
