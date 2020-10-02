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

public final class GeneralFailureException extends RuntimeException {
	
	/**
	 * Auto-generated Serial Version
	 */
	private static final long serialVersionUID = 2519438414934306057L;

	
	public GeneralFailureException() {
		super();
	}
	
	public GeneralFailureException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public GeneralFailureException(final String message) {
		super(message);
	}
	
	public GeneralFailureException(final Throwable cause) {
		super(cause);
	}
	
}
