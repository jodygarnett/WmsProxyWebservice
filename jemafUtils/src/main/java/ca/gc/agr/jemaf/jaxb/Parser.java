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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.log4j.Logger;

import ca.gc.agr.jemaf.jaxb.Constants.Charsets;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class Parser implements ParserInterface {

    //	 Logger object for logging in this class
	private static Logger LOGGER = Logger.getLogger(ParserInterface.class);
	public static final String DEFAULT_NS = "net.opengis.ows";
	public static final String DEFAULT_TARGET_NAMESPACE_MAP = "com.sun.xml.bind.defaultNamespaceRemap";
	private String namespace = "";
	private ClassLoader classLoader = null;
	private Map<String,Object>  properties = null;
	private JAXBContext jc = null;
	private ArrayList<String> documentErrors = new ArrayList<String>();
	private boolean formatOutput = Boolean.TRUE;
	private boolean isNoProcessingInstructionAccepted = Boolean.FALSE;

	/**
	  * Parser Construstor
	  */
	
	/**
	  * Create a JAXB Parser
	  *
	  * @throws JAXBException
	  */
	public Parser() throws JAXBException {
		this(DEFAULT_NS, null);
	}
	
	/**
	  * Create a JAXB Parser
	  *
	  * @param classLoader
	  * @throws JAXBException
	  */
	public Parser(ClassLoader classLoader) throws JAXBException {
		this(DEFAULT_NS, classLoader, null);
	}	
	
	 /**
	  * To be documented
	  *
	  * @param namespace
	  * @param targetNamespace
	  * @throws JAXBException
	  */
	public Parser(String namespace, String targetNamespace) throws JAXBException {
		this(namespace, null, targetNamespace, null);
	}	

	 /**
	  * To be documented
	  *
	  * @param namespace
	  * @param classLoader
	  * @param targetNamespace
	  * @throws JAXBException
	  */
	public Parser(String namespace, ClassLoader classLoader, String targetNamespace) throws JAXBException {
		this(namespace, classLoader, targetNamespace, null);
	}
	
	 /**
	  * To be documented
	  *
	  * @param namespace
	  * @param classLoader
	  * @param targetNamespace
	  * @param properties
	  * @throws JAXBException
	  */
	public Parser(String namespace, ClassLoader classLoader, String targetNamespace, Map<String,Object> properties) throws JAXBException {
		this.namespace = namespace;
		this.properties = properties;
		if ( classLoader == null ) 
			this.classLoader = Thread.currentThread().getContextClassLoader();
		if ( this.properties == null )
			this.properties = new HashMap<String, Object>();
		if ( targetNamespace != null ) 
			modifyDefaultTargetNamespace(targetNamespace);
		instanciateParser();
	}	
	

	/**
	 * @return the properties
	 */
	public Map<String, ?> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	/**
	 * @return the classLoader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * @param classLoader the classLoader to set
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * @return the targetNamespace
	 */
	public String getDefaultTargetNamespace() {
		if ( isKeyPropertyExists(DEFAULT_TARGET_NAMESPACE_MAP) )  
			return (String)getValueProperty(DEFAULT_TARGET_NAMESPACE_MAP);
		else 
			return null;
	}	
	
	/**
	 * @param ns This will set the default Target Namespace
	 */	
	public void modifyDefaultTargetNamespace(String newTargetNamespace) {
		if ( isKeyPropertyExists(DEFAULT_TARGET_NAMESPACE_MAP) ) { 
			removeProperty(DEFAULT_TARGET_NAMESPACE_MAP);
		}
		addProperty(DEFAULT_TARGET_NAMESPACE_MAP, newTargetNamespace);
	}
	
	/**
	 * @param ns This will remove the default Target Namespace
	 */	
	public void removeDefaultTargetNamespace() {
		if ( isKeyPropertyExists(DEFAULT_TARGET_NAMESPACE_MAP) ) 
			removeProperty(DEFAULT_TARGET_NAMESPACE_MAP);
	}	
	
	/**
	 * @param key Property Name
	 * @param value Property Value
	 */	
	public void addProperty(String key, Object value) {
		properties.put(key, value);
	}	
	
	/**
	 * @param key This will remove a Property Name
	 */	
	public void removeProperty(String key) {
		properties.remove(key);
	}	
	
	/**
	 * @param key Is the Key Name exist in the Properties Object?
	 */	
	public boolean isKeyPropertyExists(String key) {
		return properties.containsKey(key);
	}	

	/**
	 * @param key Is the Value exist in the Properties Object?
	 */	
	public boolean isValuePropertyExists(String value) {
		return properties.containsValue(value);
	}	
	
	/**
	 * @param key Get the value for a Key Name
	 */	
	public Object getValueProperty(String key) {
		return properties.get(key);
	}		
	
	/**
	 * @return the formatOutput
	 */
	public boolean isFormatOutput() {
		return formatOutput;
	}

	/**
	 * @param formatOutput the formatOutput to set
	 */
	public void setFormatOutput(boolean formatOutput) {
		this.formatOutput = formatOutput;
	}

	/**
	 * @return the isNoProcessingInstructionAccepted
	 */
	public boolean isNoProcessingInstructionAccepted() {
		return isNoProcessingInstructionAccepted;
	}

	/**
	 * @param isNoProcessingInstructionAccepted the isNoProcessingInstructionAccepted to set
	 */
	public void setNoProcessingInstructionAccepted(
			boolean isNoProcessingInstructionAccepted) {
		this.isNoProcessingInstructionAccepted = isNoProcessingInstructionAccepted;
	}

	/**
	  * To be documented
	  *
	  * @throws Exception
	  *
	  */
	private void instanciateParser() throws JAXBException {
	    // create a JAXBContext with the schema
		log("New JAXB Instance of " + getNamespace() );
		this.jc = JAXBContext.newInstance( getNamespace(), getClassLoader(), getProperties() );
	}

	 /**
	  * To be documented
	  *
	  * @return String
	  *
	  */
	public String getNamespace() {
		return this.namespace;
	}

	 /**
	  * To be documented
	  *
	  * @return String
	  *
	  */
	public ArrayList<String> getDocumentErrors() {
		return this.documentErrors;
	}

	 /**
	  * To be overriden
	  *
	  * @return String
	  *
	  */
	public NamespacePrefixMapper getNamespacePrefixMapper() {
		return new NamespacePrefixMapperImpl();
	}

     /**
      * To be documented
      *
      * @param xml
      * @return boolean
      *
      */
    public boolean isXMLValid(String xml) {
    	return (load(xml) != null);
    }


     /**
      * To be documented
      *
      * @param xml
      * @param toBeStricted
      * @return boolean
      *
      */
    public boolean isXMLValid(String xml, boolean toBeStricted) {
    	return (load(xml,toBeStricted) != null);
    }


     /**
      * To be documented
      *
      * @param xmlURL
      * @return boolean
      *
      */
    public boolean isXMLValid(URL xmlURL) {
    	return (load(xmlURL) != null);
    }


     /**
      * To be documented
      *
      * @param xmlURL
      * @param toBeStricted
      * @return boolean
      *
      */
    public boolean isXMLValid(URL xmlURL, boolean toBeStricted) {
    	return (load(xmlURL,toBeStricted) != null);
    }

     /**
      * To be documented
      *
      * @param xmlBytes
      * @return boolean
      *
      */
    public boolean isXMLValid(ByteArrayInputStream xmlBytes) {
    	return (load(xmlBytes) != null);
    }


     /**
      * To be documented
      *
      * @param xmlBytes
      * @param toBeStricted
      * @return boolean
      *
      */
    public boolean isXMLValid(ByteArrayInputStream xmlBytes, boolean toBeStricted) {
    	return( load(xmlBytes,toBeStricted) != null );
    }

     /**
      * To be documented
      *
      * @param strMessageBody
      * @return Object
      *
      */
    public Object load(String strMessageBody) {
    	return load(strMessageBody,false);
    }

     /**
      * To be documented
      *
      * @param strMessageBody
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(String strMessageBody, boolean toBeStricted) {
    	// New collection of error, for now, we handle them one
    	documentErrors = new ArrayList<String>();
    	return load(strMessageBody, toBeStricted, getDocumentErrors());
    }

     /**
      * To be documented
      *
      * @param xmlMessageBody
      * @return Object
      *
      */
    public Object load(ByteArrayInputStream xmlMessageBody)  {
    	return load(xmlMessageBody,false);
    }

     /**
      * To be documented
      *
      * @param xmlMessageBody
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(ByteArrayInputStream xmlMessageBody, boolean toBeStricted)  {
    	// New collection of error, for now, we handle them one
    	documentErrors = new ArrayList<String>();
    	return load(xmlMessageBody, toBeStricted, getDocumentErrors());
    }

     /**
      * To be documented
      *
      * @param xmlStream
      * @return Object
      *
      */
    public Object load(InputStream xmlStream)  {
    	return load(xmlStream,false);
    }

     /**
      * To be documented
      *
      * @param xmlStream
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(InputStream xmlStream, boolean toBeStricted)  {
    	// New collection of error, for now, we handle them one
    	documentErrors = new ArrayList<String>();
    	return load(xmlStream, null, toBeStricted, getDocumentErrors());
    }

    /**
     * To be documented
     *
     * @param urlXMLRequest
     * @return Object
     *
     */
    public Object load(URL urlXMLRequest) {
    	return load(urlXMLRequest,false);
    }

     /**
      * To be documented
      *
      * @param urlXMLRequest
      * @param toBeStricted
      * @return Object
      *
      */
    public Object load(URL urlXMLRequest, boolean toBeStricted) {
    	// New collection of error, for now, we handle them one
    	documentErrors = new ArrayList<String>();
    	return load(null, urlXMLRequest, toBeStricted, getDocumentErrors());
    }

     /**
      * To be documented
      *
      * @param strMessageBody
      * @param toBeStricted
      * @param isOWS
      * @param documentErrors
      * @return Object
      *
      */
    private Object load(String strMessageBody,
    					boolean toBeStricted,
    					ArrayList<String>  docErrors)  {

    	Object test=null;

    	if ( strMessageBody != null ) {
    		try {
    			ByteArrayInputStream bytesMessageBody = null;
		    	bytesMessageBody = new ByteArrayInputStream(strMessageBody.getBytes());
		    	test = load(bytesMessageBody,toBeStricted,docErrors);
		    	bytesMessageBody.close();
    		}
    		catch (IOException e) {
    			// Just give an idea on that was the errors on the sreen
    			catchErrors(e,docErrors);
    		}
    	}
    	else {
			documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
								"Location:" + "none" + 
								"Message:" + "No stream given to unmarshall" );
    	}

    	return test;
    }

    /**
      * To be documented
      *
      * @param xmlMessageBody
      * @param toBeStricted
      * @param documentErrors
      * @return Object
      *
      */
    private Object load(ByteArrayInputStream xmlMessageBody,
    					boolean toBeStricted,
    					ArrayList<String> docErrors )  {
    	return load((InputStream)xmlMessageBody,null,toBeStricted,docErrors);
    }

     /**
      * To be documented
      *
      * @param streamMessageBody
      * @param urlXMLRequest
      * @param xmlUnmarshaller
      * @param toBeStricted
      * @param documentErrors
      * @return Object
      */
    private Object load(InputStream streamMessageBody,
				    	URL urlXMLRequest,
						boolean toBeStricted,
						final ArrayList<String>  docErrors)  {
    	Object test = null;

    	if ( streamMessageBody != null || urlXMLRequest != null ) {
    		try {

    			// turn off the JAXB provider's default validation mechanism to
    			// avoid duplicate validation
    			//ValidationEventCollector vec = new ValidationEventCollector();
    			//xmlGetRequest.setEventHandler( vec );
    			//xmlGetRequest.setValidating( false );

    			// Relax validation by intercepting all errors
    			ValidationEventHandler validationEventHandler = new ValidationEventHandler(){


    				 /**
    				  * To be documented
    				  *
    				  * @param event
    				  * @return boolean
    				  *
    				  */
    				public boolean handleEvent(ValidationEvent event) {
    					if ( event.getSeverity() == ValidationEvent.FATAL_ERROR ||
    							event.getSeverity() == ValidationEvent.ERROR ) {
    						warn("JAXB Handling event: Severity:"+event.getSeverity()+";Locator:"+event.getLocator()+";Message:"+event.getMessage());
    						documentErrors.add( "Severity:" + new Integer(event.getSeverity()).toString() +
			    								"Location:" + event.getLocator() + 
			    								"Message:" + event.getMessage() );
    					}
    					return true;
    				}
    			};

    			// Create unmarshaller JAXB object
    			log("Instanciating UnMarshaller");
    			Unmarshaller xmlUnmarshaller = jc.createUnmarshaller();

    			// handle events error raise by JAXB
    			log("Setting Event Handler");
    			xmlUnmarshaller.setEventHandler(validationEventHandler);

    			// Be so cruel!
    			log("Validating set to 'true'");
    			//JAXB2 will raise an error if we use it: xmlUnmarshaller.setValidating( true );

    			// Read the execute request
    			if ( streamMessageBody != null ) {
        			log("UnMarshalling InputStream");
    				test = (Object)xmlUnmarshaller.unmarshal(streamMessageBody);
    			}
    			else {
        			log("UnMarshalling URL");
    				test = (Object)xmlUnmarshaller.unmarshal(urlXMLRequest);
    			}

    			// If restricted, check whatever errors
    			if ( toBeStricted )
    				if ( docErrors.size() > 0 )
    					test = null;

    		}
    		catch (JAXBException jaxbEx) {
    			//        	 already saved!
    			catchErrors(jaxbEx,docErrors);
    		}
    		catch (Exception e) {
    			// Just give an idea on that was the errors on the sreen
    			catchErrors(e,docErrors);
    		}
    	}
    	else {
			log("UnMarshalling what?");
			documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
								"Location:" + "none" + 
								"Message:" + "No stream given to unmarshall" );
    	}

    	return (Object)test;
    }

     /**
      * To be documented
      *
      * @param e
      * @param docErrors
      *
      */
    private void catchErrors(Exception e,
    				 		 ArrayList<String> docErrors) {

		if (e instanceof JAXBException) {
			warn("JAXBException exception raised. Analysing the problem.");
			JAXBException jaxEx = (JAXBException)e;
			if ( docErrors.size() == 0 ) {
				if ( jaxEx.getMessage() != null ) {
					documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
										"Location:" + "none" + 
										"Message:" + "JAXBException:" + jaxEx.getMessage() );
				}
				if ( jaxEx.getCause() != null ) {
					documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
										"Location:" + "none" + 
										"Message:" + "JAXBException:" + jaxEx.getCause().toString() );
				}
				if ( jaxEx.getLinkedException() != null ) {
					Throwable errorLinked = jaxEx.getLinkedException();

					if ( errorLinked.getMessage() != null ) {
						documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
											"Location:" + "none" + 
											"Message:" + "JAXBException:" + errorLinked.getMessage() );
					}
					if ( errorLinked.getCause() != null ) {
						documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
											"Location:" + "none" + 
											"JAXBException:" + errorLinked.getCause().toString() );
					}
				}
			}
		}
		else {
			// Just give an idea on that was the errors on the sreen
			warn("Fatal Error Exception raise with a non-JAXBException. It will add in a document errors.");
			documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
								"Location:" + "none" + 
								"Message:" + "Fatal error:" + e.getMessage() );
		}

    }

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param encoding
	  * @throws JAXBException
	  * @return String
	  *
	  */
	public String serialize(Object objectOut,
		                    String encoding) throws JAXBException
	{
		documentErrors = new ArrayList<String>();
		java.io.StringWriter sw = new java.io.StringWriter();
		serializeTo(objectOut,getDocumentErrors(),encoding,null,sw);
		return sw.toString();
	}

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param outputStream
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut,
						  OutputStream outputStream,
						  String encoding) throws JAXBException
	{
		documentErrors = new ArrayList<String>();
		serializeTo(objectOut,getDocumentErrors(),encoding,outputStream,null);
	}

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param outputWriter
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut,
						  Writer outputWriter,
					 	  String encoding) throws JAXBException
	{
		documentErrors = new ArrayList<String>();
		serializeTo(objectOut,getDocumentErrors(),encoding,null,outputWriter);
	}

	 /**
	  * To be documented
	  *
	  * @param objectOut
	  * @param bytesStream
	  * @param encoding
	  * @throws JAXBException
	  *
	  */
	public void serialize(Object objectOut,
						  ByteArrayOutputStream bytesStream,
		                  String encoding) throws JAXBException
	{
		documentErrors = new ArrayList<String>();
		serializeTo(objectOut,getDocumentErrors(),encoding,bytesStream,null);
	}

	/**
	 * To be documented
	 *
	 * @param marshallerContext
	 * @param encoding
	 * @throws JAXBException
	 *
	 */
	private void prepareEncoding(
						Marshaller marshallerContext,
						String encoding)  throws JAXBException	{
		//Depending, it might that, encoding="iso-8859-1"

		// Default UTF-8 is not defined
		if ( ! Charsets.isValid( encoding ) ) {
			warn("prepareEncoding:Invalid encoding! Was=" + encoding + " new encoding used=" + Charsets.getUTF_8() );
			encoding = Charsets.getUTF_8();
		} 
		marshallerContext.setProperty( Marshaller.JAXB_ENCODING, encoding );
		marshallerContext.setProperty( jaxbns.netJaxbBind, getNamespacePrefixMapper() );
	}

	 /**
	  * To be documented
	  *
	  * @param objectToSerialize
	  * @param documentErrors
	  * @param encoding
	  * @param outStream
	  * @param outWriter
	  * @throws JAXBException
	  *
	  */
	private void serializeTo(Object objectToSerialize,
		   				    final ArrayList<String> docErrors,
					   	    String encoding,
					   	    OutputStream outStream,
					   	    Writer outWriter) throws JAXBException {

		// Give somethng back if not defined
		// or the same object fill up for the marshaller
		if ( outStream == null && outWriter == null) {
			warn("No output to serialize in. It will add in a document errors.");
			documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
					"Location:" + "none" + 
					"Message:" + "No output to marshall" );
		}

		else if ( objectToSerialize == null) {
			warn("No object to serialize. It will add in a document errors.");
			documentErrors.add( "Severity:" + new Integer(ValidationEvent.FATAL_ERROR).toString() +
								"Location:" + "none" + 
								"Message:" + "No stream given to unmarshall" );
		}

		else {
			try {
				// Relax validation by intercepting all errors
				ValidationEventHandler validationEventHandler = new ValidationEventHandler(){

					 /**
					  * To be documented
					  *
					  * @param event
					  * @return boolean
					  *
					  */
					public boolean handleEvent(ValidationEvent event) {
						if ( event.getSeverity() == ValidationEvent.FATAL_ERROR ||
								event.getSeverity() == ValidationEvent.ERROR ) {
							documentErrors.add( "Severity:" + new Integer(event.getSeverity()).toString() +
												"Location:" + event.getLocator() + 
												"Message:" + event.getMessage() );
						}
						return true;
					}
				};

				log("Instanciating Marshaller");
				Marshaller marshallerContext = jc.createMarshaller();

				// handle events error raise by JAXB
				log("Setting Event Handler");
				marshallerContext.setEventHandler(validationEventHandler);

				// the marshaller will print out the XML declaration 
				marshallerContext.setProperty( "com.sun.xml.bind.xmlDeclaration",  isNoProcessingInstructionAccepted() );

				// Nice cr-lf will help to read the document
				marshallerContext.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, isFormatOutput() );
				
				log("Preparing Encoding");
				prepareEncoding(marshallerContext, encoding);
				
				log("Marshalling");
				if ( outStream != null )
					marshallerContext.marshal(objectToSerialize, outStream);
				else
					marshallerContext.marshal(objectToSerialize, outWriter);
			}
			catch (JAXBException jaxbEx) {
				//        	 already saved!
				catchErrors(jaxbEx,docErrors);
			}
			catch (Exception e) {
				// Just give an idea on that was the errors on the sreen
				catchErrors(e,docErrors);
			}
		}

	}

     /**
      * Message to display in debug mode
      *
      * @param message
      *
      */
    private void log(String message) {
		if ( LOGGER.isTraceEnabled() ) { LOGGER.debug(message) ; }
    }

    /**
     * Message to display in error mode with a tracable Exception
     *
     * @param message
     * @param exception
     *
     */
   private void warn(String message) {
	   LOGGER.warn(message);
   }
}
