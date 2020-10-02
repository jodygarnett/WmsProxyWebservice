package ca.gc.agr.jemaf.utils.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class JeMafNetUtils {

	public static String readPageToString(URL url) throws IOException  {
		String result = null;
		
		try {
			result = readPageToString(url, -1, -1);
		} catch (ReadPageSizeExceedException e) {
			// It will never happened
		} catch (TimeoutException e) {
			// It will never happened, it is an infinite loop, which is bad :(
		} catch (IOException e) {
			// ReThrow anything else
			throw e;
		}
		
		return result;
	}
	
	public static byte[] readPageToByteArray(URL url) throws IOException  {
		byte[] result = null;
		
		try {
			result = readPageToByteArray(url, -1, -1);
		} catch (ReadPageSizeExceedException e) {
			// It will never happened
		} catch (TimeoutException e) {
			// It will never happened, it is an infinite loop, which is bad :(
		} catch (IOException e) {
			// ReThrow anything else
			throw e;
		}
		
		return result;
	}
	
	public static ByteArrayOutputStream readPageToByteArrayStream(URL url) throws IOException  {
		ByteArrayOutputStream result = null;
		
		try {
			result =  readPageToByteArrayStream(url, -1, -1);
		} catch (ReadPageSizeExceedException e) {
			// It will never happened
		} catch (TimeoutException e) {
			// It will never happened, it is an infinite loop, which is bad :(
		} catch (IOException e) {
			// ReThrow anything else
			throw e;
		}
		
		return result;
	}
	
	public static String readPageToString(URL url, int maxLen, int timeout) throws IOException, ReadPageSizeExceedException, TimeoutException  {
		return new String( readPageToByteArray(url, maxLen, timeout) );
	}
	
	public static byte[] readPageToByteArray(URL url, int maxLen, int timeout) throws IOException, ReadPageSizeExceedException, TimeoutException  {
		ByteArrayOutputStream baos = readPageToByteArrayStream( url, maxLen, timeout );
		return baos.toByteArray();
	}
	
	public static ByteArrayOutputStream readPageToByteArrayStream(URL url, int maxLen, int timeout) throws IOException, ReadPageSizeExceedException, TimeoutException  {
		ByteArrayOutputStream baos = null;
		
		try {
			URLConnection con = url.openConnection();
			if ( timeout > 0 ) {
				con.setConnectTimeout(timeout);
				con.setReadTimeout(timeout);
			}
			InputStream in = con.getInputStream();
			baos = readPageToByteArrayStream(in, maxLen);
		}
		catch (java.net.SocketTimeoutException te) {
			throw new TimeoutException(timeout);
		}
		catch (IOException ioe) {
			throw ioe;
		}
		catch (ReadPageSizeExceedException e) {
			throw e;
		}
		
		return baos;
	}
	
	public static ByteArrayOutputStream readPageToByteArrayStream(InputStream in, int maxLen) throws IOException, ReadPageSizeExceedException, TimeoutException  {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
		    baos.write(buf, 0, len);
		    if ( maxLen >= 0 ) {
		    	 if ( baos.size() > maxLen ) {
		    		 throw new ReadPageSizeExceedException(maxLen);
		    	 }
		    }
		}

		
		return baos;
	}
}
