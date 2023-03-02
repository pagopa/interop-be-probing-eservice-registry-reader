/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 2, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper
* File Name   : JacksonMapperConfig.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * The Class JacksonMapperConfig.
 */
public class JacksonMapperConfig {
	
	/** The instance. */
	private static JacksonMapperConfig instance;

	/**
	 * Gets the single instance of JacksonMapperConfig.
	 *
	 * @return single instance of JacksonMapperConfig
	 */
	public static JacksonMapperConfig getInstance() {
		if (instance == null) {
			instance = new JacksonMapperConfig();
		}
		return instance;
	}

	
	/**
	 * Gets the object mapper.
	 *
	 * @return the object mapper
	 */
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule validationModule = new SimpleModule();
		validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
		mapper.registerModule(validationModule);
		return mapper;
	}

}
