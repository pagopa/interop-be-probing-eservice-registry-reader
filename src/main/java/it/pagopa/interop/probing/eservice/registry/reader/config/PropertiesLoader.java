/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 6, 2023
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
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class PropertiesLoader.
 */
/** The Constant log. */
@Slf4j
public class PropertiesLoader {

	/** The instance. */
	private static PropertiesLoader instance;

	/** The props. */
	private Map<String, String> props;

	/**
	 * Load properties.
	 *
	 * @return the properties
	 */
	public PropertiesLoader() {
		this.props = System.getenv();
		log.info("Properties loaded successfully");
	}

	/**
	 * Gets the key.
	 *
	 * @param key the key
	 * @return the key
	 */
	public String getKey(String key) {
		return this.props.get(key);
	}

	/**
	 * Instance.
	 *
	 * @return the properties loader
	 */
	static public PropertiesLoader getInstance(){
		if (Objects.isNull(instance)) {
			synchronized (PropertiesLoader.class) {
				instance = new PropertiesLoader();
			}
		}
		return instance;
	}

}
