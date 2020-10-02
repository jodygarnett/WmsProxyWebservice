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
package ca.gc.agr.jemaf.utils.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.apache.log4j.Logger;

/**
 * <p>
 * The Class has basics to parse an XML, read nodes and attributes
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class JeMafDomUtils {

	/** Logger */
	final static Logger LOGGER = Logger.getLogger(JeMafDomUtils.class);

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
	private JeMafDomUtils() {
	}

	/** properties ================================================================== 
	 */
	

	/** methods ===================================================================== 
	 */

	/**
	 * From an W3C Node, get the value from an attribute name
	 * 
	 * @param sAttrName String
	 * @param eElement Node
	 * @param def String
	 * @return String
	 */
	public static String getTagAttributeByNode(String sAttrName, Node eElement, String def) {
		return getTagAttributeByElement(sAttrName, (Element)eElement, def);
	}

	/**
	 * From an W3C Element, get the value from an attribute name
	 * 
	 * @param sAttrName String
	 * @param eElement Element
	 * @param def String
	 * @return String
	 */
	public static String getTagAttributeByElement(String sAttrName, Element eElement, String def) {
		String value = eElement.getAttribute(sAttrName); 
		if ( value == null ) {
			return def;
		}
		else {
			return value;
		}
	}

	/**
	 * Read an XML file, parse it and load it a W3C Document
	 * 
	 * @param file File name with the full path
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parseDoc(String file) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		return dBuilder.parse(fXmlFile);
	}
	
	/**
	 * Get the first node in a list of nodes
	 * 
	 * @param doc
	 * @param nodeName
	 * @return Node
	 * @throws ParserConfigurationException
	 */
	public static Node getFirstNode(Document doc, String nodeName) throws ParserConfigurationException {
		NodeList schemaList = doc.getElementsByTagName(nodeName);
		if ( schemaList != null ) {
			return schemaList.item(0);
		}
		else {
			return null;
		}
	}
	
	/**
	 * Get list of nodes from the parent document
	 * 
	 * @param doc
	 * @param nodeName
	 * @return NodeList
	 * @throws ParserConfigurationException
	 */
	public static NodeList getNodes(Document doc, String nodeName) throws ParserConfigurationException {
		return doc.getElementsByTagName(nodeName);
	}

	
	/** inner class ================================================================= 
	 */
	
	/*
	 * Example of SAX
	 * 
	private static void asxDoc(String xlmFile) throws ParserConfigurationException, SAXException, IOException {
	  File file = new File(xlmFile);
      InputStream inputStream= new FileInputStream(file);
      Reader reader = new InputStreamReader(inputStream,"UTF-8");

      InputSource is = new InputSource(reader);
      is.setEncoding("UTF-8");
      
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      
      DefaultHandler handler = new DefaultHandler() {
    	  
	        boolean bfname = false;
	        boolean blname = false;
	        boolean bnname = false;
	        boolean bsalary = false;

	        public void startElement(String uri, String localName,
	            String qName, Attributes attributes)
	            throws SAXException {

	          System.out.println("Start Element :" + qName);

	          if (qName.equalsIgnoreCase("schema")) {
	        	  bfname = true; attributes.getValue("name");
	          }


	        }

	        public void endElement(String uri, String localName,
	                String qName)
	                throws SAXException {

	              System.out.println("End Element :" + qName);

	        }

	        public void characters(char ch[], int start, int length)
	            throws SAXException {

	          System.out.println(new String(ch, start, length));


	          if (bfname) {
	            System.out.println("First Name : "
	                + new String(ch, start, length));
	            bfname = false;
	          }

	          if (blname) {
	              System.out.println("Last Name : "
	                  + new String(ch, start, length));
	              blname = false;
	           }

	          if (bnname) {
	              System.out.println("Nick Name : "
	                  + new String(ch, start, length));
	              bnname = false;
	           }

	          if (bsalary) {
	              System.out.println("Salary : "
	                  + new String(ch, start, length));
	              bsalary = false;
	           }

	        }

	      };

      saxParser.parse(is, handler);
	}
	*/


}

