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
package ca.gc.agr.jemaf.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * <p>
 * The Class has basics methodes to read and write text file
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class JeMafIoUtils {

	/** Logger */
	final static Logger LOGGER = Logger.getLogger(JeMafIoUtils.class);

	/** static variables ============================================================ 
	 */
	public static final int BYTES_PER_KB = 1024;
	
	/** class variables =============================================================
	 */
	
	/** instance variables ========================================================== 
	 */

	/** constructors ================================================================ 
	 */

	/**
	 * Constructs the based configuration class
	 */
	private JeMafIoUtils() {
	}


	/** properties ================================================================== 
	 */
	

	/** methods ===================================================================== 
	 */
	
	/**
	 * Read an entire text file in memory
	 * 
	 * @param filename String
	 * @param skipTabs boolean
	 * @return StringBuilder
	 * @throws IOException
	 */
	public static StringBuilder readFileAsString(String filename, boolean skipTabs) throws IOException {
		InputStream input = new FileInputStream(filename);

		BufferedReader bufferedReader =
				new BufferedReader(new InputStreamReader(input));
		// Use this instead of a String, because it is much faster and consumes less memory.
		StringBuilder stringBuilder = new StringBuilder();

		String nextLine = bufferedReader.readLine();
		while (nextLine != null) {
			if ( ! skipTabs ) {
				stringBuilder.append(nextLine.replace('\t', ' '));
			}
			else {
				stringBuilder.append(nextLine);
			}
			stringBuilder.append('\n');
			nextLine = bufferedReader.readLine();
		}

		bufferedReader.close();

		return stringBuilder;
	}

	/**
	 * Write data to a file name with with full path location
	 * 
	 * @param filename String
	 * @param data String
	 * @throws IOException
	 */
	public static void writeToFile(String filename, String data) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		out.write(data);
		out.close();
	}
	
	/** inner class ================================================================= 
	 */


}

