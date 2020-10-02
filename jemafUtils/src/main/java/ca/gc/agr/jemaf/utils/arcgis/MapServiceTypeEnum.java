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
package ca.gc.agr.jemaf.utils.arcgis;

import java.util.ArrayList;

/** 
 * <p>
 *  This class provides enumeration of 
 * </p>
 * 
 * @author Roberto Caron
 * @version 1.0.0
 * */
public enum MapServiceTypeEnum {

	/** instance variables ========================================================== 
	 */
	ARCGISDYNAMICMAPSERVICELAYER("ArcGISDynamicMapServiceLayer"),
	ARCGISTILEDMAPSERVICELAYER("ArcGISTiledMapServiceLayer"),
	ARCGISIMAGESERVICELAYER("ArcGISImageServiceLayer"),
	FEATURELAYER("FeatureLayer"),
	FEATURESERVICE("FeatureService"),
	WMSLAYER("WMSLayer"),
	NONE("")
	;

	/** Logger - no need */

	/** static variables ============================================================ 
	 */
	public static final int TOTAL = 6; 

	/**  class variables =========================================================================================
	 */

	/**
	 * Crop Type
	 */
	private final String serviceType;


	/**
	 *  constructors =========================================================================================
	 */

	/**
	 * Constructs with just create an instance. It satisfies Spring Framework having a strict construction without any parameters
	 * 
	 * @param severityType Microsoft Access XSD Data Type
	 */
	private MapServiceTypeEnum(final String serviceType) {
		this.serviceType = serviceType;
	}


	/** 
	 * properties =========================================================================================
	 */


	/**
	 *  methods ========================================================================================= 
	 */

	/** (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 * @return The current Severity Type
	 */
	@Override
	public String toString() {
		return serviceType;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static boolean validate(ArrayList<String> listType) {
		boolean isValid = false;

		if ( listType != null ) {
			for (String type: listType) {
				if ( validate(type) ) {
					isValid = true;
					break;
				}
			}
		}

		return isValid;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static boolean validate(String type) {
		boolean isValid = false;

		for (MapServiceTypeEnum item: values() ) {
			if ( item.toString().equalsIgnoreCase(type) ) {
				isValid = true;
				break;
			}
		}

		return isValid;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static MapServiceTypeEnum getMatchEnum(String type) {
		MapServiceTypeEnum enumFound = MapServiceTypeEnum.NONE;

		for (MapServiceTypeEnum item: values() ) {
			if ( item.toString().equalsIgnoreCase(type) ) {
				enumFound = item;
				break;
			}
		}

		return enumFound;
	}


	/** inner class ================================================================= 
	 */

}