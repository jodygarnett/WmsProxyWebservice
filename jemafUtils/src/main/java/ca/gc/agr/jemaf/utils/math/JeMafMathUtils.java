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
package ca.gc.agr.jemaf.utils.math;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

/**
 * <p>
 * The Class has basic math algorithms
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class JeMafMathUtils {
	/** Logger */
	final static Logger LOGGER = Logger.getLogger(JeMafMathUtils.class);

	/** static variables ============================================================ 
	 */
	
	/** class variables =============================================================
	 */
	
	/** instance variables ========================================================== 
	 */

	/** constructors ================================================================ 
	 */
	
	/**
	 * Constructs the based configuration class
	 */
	private JeMafMathUtils() {
	}

	/** properties ================================================================== 
	 */
	

	/** methods ===================================================================== 
	 */
	
	/**
	 * Convert any double to a proper decimal in rounding up
	 * 
	 * @param d Double number
	 * @param decimalPlace Decimal point
	 * @return double rounded 
	*/
	public static double round(double d, int decimalPlace){
		return round(d, decimalPlace, true);
	}
	
	/**
	 * Convert any double to a proper decimal 
	 * 
	 * @param d Double number
	 * @param decimalPlace Decimal point
	 * @param isRoundUp At the decimal, round up or not
	 * @return double rounded 
	*/
	public static double round(double d, int decimalPlace, boolean isRoundUp){
		// see the Javadoc about why we use a String in the constructor
		// http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, ( isRoundUp ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_DOWN ) );
		return bd.doubleValue();
	}



	/** inner class ================================================================= 
	 */

}

