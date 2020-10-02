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
package ca.gc.agr.jemaf.utils.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import ca.gc.agr.jemaf.jaxb.Constants.Charsets;

public class JeMafConvertUtils {
	 
	public static final String DATETIME_PATTERN = "MM/d/yyyy HH:mm:ss a";
	public static final String SIMPLE_DATETIME_PATTERN = "yyyy'-'MM'-'dd";
	public static final String COMPLEX_DATETIME_PATTERN = "yyyy'-'MM'-'dd HH:mm:ss a";
	
	 /*
	  * Convert any kind of array (Hashtable,HashSet,Collection,double[],int[],etc...
	  * to a string with this format: [0, 1, ...] or [NULL]
	  * http://www.codeproject.com/useritems/ArrayToString.asp
	  * 
	  * @param array
	  * @return String
	  */
	 public static String arrayToString(Object array) {
		 return arrayToString(array,"[","]","NULL",", ");
	 }

	 /*
	  * Convert any kind of array (Hashtable,HashSet,Collection,double[],int[],etc...
	  * to a string with this format: [0, 1, ...] or [NULL]
	  * http://www.codeproject.com/useritems/ArrayToString.asp
	  * 
	  * @param array
	  * @return String
	  */
	 @SuppressWarnings("rawtypes")
	public static String arrayToString(Object array, String tagStart, String tagEnd, String tagNull, String tagDelimiter) {
		 if (array == null) {
			 return tagStart + tagNull + tagEnd;
		 } else {
			 Object obj = null;
			 if (array instanceof Hashtable) {
				 array = ((Hashtable)array).entrySet().toArray();
			 } else if (array instanceof HashSet) {
				 array = ((HashSet)array).toArray();
			 } else if (array instanceof Collection) {
				 array = ((Collection)array).toArray();
			 }
			 int length = Array.getLength(array);
			 int lastItem = length - 1;
			 StringBuffer sb = new StringBuffer(tagStart);
			 for (int i = 0; i < length; i++) {
				 obj = Array.get(array, i);
				 if (obj != null) {
					 sb.append(obj);
				 } else {
					 sb.append(tagStart + tagNull + tagEnd);
				 }
				 if (i < lastItem) {
					 sb.append(tagDelimiter);
				 }
			 }
			 sb.append(tagEnd);
			 return sb.toString();
		 }
		 
	 }
	 
	/**
	  * To be documented
	  *
	  * @param value
	  * @return String
	  *
	  */
	public static String objectToString(Object value) {
		String strValue = null;
		try {
			if ( value instanceof String ) {
				strValue = (String)value;
			}
			else if ( value instanceof Integer ) {
				strValue = Integer.toString( ((Integer)value).intValue() );
			}
			else if ( value instanceof Double ) {
				strValue = Double.toString( ((Double)value).doubleValue() );
			}
			else if ( value instanceof Byte ) {
				strValue = Byte.toString( ((Byte)value).byteValue() );
			}
			else if ( value instanceof Float ) {
				strValue = Float.toString( ((Float)value).floatValue() );
			}
			else if ( value instanceof Long ) {
				strValue = Long.toString( ((Long)value).longValue() );
			}
			else if ( value instanceof Short ) {
				strValue = Short.toString( ((Short)value).shortValue() );
			}
			else if ( value instanceof Boolean ) {
				strValue = Boolean.toString( ((Boolean)value).booleanValue() );
			}
			else if ( value instanceof java.util.Date ) {
				strValue = dateToString((java.util.Date)value, DATETIME_PATTERN); 
			}
			else if ( value instanceof ByteArrayInputStream ) {
				strValue = JeMafConvertUtils.arrayInputStreamToString( (ByteArrayInputStream)value );
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return strValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return double
	  *
	  */
	public static double objectToDouble(Object value) {
		double dblValue = (double)0;
		try {
			if ( value instanceof String ) {
				dblValue = java.lang.Double.parseDouble((String)value);
			}
			else if ( value instanceof Integer ) {
				dblValue = ((Integer)value).doubleValue();
			}
			else if ( value instanceof Double ) {
				dblValue = ((Double)value).doubleValue();
			}
			else if ( value instanceof Byte ) {
				dblValue = ((Byte)value).doubleValue();
			}
			else if ( value instanceof Float ) {
				dblValue = ((Float)value).doubleValue();
			}
			else if ( value instanceof Long ) {
				dblValue = ((Long)value).doubleValue();
			}
			else if ( value instanceof Short ) {
				dblValue = ((Short)value).doubleValue();
			}
			else if ( value instanceof Boolean ) {
				dblValue = java.lang.Double.parseDouble(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return dblValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return int
	  *
	  */
	public static int objectToInteger(Object value) {
		int intValue = (int)0;
		try {
			if ( value instanceof String ) {
				intValue = java.lang.Integer.parseInt((String)value);
			}
			else if ( value instanceof Integer ) {
				intValue = ((Integer)value).intValue();
			}
			else if ( value instanceof Double ) {
				intValue = ((Double)value).intValue();
			}
			else if ( value instanceof Byte ) {
				intValue = ((Byte)value).intValue();
			}
			else if ( value instanceof Float ) {
				intValue = ((Float)value).intValue();
			}
			else if ( value instanceof Long ) {
				intValue = ((Long)value).intValue();
			}
			else if ( value instanceof Short ) {
				intValue = ((Short)value).intValue();
			}
			else if ( value instanceof Boolean ) {
				intValue = java.lang.Integer.parseInt(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return intValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return long
	  *
	  */
	public static long objectToLong(Object value) {
		long lngValue = (long)0;
		try {
			if ( value instanceof String ) {
				lngValue = java.lang.Long.parseLong((String)value);
			}
			else if ( value instanceof Integer ) {
				lngValue = ((Integer)value).longValue();
			}
			else if ( value instanceof Double ) {
				lngValue = ((Double)value).longValue();
			}
			else if ( value instanceof Byte ) {
				lngValue = ((Byte)value).longValue();
			}
			else if ( value instanceof Float ) {
				lngValue = ((Float)value).longValue();
			}
			else if ( value instanceof Long ) {
				lngValue = ((Long)value).longValue();
			}
			else if ( value instanceof Short ) {
				lngValue = ((Short)value).longValue();
			}
			else if ( value instanceof Boolean ) {
				lngValue = java.lang.Long.parseLong(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return lngValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return float
	  *
	  */
	public static float objectToFloat(Object value) {
		float fltValue = (float)0;
		try {
			if ( value instanceof String ) {
				fltValue = java.lang.Float.parseFloat((String)value);
			}
			else if ( value instanceof Integer ) {
				fltValue = ((Integer)value).floatValue();
			}
			else if ( value instanceof Double ) {
				fltValue = ((Double)value).floatValue();
			}
			else if ( value instanceof Byte ) {
				fltValue = ((Byte)value).floatValue();
			}
			else if ( value instanceof Float ) {
				fltValue = ((Float)value).floatValue();
			}
			else if ( value instanceof Long ) {
				fltValue = ((Long)value).floatValue();
			}
			else if ( value instanceof Short ) {
				fltValue = ((Short)value).floatValue();
			}
			else if ( value instanceof Boolean ) {
				fltValue = java.lang.Float.parseFloat(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return fltValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return short
	  *
	  */
	public static short objectToShort(Object value) {
		short shtValue = (short)0;
		try {
			if ( value instanceof String ) {
				shtValue = java.lang.Short.parseShort((String)value);
			}
			else if ( value instanceof Integer ) {
				shtValue = ((Integer)value).shortValue();
			}
			else if ( value instanceof Double ) {
				shtValue = ((Double)value).shortValue();
			}
			else if ( value instanceof Byte ) {
				shtValue = ((Byte)value).shortValue();
			}
			else if ( value instanceof Float ) {
				shtValue = ((Float)value).shortValue();
			}
			else if ( value instanceof Long ) {
				shtValue = ((Long)value).shortValue();
			}
			else if ( value instanceof Short ) {
				shtValue = ((Short)value).shortValue();
			}
			else if ( value instanceof Boolean ) {
				shtValue = java.lang.Short.parseShort(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return shtValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return byte
	  *
	  */
	public static byte objectToByte(Object value) {
		byte bytValue = (byte)0;
		try {
			if ( value instanceof String ) {
				bytValue = java.lang.Byte.parseByte((String)value);
			}
			else if ( value instanceof Integer ) {
				bytValue = ((Integer)value).byteValue();
			}
			else if ( value instanceof Double ) {
				bytValue = ((Double)value).byteValue();
			}
			else if ( value instanceof Byte ) {
				bytValue = ((Byte)value).byteValue();
			}
			else if ( value instanceof Float ) {
				bytValue = ((Float)value).byteValue();
			}
			else if ( value instanceof Long ) {
				bytValue = ((Long)value).byteValue();
			}
			else if ( value instanceof Short ) {
				bytValue = ((Short)value).byteValue();
			}
			else if ( value instanceof Boolean ) {
				bytValue = java.lang.Byte.parseByte(JeMafConvertUtils.objectToString(value));
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return bytValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return boolean
	  *
	  */
	public static boolean objectToBoolean(Object value) {
		boolean bValue = false;
		if ( value != null ) {
			try {
				if ( value instanceof Boolean ) {
					bValue = ((Boolean)value).booleanValue();
				}
				else {
					// Try if can translate it
					String newValue = JeMafConvertUtils.objectToString(value);
					if ( newValue != null ) {
						if ( newValue.equals("1") )
							bValue = true;
						else if ( newValue.equalsIgnoreCase("true") )
							bValue = true;
						else if ( newValue.equalsIgnoreCase("yes") )
							bValue = true;		
						else if ( newValue.equalsIgnoreCase("vrai") )
							bValue = true;							
						else if ( newValue.equalsIgnoreCase("oui") )
							bValue = true;							
					}
				}
			}
			catch (Exception ex) { // Just swallow
				;
			}
		}
		
		return bValue;
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return java.math.BigInteger
	  *
	  */
	public static java.math.BigInteger intToBigInteger(int value) {
		return( longToBigInteger((long)value) );
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return int
	  *
	  */
	public static int bigIntegerToInt(java.math.BigInteger value) {
		if ( value != null ) {
			return ( value.intValue() );
		}
		else {
			return( -1 );
		}
	}

	/**
	  * To be documented
	  *
	  * @param value
	  * @return java.math.BigInteger
	  *
	  */
	public static java.math.BigInteger longToBigInteger(long value) {
		return( java.math.BigInteger.valueOf(value) );
	}
	
	/**
	  * To be documented
	  *
	  * @param value String to convert
	  * @return java.math.BigInteger
	  *
	  */
	public static java.math.BigInteger stringToBigInteger(String value) {
		return longToBigInteger(Long.parseLong(value));
	}	

	/**
	  * To be documented
	  *
	  * @param value
	  * @return long
	  *
	  */
	public static long bigIntegerToLong(java.math.BigInteger value) {
		if ( value != null ) {
			return ( value.longValue() );
		}
		else {
			return( -1 );
		}
	}

	/**
	  * Convert BigInteger to String
	  *
	  * @param value
	  * @return String
	  *
	  */
	public static String bigIntegerToString(java.math.BigInteger value) {
		if ( value != null ) {
			return ( value.toString() );
		}
		else {
			return( "" );
		}
	}
	
	/**
	  * To be documented
	  *
	  * @param value
	  * @return ByteArrayInputStream
	  *
	  */
	public static ByteArrayInputStream objectToArrayInputStream(Object value) {
		ByteArrayInputStream streamValue = null;
		try {
			if ( value instanceof ByteArrayInputStream ) {
				streamValue = (ByteArrayInputStream)value;
			}
			else {
				// Convert any object in String
				String strValue = JeMafConvertUtils.objectToString(value);
				streamValue = new ByteArrayInputStream(strValue.getBytes());
			}
		}
		catch (Exception ex) { // Just swallow
			;
		}
	
		return streamValue;
	}

	/**
	  * Convert Xml String UTF-8 encoded to InputStream Object 
	  *
	  * @param xml
	  * @return InputStream
	  *
	  */	
	public static java.io.InputStream stringToInputStream(String xml) {
		
		if(xml==null) return null; 
		
		xml = xml.trim();
		java.io.InputStream in = null;
		
		try{
			in = new java.io.ByteArrayInputStream(
					xml.getBytes(Charsets.getUTF_8()));
		}
		catch(Exception ex){
		}
		
		return in; 
	}

	/**
	  * Convert InputStream Object to String   
	  *
	  * @param is
	  * @return String
	  *
	  */		
	@SuppressWarnings("deprecation")
	public static String inputStreamToString(java.io.InputStream is) {
		
		java.io.DataInputStream din = new java.io.DataInputStream(is);
		StringBuffer sb = new StringBuffer();
		try {
			String line = null;
			while((line=din.readLine()) != null){
				sb.append(line+"\n");
			}
		}
		catch(Exception ex){
			ex.getMessage();
		}
		finally{
			try{
				is.close();
			}
			catch(Exception ex) {}
		}
		
		return sb.toString();
	}

	/**
	  * To be documented
	  *
	  * @param streamIn
	  * @throws IOException
	  * @return ByteArrayOutputStream
	  *
	  */
	public static ByteArrayOutputStream arrayInputStreamToArrayOutputStream(ByteArrayInputStream streamIn) throws IOException {
		// Allocate a buffer for reading entry data.
		ByteArrayOutputStream streamOut =  null;
		if ( streamIn != null ) {
			streamOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int    bytesRead;
			streamIn.mark(0);
			while ((bytesRead = streamIn.read(buffer)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamIn.reset();
		}
	
		return( streamOut );
	}

	/**
	  * To be documented
	  *
	  * @param streamIn
	  * @throws IOException
	  * @return byte[]
	  *
	  */
	public static byte[] arrayInputStreamToByteArray(ByteArrayInputStream streamIn) throws IOException {
		// Allocate a buffer for reading entry data.
		byte[] byteOut =  null;
		if ( streamIn != null ) {
			ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int    bytesRead;
			streamIn.mark(0);
			while ((bytesRead = streamIn.read(buffer)) != -1) {
				streamOut.write(buffer, 0, bytesRead);
			}
			streamIn.reset();
			byteOut = streamOut.toByteArray();
		}
	
		return( byteOut );
	}

	/**
	  * To be documented
	  *
	  * @param streamIn
	  * @throws IOException
	  * @return String
	  *
	  */
	public static String arrayInputStreamToString(ByteArrayInputStream streamIn) throws IOException {
		// Allocate a buffer for reading entry data.
		String newString = null;
		byte[] byteOut =  arrayInputStreamToByteArray(streamIn);
		if ( byteOut != null ) {
			newString = new String(byteOut);
		}
	
		return( newString );
	}

	/**
	  * To be documented
	  *
	  *@param list
	  * @return String
	  *
	  */
	public static String stringArrayToString(String list[]) {
		StringBuffer buf = new StringBuffer();
		if ( list != null ) {
			int length = list.length;
			for (int i = 0; i < length; i++)
			{
			   buf.append(list[i]);
			 }
		}
		return( buf.toString() );
	
	}
	
	/**
	 * Convert String to ByteArrayInputStream
	 *
	 * @param anySource
	 * @return ByteArrayInputStream
	 */			
	public static ByteArrayInputStream convertStringToByteStream(String anySource) {
		if ( anySource == null ) return new ByteArrayInputStream("".getBytes());
		return convertStringToByteStream(anySource.getBytes());
	}	
	
	/**
	 * Convert byte[] to ByteArrayInputStream
	 *
	 * @param anySource
	 * @return ByteArrayInputStream
	 */		
	public static ByteArrayInputStream convertStringToByteStream(byte[] anySource) {
		if ( anySource == null ) return new ByteArrayInputStream("".getBytes());
		return new ByteArrayInputStream(anySource);
	}		
	
	/**
	  * To be documented
	  *
	  * @param list
	  * @return String
	  *
	  */
	public static String formatArrayToString(String list[]) {
		StringBuffer buf = new StringBuffer();
		if ( list != null ) {
			int length = list.length;
			for (int i = 0; i < length; i++)
			{
			   buf.append("[" + list[i] + "]");
			   if ( i < length - 1 ) buf.append(",");
			 }
		}
		return( buf.toString() );
	
	}	
	
	/**
	  * Convert Date to String with a Date/Time pattern
	  * http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html
	  *
	  * @param dateToFormat
	  * @param formatDate
	  * @return String
	  *
	  */		
	public static String dateToString(java.util.Date dateToFormat, String formatDate) {
       SimpleDateFormat sim = new SimpleDateFormat(formatDate);
		return sim.format(dateToFormat);			
	}
	
	  /**
	  * Get Complete String Date YYYY/MM/DD HH:MM:AA from an XML Gregorian Calandar
	  *
	  * @param xmlCal
	  * @return String
	  *
	  */	
    public static String getDate(XMLGregorianCalendar xmlCal)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(COMPLEX_DATETIME_PATTERN);
        Calendar cal = xmlCal.toGregorianCalendar();
        Date objDate = cal.getTime();
        return sdf.format(objDate);
    }	
    
    /**
     * Get Simple Date format from an XML Gregorian Calandar
     *
     * @param xmlCal
     * @return String
     *
     */	
    public static String getDateSimple(XMLGregorianCalendar xmlCal)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATETIME_PATTERN);
    	Calendar cal = xmlCal.toGregorianCalendar();
    	Date objDate = cal.getTime();
    	return sdf.format(objDate);
    }	    
    
    /**
     * Get Complete String Date YYYY/MM/DD HH:MM:AA from a regular Calandar
     *
     * @param cal
     * @return String
     */    
    public static String getDate(Calendar cal)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(COMPLEX_DATETIME_PATTERN);
    	Date objDate = cal.getTime();
    	return sdf.format(objDate);
    }    

    /**
     *  Get Simple Date format from a regular Calandar
     *
     * @param xmlCal
     * @return String
     */    
    public static String getDateSimple(Calendar cal)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATETIME_PATTERN);
    	Date objDate = cal.getTime();
    	return sdf.format(objDate);
    }      
    
    /**
     * Convert regular Datetime to XMLGregorianCalendar that fit with W3C XML Schema 1.0
     *
     * @param date
     * @return XMLGregorianCalendar
     */    
    public static XMLGregorianCalendar getDateToXMLGregorianCalendar(java.util.Date date) throws DatatypeConfigurationException
    {
    	DateFormat dateFormat = new SafeSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'S'Z'");
    	dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    	return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateFormat.format(date.getTime()) );
    }        
    
    /**
     * Convert XMLGregorianCalendar with W3C XML Schema 1.0 to regular Datetime
     *
     * @param date
     * @return XMLGregorianCalendar
     */    
    public static Date getXMLGregorianCalendarToDate(XMLGregorianCalendar calendar)
    {
        return calendar.toGregorianCalendar().getTime();
    }      
    
    /**
     * Get current datetime
     *
     * @return Date
     */    
    public static java.util.Date getDateNow()
    { 
    	return java.util.Calendar.getInstance().getTime();
    }       
    
    /**
     * Get current datetime in XMLGregorianCalendar standard
     *
     * @return XMLGregorianCalendar
     */    
    public static XMLGregorianCalendar getXMLGregorianCalendarNow() throws DatatypeConfigurationException
    { 
    	return getDateToXMLGregorianCalendar(getDateNow());
    }      
    
    /*
     * Compute seconds between two date
     */
    public static long diffInSeconds(XMLGregorianCalendar start, XMLGregorianCalendar end) {
		// Convert in second
	      // the above two dates are one second apart
	      java.util.Date dStart = start.toGregorianCalendar().getTime();
	      java.util.Date dEnd = end.toGregorianCalendar().getTime();
	      return diffInSeconds(dStart, dEnd);
    }
    
    /*
     * Compute seconds between this date on the current date
     */
    public static long diffInSeconds(XMLGregorianCalendar start) throws DatatypeConfigurationException {
		// Convert in second
	      // the above two dates are one second apart
	      java.util.Date dStart = start.toGregorianCalendar().getTime();
	      java.util.Date dEnd = getXMLGregorianCalendarNow().toGregorianCalendar().getTime();
	      return diffInSeconds(dStart, dEnd);
    }    
    
    /*
     * Compute seconds between two date
     */
    public static long diffInSeconds(Date start, Date end) {
	      // the above two dates are one second apart
	      long l1 = start.getTime();
	      long l2 = end.getTime();
	      return (l2 - l1) / 1000;		    	
    }    
    
    /*
     * Compute seconds between this date on the current date
     */
    public static long diffInSeconds(Date start) {
	      // the above two dates are one second apart
	      long l1 = start.getTime();
	      long l2 = getDateNow().getTime();
	      return (l2 - l1) / 1000;		    	
    }        
    
    private static class SafeSimpleDateFormat extends SimpleDateFormat
    {
    	private static final long serialVersionUID = 982347987249L;

    	public SafeSimpleDateFormat(String pattern)
    	{
    		super(pattern);
    	}

    	@Override
    	public synchronized java.util.Date parse(String source) throws ParseException
    	{
    		return super.parse(source);
    	}
    }	    
	
}
