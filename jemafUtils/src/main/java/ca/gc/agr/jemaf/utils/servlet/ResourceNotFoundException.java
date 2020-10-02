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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Bill Zborowski <bill.zborowski@agr.gc.ca>
 *
 */
@ResponseStatus ( value = HttpStatus.CONFLICT)
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * Auto-generated Serial Version
	 */
	private static final long serialVersionUID = -7333145015426669019L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public ResourceNotFoundException(final String message) {
		super(message);
	}
	
	public ResourceNotFoundException(final Throwable cause) {
		super(cause);
	}
	
}
