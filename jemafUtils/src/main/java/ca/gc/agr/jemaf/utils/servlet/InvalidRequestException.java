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
package ca.gc.agr.jemaf.utils.servlet;

public final class InvalidRequestException extends RuntimeException {
	
	/**
	 * Auto-generated Serial Version
	 */
	private static final long serialVersionUID = -5798458358844907758L;

	
	public InvalidRequestException() {
		super();
	}
	
	public InvalidRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public InvalidRequestException(final String message) {
		super(message);
	}
	
	public InvalidRequestException(final Throwable cause) {
		super(cause);
	}
	
}
