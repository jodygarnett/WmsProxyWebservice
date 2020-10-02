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
package ca.gc.agr.jemaf.jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


 /**
  * To be documented
  *
  */
public class NamespacePrefixMapperImpl extends NamespacePrefixMapper {

	public final static String XSI_NS = "http://www.w3.org/2001/XMLSchema-instance";
	public final static String XSI_MAP = "xsi";
	
	public final static String XLINK_NS = "http://www.w3.org/1999/xlink";
	public final static String XLINK_MAP = "xlink";
	
	public final static String OGC_NS = "http://www.opengis.net/ogc";
	public final static String OGC_MAP = "ogc";

	public final static String GML_NS = "http://www.opengis.net/gml";
	public final static String GML_MAP = "gml";
    
	public final static String GIS_OWS_NS = "http://www.opengis.net/ows";
	public final static String GIS_OWS10_NS = "http://www.opengeospatial.net/ows";
	public final static String GIS_OWS11_NS = "http://www.opengis.net/ows/1.1";
	public final static String OWS_MAP = "ows";
	
	/**
     * To be ovverriden
     *
     * @param namespaceUri
     * @param suggestion
     * @param requirePrefix
     * @return String
     *
     */	
	public String validatePreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return null;
	}
	
     /**
      * To be documented
      *
      * @param namespaceUri
      * @param suggestion
      * @param requirePrefix
      * @return String
      *
      */
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {

    	// Based mapper with OWS, XLINK, XSI
    	
        // I want this namespace to be mapped to "xsi"
        if( XSI_NS.equals(namespaceUri) )
            return XSI_MAP;

        // I want this namespace to be mapped to "xlink"
        if( XLINK_NS.equals(namespaceUri) )
            return XLINK_MAP;

        // I want this namespace to be mapped to "ows"
        if( GIS_OWS_NS.equals(namespaceUri) || 
        	GIS_OWS10_NS.equals(namespaceUri) ||
        	GIS_OWS11_NS.equals(namespaceUri))
            return OWS_MAP;
        
        // I want this namespace to be mapped to "ogc"
        if( OGC_NS.equals(namespaceUri) )
            return OGC_MAP;        

        // I want this namespace to be mapped to "gml"
        if( GML_NS.equals(namespaceUri) )
            return GML_MAP;        
        
        return validatePreferredPrefix(namespaceUri, suggestion, requirePrefix);
    }

	/* (non-Javadoc)
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreDeclaredNamespaceUris()
	 */
	@Override
	public String[] getPreDeclaredNamespaceUris() {
		return super.getPreDeclaredNamespaceUris();
	}

	/* (non-Javadoc)
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getContextualNamespaceDecls()
	 */
	@Override
	public String[] getContextualNamespaceDecls() {
		return super.getContextualNamespaceDecls();
	}

	/* (non-Javadoc)
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreDeclaredNamespaceUris2()
	 */
	@Override
	public String[] getPreDeclaredNamespaceUris2() {
		return super.getPreDeclaredNamespaceUris2();
	}
    
}
