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
package ca.gc.agr.jemaf.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import ca.gc.agr.jemaf.utils.convert.JeMafConvertUtils;



/**
 * <p>
 * The Class has basics to convert datetime, get temporary folder and many other
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class JeMafUtils {

	/** Logger */
	final static Logger LOGGER = Logger.getLogger(JeMafUtils.class);

	/** static variables ============================================================ 
	 */

	/**
	 * Datetime format
	 */
	public static final String DATETIME_FORMAT =							// $codepro.audit.disable fieldJavadoc
			"yyyy/MM/dd HH:mm:ss";

	/**
	 * Either use TEXT or HTML in UTF-8
	 */
	public static final String PLAIN_UTF_RESPONSE_CONTENT =					// $codepro.audit.disable fieldJavadoc
			"text/plain;charset=UTF-8";

	public static final String HTML_UTF_RESPONSE_CONTENT =					// $codepro.audit.disable fieldJavadoc
			"text/html;charset=UTF-8";

	public static final String JSON_UTF_RESPONSE_CONTENT =					// $codepro.audit.disable fieldJavadoc
			"application/json;charset=UTF-8";

	public static String XML_EXTENSION = ".xml";
	public static String WEBINF_CLASSES_PATH = "/WEB-INF/classes/";
	public static String FILESYSTEM_CLASSES_PATH = "build/classes/";
	public static String CLASSES_PATH = "classes/";
	public static final String ENV_SYSTEM_PROPERTY_NAME = "emaf_config";
	
	/** class variables =============================================================
	 */

	/** instance variables ========================================================== 
	 */

	/** constructors ================================================================ 
	 */

	/**
	 * Constructs the based configuration class
	 */
	private JeMafUtils() {
	}

	/** properties ================================================================== 
	 */


	/** methods ===================================================================== 
	 */

	/**
	 * Convert request lang to the proper Locale either ENGLISH or FRENCH
	 * 
	 * @param lang Language string
	 * @return Locale 
	 */
	public static Locale convertLang(String lang) {
		Locale locale = Locale.ENGLISH;
		if ( lang != null && ! lang.isEmpty() && ( lang.equalsIgnoreCase("FR") || lang.equalsIgnoreCase("FRA")) ) {
			locale = Locale.FRENCH;
		}
		return locale;
	}

	/**
	 * Return the current datetime in 
	 * 
	 * @return String 
	 */
	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}

	/**
	 * Return the current tenporary folder 
	 * 
	 * @return String 
	 */
	public static String getTemp() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * Analyze String value to if a value is either TRUE, ON or 1
	 * 
	 * @param value
	 * @return boolean return true, false not any other values
	 */
	public static boolean isTrue(String value) {
		boolean isOk = false;

		if ( value != null && ! value.isEmpty() ) {
			if ( value.trim().equalsIgnoreCase("TRUE") ||
					value.trim().equalsIgnoreCase("ON") ||
					value.trim().equalsIgnoreCase("1") ) {
				isOk = true;
			}
		}


		return isOk;
	}

	/**
	 * Analyze a value if this is a valid double number
	 * 
	 * @param value
	 * @return true or false
	 */
	public static boolean isDoubleNumber(String value) {
		try  
		{  
			Double.parseDouble( value );  
			return true;  
		}  
		catch( Exception ex)  
		{  
			return false;  
		}  
	}

	/**
	 * Analyze a value if this is a valid integer number
	 * 
	 * @param value
	 * @return true or false
	 */
	public static boolean isIntegerNumber(String value) {
		try  
		{  
			Integer.parseInt( value );  
			return true;  
		}  
		catch( Exception ex)  
		{  
			return false;  
		}  
	}
	
	/**
	 * Analyze a value if this is a valid double number
	 * 
	 * @param value
	 * @return true or false
	 */
	public static boolean isLongNumber(String value) {
		try  
		{  
			Long.parseLong( value );  
			return true;  
		}  
		catch( Exception ex)  
		{  
			return false;  
		}  
	}

	/**
	 * This method replace an URL by the JVM arguments {ags_domain} and {ags_service}
	 * 
	 * @param identifyRestService the identifyRestService to set
	 */
	public static String replaceByGlobalAgsJvmArguments(String identifyRestServiceUrl) {
		if ( identifyRestServiceUrl == null ) {
			// When there is nothing, instead of crashing, just send back a empty string
			return "";
		}

		String agsDomain = System.getProperty("ags_domain", ""); 
		String agsService = System.getProperty("ags_service", ""); 

		return identifyRestServiceUrl.trim()
				.replaceAll("\\{ags_domain\\}", agsDomain)
				.replaceAll("\\{ags_service\\}", agsService);
	}

	/**
	 * Unsigned a byte
	 * 
	 * @param b
	 * @return
	 */
	public static int unsignedToBytes(byte b) {
		return b & 0xFF;
	}


	/**
	 * Generate a UUID random number
	 * 
	 * @return
	 */
	public static String randomUUID() {
		return java.util.UUID.randomUUID().toString();
	}
	
	 /**
	  * Get the host name
	  *
	  * @return String
	  */	 
	 public static final String getLocalName(boolean withCanonical) {
		 String name = "localhost";

		 try {
			 // Process to find out by our-self, the server name
			 java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			 if ( withCanonical )
				 name = i.getCanonicalHostName();
			 else
				 name = i.getHostName();
		 }
		 catch (Exception e) {
			 System.out.println("Cannot get the Local Name. Here is the detail: " + e.getMessage());
		 }

		 return name;
	 }	 
	 
	 /**
	  * Get the machine name.
	  *
	  * @param withCanonical : Indicate to get the Canonical Host Name
	  * @param checkConfig :  If true, Look the common-frogs.properties
	  *                       and find out about host name to load
	  *                       override the value with this one, if exists.
	  *                       NB: This function will check once the environment variable,
	  *                       if you change it, just restart your apps ou webapps.
	  * @return Host Name
	  */
	 public synchronized static final String getMachineName(boolean withCanonical, boolean checkConfig)  {

		 //  VM SETUP -DSERVER_NAME=
		 String serverName = System.getProperty("SERVER_NAME");
		 if ( serverName == null || serverName.length() == 0 ) {
			 // Not set, guess by the machine name, this is brutal
			 if ( withCanonical )
				 serverName = getLocalName(true);
			 else
				 serverName = getLocalName(false);				   
		 }

		 return serverName;
	 }

	 /**
	  * Get the machine name. By default, it will look the environment variable
	  * 'FrogsCanonicalHostName' and override the machine name OR return the current
	  * OS Canonical Server Name
	  *
	  * @return Host Name OR Canonical Server Name
	  */
	 public static final String getMachineName()  {
		 return getMachineName(true,true);
	 }		 

	 /**
	  * Get the machine name where this servlet is runnning. 
	  *
	  * @return String
	  */
	 public static String getRequestServerName() {
		 String httpServerName = null;
		 try {
			 httpServerName = getMachineName();
		 }
		 catch (Exception e) {
			 //	sServerName = HTTPrequest.getLocalName(); // only compatible with servlet 2.4 specification
			 e.printStackTrace();
			 httpServerName = new String("");
		 }

		 return "http://" + httpServerName;
	 }	
	 
	 /**
	  * This method uses a deprecated URL Decoder
	  * but it will help to centralize all calls
	  * to one day change it.
	  * Decodes a application/x-www-form-urlencoded 
	  * string using a specific encoding scheme. 
	  * The supplied encoding is used to determine 
	  * what characters are represented by any consecutive 
	  * sequences of the form "%xy". 
	  *
	  * @param url
	  * @return String
	  *
	  */	
	 @SuppressWarnings("deprecation")
	 public static String URLDecoder(String url) {
		 if ( url == null ) return null;
		 return (java.net.URLDecoder.decode(url));
	 }		

	 /**
	  * This method uses a deprecated URL Decoder
	  * but it will help to centralize all calls
	  * to one day change it.
	  * Decodes a application/x-www-form-urlencoded 
	  * string using a specific encoding scheme. 
	  * The supplied encoding is used to determine 
	  * what characters are represented by any consecutive 
	  * sequences of the form "%xy". 
	  *
	  * @param url
	  * @return String
	  *
	  */	
	 @SuppressWarnings("deprecation")
	 public static String URLEncoder(String url) {
		 if ( url == null ) return null;
		 return (java.net.URLEncoder.encode(url));
	 }		

     /**
      * To be documented
      *
      * @param value
      * @return boolean
      *
      */
    public static final boolean isEmptyString(String value) {
		boolean isEmpty = true;

		if ( value != null && ! value.isEmpty() ) {
			isEmpty = false;
		}

		return( isEmpty );
	}

	public static String substituteSystemProperty(String initialValue)	{
		String placeHolder = "$env"; // complete place holder is $env(SystemPropertyName)
		String finalValue = initialValue;
		
		int startIndex = 0;
		int endIndex = 0;
		
		do	{
			startIndex = finalValue.indexOf(placeHolder, startIndex);
			if(startIndex != -1) {
				endIndex = finalValue.indexOf(')', startIndex + 5);
				if(endIndex != -1 && finalValue.charAt(startIndex + 4) == '(')	{
					String systemPropertyName = finalValue.substring(startIndex + 5, endIndex);
					String systemPropertyValue = System.getProperty(systemPropertyName, "");
					finalValue = finalValue.replace(placeHolder + "("+ systemPropertyName +")", systemPropertyValue);
				}
				else {
					startIndex ++;
				}
			}
		}
		
		while(startIndex != -1);
		
		return finalValue;
	}
	
	/**
	   * Validate if a source if URL, URL or File, if not, regular text we assumed
	   *
	   * @param source
	   * @return boolean Is really stream xml or any kind of data
	   *
	   */		
	public static boolean isSourceText(String source) {
		try {
			String decodedSource = URLDecoder(source);
			// Is this a URL Source?
			if ( isURLPatternValid(decodedSource) ) return false;
			// Is this a URI file?
			if ( isUriExistValid(decodedSource) )  return false;
			// Is this a regular file name
			if ( isFileExistValid(source) )  return false;
		}
		catch (Throwable ex) {;}
					
		// ok .. whatever .. in hoping it is xml or any kind of data
		return true;
	}	
	
	/**
	   * To be documented
	   *
	   * @param filename
	   * @return boolean
	   *
	   */
	 public static boolean isFileExistValid(String filename) {
		boolean isFileExist = false;
		if ( filename != null ) {
			filename = cutValidFile(filename);
			// A filename should not start with a '<' meaning this is an xml
			if ( isValidStartFilename(filename) ) {
				try {
					// it may crash with a out of memory if too much characters
				    File hnd = new File(filename);
				    isFileExist = hnd.exists();
				}
				catch (Throwable Ex) {;}
			}
		}
		
		return (isFileExist);
	 }
	 
	/**
	   * To be documented
	   *
	   * @param uriResource
	   * @return boolean
	   *
	   */
	 public static boolean isUriExistValid(String uriResource) {
		boolean isFileExist = false;
		if ( uriResource != null ) {
			uriResource = cutValidFile(uriResource);
			// A filename should not start with a '<' meaning this is an xml
			if ( isValidStartFilename(uriResource) ) {
				try {
					java.net.URI uri = new java.net.URI(uriResource);
				    File hnd2 = new File(uri);
				    isFileExist = hnd2.exists();
				}
				catch (Exception Ex) {
					// On any error, something bad with this file
					;
				}
			}
		}
	
		return (isFileExist);
	 }
	
	 /*
	  * Valid start filename with based invalid filename
	  */
	 private static boolean isValidStartFilename(String filename) {
		 boolean isOk = false;
		 if ( filename != null ) {
			 filename = filename.trim();
		 
			 if ( filename.charAt(0) != '<' && 
				  filename.charAt(0) != '}' && 
				  filename.charAt(0) != '{' && 
				  filename.charAt(0) != '>' && 
			      filename.charAt(0) != '[' && 
			      filename.charAt(0) != '!' && 
			      filename.charAt(0) != '@' && 
			      filename.charAt(0) != '#' && 
			      filename.charAt(0) != '%' && 
			      filename.charAt(0) != '?' && 
			      filename.charAt(0) != '&' && 
			      filename.charAt(0) != '*' && 
			      filename.charAt(0) != '|' && 
			      filename.charAt(0) != '=' && 
			      filename.charAt(0) != '+'  ) {
				 isOk = true;
			 }
		 }
			 
		 return isOk;
	 }

	 
	 /*
	  * Just take a regular filename lenght
	  */
	 private static String cutValidFile(String filename) {
		return filename.substring(0, (filename.length()>256 ? 256 : filename.length())).trim();
	 }
	 
	 
	/**
	   * Validate is the URL is valid
	   *
	   * @param url
	   * @return boolean
	   *
	   */
	 public static boolean isURLPatternValid(String url) {
	    boolean isURL = false;
	
	    if ( url != null ) {
	    	url = cutValidFile(url);
			// A filename should not start with a '<' meaning this is an xml
			if ( isValidStartFilename(url) ) {
			 	try {
					// If it pass this point, the uRL seems ok
					isURL = true;
				}
				catch (Throwable ex) {;}
			}
	    }
	    
		return (isURL);
	 }
	 
		/**
	   * Construct an URL Object from a filename in String
	   * It could follow URL standard, URI standard or typically file
	   * 
	   * @param filename
	   * @return URL
	   *
	   */
	@SuppressWarnings("deprecation")
	public static URL getUrlFrom(String filename) throws MalformedURLException, URISyntaxException {
		URL urlFilename = null;
		
		// Guess an URL to access
		if ( ! isEmptyString(filename) ) {
			if ( isURLPatternValid(filename) ) {
				urlFilename =  new URL(filename);
			}
			// Nop.. guess any URI structure
			else if ( isUriExistValid(filename) ) {
				urlFilename =  new URI(filename).toURL();
			}
			else {
				// nop..guess a File. 1024 is quite a long filename!
				// If it is a long stream containg a kind of non file structure
				if ( isFileExistValid(filename) ) {
					urlFilename =  new File(filename).toURL();
				}
			}		
		}
		
		return urlFilename;
	}
	
	/**
	 * This function loads a properties file that will be located at a path
	 * provided to us by the JVM system property 'emaf_config'.
	 */
	public static Properties loadEnvironmentProperties() throws IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(new File(System.getProperty(JeMafUtils.ENV_SYSTEM_PROPERTY_NAME))));
		return props;
	}
	
	/**
	 * Encode value in Base64 String
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static String encodeToBase64(String value) throws IOException, MessagingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream base64OutputStream = MimeUtility.encode(baos, "base64");
		base64OutputStream.write(value.getBytes());
		base64OutputStream.close();
		return baos.toString();
	}

	/**
	 * Decode Base64 String in raw String
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public static String decodeFromBase64(String value) throws IOException, MessagingException {
	    ByteArrayInputStream bais = new ByteArrayInputStream(value.getBytes());
	    InputStream base64InputStream = MimeUtility.decode(bais, "base64");
	    String base64 = JeMafConvertUtils.inputStreamToString(base64InputStream);
	    return base64;
	}
	 
	/** inner class ================================================================= 
	 */

}

