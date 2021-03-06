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

/** 
 * <p>
 *  This class provides Exception about the limit of byte read exceed capacity
 * </p>
 * 
 * @author Roberto Caron
 * @version 1.0.0
 */
public class InvalidXmlRequestException extends Exception {
	/** Logger - no need */

	/** static variables ============================================================ 
	 */
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -1795112679416295434L;
	
	/** class variables ========================================================== 
	 */
	
	/**
	 * Keep the error message
	 */
	private String error = "";

	/** instance variables ========================================================== 
	 */
	
	/** constructors ================================================================ 
	 */
	
	/**
	 * Constructor for MemoryDatabaseAttributeMissing. It keeps an error message
	 * 
	 * @param msg Error message
	 */
	public InvalidXmlRequestException() {
		this.error = "Invalid XML Post Request";
	}

	/** properties ================================================================== 
	 */
	
	/** methods ===================================================================== 
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return this.error;
	}

	/** inner class ================================================================= 
	 */
	
}
