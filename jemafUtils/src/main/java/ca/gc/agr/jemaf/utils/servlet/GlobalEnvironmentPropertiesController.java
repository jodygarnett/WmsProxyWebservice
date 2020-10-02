/**
 * Produced by the AGS Team
 *
 * Crown Copyright 2012. All rights reserved.
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
package ca.gc.agr.jemaf.utils.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import ca.gc.agr.jemaf.utils.JeMafUtils;


/**
 * <p>
 *
 *  This class provides the functionality to load a configuration properties file and allow substitution
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
public class GlobalEnvironmentPropertiesController {
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(GlobalEnvironmentPropertiesController.class);
	
	/** static variables ========================================================================
	 */
	// Standard property key 
	public final static String PROXY_HOSTNAME 	= "proxy_hostname";		// $codepro.audit.disable fieldJavadoc
	public final static String AGS_HOSTNAME 	= "ags_hostname";		// $codepro.audit.disable fieldJavadoc
	public final static String WL_HOSTNAME 		= "wl_hostname";		// $codepro.audit.disable fieldJavadoc
	public final static String GP_HOSTNAME 		= "gp_hostname";		// $codepro.audit.disable fieldJavadoc
	public final static String WRAPPER_ENV 		= "wrapper_env";		// $codepro.audit.disable fieldJavadoc
	public final static String WRAPPER_CMS_ID 	= "wrapper_cms_id";		// $codepro.audit.disable fieldJavadoc
	public final static String AGS_SHARE   		= "ags_share";          // $codepro.audit.disable fieldJavadoc
	public final static String BEGIN_SUBSTITUTE = "{";					// $codepro.audit.disable fieldJavadoc
	public final static String END_SUBSTITUTE 	=  "}";					// $codepro.audit.disable fieldJavadoc

	
	/** class variables =========================================================================
	 */

	/** instance variables ======================================================================
	 */
	private Properties properties = null;
	
	/** constructors ============================================================================
	 */
	public GlobalEnvironmentPropertiesController() {
		load();
	}
	

	/** properties ==============================================================================
	 */
	
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	

	/**
	 * Return a property value from a key
	 * 
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		if ( properties == null ) {
			properties = new Properties();
		}
		
		return properties.getProperty(key);
	}

	
	/** methods =================================================================================
	 */
	

	/**
	 * Load the environment configuration file
	 */
	public void load() {
		try {
			LOGGER.warn("Reading the eMaf Poperties file " + System.getProperty(JeMafUtils.ENV_SYSTEM_PROPERTY_NAME) );
			properties = JeMafUtils.loadEnvironmentProperties();
			properties.setProperty( AGS_SHARE, "/aafc-aac/agsshare" );
			LOGGER.warn("eMaf Poperties has been read succesfully");
		}
		catch (Exception ex) {
			LOGGER.error("Can't read the eMaf properties file " + 
						 System.getProperty(JeMafUtils.ENV_SYSTEM_PROPERTY_NAME) + 
						 ".\nPlease setup Tomcat or WL with -D" + JeMafUtils.ENV_SYSTEM_PROPERTY_NAME + "=/path/file.properties", ex);
		}
	}
	
	/**
	 * Allow to set a new property key
	 * 
	 * @param key String
	 * @param value String
	 */
	public void setProperty(String key, String value) {
		if ( properties == null ) {
			properties = new Properties();
		}
		
		properties.setProperty(key, value);
	}
	
	/**
	 * Substitute a value from the properties file according to the standard {key}
	 * 
	 * @param valueToChange String
	 * @return String
	 */
	public String substitute(String valueToChange) {
		return substitute(getProperties(), valueToChange);
	}
	
	/**
	 * Substitute a value from the properties file according to the standard {key}
	 * 
	 * @param valueToChange String
	 * @return String
	 */
	public static String substitute(Properties props, String valueToChange) {
		String newValue = valueToChange;
		
		if ( valueToChange != null && props != null ) {
			Map<String, String> params = new HashMap<String, String>();
			@SuppressWarnings("rawtypes")
			Enumeration e = props.propertyNames();

		    while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      params.put(key, props.getProperty(key));
		    }
		    newValue = substituteValue(params, valueToChange);
		}
		
		return newValue;
	}
	
	/**
	 * Substitute a value from parameters values like "{key}"
	 * 
	 * @param params HashMap
	 * @param value String
	 * @return String
	 */
	public static String substituteValue(Map<String, String> params, String value) {
		if ( params != null && params.size() != 0 && value != null ) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				value = value.replace("{" + entry.getKey() + "}", entry.getValue());
			}
		}
		
		return value;
	}

	
	/** inner class ================================================================= 
	 */

}
