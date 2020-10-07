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
package ca.gc.agr.jemaf.utils.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.MultipartStream;
import org.apache.log4j.Logger;

import ca.gc.agr.jemaf.utils.JeMafUtils;
import ca.gc.agr.jemaf.utils.io.JeMafIoUtils;
import ca.gc.agr.jemaf.utils.net.JeMafNetUtils;
import ca.gc.agr.jemaf.utils.net.ReadPageSizeExceedException;
import ca.gc.agr.jemaf.utils.net.TimeoutException;

/**
 * <p>
 * The Class has basics to convert datetime, get temporary folder and many other
 * This class access cannot be instantiated from other package
 * </p>
 * 
 * @author Roberto Caron
 * @version $Revision: 1.0 $
 */
public class JeMafHttpUtils {

    /** Logger */
    final static Logger LOGGER = Logger.getLogger(JeMafHttpUtils.class);

    /**
     * static variables
     * ============================================================
     */
    public static final String XML_UTF_8 = "UTF-8";
    public static final String HTML_MIMETYPE = "text/html; charset=UTF-8";
    public static final String XML_MIMETYPE = "text/xml; charset=UTF-8";
    public static final String TEXT_MIMETYPE = "text/plain; charset=UTF-8";
    public static final String JSON_MIMETYPE = "application/json";
    public static final String ZIP_MIMETYPE = "application/zip";
    public static final String CONNECT_TIMEOUT = "com.sun.xml.ws.connect.timeout";
    public static final String REQUEST_WS_TIMEOUT = "com.sun.xml.ws.request.timeout";
    public static final String REQUEST_INTERNAL_WS_TIMEOUT = "com.sun.xml.internal.ws.request.timeout";
    public static final String HTTP_CLIENT_STREAMING_CHUNK_SIZE = "com.sun.xml.ws.transport.http.client.streaming.chunk.size";
    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_GET = "GET";
    public static final String CONNECTION_CONTENT_TYPE = "Content-Type";
    public static final String NO_PROXY_PARAM_NAME = "NO_PROXY";
    public static final String CONNECTION_PARAM_NAME = "CONNECTION";

    // See RFC 1918 - http://tools.ietf.org/html/rfc1918
    public static final int SUBNET_10_0_0_0 = 0x0A000000; // Hex representation
                                                          // of 10.0.0.0
    public static final int SUBNETMASK_10_0_0_0 = 8; // Subnet mask 255.0.0.0
    public static final int SUBNET_172_16_0_0 = 0xAC100000; // Hex
                                                            // representation of
                                                            // 172.16.0.0
    public static final int SUBNETMASK_172_16_0_0 = 12; // Subnet mask
                                                        // 255.240.0.0
    public static final int SUBNET_192_168_0_0 = 0xC0A80000; // Hex
                                                             // representation
                                                             // of 192.168.0.0
    public static final int SUBNETMASK_192_168_0_0 = 16; // Subnet mask
                                                         // 255.255.0.0
    public static final int SUBNET_127_0_0_1 = 0x7F000001; // Hex representation
                                                           // of 127.0.0.1
    public static final int SUBNETMASK_127_0_0_1 = 32; // Subnet mask
                                                       // 255.255.255.255

    /**
     * class variables
     * =============================================================
     */

    /**
     * instance variables
     * ==========================================================
     */

    /**
     * constructors
     * ================================================================
     */

    /**
     * Constructs the based configuration class
     */
    private JeMafHttpUtils() {
    }

    /**
     * properties
     * ==================================================================
     */

    /**
     * methods
     * =====================================================================
     */

    /**
     * Get the HTTP Request Post data
     * 
     * @param request
     * @param payload
     * @return
     * @throws IOException
     * @throws ReadPageSizeExceedException
     * @throws TimeoutException
     */
    public static String getPostData(HttpServletRequest request, int payload) throws IOException,
            ReadPageSizeExceedException, TimeoutException {
        return new String(JeMafNetUtils.readPageToByteArrayStream(request.getInputStream(),
                payload * JeMafIoUtils.BYTES_PER_KB).toByteArray());
    }

    /**
     * Get the HTTP Request Method
     * 
     * @param request
     * @return
     */
    public static String getMethod(HttpServletRequest request) {
        return request.getMethod();
    }

    /**
     * Get the HTTP Request Content Type which could be text/html; charset=ut8
     * 
     * @param request
     * @return
     */
    public static String getContentType(HttpServletRequest request) {
        return request.getContentType();
    }


    public static Map<String, ArrayList<String>> fixParameters(String query){
        query = query.replace('?','&');
        Map<String,ArrayList<String>> params = new HashMap<>();
        String[] split = query.split("&");
        for( String entry : split ){
            String key, value;
            int index = entry.indexOf('=');
            if( index == -1 ){
                key = entry;
                value = null;
                params.put(key,null);
            }
            else {
                key = entry.substring(0,index);
                value = entry.substring(index+1);
                if( params.containsKey(key)){
                    ArrayList arr = params.get(key);
                    arr.add(JeMafUtils.URLDecoder(value));
                }
                else {
                    ArrayList arr = new ArrayList<String>();
                    arr.add(JeMafUtils.URLDecoder(value));
                    params.put(key, arr);
                }
            }
        }
        return params;
    }
    /**
     * Read all parameters even for a multi/form and form encoded url
     * 
     * @param request
     * @param postData
     * @return
     * @throws IOException
     */
    public static Map<String, ArrayList<String>> getParameters(HttpServletRequest request, String postData)
            throws IOException {

        Map<String, ArrayList<String>> params = new HashMap<String, ArrayList<String>>();

        @SuppressWarnings("unchecked")
        Enumeration<String> parameterNames = request.getParameterNames();

        String contentType = getContentType(request);

        if (contentType != null && contentType.toLowerCase().indexOf("multipart/form-data") != -1 && postData != null
                && postData.length() > 0) {
            int boundaryIndex = contentType.indexOf("boundary=");
            byte[] boundary = (contentType.substring(boundaryIndex + 9)).getBytes();

            ByteArrayInputStream input = new ByteArrayInputStream(postData.getBytes());
            @SuppressWarnings("deprecation")
            MultipartStream multipartStream = new MultipartStream(input, boundary);

            boolean nextPart = multipartStream.skipPreamble();
            while (nextPart) {
                String headers = multipartStream.readHeaders();
                String keyList[] = headers.split("\"");
                String key = keyList[1];
                ByteArrayOutputStream data = new ByteArrayOutputStream();
                multipartStream.readBodyData(data);
                String value = new String(data.toByteArray());

                ArrayList<String> arr = null;
                if (params.containsKey(key))
                    arr = params.get(key);
                else
                    arr = new ArrayList<String>();
                arr.add(value);
                params.put(key, arr);

                nextPart = multipartStream.readBoundary();
            }
        } else if (contentType != null && contentType.toLowerCase().indexOf("application/x-www-form-urlencoded") != -1
                && postData != null && postData.length() > 0) {

            String[] paramsPostSplitted = postData.split("&");
            for (int i = 0; i < paramsPostSplitted.length; i++) {
                String[] values = paramsPostSplitted[i].split("=");
                String key = values[0];
                String value = null;
                if (values.length >= 2)
                    value = JeMafUtils.URLDecoder(values[1]);

                ArrayList<String> arr = null;
                if (params.containsKey(key))
                    arr = params.get(key);
                else
                    arr = new ArrayList<String>();
                arr.add(value);
                params.put(key, arr);
            }

        }

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement().toString().trim();
            String[] values = request.getParameterValues(key);

            if (values != null) {

                ArrayList<String> arr = null;
                if (params.containsKey(key)) {
                    arr = params.get(key);
                    // If the value already present? Bug with Web Logic which
                    // has two parameters listed

                    for (String value : values)
                        for (String presentValue : arr)
                            if (presentValue != null && presentValue.equalsIgnoreCase(value))
                                break;
                } else {
                    arr = new ArrayList<String>();
                    for (String value : values) {
                        arr.add(JeMafUtils.URLDecoder(value));
                        params.put(key, arr);
                    }
                }
            } else {
                ArrayList<String> arr = null;
                if (!params.containsKey(key)) {
                    arr = new ArrayList<String>();
                    params.put(key, null);
                }
            }
        }

        return params;
    }

    /**
     * Request an external Web Service and return the data
     * 
     * @param url
     * @param rawData
     * @param method
     * @param contentType
     * @param maxLen
     * @param timeout
     * @return
     * @throws IOException
     * @throws ReadPageSizeExceedException
     * @throws TimeoutException
     */
    public static URLResponse callSyncWebServices(URL url, String rawData, String method, String contentType,
            int maxLen, int timeout) throws IOException, ReadPageSizeExceedException, TimeoutException {
        URLResponse response = null;
        try {
            response = callSyncWebServices(url, rawData, method, contentType, maxLen, timeout, null);
        } catch (AuthentificationException ex) {
            throw new IOException(ex);
        } catch (IOException ex) {
            throw ex;
        } catch (ReadPageSizeExceedException ex) {
            throw ex;
        } catch (TimeoutException ex) {
            throw ex;
        }

        return response;
    }

    /**
     * Request an external Web Service and return the data with more request
     * parameter to add to the header
     * 
     * @param url
     * @param rawData
     * @param method
     * @param contentType
     * @param maxLen
     * @param timeout
     * @param requestProperty
     * @return
     * @throws IOException
     * @throws ReadPageSizeExceedException
     * @throws TimeoutException
     * @throws AuthentificationException
     */
    public static URLResponse callSyncWebServices(URL url, String rawData, String method, String contentType,
            int maxLen, int timeout, Map<String, String> requestProperty) throws IOException,
            ReadPageSizeExceedException, TimeoutException, AuthentificationException {
        URLResponse urlResponse = new URLResponse();
        HttpURLConnection conn = null;

        try {
            boolean isNoProxy = false;
            if (requestProperty != null && requestProperty.size() != 0) {
                Iterator<Entry<String, String>> it = requestProperty.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = it.next();
                    if (pairs.getKey().equalsIgnoreCase(NO_PROXY_PARAM_NAME)
                            && pairs.getValue().equalsIgnoreCase("TRUE")) {
                        // If the connection is already set, don't touch it
                        // later
                        isNoProxy = true;
                        break;
                    }
                }
            }

            if (isNoProxy)
                // Ovveride the -Dhttp.proxyhost and -Dhttps.proxyhost
                conn = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
            else
                conn = (HttpURLConnection) url.openConnection();
            if (timeout > 0) {
                conn.setConnectTimeout(timeout);
                conn.setReadTimeout(timeout);
            }
            conn.setDoInput(true);
            if (method != null && !method.isEmpty()
                    && (method.trim().equalsIgnoreCase(REQUEST_POST) || method.trim().equalsIgnoreCase(REQUEST_GET)))
                conn.setRequestMethod(method);
            else
                conn.setRequestMethod(REQUEST_GET);
            if (contentType != null && !contentType.isEmpty())
                conn.setRequestProperty(CONNECTION_CONTENT_TYPE, contentType);
            else
                conn.setRequestProperty(CONNECTION_CONTENT_TYPE, XML_MIMETYPE);

            boolean isConnectionToClose = true;

            if (requestProperty != null && requestProperty.size() != 0) {
                Iterator<Entry<String, String>> it = requestProperty.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = it.next();
                    if (pairs.getKey().equalsIgnoreCase(NO_PROXY_PARAM_NAME))
                        // Skip proxy configuration
                        continue;
                    else {
                        conn.setRequestProperty(pairs.getKey(), pairs.getValue());
                        if (pairs.getKey().equalsIgnoreCase(CONNECTION_PARAM_NAME))
                            // If the connection is already set, don't touch it
                            // later
                            isConnectionToClose = false;
                    }
                }
            }

            if (isConnectionToClose)
                // disable the keep alive (before connecting)
                conn.setRequestProperty(CONNECTION_PARAM_NAME, "close");

            if (rawData != null && !rawData.trim().isEmpty()) {
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(rawData.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException("Failed : HTTP error code : " + conn.getResponseCode() + "="
                        + conn.getResponseMessage());

            urlResponse.setRawResponseData(JeMafNetUtils.readPageToByteArrayStream(conn.getInputStream(), maxLen)
                    .toByteArray());
            String responseContentType = conn.getHeaderField(CONNECTION_CONTENT_TYPE);
            if (responseContentType != null && !responseContentType.isEmpty())
                urlResponse.setContentType(responseContentType);
            else
                urlResponse.setContentType(XML_MIMETYPE);

        } catch (IOException io) {
            LOGGER.error("An IOException error occurred in communicating with " + url.toString(), io);
            if ((io.getMessage() != null && io.getMessage().indexOf("Unauthorized)") != -1)
                    || (conn != null && conn.getResponseCode() == 401))
                throw new AuthentificationException();
            throw io;
        } catch (ReadPageSizeExceedException re) {
            LOGGER.error("An ReadPageSizeExceedException error occurred in communicating with " + url.toString(), re);
            throw re;
        } catch (TimeoutException te) {
            LOGGER.error("An TimeoutException error occurred in communicating with " + url.toString(), te);
            throw te;
        } finally {
            try {
                if (conn != null)
                    conn.disconnect();
            } catch (Throwable t) {
            }
            conn = null;
        }

        return urlResponse;
    }

    public static String createBasic64Authentificaton(String username, String password) throws IOException,
            MessagingException {
        String encodedValue = "";

        if (username != null || password != null) {
            String valueToEncode = "";

            if (username != null)
                valueToEncode += username;
            valueToEncode += ":";
            if (password != null)
                valueToEncode += password;
            encodedValue = "Basic " + JeMafUtils.encodeToBase64(valueToEncode).replace("\n", "").replace("\r", "");
        }

        return encodedValue;
    }

    /**
     * Convenience function for isInetAddressLocal - lets you check a URL
     * instead
     * 
     * @param url
     * @return true if the URL points to an internal host, false otherwise.
     * @throws UnknownHostException
     */
    public static boolean isUrlLocal(URL url) throws UnknownHostException {
        boolean b = isInetAddressLocal(InetAddress.getByName(url.getHost()));
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("The following URL is " + (b ? "" : "not ") + "local:" + url);
        return b;
    }

    /**
     * This method takes an inetaddress as input and gets the IP from that
     * inetaddress. It then checks to see if the IP is in one of the declared
     * 'private' subnets by RFC1918 - see http://tools.ietf.org/html/rfc1918 .
     * If it is within one of the 'private' subnets (or localhost) it will
     * return true. It will return false otherwise.
     * 
     * @param address
     *            - the address to check whether it is internal or external
     * @return true if the address is internal, false otherwise
     */
    public static boolean isInetAddressLocal(InetAddress address) {
        // Create the mask. The mask is stored in the number of bits (ie Class A
        // = 8, Class B = 16)
        // So, 8 should be 11111111000000000000000000000000
        // 16 would be 11111111111111110000000000000000
        int mask = -1 << (32 - SUBNETMASK_10_0_0_0);
        // Get the IP address in binary format
        byte[] b = address.getAddress();
        // Convert the address to an integer representation of the binary format
        // For example, 8.16.32.3 means 00001000 00010000 00100000 00000011
        // Which would be 00001000000100000010000000000011, or 135274499
        int ip = ((b[0] & 0xFF) << 24) | ((b[1] & 0xFF) << 16) | ((b[2] & 0xFF) << 8) | ((b[3] & 0xFF) << 0);
        // If the IP is the same as the subnet after applying our mask, it is
        // within the subnet
        // ie mask = 11100000 IP is 10110100 and subnet is 10100000 then
        // 11100000
        // & 10110100
        // = 10100000 <- same as subnet mask, 10100000
        // therefore, it is within the subnet.
        if ((SUBNET_10_0_0_0 & mask) == (ip & mask))
            return true;
        // Continue comparing for each private network's subnet/mask
        mask = -1 << (32 - SUBNETMASK_172_16_0_0);
        if ((SUBNET_172_16_0_0 & mask) == (ip & mask))
            return true;
        mask = -1 << (32 - SUBNETMASK_192_168_0_0);
        if ((SUBNET_192_168_0_0 & mask) == (ip & mask))
            return true;
        mask = -1 << (32 - SUBNETMASK_127_0_0_1);
        if ((SUBNET_127_0_0_1 & mask) == (ip & mask))
            return true;
        // If we make it to the end then it was not within the subnet. return
        // false
        return false;
    }

    /**
     * inner class
     * =================================================================
     */

}
