/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config
* File Name   : PropertiesLoader.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The Class PropertiesLoader.
 */
public class PropertiesLoader {

    private PropertiesLoader() {
		super();
	}

	/**
     * Load properties.
     *
     * @param resourceFileName the resource file name
     * @return the properties
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }

}