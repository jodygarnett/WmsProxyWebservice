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
package ca.gc.agr.jemaf.utils.servlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ca.gc.agr.jemaf.gson.ErrorResponse;
import ca.gc.agr.jemaf.utils.JeMafUtils;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
 * @author Who?
 * @version 1.0.0
 */
@Controller
public class AbstractController implements ApplicationContextAware, InitializingBean {
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(AbstractController.class);

	/** static variables ========================================================================
	 */

	/**
	 * Bean that defines Error Response in JSON format
	 */
	private   static final String ERROR_RESPONSE_BEAN_ID = "errorResponse";
	protected static final String WEB_DIVIDER_SLASH = "/";
	protected static final String WEBINF_CLASSES_PATH = "/WEB-INF/classes/";
	protected static final String FILESYSTEM_CLASSES_PATH = "build/classes/";
	protected static final String CLASSES_PATH = "classes/";
	
	/**
	 * Message keys
	 */
	public static final String ERROR_GENERALFAILURE = 
			"error.generalFailure";							// $codepro.audit.disable fieldJavadoc
	
	public static final String ERROR_GENERALJSONFAILURE = 							
			"error.generalJsonFailure";						// $codepro.audit.disable fieldJavadoc
	
	public static final String ERROR_REQUESTINVALID = 		
			"error.request.invalid";						// $codepro.audit.disable fieldJavadoc
	
	public static final String ERROR_REQUESTNULL = 			
			"error.request.null";							// $codepro.audit.disable fieldJavadoc
	
	public static final String INFO_ITWORKS = 				
			"info.print";									// $codepro.audit.disable fieldJavadoc

	

	/** class variables =========================================================================
	 */


	/** instance variables ======================================================================
	 */
	private RestPreconditions restPreconditions = new RestPreconditions();

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
	 * Method that sets the Web Servlet Application Context
	 *
	 * @param applicationContext ApplicationContext
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(ApplicationContext)
	 */
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
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}


	/**
	 * @return the restPreconditions
	 */
	public RestPreconditions getRestPreconditions() {
		return restPreconditions;
	}


	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	/** RESTful Request Mapping =================================================================
	 */
	// Implement your services
	
	
	/** methods =================================================================================
	 */
	
	/**
	 * Override this if you need to check the Web Service when it starts
	 */
	public void afterPropertiesSet() throws Exception {
	}
	
	/**
	 * It returns a message according to replace parameters like {0}, {1}
	 * 
	 * @param msgId		Properties Message ID
	 * @param locale	In which language
	 * @return String
	 */
	public String getMessage(String msgId, Locale locale) {
		return getMessageSource().getMessage(msgId, null, locale);
	}
	
	/**
	 * It returns a message according to replace parameters like {0}, {1}
	 * 
	 * @param msgId		Properties Message ID
	 * @param params	All parameters needed
	 * @param locale	In which language
	 * @return String
	 */
	public String getMessage(String msgId, Object[] params, Locale locale) {
		return getMessageSource().getMessage(msgId, params, locale);
	}

	/**
	 * Return a ErrorResponse Bean Object
	 * 
	 * @return ErrorResponse
	 */
	protected ErrorResponse getErrorResponse() {
		ErrorResponse errJson = (ErrorResponse)getBean(ERROR_RESPONSE_BEAN_ID);
		return errJson;
	}
	
	/**
	 * Return a ErrorResponse Bean Object
	 * 
	 * @param lang
	 * @return ErrorResponse
	 */
	protected ErrorResponse getErrorResponse(String lang) {
		ErrorResponse errJson = (ErrorResponse)getBean(ERROR_RESPONSE_BEAN_ID);
		errJson.setLocale(lang);
		return errJson;
	}
	
	/**
	 * Return a ErrorResponse Bean Object
	 * 
	 * @param lang
	 * @return ErrorResponse
	 */
	protected ErrorResponse getErrorResponse(Locale lang) {
		ErrorResponse errJson = (ErrorResponse)getBean(ERROR_RESPONSE_BEAN_ID);
		errJson.setLocale(lang);
		return errJson;
	}
	
	/**
	 * Get the Object from Bean Name ID (see servlet xml)
	 * 
	 * @param context
	 * @return
	 */
	protected Object getBean(String beanID) {
		// get the current path and add the web inf classes path for servlet
		LOGGER.info("Getting Bean Object");
		return getApplicationContext().getBean(beanID);
	}
	
	/**
	 * Get the cuurent Class Path
	 * 
	 * @param context
	 * @return
	 */
	protected static String getClassPath(ApplicationContext context) {
		// get the current path and add the web inf classes path for servlet
		LOGGER.info("Getting classpath in ApplicationContext");
		String filename = "";
		if ( context instanceof WebApplicationContext ) {
			filename = ((WebApplicationContext)context).getServletContext().getRealPath("") + WEBINF_CLASSES_PATH;
		}
		else if ( context instanceof FileSystemXmlApplicationContext ) {
			filename = FILESYSTEM_CLASSES_PATH;
		}
		else {
			filename = CLASSES_PATH;
		}
		return filename;
	}

	/**
	 * Get the XML configuration
	 * 
	 * @param context
	 * @param repositoryFile
	 * @return
	 */
	protected static String getFullRepositoryFilename(ApplicationContext context, String repositoryFile) {
		LOGGER.info("Getting full repository filename in ApplicationContext");
		// get the current path and add the web inf classes path for servlet
		String filename = getClassPath(context);

		// add the repository name
		filename += repositoryFile.trim();

		// if the extension xml does not exist, just append it at the end
		if ( repositoryFile.indexOf(JeMafUtils.XML_EXTENSION) == -1 ) {
			filename += JeMafUtils.XML_EXTENSION;
		}

		return filename;
	}

	/** inner class ================================================================= 
	 */
	
	public final class RestPreconditions {
		
		/**
		 * Checks parameter for null or empty value.
		 * @param reference the request parameter.
		 * @param referenceName the name of the parameter.
		 * @param lang request language
		 */
		public <T> void checkNotNull(final T reference, final String referenceName, final Locale lang) {
			if (reference == null ||
					((reference instanceof String) && ((String)reference).isEmpty())) {		
				throwNullRequestException(referenceName, lang);
			}
		}
		
		/**
		 * Checks parameter by evaluating the truth of a boolean expression.
		 * @param expression the expression.
		 * @param referenceName the name of the parameter.
		 * @param lang request language
		 */
		public void checkRequestValidation(final boolean expression, final String referenceName, final Locale lang) {
			if(!expression) {
				throwInvalidRequestException(referenceName, lang);
			}
		}
		
		/**
		 * Checks a request parameter to validate the json.
		 * @param json the json string.
		 * @param reference the class to validate the json against.
		 * @param referenceName the reference name of the request attribute.
		 * @param lang the language.
		 */
		public <T> void checkRequestJson(final String json, final Class<T> reference, final String referenceName, final Locale lang) {
			try {
				new Gson().fromJson(json, reference);
			} catch (JsonSyntaxException ex) {
				throwInvalidRequestException(referenceName, lang);
			}
		}
		
		/**
		 * Checks a request parameter to validate the url
		 * @param url the url.
		 * @param referenceName the reference name of the request attribute.
		 * @param lang the language.
		 */
		public void checkRequestURL(final String url, final String referenceName, final Locale lang) {
			try {
				new URL(url);
			} catch (MalformedURLException ex) {
				throwInvalidRequestException(referenceName, lang);
			}
		}
		
		/**
		 * Checks a request URL 
		 * @param url the url
		 * @param extension the file extension to check for
		 * @param referenceName the reference name of the request attribute.
		 * @param lang the language.
		 */
		public void checkRequestExtensionType(final String url, final String extension, final String referenceName, final Locale lang) {
			checkRequestURL(url, referenceName, lang);
			if(!url.trim().endsWith(extension)) {
				throwInvalidRequestException(referenceName, lang);
			}
		}
		
		/**
		 * Just throw an error against an Invalid Json received
		 *
		 * @param lang request language
		 */
		public void throwInvalidJsonException(final Locale lang) {
			throw new InvalidJsonException( getMessage(ERROR_GENERALJSONFAILURE, lang) );
		}
		
		/**
		 * Just throw an general error
		 *
		 * @param lang request language
		 */
		public void throwGeneralException(final Locale lang) {
			throw new GeneralFailureException( getMessage(ERROR_GENERALFAILURE, lang) );
		}
		
		/**
		 * Just throw an Invalid Request
		 *
		 * @param lang request language
		 */
		public void throwInvalidRequestException(final String referenceName, final Locale lang) {
			throw new InvalidRequestException( getMessage(ERROR_REQUESTINVALID, new Object[]{referenceName}, lang) );
		}

		/**
		 * Just throw an Invalid Request
		 *
		 * @param lang request language
		 */
		public void throwNullRequestException(final String referenceName, final Locale lang) {
			throw new NullRequestException( getMessage(ERROR_REQUESTNULL, new Object[]{referenceName}, lang) );
		}
	}
}
