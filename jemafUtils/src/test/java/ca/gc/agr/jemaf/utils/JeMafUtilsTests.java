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
package ca.gc.agr.jemaf.utils;

import java.io.File;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


import ca.gc.agr.jemaf.utils.JeMafUtils;
import ca.gc.agr.jemaf.utils.dom.JeMafDomUtils;
import ca.gc.agr.jemaf.utils.io.JeMafIoUtils;
import ca.gc.agr.jemaf.utils.math.JeMafMathUtils;

import org.apache.log4j.Logger;

/** 
 *  This class provides JUnit functionality
 *
 * @author Roberto Caron
 * @version 1.0.0
 */
@ContextConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
public class JeMafUtilsTests { 
	
	/**
	 * Logger
	 */
	final static Logger LOGGER = Logger.getLogger(JeMafUtilsTests.class);

	@Test
	public void testLocale() throws Exception {
		LOGGER.info("Testing Locale in progress...");
		Locale locale = JeMafUtils.convertLang("asas");
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.ENGLISH ));
		
		locale = JeMafUtils.convertLang("fr");
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.FRENCH ));
		
		locale = JeMafUtils.convertLang("FR");
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.FRENCH ));
		
		locale = JeMafUtils.convertLang("fRa");
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.FRENCH ));
		
		locale = JeMafUtils.convertLang(null);
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.ENGLISH ));
		
		locale = JeMafUtils.convertLang("");
		Assert.notNull(locale);
		Assert.isTrue( locale.equals(Locale.ENGLISH ));
		
		Assert.notNull( JeMafUtils.getCurrentDateTime() );
		
		LOGGER.info("Testing done");
	}
	
	@Test
	public void testMath() throws Exception {
		LOGGER.info("Testing Math in progress...");
		
		double result =  JeMafMathUtils.round(10.123456, 0);
		Assert.isTrue( result == 10.0 );
		
		result =  JeMafMathUtils.round(10.123456, 1);
		Assert.isTrue( result == 10.1 );
		
		result =  JeMafMathUtils.round(10.123456, 2);
		Assert.isTrue( result == 10.12 );
		
		result =  JeMafMathUtils.round(10.123456, 3);
		Assert.isTrue( result == 10.123 );
		
		result =  JeMafMathUtils.round(10.123456, 4);
		Assert.isTrue( result != 10.1234 );
		Assert.isTrue( result == 10.1235 );
		
		result =  JeMafMathUtils.round(10.123456, 5);
		Assert.isTrue( result != 10.12345 );
		Assert.isTrue( result == 10.12346 );

		result =  JeMafMathUtils.round(10.1234567, 6);
		Assert.isTrue( result != 10.123456 );
		Assert.isTrue( result == 10.123457 );
		
		result =  JeMafMathUtils.round(10.1234567890, 8);
		Assert.isTrue( result != 10.12345678 );
		Assert.isTrue( result == 10.12345679 );
		
		
		result =  JeMafMathUtils.round(10.123456, 0, false);
		Assert.isTrue( result == 10.0 );
		
		result =  JeMafMathUtils.round(10.123456, 1, false);
		Assert.isTrue( result == 10.1 );
		
		result =  JeMafMathUtils.round(10.123456, 2, false);
		Assert.isTrue( result == 10.12 );
		
		result =  JeMafMathUtils.round(10.123456, 3, false);
		Assert.isTrue( result == 10.123 );
		
		result =  JeMafMathUtils.round(10.123456, 4, false);
		Assert.isTrue( result != 10.1235 );
		Assert.isTrue( result == 10.1234 );
		
		result =  JeMafMathUtils.round(10.123456, 5, false);
		Assert.isTrue( result != 10.12346 );
		Assert.isTrue( result == 10.12345 );

		result =  JeMafMathUtils.round(10.1234567, 6, false);
		Assert.isTrue( result != 10.123457 );
		Assert.isTrue( result == 10.123456 );
		
		result =  JeMafMathUtils.round(10.1234567890, 8, false);
		Assert.isTrue( result != 10.12345679 );
		Assert.isTrue( result == 10.12345678 );
		
		LOGGER.info("Testing done");
	}
	
	@Test
	public void testIO() throws Exception {
		LOGGER.info("Testing IO in progress...");
		
		final String text = " This is a test\n";
		
		final String filename = JeMafUtils.getTemp() + File.separator + "test.txt";
		
		JeMafIoUtils.writeToFile( filename, text);
				
		Assert.isTrue( JeMafIoUtils.readFileAsString(filename, true).toString().equals(text) );
		
		
		final String textNoTab = "\tThis\tis\ta\ttest\n";
		
		final String filenameNoTab = JeMafUtils.getTemp() + File.separator + "testNoTab.txt";
		
		JeMafIoUtils.writeToFile( filenameNoTab, textNoTab);
				
		Assert.isTrue( JeMafIoUtils.readFileAsString(filenameNoTab, false).toString().equals(text) );
		
		LOGGER.info("Testing done");
	}
	
	@Test
	public void testXml() throws Exception {
		LOGGER.info("Testing Xml in progress...");
		
		final String rootNode = "root";
		final String attrName = "name";
		final String attrNameValue = "test";
		
		final String text = "<" + rootNode + " " + attrName + "=\"" + attrNameValue + "\">\n" + 
								"<child>\n" + 
								"</child>\n" + 
							"</" + rootNode + ">\n";
		
		final String filename = JeMafUtils.getTemp() + File.separator + "test.xml";
		
		JeMafIoUtils.writeToFile( filename, text);
				
		Assert.notNull( JeMafDomUtils.parseDoc(filename) );
		
		Document doc = JeMafDomUtils.parseDoc(filename);
		
		Node node = JeMafDomUtils.getFirstNode(doc, rootNode);
		
		Assert.notNull( JeMafDomUtils.getTagAttributeByNode(attrName, node, null) );
		
		Assert.hasText( JeMafDomUtils.getTagAttributeByNode(attrName, node, null), attrNameValue );
		
		LOGGER.info("Testing done");
	}


}
