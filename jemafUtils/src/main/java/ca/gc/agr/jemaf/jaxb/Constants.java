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

import ca.gc.agr.jemaf.utils.JeMafUtils;

 /**
  * To be documented
  *
  */
public class Constants {

	public static class Charsets {

		final static String CHARSET = "CHARSET=";
		
		private final static String[] Charsets = {
				"US-ASCII",
				"ISO-8859-1",
				"ISO-8859-2",
				"ISO-8859-3",
				"ISO-8859-4",
				"ISO-8859-5",
				"ISO-8859-6",
				"ISO-8859-7",
				"ISO-8859-8",
				"ISO-8859-9",
				"UTF-8",
				"UTF-16BE",
				"UTF-16LE",
				"UTF-16"
			};

		// Must match index 'String[] Charsets' by index
		private final static String[] EncodingCharsets = {
				"ASCII",
				"8859_1",
				"8859_2",
				"8859_3",
				"8859_4",
				"8859_5",
				"8859_6",
				"8859_7",
				"8859_8",
				"8859_9",
				"UTF8",
				"UTF16BE",
				"UTF16LE",
				"UTF16"
			};
		
		/**
		  * To be documented
		  *
		  * @param encoding
		  * @return String
		  *
		  */
		public static String getCorresponding(String encoding) {
			String newEncoding = null;
			if ( encoding != null ) {
		    	for (int i=0; i<Charsets.length; i++) {
		    		newEncoding = checkIndex(encoding,i);
		    		if ( newEncoding != null ) break;
		    	}
			}
		
			return newEncoding;
		}

		/**
		  * To be documented
		  *
		  * @param encoding
		  * @param indexCharset
		  * @return String
		  *
		  */
		private static String checkIndex(String encoding,
												int indexCharset) {
			if ( encoding.equalsIgnoreCase( Charsets[indexCharset] ) ||
			     encoding.equalsIgnoreCase( EncodingCharsets[indexCharset] )  ) {
				return( EncodingCharsets[indexCharset] );
			}
		
			return( null );
		}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getUS_ASCII() {return Charsets[0];}
		//ISO-8859-1   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_1() {return Charsets[1];}
		public static final String getDefault() {return getISO_8859_1();}
		//ISO-8859-2   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_2() {return Charsets[2];}
		//ISO-8859-3   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_3() {return Charsets[3];}
		//ISO-8859-4   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_4() {return Charsets[4];}
		//ISO-8859-5   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_5() {return Charsets[5];}
		//ISO-8859-6   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_6() {return Charsets[6];}
		//ISO-8859-7   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_7() {return Charsets[7];}
		//ISO-8859-8   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_8() {return Charsets[8];}
		//ISO-8859-9   ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getISO_8859_9() {return Charsets[9];}
		//UTF-8 Eight-bit UCS Transformation Format

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getUTF_8() {return Charsets[10];}
		//UTF-16BE Sixteen-bit UCS Transformation Format, big-endian byte order

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getUTF_16BE() {return Charsets[11];}
		//UTF-16LE Sixteen-bit UCS Transformation Format, little-endian byte order

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getUTF_16LE() {return Charsets[12];}
		//UTF-16 Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getUTF_16() {return Charsets[13];}

		/**
		  * To be documented
		  *
		  * @param charset
		  * @return boolean
		  *
		  */
		public static boolean isValid(String charset) {
			boolean bOk = false;
		
			if ( charset != null ) {
		    	for (int i=0; i<Charsets.length; i++) {
		    		if ( charset.equalsIgnoreCase(Charsets[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}
	    
	    public static String getContentTypeCharset(String contentType) {
	    	String defaultCharset = "";
	    	
	    	// Extract the charset
	    	if ( ! JeMafUtils.isEmptyString(contentType) ) {
	    		contentType = contentType.toUpperCase();
	    		int posStart = contentType.indexOf(CHARSET);
	    		if ( posStart != -1 ) {
	    			int posEnd = contentType.indexOf(";", posStart+CHARSET.length());
	    			String charset = "";
	    			if ( posEnd != -1 )
	    				charset = contentType.substring(posStart+CHARSET.length(), posEnd);
	    			else 
	    				charset = contentType.substring(posStart+CHARSET.length());
	    			defaultCharset = charset.trim().toUpperCase();
	    		}
	    	}
	    	
	    	if ( JeMafUtils.isEmptyString(defaultCharset) ) {
	    		// Still nothing, set a default
	    		defaultCharset = Constants.Charsets.getUTF_8();
	    	}
	    	
	    	return defaultCharset;
	    }		

	}
	


	 public static class Mimetypes {

	    /* MIME type syntax according to RFC 2045:

        content := type "/" subtype
                    *(";" parameter)
                    ; Matching of media type and subtype
                    ; is ALWAYS case-insensitive.

         type := discrete-type / composite-type

         discrete-type := "text" / "image" / "audio" / "video" /
                          "application" / extension-token

         composite-type := "message" / "multipart" / extension-token

         extension-token := ietf-token / x-token

         ietf-token := <An extension token defined by a
                        standards-track RFC and registered
                        with IANA.>

         x-token := <The two characters "X-" or "x-" followed, with
                     no intervening white space, by any token>

         subtype := extension-token / iana-token

         iana-token := <A publicly-defined extension token. Tokens
                        of this form must be registered with IANA
                        as specified in RFC 2048.>

         parameter := attribute "=" value

         attribute := token
                      ; Matching of attributes
                      ; is ALWAYS case-insensitive.

         value := token / quoted-string

         token := 1*<any (US-ASCII) CHAR except SPACE, CTLs,
                     or tspecials>

         tspecials :=  "(" / ")" / "<" / ">" / "@" /
                       "," / ";" / ":" / "\" / <">
                       "/" / "[" / "]" / "?" / "="
                       ; Must be in quoted-string,
                       ; to use within parameter values

    */
		
		private final static String[] MimeTypes = {
				"text/plain",
				"text/html",
				"text/xml",
				"application/octet-stream",
				"application/zip",
				"image/png",
				"image/jpeg",
				"image/x-bmp",
				"image/svg+xml",
				"model/vrml",
				"model/x3d",
				"audio/wav",
				"audio/x-mp3",
				"application/x-zip-compressed",
				"application/gml",
				"application/esriShapefile",
				"application/wkt",
				"application/wkb",
				"application/x-www-form-urlencoded",
				"application/vnd.google-earth.kml+xml",
				"application/vnd.google-earth.kmz",
				"application/xop+xml",
				"application/soap+xml",
				"multipart/form-data"
		};

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getPlain() {return MimeTypes[0];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getHtml() {return MimeTypes[1];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getXml() {return MimeTypes[2];}
		public static final String getDefault() {return getXml();}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getBytes() {return MimeTypes[3];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getZip() {return MimeTypes[4];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getImagePng() {return MimeTypes[5];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getImageJpeg() {return MimeTypes[6];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getWinXBmp() {return MimeTypes[7];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getImageSvgXml() {return MimeTypes[8];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getImageModelVrml() {return MimeTypes[9];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getImageModelx3d() {return MimeTypes[10];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getAudioWav() {return MimeTypes[11];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getWinXAudioMp3() {return MimeTypes[12];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getWinXZip() {return MimeTypes[13];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getGml() {return MimeTypes[14];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getESRIShapefiles() {return MimeTypes[15];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getWkt() {return MimeTypes[16];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getWkb() {return MimeTypes[17];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getFormUrlEncoded() {return MimeTypes[18];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getGoogleEarth() {return MimeTypes[19];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getGoogleEarthZ() {return MimeTypes[20];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getXOP() {return MimeTypes[21];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String getSOAP() {return MimeTypes[22];}

		/**
		     * To be documented
		     *
		     * @return final
		     *
		     */
	    public static final String getMultiPart() {return MimeTypes[23];}

		/**
		  * Validate against all mimetype possible
		  *
		  * @param mimetype
		  * @return boolean
		  *
		  */
		public static boolean isValid(String mimetype) {
			boolean bOk = false;
		
			if ( mimetype != null ) {
		    	for (int i=0; i<MimeTypes.length; i++) {
		    		if ( mimetype.equalsIgnoreCase(MimeTypes[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}
		
		/**
		  * Validate only with known xml mimetype
		  *
		  * @param mimetype
		  * @return boolean
		  *
		  */
		public static boolean isXmlValid(String mimetype) {
			boolean bOk = false;
			final String[] xmlMimeType = {
					getHtml(),
					getXml(),
					getImageSvgXml(),
					getGml(),
					getGoogleEarth(),
					getGoogleEarthZ(),
					getXOP(),
					getSOAP()	};
		
			if ( xmlMimeType != null ) {
		    	for (int i=0; i<xmlMimeType.length; i++) {
		    		if ( mimetype.equalsIgnoreCase(xmlMimeType[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}		
		
	}

    public static class Schemas {

		/*
		XML Schema: Datatypes is part 2 of the specification of the XML Schema language.
		It defines facilities for defining datatypes to be used in XML Schemas as well as
		other XML specifications. The datatype language, which is itself represented in XML
		1.0, provides a superset of the capabilities found in XML 1.0 document type
		definitions (DTDs) for specifying datatypes on elements and attributes.
		*/
		private final static String W3C_XMLSchema = "http://www.w3.org/2001/XMLSchema";
		public static final String W3CXMLSchema = W3C_XMLSchema;
		/*
		[Definition:]  Built-in datatypes are those which are defined in this specification,
		               and can be either �primitive� or �derived�;
		[Definition:]  User-derived datatypes are those �derived� datatypes that are defined
		               by individual schema designers.
		Conceptually there is no difference between the �built-in� �derived� datatypes included
		in this specification and the �user-derived� datatypes which will be created by individual
		schema designers. The �built-in� �derived� datatypes are those which are believed to
		be so common that if they were not defined in this specification many schema designers
		would end up "reinventing" them. Furthermore, including these �derived� datatypes in
		this specification serves to demonstrate the mechanics and utility of the datatype
		generation facilities of this specification.
		*/
		private final static String[] UserTypes = {
			"anyType",
			"anySimpleType"
		};
		/*
		 The �primitive� datatypes defined by this specification are described below.
		 For each datatype, the �value space� and �lexical space� are defined, �constraining
		 facet�s which apply to the datatype are listed and any datatypes �derived� from
		 this datatype are specified.
		 */
		private final static String[] PrimitiveTypes = {
			"string",
			"boolean",
			"decimal",
			"float",
			"double",
			"duration",
			"dateTime",
			"time",
			"date",
			"gYearMonth",
			"gYear",
			"gMonthDay",
			"gDay",
			"gMonth",
			"hexBinary",
			"base64Binary",
			"anyURI",
			"QName",
			"NOTATION"
		};
		/*
		This section gives conceptual definitions for all �built-in� �derived� datatypes
		defined by this specification. The XML representation used to define �derived�
		datatypes (whether �built-in� or �user-derived�) is given in section XML Representation
		of Simple Type Definition Schema Components (�4.1.2) and the complete definitions of
		the �built-in�  �derived� datatypes are provided in Appendix A Schema for Datatype
		Definitions (normative) (�A).
		*/
		private final static String[] DerivedTypes = {
			"normalizedString",
			"token" ,
			"language" ,
			"NMTOKEN" ,
			"NMTOKENS" ,
			"Name" ,
			"NCName" ,
			"ID" ,
			"IDREF" ,
			"IDREFS" ,
			"ENTITY" ,
			"ENTITIES" ,
			"integer" ,
			"nonPositiveInteger",
			"negativeInteger" ,
			"long" ,
			"int" ,
			"short" ,
			"byte" ,
			"nonNegativeInteger",
			"unsignedLong" ,
			"unsignedInt" ,
			"unsignedShort" ,
			"unsignedByte" ,
			"positiveInteger"
		};
		/*
		Facets are of two types: fundamental facets that define the datatype and
		non-fundamental or constraining facets that constrain the permitted values of
		a datatype.
		
		 Fundamental facets: A fundamental facet is an abstract property which serves to
		                     semantically characterize the values in a �value space�.
		
		 Constraining or Non-fundamental facets: A constraining facet is an optional
		                     property that can be applied to a datatype to constrain
		                     its �value space�.
		*/
		private final static String[] ConstrainingFacets = {
			"pattern",
			"whiteSpace",
			"length",
			"minLength",
			"maxLength",
			"enumeration",
			"totalDigits",
			"fractionDigits",
			"maxInclusive",
			"maxExclusive",
			"minInclusive",
			"minExclusive"
		};

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String UserTypeAnyType() {return UserTypes[0];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String UserTypeAnySimpleType() {return UserTypes[1];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeString() {return PrimitiveTypes[0];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeBoolean() {return PrimitiveTypes[1];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeDecimal() {return PrimitiveTypes[2];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeFloat() {return PrimitiveTypes[3];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeDouble() {return PrimitiveTypes[4];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeDuration() {return PrimitiveTypes[5];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeDateTime() {return PrimitiveTypes[6];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeTime() {return PrimitiveTypes[7];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeDate() {return PrimitiveTypes[8];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeGYearMonth() {return PrimitiveTypes[9];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeGYear() {return PrimitiveTypes[10];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeGMonthDay() {return PrimitiveTypes[11];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeGDay() {return PrimitiveTypes[12];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeGMonth() {return PrimitiveTypes[13];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeHexBinary() {return PrimitiveTypes[14];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeBase64Binary() {return PrimitiveTypes[15];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeAnyURI() {return PrimitiveTypes[16];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeQName() {return PrimitiveTypes[17];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String PrimitiveTypeNOTATION() {return PrimitiveTypes[18];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNormalizedString() {return DerivedTypes[0];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeToken() {return DerivedTypes[1];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeLanguage() {return DerivedTypes[2];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNMTOKEN() {return DerivedTypes[3];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNMTOKENS() {return DerivedTypes[4];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeName() {return DerivedTypes[5];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNCName() {return DerivedTypes[6];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeID() {return DerivedTypes[7];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeIDREF() {return DerivedTypes[8];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeIDREFS() {return DerivedTypes[9];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeENTITY() {return DerivedTypes[10];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeENTITIES() {return DerivedTypes[11];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeInteger() {return DerivedTypes[12];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNonPositiveInteger() {return DerivedTypes[13];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNegativeInteger() {return DerivedTypes[14];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeLong() {return DerivedTypes[15];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeInt() {return DerivedTypes[16];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeShort() {return DerivedTypes[17];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeByte() {return DerivedTypes[18];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypeNonNegativeInteger() {return DerivedTypes[19];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String DerivedTypeUnsignedLong() {return DerivedTypes[20];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String DerivedTypeUnsignedInt() {return DerivedTypes[21];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String DerivedTypeUnsignedShort() {return DerivedTypes[22];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String DerivedTypeUnsignedByte() {return DerivedTypes[23];}

		/**
		  * To be documented
		  *
		  * @return final
		  *
		  */
		public static final String DerivedTypePositiveInteger() {return DerivedTypes[24];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsPattern() {return ConstrainingFacets[0];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsWhiteSpace() {return ConstrainingFacets[1];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsLength() {return ConstrainingFacets[2];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMinLength() {return ConstrainingFacets[3];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMaxLength() {return ConstrainingFacets[4];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsEnumeration() {return ConstrainingFacets[5];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsTotalDigits() {return ConstrainingFacets[6];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsFractionDigits() {return ConstrainingFacets[7];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMaxInclusive() {return ConstrainingFacets[8];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMaxExclusive() {return ConstrainingFacets[9];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMinInclusive() {return ConstrainingFacets[10];}

		/**
		  * To be documented
		  *
		  *
		  *
		  */
		public static final String ConstrainingFacetsMinExclusiv() {return ConstrainingFacets[11];}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchemaTypeValid(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
		    	// Test Pure primitive
				bOk = isXMLSchemaPrimitiveTypeValid(datatype);
				if ( ! bOk ) {
					bOk = isXMLSchemaDerivedTypeValid(datatype);
				}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchemaPrimitiveTypeValid(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
		    	// Test Pure primitive
		    	for (int i=0; i<PrimitiveTypes.length; i++) {
		    		if ( datatype.startsWith(PrimitiveTypes[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchemaDerivedTypeValid(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
		    	// Test Derived primitive
		    	for (int i=0; i<DerivedTypes.length; i++) {
		    		if ( datatype.startsWith(DerivedTypes[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param facet
		  *
		  *
		  */
		public static boolean isXMLSchemaConstrainingFacetsValid(String facet) {
			boolean bOk = false;
		
			if ( facet != null ) {
		    	// Test Derived primitive
		    	for (int i=0; i<ConstrainingFacets.length; i++) {
		    		if ( facet.startsWith(ConstrainingFacets[i]) ) {
		    			bOk = true;
		    			break;
		    		}
		    	}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchema(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
				bOk =  datatype.equals(W3C_XMLSchema.toUpperCase());
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchemaPrefixed(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
				if ( datatype.startsWith(W3C_XMLSchema.toUpperCase()) ) {
		    		bOk = true;
		    	}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return boolean
		  *
		  */
		public static boolean isXMLSchemaValid(String datatype) {
			boolean bOk = false;
		
			if ( datatype != null ) {
				if ( isXMLSchemaPrefixed(datatype) ) {
					// After the XMLSchema, a bound '#' should be found
					String dataXML = datatype.substring(W3C_XMLSchema.length());
		    		if ( dataXML.startsWith("#") ) {
		
						// If there is a '.', we have to test the type.facet
						int posDot = dataXML.indexOf(".");
						if ( posDot != -1 ) {
							// test Type and then facet
							String dataType = dataXML.substring(0,posDot-1);
							String datafacet = dataXML.substring(posDot+1);
							if ( isXMLSchemaTypeValid(dataType) &&
								 isXMLSchemaConstrainingFacetsValid(datafacet) ) {
								bOk = true;
							}
						}
						else {
		        			// Test Type (Primitive or Derived) or starting with facet
		        			dataXML = dataXML.substring(1);
		        			bOk = isXMLSchemaTypeValid(dataXML);
		        			if ( ! bOk ) {
		        				// May be a facet?
		        				bOk = isXMLSchemaConstrainingFacetsValid(dataXML);
		        			}
						}
		    		}
				}
				else if ( isXMLSchemaPrimitiveTypeValid(datatype) ) {
					bOk = true;
				}
				else if ( isXMLSchemaDerivedTypeValid(datatype) ) {
					bOk = true;
				}
			}
		
			return bOk;
		}

		/**
		  * To be documented
		  *
		  * @return String
		  *
		  */
		public static String buildXMLSchema() {
			return buildXMLSchema(null,null);
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @return String
		  *
		  */
		public static String buildXMLSchema(String datatype) {
			return buildXMLSchema(datatype,null);
		}

		/**
		  * To be documented
		  *
		  * @param datatype
		  * @param facet
		  * @return String
		  *
		  */
		public static String buildXMLSchema(String datatype, String facet) {
			String dataType = W3C_XMLSchema;
		
			if ( datatype != null || facet != null ) {
		    	dataType = dataType.concat("#");
		    	if ( datatype != null && facet != null ) {
		    		dataType = dataType.concat(datatype);
					dataType = dataType.concat(".");
					dataType = dataType.concat(facet);
		    	}
		    	else if ( datatype != null ) {
		    		dataType = dataType.concat(datatype);
		    	}
		    	else if ( facet != null ) {
		    		dataType = dataType.concat(facet);
		    	}
			}
		
			return( dataType );
		}
		
	}

	final static String CHARSET = "CHARSET=";
    
    public static void main(String[] args) {
    	String test = "content-type=text/xml; charset=utf-8             ";
    	
    	String defaultCharset = Constants.Charsets.getUTF_8();
    	if ( ! JeMafUtils.isEmptyString(test) ) {
    		test = test.toUpperCase();
    		int posStart = test.indexOf(CHARSET);
    		if ( posStart != -1 ) {
    			int posEnd = test.indexOf(";", posStart+CHARSET.length());
    			String charset = "";
    			if ( posEnd != -1 )
    				charset = test.substring(posStart+CHARSET.length(), posEnd);
    			else 
    				charset = test.substring(posStart+CHARSET.length());
    			defaultCharset = charset.trim().toUpperCase();
    		}
    	}
    	System.out.println("Charset='" + defaultCharset + "'");
    }

}
