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

package ca.gc.agr.jemaf.gson;

import java.io.Reader;
import java.lang.reflect.Modifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;


public class JeMafGsonUtils {

	/**
	 * Builds the Object response from JSON.
	 * @param response the Class Object.
	 * @return a Object
	 */
	@SuppressWarnings("unchecked")
	public static Object deserializeJsonToObject(String json, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().fromJson(json, klazz);
	}

	/**
	 * Builds the Object response from JSON.
	 * @param response the Class Object.
	 * @return a Object
	 */
	@SuppressWarnings("unchecked")
	public static Object deserializeJsonToObject(byte[] json, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().fromJson(new String(json), klazz);
	}
	
	/**
	 * Builds the Object response from JSON.
	 * @param response the Class Object.
	 * @return a Object
	 */
	@SuppressWarnings("unchecked")
	public static Object deserializeJsonToObject(JsonElement json, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().fromJson(json, klazz);
	}	
	
	/**
	 * Builds the Object response from JSON.
	 * @param response the Class Object.
	 * @return a Object
	 */
	public static Object deserializeJsonToObject(JsonReader json, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().fromJson(json, klazz);
	}	

	/**
	 * Builds the Object response from JSON.
	 * @param response the Class Object.
	 * @return a Object
	 */
	@SuppressWarnings("unchecked")
	public static Object deserializeJsonToObject(Reader json, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().fromJson(json, klazz);
	}	
	
	/**
	 * Builds the json response object from the Type class.
	 * @param response the response.
	 * @return a json string represented as a byte[].
	 */
	public static byte[] serializeObjectToJsonByte(Object instance, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().toJson(instance, klazz).getBytes();
	}
	
	/**
	 * Builds the json response object from the Type class.
	 * @param response the response.
	 * @return a json string represented as a byte[].
	 */
	public static String serializeObjectToJsonString(Object instance, @SuppressWarnings("rawtypes") Class klazz) {
		return createGson().toJson(instance, klazz);
	}
	
	/**
	 * Builds the json response object from the Type class.
	 * @param response the response.
	 * @return a json string represented as a byte[].
	 */
	public static byte[] serializeObjectToPrettyJsonByte(Object instance, @SuppressWarnings("rawtypes") Class klazz) {
		return createPrettyGson().toJson(instance, klazz).getBytes();
	}
	
	/**
	 * Builds the json response object from the Type class.
	 * @param response the response.
	 * @return a json string represented as a byte[].
	 */
	public static String serializeObjectToPrettyJsonString(Object instance, @SuppressWarnings("rawtypes") Class klazz) {
		return createPrettyGson().toJson(instance, klazz);
	}


	/**
	 * Factories to create GSON builder and GSON
	 */
	public static GsonBuilder createPrettyGsonBuilder() {
		GsonBuilder builder = createGsonBuilder();
		builder.setPrettyPrinting();

		return builder;
	}
	
	/**
	 * 
	 * @return
	 */
	public static GsonBuilder createGsonBuilder() {
		GsonBuilder builder = new GsonBuilder();
		builder.disableHtmlEscaping();
		builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE);
		return builder;
	}

	/**
	 * 
	 * @return
	 */
	public static Gson createPrettyGson() {
		// Don't serialize any Static, Transient and volatile field name
		return createPrettyGsonBuilder().create();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Gson createGson() {
		// Don't serialize any Static, Transient and volatile field name
		return createGsonBuilder().create();
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonObject pureParser(String jsonInput) throws JsonSyntaxException {
		JsonParser parser = new JsonParser();
	    return parser.parse(jsonInput).getAsJsonObject();
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static boolean isPropertyExist(String jsonInput, String propName) throws JsonSyntaxException {
		return  isPropertyExist( pureParser(jsonInput), propName);
	}

	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static boolean isPropertyExist(JsonObject json, String propName) throws JsonSyntaxException {
		boolean isExist = false;
		
		if ( json != null && json.isJsonObject() ) {
			JsonElement elem = json.get(propName);
			if ( elem != null ) {
				isExist = true;
			}
		}
		
		return isExist;
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonElement getProperty(String jsonInput, String propName) throws JsonSyntaxException {
		JsonElement elem = null;
		
		JsonObject json  = pureParser(jsonInput);
		if ( json != null ) {
			elem = getProperty(json, propName);
		}
		
		return elem;
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonElement getProperty(JsonObject json, String propName) throws JsonSyntaxException {
		JsonElement elem = null;
		
		if ( json != null && json.isJsonObject() ) {
			elem = json.get(propName);
		}
		
		return elem;
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @param klazz
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Object getPropertyValue(String jsonInput, String propName, @SuppressWarnings("rawtypes") Class klazz) throws JsonSyntaxException {
		return getPropertyValue(pureParser(jsonInput), propName, klazz);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @param klazz
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Object getPropertyValue(JsonObject json, String propName, @SuppressWarnings("rawtypes") Class klazz) throws JsonSyntaxException {
		Object value = null;
		
		JsonElement elem = getProperty(json, propName); 
		if ( isPrimitiveValue(elem) ) {
			try {
				if ( klazz == null || klazz == java.lang.String.class) {
					value = elem.getAsString();
				}
				else if ( klazz == java.lang.Integer.class) {
					value = new Integer(elem.getAsInt());
				}
				else if (klazz == java.lang.Double.class) {
					value = new Double(elem.getAsDouble());
				}
				else if (klazz == java.lang.Long.class) {
					value = new Long(elem.getAsLong());
				}
				else if (klazz == java.lang.Number.class) {
					value = elem.getAsNumber();
				}
				else if (klazz == java.lang.Boolean.class) {
					value = new Boolean(elem.getAsBoolean());
				}
				else if (klazz == java.math.BigInteger.class) {
					value = elem.getAsBigInteger();
				}
				else if (klazz == java.math.BigDecimal.class) {
					value = elem.getAsBigDecimal();
				}
				
			}
			catch (Exception ex) {} // Don't care
		}
		else {
			if ( elem != null ) {
				if (klazz == JsonObject.class && elem.isJsonObject() ) {
					value = elem.getAsJsonObject();
				}
				else if (klazz == JsonArray.class && elem.isJsonArray() ) {
					value = elem.getAsJsonArray();
				}
			}
		}
		
		return value;
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonArray getPropertyJsonArrayValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (JsonArray)getPropertyValue(jsonInput, propName, JsonArray.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonArray getPropertyJsonArrayValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (JsonArray)getPropertyValue(json, propName, JsonArray.class);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonObject getPropertyJsonObjectValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (JsonObject)getPropertyValue(jsonInput, propName, JsonObject.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static JsonObject getPropertyJsonObjectValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (JsonObject)getPropertyValue(json, propName, JsonObject.class);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static String getPropertyStringValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (String)getPropertyValue(jsonInput, propName, String.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static String getPropertyStringValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (String)getPropertyValue(json, propName, String.class);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Number getPropertyNumberValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (Number)getPropertyValue(jsonInput, propName, Number.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Number getPropertyNumberValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (Number)getPropertyValue(json, propName, Number.class);
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Double getPropertyDoubleValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (Double)getPropertyValue(jsonInput, propName, Double.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Double getPropertyDoubleValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (Double)getPropertyValue(json, propName, Double.class);
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Integer getPropertyIntegerValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (Integer)getPropertyValue(jsonInput, propName, Integer.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Integer getPropertyIntegerValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (Integer)getPropertyValue(json, propName, Integer.class);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Boolean getPropertyBooleanValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (Boolean)getPropertyValue(jsonInput, propName, Boolean.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Boolean getPropertyBooleanValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (Boolean)getPropertyValue(json, propName, Boolean.class);
	}
	
	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Long getPropertyLongValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (Long)getPropertyValue(jsonInput, propName, Long.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static Long getPropertyLongValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (Long)getPropertyValue(json, propName, Long.class);
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static java.math.BigInteger getPropertyBigIntegerValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (java.math.BigInteger)getPropertyValue(jsonInput, propName, java.math.BigInteger.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static java.math.BigInteger getPropertyBigIntegerValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (java.math.BigInteger)getPropertyValue(json, propName, java.math.BigInteger.class);
	}

	/**
	 * 
	 * @param jsonInput
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static java.math.BigDecimal getPropertyBigDecimalValue(String jsonInput, String propName) throws JsonSyntaxException {
		return (java.math.BigDecimal)getPropertyValue(jsonInput, propName, java.math.BigDecimal.class);
	}
	
	/**
	 * 
	 * @param json
	 * @param propName
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static java.math.BigDecimal getPropertyBigDecimalValue(JsonObject json, String propName) throws JsonSyntaxException {
		return (java.math.BigDecimal)getPropertyValue(json, propName, java.math.BigDecimal.class);
	}
	
	/**
	 * 
	 * @param elem
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static boolean isPrimitiveValue(JsonElement elem) throws JsonSyntaxException {
		boolean isPrimitive = false;

		if ( elem != null ) {
			isPrimitive = elem.isJsonPrimitive();
		}
		
		return isPrimitive;
	}

	/**
	 * 
	 * @param elem
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static boolean isJsonArrayValue(JsonElement elem) throws JsonSyntaxException {
		boolean isJsonArray = false;

		if ( elem != null ) {
			isJsonArray = elem.isJsonArray();
		}
		
		return isJsonArray;
	}
	
	/**
	 * 
	 * @param elem
	 * @return
	 * @throws JsonSyntaxException
	 */
	public static boolean isJsonObjectValue(JsonElement elem) throws JsonSyntaxException {
		boolean isJsonObject = false;

		if ( elem != null ) {
			isJsonObject = elem.isJsonObject();
		}
		
		return isJsonObject;
	}
	
	
}
