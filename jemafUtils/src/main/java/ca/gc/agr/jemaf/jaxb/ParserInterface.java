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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;

import javax.xml.bind.JAXBException;


 /**
  * To be documented
  *
  */
public interface ParserInterface {

	 /**
	  * To be documented
	  *
	  * @param xml
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(String xml);

	 /**
	  * To be documented
	  *
	  * @param xml
	  * @param toBeStricted
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(String xml, boolean toBeStricted);

	 /**
	  * To be documented
	  *
	  * @param xmlURL
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(URL xmlURL);

	 /**
	  * To be documented
	  *
	  * @param xmlURL
	  * @param toBeStricted
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(URL xmlURL, boolean toBeStricted);

	 /**
	  * To be documented
	  *
	  * @param xmlBytes
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(ByteArrayInputStream xmlBytes);

	 /**
	  * To be documented
	  *
	  * @param xmlBytes
	  * @param toBeStricted
	  * @return boolean
	  *
	  */
	public boolean isXMLValid(ByteArrayInputStream xmlBytes, boolean toBeStricted);

     /**
      * To be documented
      *
      * @param strMessageBody
      * @return Object
      *
      */
    public Object load(String strMessageBody);

     /**
      * To be documented
      *
      * @param urlXMLRequest
      * @return Object
      *
      */
    public Object load(URL urlXMLRequest);

     /**
      * To be documented
      *
      * @param strMessageBody
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(String strMessageBody, boolean toBeStricted);

     /**
      * To be documented
      *
      * @param xmlMessageBody
      * @return Object
      *
      */
    public Object load(ByteArrayInputStream xmlMessageBody);

     /**
      * To be documented
      *
      * @param xmlMessageBody
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(ByteArrayInputStream xmlMessageBody, boolean toBeStricted);

     /**
      * To be documented
      *
      * @param urlXMLRequest
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(URL urlXMLRequest, boolean toBeStricted);

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param encoding
	  * @throws JAXBException
	  * @return String
	  *
	  */
	public String serialize(Object objectOut, String encoding) throws JAXBException;

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param outputStream
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut, OutputStream outputStream, String encoding) throws JAXBException;

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param outputWriter
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut, Writer outputWriter, String encoding) throws JAXBException;

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param bytesStream
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut, ByteArrayOutputStream bytesStream,  String encoding) throws JAXBException;

}
