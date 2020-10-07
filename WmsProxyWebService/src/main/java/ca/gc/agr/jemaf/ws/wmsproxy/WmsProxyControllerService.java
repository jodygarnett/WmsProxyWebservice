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
package ca.gc.agr.jemaf.ws.wmsproxy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import ca.gc.agr.jemaf.gson.ErrorResponse;
import ca.gc.agr.jemaf.utils.http.URLResponse;
import ca.gc.agr.jemaf.utils.http.JeMafHttpUtils;
import ca.gc.agr.jemaf.utils.net.ReadPageSizeExceedException;
import ca.gc.agr.jemaf.utils.net.TimeoutException;
import ca.gc.agr.jemaf.utils.servlet.GlobalEnvironmentPropertiesController;
import ca.gc.agr.jemaf.utils.JeMafUtils;
import ca.gc.agr.jemaf.utils.io.JeMafIoUtils;

import org.apache.log4j.Logger;

/**
 * <p>
 *
 *  This class provides the functionality to  ....
 *
 *  Spring Auto scanning components
 *  Normally you declare all the beans or components in XML bean configuration file,
 *  so that Spring container can detect and register your beans or components. Actually,
 *  Spring is able to auto scan, detect and instantiate your beans from pre-defined project package,
 *  no more tedious beans declaration in in XML file.
 * </p>
 *
 * http://eclipsejpa.blogspot.ca/2010/12/spring-305-on-weblogic-10330.html
 *
 * @author Roberto Caron
 * @version 1.0.0
 */
@Controller
public class WmsProxyControllerService implements ApplicationContextAware, InitializingBean {
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(WmsProxyControllerService.class);

	/** static variables ========================================================================
	 */


	/**
	 * Message keys
	 */
	private static final String WMSPROXY_GENERAL_FAILURE = 						// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.GENERAL.FAILURE";
	private static final String WMSPROXY_PAGE_EXCEED = 							// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.PAGE.EXCEED";
	private static final String WMSPROXY_MALFORMED_URL = 						// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.MALFORMED.URL";
	private static final String WMSPROXY_IO_FAILURE = 							// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.IO.FAILURE";
	private static final String WMSPROXY_MANDATORY_PARAMS = 					// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.MANDATORY.PARAMS";
	private static final String WMSPROXY_TIMEOUT = 								// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.TIMEOUT";
	private static final String WMSPROXY_REQUEST_TYPE_VALID = 					// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.REQUEST.TYPE.VALID";
	private static final String WMSPROXY_INVALID_POST_XML = 					// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.INVALID.POST.XML";
	private static final String WMSPROXY_INVALID_RESPONSE = 					// $codepro.audit.disable fieldJavadoc
			"WMSPROXY.INVALID.RESPONSE";


	/** class variables =========================================================================
	 */
	private Integer payload = 1000;
	private Integer timeout = 30000;

	/** instance variables ======================================================================
	 */

	/**
	 * Reference to Global Web Logic Properties File
	 */
	@Autowired
	private GlobalEnvironmentPropertiesController globalEnvironmentPropertiesController;

	/**
	 * Spring will "auto-inject" a Singleton ReloadableResourceBundleMessageSource Object to this variable
	 * to get messages properties in english and french in a commun way
	 */
	@Autowired
	private MessageSource  messageSource = null;

	/**
	 * The root WebApplicationContext is created by the ContextLoaderListener and
	 * contains all of the beans defined in the configuration files referenced by
	 * the contextConfigLocation context parameter (unless you provide an explicit alternative,
	 * this means WEB-INF/applicationContext.xml).
	 * By insisting that each DispatcherServlet have its own WebApplicationContext
	 * (which extends the global context), Spring encourages encapsulation: beans
	 * that are "general purpose" and shared between multiple DispatcherServlets should
	 * live in the global context, where as beans that are only interacted with by your
	 * DispatcherServlet aren't shared.
	 */
	@Autowired
	private WebApplicationContext applicationContext = null;


	/** constructors ============================================================================
	 */

	/** properties ==============================================================================
	 */
	/**
	 * @return the globalEnvironmentPropertiesController
	 */
	public GlobalEnvironmentPropertiesController getGlobalEnvironmentPropertiesController() {
		return globalEnvironmentPropertiesController;
	}

	/**
	 * @param globalEnvironmentPropertiesController the globalEnvironmentPropertiesController to set
	 */
	public void setGlobalEnvironmentPropertiesController(
			GlobalEnvironmentPropertiesController globalEnvironmentPropertiesController) {
		this.globalEnvironmentPropertiesController = globalEnvironmentPropertiesController;
	}

	/**
	 * RESTful HTTP GET/POST '/reloadProperties' Web Service
	 * It allows to realod server side properties setup under Web Logic
	 *
	 * For the parameters:
	 * @param lang
	 * @param response
	 *
	 * @throws NoSuchMessageException
	 * @throws IOException
	 */
	@RequestMapping(value = "/reloadProperties", method = { RequestMethod.GET } )
	public @ResponseBody()  void  reloadproperties(
			HttpServletRequest  request,
			HttpServletResponse response) throws NoSuchMessageException, IOException {

		getGlobalEnvironmentPropertiesController().load();

		LOGGER.warn("The properties have been reloaded");
		response.setContentType(JeMafUtils.JSON_UTF_RESPONSE_CONTENT);
		ErrorResponse errResponse = new ErrorResponse();
		errResponse.setSuccess(true);
		errResponse.setMessage("The properties have been reloaded");
		response.getWriter().write( ErrorResponse.serializeObjectToPrettyJsonString( errResponse ) );
	}


	/**
	 * Method that sets the Web Servlet Application Context
	 *
	 * @param applicationContext ApplicationContext
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		applicationContext = (WebApplicationContext)applicationContext;
	}


	/**
	 * Return the Web Servlet Application Context
	 *
	 * @return WebApplicationContext
	 */
	public WebApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @return the payload
	 */
	public Integer getPayload() {
		return payload;
	}


	/**
	 * @param payload the payload to set
	 */
	public void setPayload(Integer payload) {
		this.payload = payload;
	}


	/**
	 * @return the timeout
	 */
	public Integer getTimeout() {
		return timeout;
	}


	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}


	/** methods =================================================================================
	 */


	/** RESTful Request Mapping =================================================================
	 */



	/**
	 * RESTful HTTP GET/POST '/executeFromProxy' Web Service
	 * It allows to executeFromProxy ....
	 *
	 * For the parameters:
	 * @param lang
	 * @param response
	 *
	 * @throws NoSuchMessageException
	 * @throws IOException
	 */
	@RequestMapping(value = "/executeFromProxy", method = { RequestMethod.POST , RequestMethod.GET } )
	public @ResponseBody()  void  executeFromProxy(
			HttpServletRequest  request,
			HttpServletResponse response) throws NoSuchMessageException, IOException {

		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.1.1&service=wms&request=GetStyles&layers=wld_ocean&_dc=1353352206677
		// ?remoteService=http://atlas.agr.gc.ca/nrh-szrn-mb_wms/tilecache.py&VERSION=1.1.1&TITLE=AgriMap%20-%20Manitoba&FORMAT=image%2Fpng&TRANSPARENT=false&LAYERS=nrh-szrn-mb_wms!BathymetryForeignLand&SERVICE=WMS&REQUEST=GetMap&STYLES=&EXCEPTIONS=application%2Fvnd.ogc.se_inimage&SRS=EPSG%3A26914&BBOX=3009331.8702899,6764665.935145,3686664.8378624,7441998.9027174&WIDTH=256&HEIGHT=256

		//http://atlas.agr.gc.ca/nrh-szrn-mb_wms/tilecache.py?VERSION=1.1.1&TITLE=AgriMap%20-%20Manitoba&FORMAT=image%2Fpng&TRANSPARENT=false&LAYERS=nrh-szrn-mb_wms!BathymetryForeignLand&SERVICE=WMS&REQUEST=GetMap&STYLES=&EXCEPTIONS=application%2Fvnd.ogc.se_inimage&SRS=EPSG%3A26914&BBOX=3009331.8702899,6764665.935145,3686664.8378624,7441998.9027174&WIDTH=256&HEIGHT=256
		///http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.1.1&service=wms&request=GetStyles&layers=can_1m_provterr&_dc=1353095012390
		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=%20http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.3.0&request=GetCapabilities&service=wms&_dc=1353007416619
		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=%20http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.3.0&service=wms&request=GetStyles&layers=wld_ocean&_dc=1353007289697

		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=%20http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.1.1&request=GetCapabilities&service=wms&_dc=1353007416619
		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=%20http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.1.1&service=wms&request=GetStyles&layers=wld_ocean&_dc=1353007289697

		//http://atlas.agr.gc.ca/agmafWSWmsProxy/rest/wmsproxy?remoteService=http://atlas.gc.ca/cgi-bin/atlaswms_en&version=1.1.1&service=wms&request=GetStyles&layers=can_1m_ocean,can_1m_provterr,can_1m_roc,can_1m_mountain,can_1m_freshwat,can_1m_glac,can_1m_protect,can_1m_atl_oceandr,can_1m_env_majordr,can_1m_env_subdr,can_1m_env_s_subdr&_dc=1353067513212

		//http://localhost:8080/WmsProxyWebService/ws/wmsproxy/executeFromProxy?remoteService=http://sampleserver1.arcgisonline.com/ArcGIS/services/Specialty/ESRI_StatesCitiesRivers_USA/MapServer/WMSServer/&version=1.1.1&request=GetCapabilities&service=wms&_dc=1353007416619
		//ws/wmsproxy/executeFromProxy?remoteService=http://sampleserver1.arcgisonline.com/ArcGIS/services/Specialty/ESRI_StatesCitiesRivers_USA/MapServer/WMSServer&version=1.3.0&service=wms&request=GetCapabilities


		URLResponse wmsResponse = executeExternalProxyRequest(request);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(wmsResponse.getContentType());
		response.getOutputStream().write(wmsResponse.getRawResponseData());
		response.getOutputStream().flush();
	}

	public URLResponse executeExternalProxyRequest(HttpServletRequest  request) throws NoSuchMessageException, IOException {

		URLResponse wmsResponse = null;

		String remoteService = "";
		String requestType = "GetCapabilities";
		String service = "wms";
		String version = "1.1.1";
		Locale locale = Locale.ENGLISH;
		String msgKey = null;
		Object params[] = null;

		try {
			// Reformulate the query
			StringBuilder sb = new StringBuilder();
			String requestPostData = JeMafHttpUtils.getPostData(request, getPayload() );
			String requestMethod = JeMafHttpUtils.getMethod(request);
			String requestContentType = JeMafHttpUtils.getContentType(request);

			Map<String,ArrayList<String>> requestParams = new HashMap<>();

			String query = request.getQueryString();
			if( query.indexOf('?') != -1 ){
				LOGGER.trace("fix remoteService sub-query '?':" + query);
				query = query.replace('?','&');
				requestParams = JeMafHttpUtils.fixParameters( query );
			}
			else if (query.indexOf("%3F") != -1 ){
				LOGGER.trace("fix remoteService sub-query '%3F':" + query);
				query = query.replace("%3F","&");
				requestParams = JeMafHttpUtils.fixParameters( query );
			}
			else {
				requestParams = JeMafHttpUtils.getParameters(request, requestPostData);
				String fixKey = null, fixValue = null;
				for( Map.Entry<String,ArrayList<String>> entry : requestParams.entrySet() ){
					if( entry.getKey().equals("remoteService")){
						ArrayList<String> arr = entry.getValue();
						if( arr == null || arr.isEmpty() ){
							continue;
						}
						String remote = arr.get(0);
						if (remote.contains("?")){
							int index = remote.indexOf('?');
							String fixed = remote.substring(0,index+1);
							LOGGER.trace("split remoteService '?':" + fixed);
							arr.set(0,fixed);

							int index2 = remote.indexOf('=',index);
							fixKey = remote.substring(index+1,index2);
							fixValue = remote.substring(index2+1);
						}
					}
				}
				if( fixKey != null && fixValue != null ){
					LOGGER.trace("split remoteService '?':" + fixKey + "="+fixValue);
					ArrayList<String> array = new ArrayList<>();
					array.add(fixValue);
					requestParams.put(fixKey,array);
				}
			}

			Iterator<Entry<String, ArrayList<String>>> it = requestParams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, ArrayList<String>> pairs = it.next();
				String key = pairs.getKey();
				String value = null;
				ArrayList<String> values = pairs.getValue();
				if ( values != null && values.size() > 0 ) {
					// Always the first value
					value = values.get(0);
				}

				if ( ! key.equalsIgnoreCase("lang") &&
					 ! key.equalsIgnoreCase("_dc") &&
					 ! key.equalsIgnoreCase("remoteService") &&
						value != null ) {

					if ( sb.toString().length() > 0 ) {
						sb.append("&");
					}

					//include the namespace in the xml sld_body parameter of a getmap request
					if ( key.equalsIgnoreCase("sld_body") ) {
						LOGGER.trace("sld_body original value: " + value);
						if (value.contains("<sld:StyledLayerDescriptor version='1.0.0'>")) {
							String newRoot = "<sld:StyledLayerDescriptor version=\"1.0.0\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:sld=\"http://www.opengis.net/sld\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/sld StyledLayerDescriptor.xsd\">";
							String oldRoot = "<sld:StyledLayerDescriptor version='1.0.0'>";
							value = value.replace(oldRoot, newRoot);
							LOGGER.trace("sld_body value updated method 1: " + value);
						}
						else if (value.contains("<sld:StyledLayerDescriptor version=\"1.0.0\">")) {
							String newRoot = "<sld:StyledLayerDescriptor version=\"1.0.0\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:sld=\"http://www.opengis.net/sld\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/sld StyledLayerDescriptor.xsd\">";
							String oldRoot = "<sld:StyledLayerDescriptor version=\"1.0.0\">";
							value = value.replace(oldRoot, newRoot);
							LOGGER.trace("sld_body value updated method 2: " + value);
						}
					}

					sb.append(key);
					sb.append("=");
					sb.append( value );
				}

				if ( key.equalsIgnoreCase("remoteService") ) {
					remoteService = value;
					if ( ! remoteService.endsWith("?") ) {
						remoteService += "?";
					}
				}
				else if ( key.equalsIgnoreCase("lang") ) {
					locale = JeMafUtils.convertLang(value);
				}
				else if ( key.equalsIgnoreCase("service") ) {
					if ( value != null && !value.isEmpty() ) {
						// Allow assign a value if there is actually one
						service = value;
					}
				}
				else if ( key.equalsIgnoreCase("version") ) {
					if ( value != null && !value.isEmpty() ) {
						// Allow assign a value if there is actually one
						version = value;
					}
				}
				else if ( key.equalsIgnoreCase("request") ) {
					if ( value != null && !value.isEmpty() ) {
						// Allow assign a value if there is actually one
						requestType = value;
					}
				}
			}

			if ( remoteService == null || remoteService.trim().isEmpty() ||
				 requestType == null   || requestType.trim().isEmpty() ||
				 service == null       || service.trim().isEmpty() ||
				 version == null       || version.trim().isEmpty() ) {
				msgKey = WMSPROXY_MANDATORY_PARAMS;
			}
			else {
				remoteService = remoteService.trim();
				requestType = requestType.trim();
				service = service.trim();
				version = version.trim();

				if	( !requestType.equalsIgnoreCase("GETMAP") &&
					  !requestType.equalsIgnoreCase("GETLEGENDGRAPHIC") &&
					  !requestType.equalsIgnoreCase("GETCAPABILITIES") &&
					  !requestType.equalsIgnoreCase("GETFEATUREINFO") &&
					  !requestType.equalsIgnoreCase("GETSTYLES") ) {
					msgKey =  WMSPROXY_REQUEST_TYPE_VALID;
				}
				else {
					String newRemoteServiceToBeRequest = remoteService + sb.toString();

					// Post Data from the client. If there is something, it must be send to the Remote Server
					if ( LOGGER.isInfoEnabled() ) {
						LOGGER.info("URL [" + newRemoteServiceToBeRequest + "]");
						LOGGER.info("Post Data [\n" + requestPostData + "\n]");
					}

					if ( requestPostData != null && ! requestPostData.trim().isEmpty() ) {
						// Must be an XML format only! This tag must be there somewhere
						if ( requestPostData.trim().indexOf("<?xml") == -1 ) {
							// Throw invalid POST XML Data
							throw new InvalidXmlRequestException();
						}
						else {
							// In XML, Tags are precise, there are case-sensitive
							if ( requestPostData.trim().indexOf("GetCapabilities") != -1 ) {
								requestType = "GETCAPABILITIES";
							}
							else if ( requestPostData.trim().indexOf("StyledLayerDescriptor") != -1 || requestPostData.trim().indexOf("GetStyles") != -1) {
								requestType = "GETSTYLES";
							}
							else if ( requestPostData.trim().indexOf("GetMap") != -1 ) {
								requestType = "GETMAP";
							}
							else if ( requestPostData.trim().indexOf("GetFeatureInfo") != -1 ) {
								requestType = "GETFEATUREINFO";
							}
							else if ( requestPostData.trim().indexOf("GetLegendGraphic") != -1 ) {
								requestType = "GETLEGENDGRAPHIC";
							}
							else {
								// POST XML Data does not have any of the standard
								throw new InvalidXmlRequestException();
							}
						}
					}

		    		// If -Dhttp.proxyHost and/or -Dhttps.proxyHost are defined in the JVM, it will take precedence
					wmsResponse = JeMafHttpUtils.callSyncWebServices( new URL(newRemoteServiceToBeRequest),
																		requestPostData,
																		requestMethod,
																		requestContentType,
																		getPayload() * JeMafIoUtils.BYTES_PER_KB,
																		getTimeout() );

					if ( LOGGER.isInfoEnabled() ) {
						LOGGER.info("WMS sucessfull response [\n" + new String(wmsResponse.getRawResponseData()) + "\n]");
					}

					if ( wmsResponse.getRawResponseData() == null || wmsResponse.getRawResponseData().length <= 8 ) {
						throw new InvalidResponseException();
					}
					else {
						requestType = requestType.toUpperCase();

						if ( wmsResponse.getContentType().toLowerCase().indexOf("text/xml") != -1 ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/svg\\+xml") != -1 ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/svg+xml") != -1 ||
								wmsResponse.getContentType().toLowerCase().indexOf("application/vnd.ogc") != -1 ){
							String xml =  new String(wmsResponse.getRawResponseData()).trim();
							if ( !(xml.indexOf("<?xml") != -1 || xml.indexOf("<StyledLayerDescriptor") != -1) ) {
								throw new InvalidResponseException();
							}
						}
						else if ( wmsResponse.getContentType().toLowerCase().indexOf("image/png") != -1 ) {
							//The first eight bytes of all PNG files are 89 50 4e 47 0d 0a 1a 0ah.
							if ( ! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0x89 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0x50 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0x4E &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[3]) == 0x47 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[4]) == 0x0D &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[5]) == 0x0A &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[6]) == 0x1A &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[7]) == 0x0A )  ) {
								throw new InvalidResponseException();
							}
						}
						else if ( wmsResponse.getContentType().toLowerCase().indexOf("image/gif") != -1 ) {
							// 47 49 46 38 37 61h ("GIF87a") or 47 49 46 38 39 61h ("GIF89a").
							// signaturev89a = \\x47\\x49\\x46\\x38\\x39\\x61
							if ( ! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0x47 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0x49 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0x46 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[3]) == 0x38 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[4]) == 0x37 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[5]) == 0x61 )  ||

									! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0x47 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0x49 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0x46 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[3]) == 0x38 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[4]) == 0x39 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[5]) == 0x61  )) {
								throw new InvalidResponseException();
							}
						}
						else if ( wmsResponse.getContentType().toLowerCase().indexOf("image/jpeg")  != -1  ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/jpg")  != -1   ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/jfif")  != -1  ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/jpe")  != -1   ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/jfi")  != -1   ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/jif")  != -1   ||
								wmsResponse.getContentType().toLowerCase().indexOf("image/spiff")  != -1  ) {
							//The first three bytes are ff d8 ff h
							if ( ! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0xFF &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0xD8 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0xFF)    ||

									! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0xFF &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0xE0 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0xFF)) {
								throw new InvalidResponseException();
							}
						}
						else if ( wmsResponse.getContentType().toLowerCase().indexOf("image/tiff")  != -1  ) {
							// The first four bytes of a big-endian TIFF files are 4d 4d 00 2a h and
							// 49 49 2a 00h for little-endian TIFF files.
							if ( ! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0x4D &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0x4D &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0x00 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[3]) == 0x2A )    ||

									! (JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[0]) == 0x49 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[1]) == 0x49 &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[2]) == 0x2A &&
									JeMafUtils.unsignedToBytes(wmsResponse.getRawResponseData()[3]) == 0x00 )) {
								throw new InvalidResponseException();
							}
						}
						else {
							throw new InvalidResponseException();
						}
					}
				}
			}

		} catch (ReadPageSizeExceedException e) {
			msgKey = WMSPROXY_PAGE_EXCEED;
		} catch (InvalidResponseException e) {
			msgKey = WMSPROXY_INVALID_RESPONSE;
		} catch (InvalidXmlRequestException e) {
			msgKey = WMSPROXY_INVALID_POST_XML;
		} catch (TimeoutException e) {
			msgKey = WMSPROXY_TIMEOUT;
			params = new Object[] { remoteService, getTimeout().toString()  };
		} catch (MalformedURLException e) {
			msgKey = WMSPROXY_MALFORMED_URL;
			params = new Object[] { remoteService };
		} catch (IOException e) {
			msgKey = WMSPROXY_IO_FAILURE;
			params = new Object[] { remoteService };
		} catch (Throwable e) {
			msgKey = WMSPROXY_GENERAL_FAILURE;
			params = new Object[] { remoteService };
		}

		if ( msgKey != null ) {
			// return a WMS Exception back
			wmsResponse = new URLResponse();
			wmsResponse.setContentType(JeMafHttpUtils.XML_UTF_8);
			wmsResponse.setRawResponseData(
				("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>" +
					"<!DOCTYPE ServiceExceptionReport SYSTEM \"http://schemas.opengis.net/wms/1.1.1/exception_1_1_1.dtd\">" +
					"<ServiceExceptionReport version=\"1.1.1\">" +
					"<ServiceException>" + messageSource.getMessage(msgKey,  params, locale)  + "</ServiceException>" +
				 "</ServiceExceptionReport>").getBytes() );
		}

		return wmsResponse;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
	}

}
