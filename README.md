# WMS Proxy WebService

This application is a proxy for WMS services, specifically setup to access Web Map Services that have not setup with CORS headers.

While a generic proxy can be used the advantage of this project is its deep knowledge of the WMS protocol, and ability to report any errors using the appropriate `ServiceExceptionReport` document expected by WMS clients.

* GETCAPABILITIES
* GETSTYLES
* GETMAP
* GETFEATUREINFO
* GETLEGENDGRAPHIC

The web application double checks content is of the expected type (such as a `GetMap` request expecting an `image/png`).

# Environment 

Build using maven:
   
* Tested with AdoptOpenJDK 1.8.0_251
* Tested with Apache Maven 3.6.3

# JeMafUtils

The project makes use of `jemafutils`.
   
1. Build locally using the provided script.
   
   ```
   cd jemafUtils
   ./makejar.sh
   ```
   
2. Or build using maven:

   ```
   cd jemafUtils
   mvn clean install
   ```

# WmsProxyWebService

1. Build using the provided scripts.

   * `makejar.sh` uses `log4j-build.properties` configuration to record only `WARN` messages: 
   
      ```
      cd WmsProxyWebService
      makejar.sh
      ```
     
     Record logs to `/aafc-aac/agsshare/emaf/logs/wmsproxy.log`.
   
   * `devMakejar.sh` uses `log4j.properties` configuration recording `INFO` messages:
   
      ```
      cd WmsProxyWebService
      devMakejar.sh
      ```
     
     Record logs to `wmsproxy.log`
   
2. Or build using maven:

   ```
   cd WmsProxyWebService
   mvn clean package
   ```

3. The resulting war:

   ```
   ls target/*.war
   ```
   
4. Run locally:
    
   ```
   mvn jetty:run-war
   ```
   
## Filters

The `web.xml` contains several apache specific filters:

* url rewrite filter: `http` to `remoteService=http`
* CORS filter

It is a good practice to manage CORS filter in Tomcat `$CATALINA_BASE/conf/web.xml`so  policy is strictly under control of the system administrator.

## Configuration

The `emaf_config` property is used to supply a configuration file:

```
-Demaf_config=configFiles/emaf.properties
```

This is read by `GlobalEnvironmentPropertiesController`, and does not appear to be used.

## Logging

Logging is provided by log4j, see `WEB-INF/classes/log4j.properties`. To debug proxy activity a level of `TRACE` is recommended:

```
log4j.logger.ca.gc.agr.jemaf.ws.wmsproxy=TRACE
```

By default this is hardcoded to record a log file in `/aafc-aac/agsshare/emaf/logs/wmsproxy.log`.