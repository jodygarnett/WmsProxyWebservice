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

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
/**
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/

import ca.gc.agr.jemaf.gson.JeMafGsonUtils;
import ca.gc.agr.jemaf.utils.JeMafUtils;
import ca.gc.agr.jemaf.utils.http.JeMafHttpUtils;
import ca.gc.agr.jemaf.utils.http.URLResponse;
import ca.gc.agr.jemaf.utils.net.ReadPageSizeExceedException;
import ca.gc.agr.jemaf.utils.net.TimeoutException;

public class TokenWebApplication implements Serializable {
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(TokenWebApplication.class);
	
	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = -2867612923183915482L;
	
	static {
		// This ignores the host name verification for the Public Certificate used by the Server 
	      System.setProperty("weblogic.security.SSL.ignoreHostnameVerification","true");
	}
	
	private static final String ARCGIS_CONTENT_URL = "http://www.arcgis.com/sharing/rest/content/items/";
	public  static final String ESRI_JSON_FORMAT_PARAM = "f=json";
	
	public  static final String JSON_SERVICE_TYPE = "serviceType";
	public  static final String JSON_SUCCESS = "success";
	public  static final String JSON_IS_SSL  = "isSSL";
	public  static final String JSON_TYPE_WMS = "serviceTypeWMS";
	public  static final String JSON_SERVICE_TYPE_MSG = "serviceTypeExtraMessage";
		
	private static final String ESRI_TOKEN_PARAM = "token=";
	private static final String HTTP_METHOD_GET = "GET";
	private static final String JSON_ERROR_PARENT_NAME = "error";
	private static final String JSON_ERROR_CODE_ATTR = "code";
	private static final String	JSON_ERROR_MESSAGE_ATTR = "message";

	private String token = null;
	private long expires = 0l;
	private boolean isSSL = false;
	private boolean isSuccess = false;
	private String webApplication = "";
	private String errorMessage = "";
	private String message = "";
	private String url = "";
	private JsonObject info = null;
	private JsonObject data = null;

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
	public long getExpires() {
		return expires;
	}
	/**
	 * @param expires the expires to set
	 */
	public void setExpires(long expires) {
		this.expires = expires;
	}
	/**
	 * @return the isSSL
	 */
	public boolean isSSL() {
		return isSSL;
	}
	/**
	 * @param isSSL the isSSL to set
	 */
	public void setSSL(boolean isSSL) {
		this.isSSL = isSSL;
	}
	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * @return the webApplication
	 */
	public String getWebApplication() {
		return webApplication;
	}
	/**
	 * @param webApplication the webApplication to set
	 */
	public void setWebApplication(String webApplication) {
		this.webApplication = webApplication;
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setMessage(String errorMessage) {
		this.message = errorMessage;
		this.errorMessage = errorMessage;
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
	/*
	 * Create JSON out to reflect the this object
	 */
	public static String createJson(TokenWebApplication tokenWeb, boolean pretty) {
		TokenResponse tokenErrorResponse = new TokenResponse();
		tokenErrorResponse.setSuccess( tokenWeb.isSuccess() );
		tokenErrorResponse.setIsSSL( tokenWeb.isSSL() );
		tokenErrorResponse.setUrl( tokenWeb.getUrl() );
		tokenErrorResponse.setWebmap( (tokenWeb.getWebApplication() == null ? "" : tokenWeb.getWebApplication()) );

		if ( tokenWeb.isSuccess() ) {
			tokenErrorResponse.setExpires( tokenWeb.getExpires() );
			tokenErrorResponse.setToken( tokenWeb.getToken() );
			if ( tokenWeb.getInfo() != null ) {
				tokenErrorResponse.setInfo( tokenWeb.getInfo() );
			}
			if ( tokenWeb.getData() != null ) {
				tokenErrorResponse.setData( tokenWeb.getData() );
			}
		}
		else {
			tokenErrorResponse.setMessage( (tokenWeb.getErrorMessage() == null ? "" : tokenWeb.getErrorMessage()) );
		}

		if ( pretty ) {
			return TokenResponse.serializeObjectToPrettyJsonString(tokenErrorResponse);
		}
		else {
			return TokenResponse.serializeObjectToJsonString(tokenErrorResponse);
		}
	}

    public static boolean isWebApplicationSecured(String webAppdID) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
    	boolean isSecured = false;
    	String url = ARCGIS_CONTENT_URL + webAppdID + "?" + ESRI_JSON_FORMAT_PARAM;

    	try {
    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
	    	URLResponse jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
	    																	"", 
	    																	HTTP_METHOD_GET, 
	    																	"", 
	    																	-1, 
	    																	30000);
	    	try {
	    		JsonObject json = JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) );
	    		JsonObject jsonError = JeMafGsonUtils.getPropertyJsonObjectValue(json, JSON_ERROR_PARENT_NAME);
	    		if ( jsonError != null ) {
	    			Long value = JeMafGsonUtils.getPropertyLongValue(jsonError, JSON_ERROR_CODE_ATTR);
	    			if ( value != null && value == 403 ) {
	    				isSecured = true;
	    			}
	    		}
	    	}
	    	catch (Exception ex) {}
    	}
    	catch (Exception io) {}
    	
    	return isSecured;
    }
    
    public static boolean isWebApplicationExist(String webAppdID) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
    	boolean isExist = false;
    	String url = ARCGIS_CONTENT_URL + webAppdID + "?" + ESRI_JSON_FORMAT_PARAM;

    	try {
    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
	    	URLResponse jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
	    																	"", 
	    																	HTTP_METHOD_GET, 
	    																	"", 
	    																	-1, 
	    																	30000);

    		JsonObject json = JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) );
    		JsonObject jsonError = JeMafGsonUtils.getPropertyJsonObjectValue(json, JSON_ERROR_PARENT_NAME);
    		if ( jsonError != null ) {
    			Long value = JeMafGsonUtils.getPropertyLongValue(jsonError, JSON_ERROR_CODE_ATTR);
    			if ( value != null && value == 403 ) {
    				isExist = true; // Indicating secure means the Web Application Exist
    			}
    		}
    		else {
    			isExist = true;
    		}
    	}
    	catch (Exception io) {}
    	
    	return isExist;
    }
    
	public static TokenWebApplication extractWebApplicationCredential(String webAppdID, String username, String password) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
		return extractWebApplicationCredential(webAppdID, username, password, true);
	}

	public static TokenWebApplication extractWebApplicationCredential(String webAppdID, String username, String password, boolean addLayoutInfo) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
		TokenWebApplication tokenWebApp = new TokenWebApplication();

		String url = "https://www.arcgis.com/sharing/generateToken?referer=http://www.agr.gc.ca/index_e.php?webmap=" + webAppdID + "&expiration=60&" + ESRI_JSON_FORMAT_PARAM + "&password=" + password + "&request=getToken&username=" + username;
		tokenWebApp.setWebApplication(webAppdID);
		tokenWebApp.setUrl(url);

		try {
    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
			URLResponse jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
																				"", 
																				HTTP_METHOD_GET, 
																				"", 
																				-1, 
																				30000);
			JsonObject json = null;

			try {
				json = JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) );
				
				if ( json == null ) {
					tokenWebApp.setSuccess( false );
					tokenWebApp.setErrorMessage("The JSON is not valid");
				}
				else {
				
					if ( JeMafGsonUtils.isPropertyExist(json, JSON_ERROR_PARENT_NAME) ) {
						JsonObject jsonErrorCode = JeMafGsonUtils.getPropertyJsonObjectValue(json, JSON_ERROR_PARENT_NAME);
						if ( jsonErrorCode != null ) {
							String errorCode = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_CODE_ATTR);
							String message = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_MESSAGE_ATTR);
							if ( ! JeMafUtils.isEmptyString(errorCode) ) {
								errorCode = errorCode.trim().toUpperCase();
								if ( errorCode.equals("498") ||	errorCode.equals("499")) {
									tokenWebApp.setSSL( true );
								}
							}
							tokenWebApp.setSuccess( false );
							tokenWebApp.setMessage(
									"Code #" + errorCode + " " + message );
						}
					}
					else {
						tokenWebApp.setToken( JeMafGsonUtils.getPropertyStringValue(json, "token") );
						tokenWebApp.setExpires( JeMafGsonUtils.getPropertyLongValue(json, "expires") );
						tokenWebApp.setSSL( JeMafGsonUtils.getPropertyBooleanValue(json, "ssl")  );
						tokenWebApp.setSuccess( true );
						
			    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
						url = ARCGIS_CONTENT_URL + tokenWebApp.getWebApplication() + "?" + ESRI_JSON_FORMAT_PARAM + "&" + ESRI_TOKEN_PARAM + tokenWebApp.getToken();
						jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
																			"", 
																			HTTP_METHOD_GET, 
																			"", 
																			-1, 
																			30000);

						// "info"
						tokenWebApp.setInfo( JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) ) );

			    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
						// "data"
						url = ARCGIS_CONTENT_URL + tokenWebApp.getWebApplication() + "/data?" + ESRI_JSON_FORMAT_PARAM + "&" + ESRI_TOKEN_PARAM + tokenWebApp.getToken();
						jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
																				"", 
																				HTTP_METHOD_GET, 
																				"", 
																				-1, 
																				30000);

						tokenWebApp.setData( JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) ) );

						if ( tokenWebApp.getData() != null ) {
							try {
								JsonObject baseMap =  JeMafGsonUtils.getPropertyJsonObjectValue( tokenWebApp.getData(), "baseMap");
								if ( baseMap != null ) {
									JsonArray baseMapLayers = JeMafGsonUtils.getPropertyJsonArrayValue( baseMap, "baseMapLayers");
									List<LayerResponse> lstLayers = readLayerInfos(baseMapLayers, tokenWebApp.getToken(), addLayoutInfo);
									
									for (int i=0; i<baseMapLayers.size(); i++) {
										JsonObject item = baseMapLayers.get(i).getAsJsonObject();
										item.add("layerInfo", lstLayers.get(i).getLayerInfo());
									}
								}
							}
							catch (Exception ex3) {
								LOGGER.error("Can't parse Base Map Layers", ex3);
							}

							try {
								JsonArray operatioalLayers = JeMafGsonUtils.getPropertyJsonArrayValue( tokenWebApp.getData(), "operationalLayers");
								List<LayerResponse> lstLayers = readLayerInfos(operatioalLayers, tokenWebApp.getToken(), addLayoutInfo);
								for (int i=0; i<operatioalLayers.size(); i++) {
									JsonObject item = operatioalLayers.get(i).getAsJsonObject();
									item.add("layerInfo", lstLayers.get(i).getLayerInfo());
								}
							}
							catch (Exception ex3) {
								LOGGER.error("Can't parse Operational Layers", ex3);
							}
						}
					}
				}
			}
			catch (Exception ex) {
				tokenWebApp.setSuccess( false );
				tokenWebApp.setErrorMessage( "An error occurred during requesting layers information and/or parsing JSON from layers" );
				LOGGER.error( tokenWebApp.getMessage() , ex);
			}
		}
		catch (Exception ex) {
			tokenWebApp.setSuccess( false );
			tokenWebApp.setErrorMessage( "An error occurred during requesting and/or parsing JSON from the Web Application" );
			LOGGER.error(tokenWebApp.getMessage(), ex);
		}

		return tokenWebApp;
	}



	public static List<LayerResponse> readLayerInfos(JsonArray arList, String token, boolean addLayoutInfo) {
		if ( arList == null ) return null;
		
		List<LayerResponse> layerList = new ArrayList<LayerResponse>( arList.size() );
		
		for (int i=0; i<arList.size(); i++) {
			JsonObject item = arList.get(i).getAsJsonObject();
			if ( JeMafGsonUtils.isPropertyExist(item, "url") ) {
				String url = JeMafGsonUtils.getPropertyStringValue(item, "url");
				LayerResponse layerResponse = 
						readLayerInfo( url + "?" + ESRI_JSON_FORMAT_PARAM + "&" + ESRI_TOKEN_PARAM + token, 
									   addLayoutInfo);
				layerResponse.setMapInfo( url );
				layerList.add(layerResponse);
			}
		}
		
		return layerList;
	}
	
	public static LayerResponse readMapServiceLayerInfo(String urlInfo, boolean addLayoutInfo) {
		if ( urlInfo == null ) {
			LayerResponse jsonLayer = new LayerResponse();
			jsonLayer.setSuccess(false);
			jsonLayer.setServiceTypeExtraMessage("There is no URL specified");
			return jsonLayer;
		}
		else {
			return readLayerInfo(urlInfo + "?" + ESRI_JSON_FORMAT_PARAM, addLayoutInfo);
		}
	}

	public static LayerResponse readLayerInfo(String urlInfo, boolean addLayoutInfo) {
		
		LayerResponse jsonLayer = new LayerResponse();
		jsonLayer.setServiceUrl( urlInfo );

		/**
		 * ArcGISDynamicMapServiceLayer
		 * ArcGISImageServiceLayer
		 * ArcGISTiledMapServiceLayer 
		 */
		jsonLayer.setServiceType( MapServiceTypeEnum.ARCGISDYNAMICMAPSERVICELAYER.toString() );
		jsonLayer.setServiceTypeWMS( false );
		jsonLayer.setSuccess( true );
		jsonLayer.setIsSSL( false );


		try {
    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
			URLResponse jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(urlInfo), 
																				"", 
																				HTTP_METHOD_GET, 
																				"", 
																				-1, 
																				30000);

			String jsonLiteral = new String(jsonResponse.getRawResponseData());

			if ( jsonLiteral != null && jsonLiteral.startsWith("{") ) {
				JsonObject jsonInfo = JeMafGsonUtils.pureParser(jsonLiteral);
				if ( jsonInfo != null ) {
					if ( JeMafGsonUtils.isPropertyExist(jsonInfo, JSON_ERROR_PARENT_NAME) ) {
						JsonObject jsonErrorCode = JeMafGsonUtils.getPropertyJsonObjectValue(jsonInfo, JSON_ERROR_PARENT_NAME);
						if ( jsonErrorCode != null ) {
							String errorCode = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_CODE_ATTR);
							String message = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_MESSAGE_ATTR);
							if ( ! JeMafUtils.isEmptyString(errorCode) ) {
								errorCode = errorCode.trim().toUpperCase();
								if ( errorCode.equals("498") ||	errorCode.equals("499")) {
									// In this case, try without a TOKEN
									if ( urlInfo.indexOf("&token") != -1 ) {
										urlInfo = urlInfo.substring(0, urlInfo.indexOf("&token") );
										jsonLayer = readLayerInfo(urlInfo, addLayoutInfo);
										if ( addLayoutInfo ) {
											jsonLayer.setLayerInfo( jsonLayer.getLayerInfo() );
										}
									}
									else {
										jsonLayer.setSuccess( false );
										jsonLayer.setServiceTypeExtraMessage(
												"Error code #" + errorCode + ": " + message );
									}
									jsonLayer.setIsSSL( true );
								}
								else {
									jsonLayer.setSuccess( false );
									jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString() );
									jsonLayer.setServiceTypeExtraMessage(
											"Error code #" + errorCode + ": " + message );
								}
							}
							else {
								jsonLayer.setSuccess( false );
								jsonLayer.setServiceTypeExtraMessage(
										"There is no error code eventhough there is a error property" );
							}
						}
						else {
							jsonLayer.setSuccess( false );
							jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString() );
							jsonLayer.setServiceTypeExtraMessage(
									"There is no error object eventhough there is a error property" );
						}
					}
					else {
						boolean tileInfoExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "tileInfo");
						boolean serviceDataTypeExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "serviceDataType");
						boolean typeExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "type");
						boolean capabilitiesExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "capabilities");
						boolean layersExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "layers");
						boolean operationLayersExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "operationLayers");
						boolean mapNameExist = JeMafGsonUtils.isPropertyExist(jsonInfo, "mapName");

						JsonObject tileInfo = JeMafGsonUtils.getPropertyJsonObjectValue(jsonInfo, "tileInfo");
						String serviceDataType = JeMafGsonUtils.getPropertyStringValue(jsonInfo, "serviceDataType");
						String type = JeMafGsonUtils.getPropertyStringValue(jsonInfo, "type");
						String capabilities = JeMafGsonUtils.getPropertyStringValue(jsonInfo, "capabilities");
						JsonArray layers = JeMafGsonUtils.getPropertyJsonArrayValue(jsonInfo, "layers");
						
						if ( tileInfoExist ) {
							if ( tileInfo != null ) {
								jsonLayer.setServiceType( MapServiceTypeEnum.ARCGISTILEDMAPSERVICELAYER.toString() );
							}
						}
						else if ( serviceDataTypeExist ) {
							if ( serviceDataType != null &&
								 serviceDataType.trim().toUpperCase().startsWith("ESRIIMAGESERVICEDATATYPE") || 
								 serviceDataType.trim().toUpperCase().startsWith("ESRIIMAGESERVICEDATATYPEGENERIC") ) {
								jsonLayer.setServiceType( MapServiceTypeEnum.ARCGISIMAGESERVICELAYER.toString() );
							}
						}
						else if ( typeExist || capabilitiesExist ) {
							if ( typeExist ) {
								if ( type != null && type.trim().toUpperCase().indexOf("FEATURE LAYER") != -1 ) {
									jsonLayer.setServiceType( MapServiceTypeEnum.FEATURELAYER.toString() );
								}
							}
							if ( capabilitiesExist ) {
								if ( capabilities != null && capabilities.trim().toUpperCase().indexOf("DATA")  == -1 ) {
									if ( capabilities.trim().toUpperCase().indexOf("QUERY") != -1 ||
										 capabilities.trim().toUpperCase().indexOf("EDITING") != -1 ) {
										jsonLayer.setServiceType( MapServiceTypeEnum.FEATURELAYER.toString() );
									}
								}
							}
						}
						else {
							if ( !layersExist && !operationLayersExist ) {
								jsonLayer.setSuccess( false );
								jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString() );
							}
							else if ( layersExist && layers != null && layers.size() > 0 ) {
								jsonLayer.setServiceType( MapServiceTypeEnum.FEATURESERVICE.toString() );
							}
							else if ( !mapNameExist ) {
								jsonLayer.setSuccess( false );
								jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString() );
								jsonLayer.setServiceTypeExtraMessage( "There is no layers to use at this location" );
							}
						}

						if ( addLayoutInfo ) {
							jsonLayer.setLayerInfo( jsonInfo );
						}	
					}
				}
				else {
					jsonLayer.setSuccess( false );
					jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString() );
					jsonLayer.setServiceTypeExtraMessage("Invalid JSON");
					LOGGER.warn(jsonLayer.getServiceTypeExtraMessage());
				}
			}
			else if ( jsonLiteral != null && jsonLiteral.trim().startsWith("<?xml") ) {
				jsonLayer.setServiceTypeWMS( true );
				jsonLayer.setServiceType( MapServiceTypeEnum.WMSLAYER.toString() );
				jsonLayer.setServiceTypeExtraMessage( "There is no JSON available since it is a WMS GetCapabilities" );
				LOGGER.warn(jsonLayer.getServiceTypeExtraMessage());
			}
		}
		catch (Exception ex3) {
			jsonLayer.setSuccess( false );
			jsonLayer.setServiceType( MapServiceTypeEnum.NONE.toString());
			jsonLayer.setServiceTypeExtraMessage( "An error occured in trying to get service information" );
			LOGGER.error(jsonLayer.getServiceTypeExtraMessage(), ex3);
		}

		return jsonLayer;
	}
	
	public static TokenWebApplication generateToken(String username, String password, String refererer) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
		TokenWebApplication tokenWebApp = new TokenWebApplication();

		if ( refererer != null ) {
			// Only work with the first one
			refererer = extractDomains(refererer).get(0); 
		}
		else {
			refererer = "/";
		}
		String url = "https://www.arcgis.com/sharing/generateToken?referer=" + refererer + "&client=referrer&expiration=60&" + ESRI_JSON_FORMAT_PARAM + "&password=" + password + "&request=getToken&username=" + username;
		tokenWebApp.setUrl(url);

		try {
    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
			URLResponse jsonResponse = JeMafHttpUtils.callSyncWebServices(new URL(url), 
																		  "", 
																		  HTTP_METHOD_GET, 
																		  "", 
																		  -1, 
																		  30000);
			JsonObject json = null;

			try {
				json = JeMafGsonUtils.pureParser( new String(jsonResponse.getRawResponseData()) );
				
				if ( json == null ) {
					tokenWebApp.setSuccess( false );
					tokenWebApp.setErrorMessage("The JSON is not valid");
				}
				else {
				
					if ( JeMafGsonUtils.isPropertyExist(json, JSON_ERROR_PARENT_NAME) ) {
						JsonObject jsonErrorCode = JeMafGsonUtils.getPropertyJsonObjectValue(json, JSON_ERROR_PARENT_NAME);
						if ( jsonErrorCode != null ) {
							String errorCode = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_CODE_ATTR);
							String message = JeMafGsonUtils.getPropertyStringValue(jsonErrorCode, JSON_ERROR_MESSAGE_ATTR);
							if ( ! JeMafUtils.isEmptyString(errorCode) ) {
								errorCode = errorCode.trim().toUpperCase();
								if ( errorCode.equals("498") ||	errorCode.equals("499")) {
									tokenWebApp.setSSL( true );
								}
							}
							tokenWebApp.setSuccess( false );
							tokenWebApp.setMessage(
									"Code #" + errorCode + " " + message );
						}
					}
					else {
						tokenWebApp.setToken( JeMafGsonUtils.getPropertyStringValue(json, "token") );
						tokenWebApp.setExpires( JeMafGsonUtils.getPropertyLongValue(json, "expires") );
						tokenWebApp.setSSL( JeMafGsonUtils.getPropertyBooleanValue(json, "ssl")  );
						tokenWebApp.setSuccess( true );
					}
				}
			}
			catch (Exception ex) {
				tokenWebApp.setSuccess( false );
				tokenWebApp.setErrorMessage( "An error occurred during requesting layers information and/or parsing JSON from layers" );
				LOGGER.error( tokenWebApp.getMessage() , ex);
			}
		}
		catch (Exception ex) {
			tokenWebApp.setSuccess( false );
			tokenWebApp.setErrorMessage( "An error occurred during requesting and/or parsing JSON from the Web Application" );
			LOGGER.error(tokenWebApp.getMessage(), ex);
		}

		return tokenWebApp;
	}
	
	public static List<String> extractDomains(String value){
	    List<String> result = new ArrayList<String>();
	    if (value != null) {
	    	String[] domains = value.split(" ");
	    	for (String domain: domains) {
	    		// Find the very '/' and not '//"
	    		domain = domain.trim();
	    		int fromIndex = 0;
	    		int pos = -1;
	    		while ( (pos = domain.indexOf("/", fromIndex)) != -1 ) {
	    			// If there is a '/' after, it means we are facing a // domain, keep going the
	    			if ( pos < domain.length()-1 && domain.charAt(pos+1) == '/') {
	    				fromIndex = pos+1; // keep going to look for
	    			}
	    			else if ( pos < domain.length()-1 && domain.charAt(pos-1) == '/') {
	    				fromIndex = pos+1; // keep going to look for
	    			}
	    			else {
	    				domain = domain.substring(0, pos);
	    				break;
	    			}
	    		}
	    		if ( !domain.endsWith("/") ) {
	    			domain += "/";
	    		}
	    		result.add( domain );
	    	}
	    }
	    return result;
	}


	public static void main(String[] args) throws MalformedURLException, IOException, ReadPageSizeExceedException, TimeoutException {
		List<String> resultDomains = extractDomains(
													"http://www.agr.gc.ca/index.html " + 
													"www.agr.gc.ca " +
													"www4.agr.gc.ca " + 
													"http://www.agr.gc.ca " +
													"https://www4.agr.gc.ca " + 
													"www.agr.gc.ca/ " +
													"www4.agr.gc.ca/ " + 
													"http://www.agr.gc.ca/ " +
													"https://www4.agr.gc.ca/ " +
													"www.agr.gc.ca:8080 " +
													"www4.agr.gc.ca:8080 " + 
													"http://www.agr.gc.ca:8080 " +
													"https://www4.agr.gc.ca:8080 " + 
													"www.agr.gc.ca:8080/ " +
													"www4.agr.gc.ca:8080/ " + 
													"http://www.agr.gc.ca:8080/ " +
													"https://www4.agr.gc.ca:8080/ " + 
													"www.agr.gc.ca/index.html " +
													"www4.agr.gc.ca/index.html " + 
													"www.agr.gc.ca/atlas/air " +
													"www4.agr.gc.ca/atlas/bimat " +	
													"www.agr.gc.ca/index.html " +
													"http://devproxyint.agr.gc.ca:8080/index.html " +
													"http://localhost/index.html " + 
													"http://localhost:9080/index.html " +
													"www4.agr.gc.ca/atlas/bimat " +														
													"http://www4.agr.gc.ca/index.html " +
													"http://www.agr.gc.ca/atlas/index.html " + 
													"http://www4.agr.gc.ca/atlas/index.html " +
													"www.agr.gc.ca:663/index.html " +
													"www4.agr.gc.ca:8080/index.html " + 
													"www.agr.gc.ca:9080/atlas/air " +
													"www4.agr.gc.ca:443/atlas/bimat " +				
													"http://www.agr.gc.ca:12/index.html " + 
													"https://www4.agr.gc.ca:123/index.html " +
													"https://www.agr.gc.ca:1234/atlas/index.html " + 
													"http://www4.agr.gc.ca/atlas/index.html "
													);
		for (String domain: resultDomains) {
			System.out.println("domain=" + domain);
		}
		
		TokenWebApplication token = TokenWebApplication.generateToken("aafc2", "aafc2", "");
		
		System.out.println( token.isSuccess() );
		System.out.println( token.getErrorMessage() );
		System.out.println( token.getToken() );
	}
}
