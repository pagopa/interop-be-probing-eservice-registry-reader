/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config.jacksonMapper
* File Name   : BeanDeserializerModifierWithValidation.java
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

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;

/**
 * The Class BeanDeserializerModifierWithValidation.
 */
public class BeanDeserializerModifierWithValidation extends BeanDeserializerModifier {

	/**
	 * Modify deserializer.
	 *
	 * @param config       the config
	 * @param beanDesc     the bean desc
	 * @param deserializer the deserializer
	 * @return the json deserializer
	 */
	@Override
	public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc,
			JsonDeserializer<?> deserializer) {
		if (deserializer instanceof BeanDeserializer) {
			return new BeanDeserializerWithValidation((BeanDeserializer) deserializer);
		}

		return deserializer;
	}

}
