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
package ca.gc.agr.jemaf.utils.http;

/**
 * <p>
 * The Class has basics to convert datetime, get temporary folder and many other
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class URLResponse {
	private byte[] rawResponseData;
	private String contentType;

	/**
	 * @return the rawResponseData
	 */
	public byte[] getRawResponseData() {
		return rawResponseData;
	}
	/**
	 * @param rawResponseData the rawResponseData to set
	 */
	public void setRawResponseData(byte[] rawResponseData) {
		this.rawResponseData = rawResponseData;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
