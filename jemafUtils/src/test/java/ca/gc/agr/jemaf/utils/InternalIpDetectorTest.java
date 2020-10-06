package ca.gc.agr.jemaf.utils;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.gc.agr.jemaf.utils.http.JeMafHttpUtils;

public class InternalIpDetectorTest {

    private static List<URL> urlsInternal;
    private static List<URL> urlsExternal;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        urlsInternal = new ArrayList<URL>();
        urlsExternal = new ArrayList<URL>();

        urlsExternal.add(new URL("http://www.yahoo.ca/"));
        urlsExternal.add(new URL("http://www.microsoft.com/"));
        urlsExternal.add(new URL("https://www.google.com/"));
        urlsExternal.add(new URL("http://www.esri.com/"));
        urlsExternal.add(new URL("http://maps.arcgis.com/"));
        urlsExternal.add(new URL("https://aafc.maps.arcgis.com/"));
        urlsExternal.add(new URL("http://9.255.255.255"));
        urlsExternal.add(new URL("http://11.0.0.0"));
        urlsExternal.add(new URL("http://172.15.255.255"));
        urlsExternal.add(new URL("http://172.32.0.0"));
        urlsExternal.add(new URL("http://192.167.255.255"));
        urlsExternal.add(new URL("http://192.169.0.0"));

        /*
        urlsInternal.add(new URL("http://devproxyint.agr.gc.ca/"));
        urlsInternal.add(new URL("http://devproxyint"));
        urlsInternal.add(new URL("http://tstproxyint"));
        urlsInternal.add(new URL("http://prdproxyint"));
        urlsInternal.add(new URL("http://devapp"));
        urlsInternal.add(new URL("http://tstapp"));
        urlsInternal.add(new URL("http://prdapp"));
        urlsInternal.add(new URL("http://devweb"));
        urlsInternal.add(new URL("http://tstweb"));
        urlsInternal.add(new URL("http://prdweb"));
        urlsInternal.add(new URL("http://devags1"));
        urlsInternal.add(new URL("http://tstags1"));
        urlsInternal.add(new URL("http://prdags1"));
        urlsInternal.add(new URL("http://webmail"));
        urlsInternal.add(new URL("https://webmail"));
        urlsInternal.add(new URL("http://air"));
        urlsInternal.add(new URL("http://air.agr.gc.ca/"));
        urlsInternal.add(new URL("http://air-tst-ad.agr.gc.ca/"));
        urlsInternal.add(new URL("http://air-dev-ad.agr.gc.ca/"));
        urlsInternal.add(new URL("http://10.0.0.0"));
        urlsInternal.add(new URL("http://10.1.1.1"));
        urlsInternal.add(new URL("http://10.255.255.255"));
        urlsInternal.add(new URL("http://172.16.0.0"));
        urlsInternal.add(new URL("http://172.16.1.1"));
        urlsInternal.add(new URL("http://172.31.255.255"));
        urlsInternal.add(new URL("http://192.168.0.0"));
        urlsInternal.add(new URL("http://192.168.1.1"));
        urlsInternal.add(new URL("http://192.168.255.255"));
        urlsInternal.add(new URL("http://127.0.0.1"));
        urlsInternal.add(new URL("http://localhost"));
         */

    }

    @Test
    public void testInternal() throws UnknownHostException {
        for (URL url : urlsInternal) {
            if (JeMafHttpUtils.isUrlLocal(url) != true)
                fail("An internal URL returned that it was external: " + url);
        }
    }

    @Test
    public void testExternal() throws UnknownHostException {
        for (URL url : urlsExternal) {
            if (JeMafHttpUtils.isUrlLocal(url) == true)
                fail("An external URL returned that it was internal: " + url);
        }
    }

    @Test
    public void testUnknownHost() {
        try {
            JeMafHttpUtils.isInetAddressLocal(InetAddress
                    .getByName("ThisShouldntBeReal"));
        } catch (UnknownHostException ex) {
            try {
                JeMafHttpUtils.isUrlLocal(new URL("http://ThisShouldntBeReal"));
            } catch (UnknownHostException ex2) {
                return;
            } catch (MalformedURLException e) {
                fail("This test was poorly written.  Fix it");
            }
        }
        fail("An unknown host did not cause the method to throw an expected UnknownHostException");
    }

    @Test
    public void testEmptyString() {
        try {
            if (JeMafHttpUtils.isInetAddressLocal(InetAddress.getByName("")) != true)
                fail("Empty string resolves to localhost - this should have been internal");
        } catch (UnknownHostException e) {
            fail("Expected behavior was empty string resolving to localhost");
        }
    }

    @Test
    public void testNull() {
        try {
            JeMafHttpUtils.isUrlLocal(null);
            fail("This should have triggered a NPE");
        } catch (UnknownHostException e) {
            fail("This should have triggered a NPE");
        } catch (NullPointerException ex) {
            // This is good
        }
        try {
            JeMafHttpUtils.isInetAddressLocal(null);
        } catch (NullPointerException ex) {
            // This is good.
            return;
        }
        fail("A NPE should have been triggered!");
    }

}
