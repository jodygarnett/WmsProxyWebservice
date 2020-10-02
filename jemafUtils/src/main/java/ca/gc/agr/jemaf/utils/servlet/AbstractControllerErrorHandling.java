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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



import org.apache.log4j.Logger;

import ca.gc.agr.jemaf.gson.ErrorResponse;
import ca.gc.agr.jemaf.utils.JeMafUtils;

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
public class AbstractControllerErrorHandling extends AbstractController {
	/**
	 *  Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(AbstractControllerErrorHandling.class);

	/**
	 * This exception occurs when a request parameter is deemed invalid.  Possibly when
	 * failing an input validation.
	 * @param ex the actual exception.
	 * @return a json formatted exception response.
	 */
	@ExceptionHandler(InvalidRequestException.class)
	public @ResponseBody void handleInvalidRequestException(final InvalidRequestException ex, final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.warn("BadRequestException on for", ex);
		// The exception should be already have the right message according to the language
		writeJsonBackToClient( createGsonResponseError( ex.getMessage() ),
   								response );
	}
	
	/**
	 * This exception occurs when a request does not include the required parameters.
	 * @param ex the actual exception.
	 * @return a json formatted exception response.
	 */
	@ExceptionHandler(NullRequestException.class)
	public @ResponseBody void handleNullRequestException(final NullRequestException ex, final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.warn("NullRequestException on for", ex);
		// The exception should be already have the right message according to the language
		writeJsonBackToClient( createGsonResponseError( ex.getMessage() ),
				   				response );
	}

	/**
	 * This exception occurs when a JSON received is invalid format
	 * @param ex the actual exception.
	 * @return a json formatted exception response.
	 */
	@ExceptionHandler(InvalidJsonException.class)
	public @ResponseBody void handleInvalidJsonException(final InvalidJsonException ex, final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.warn("InvalidJsonException on for", ex);
		// The exception should be already have the right message according to the language
		writeJsonBackToClient( createGsonResponseError( ex.getMessage() ),
	   						   response );
	}

	/**
	 * This exception occurs when a JSON received is invalid format
	 * @param ex the actual exception.
	 * @return a json formatted exception response.
	 */
	@ExceptionHandler(GeneralFailureException.class)
	public @ResponseBody void handleGeneralFailureException(final GeneralFailureException ex, final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.warn("GeneralFailureException on for", ex);
		// The exception should be already have the right message according to the language
		writeJsonBackToClient( createGsonResponseError( ex.getMessage() ),
		 	   	   				response );
	}
	
	/**
	 * This exception occurs to catch any exception.
	 * @param ex the actual exception
	 * @return a json formatted exception response.
	 */
	@ExceptionHandler(Throwable.class)
	public @ResponseBody void handleThrowable(final Throwable ex, final HttpServletRequest request, final HttpServletResponse response) {
		LOGGER.warn("Catch any other exception for", ex);
		// Getting this point, we don't want to show the Java Error at all
		writeJsonBackToClient( createGsonResponseError( getMessage(ERROR_GENERALFAILURE, Locale.ENGLISH) ),
					 	   	   response );
	}
	
	/**
	 * It returns a well formatted JSON Response to be easier to read. 
	 * 
	 * @param errorMessage
	 * @return
	 */
	public String createGsonResponseError(final String errorMessage) {
		ErrorResponse errResponse = getErrorResponse();
		
		errResponse.setMessage( errorMessage );
		return ErrorResponse.serializeObjectToPrettyJsonString( errResponse );
	}
	
	/**
	 * If we don't set the response this way and let Spring doing things, it will write back internally 
	 * String in Unicode which is bad. We want to control that.
	 * @param outputJson
	 * @param response
	 */
	public void writeJsonBackToClient(final String outputJson, final HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(JeMafUtils.JSON_UTF_RESPONSE_CONTENT);
		try {
			response.getWriter().write( outputJson );
		} catch (Throwable t) {
			LOGGER.fatal("Can't write a JSON back to the client. This is very odd", t);
		}
	}
	

}
