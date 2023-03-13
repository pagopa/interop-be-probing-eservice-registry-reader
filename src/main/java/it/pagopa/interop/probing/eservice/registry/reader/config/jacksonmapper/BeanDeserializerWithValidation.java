/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config.jacksonMapper
* File Name   : BeanDeserializerWithValidation.java
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

import java.io.IOException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;

/**
 * The Class BeanDeserializerWithValidation.
 */
public class BeanDeserializerWithValidation extends BeanDeserializer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new bean deserializer with validation.
	 *
	 * @param src the src
	 */
	protected BeanDeserializerWithValidation(BeanDeserializerBase src) {
		super(src);
	}

	/**
	 * Deserialize.
	 *
	 * @param p    the p
	 * @param ctxt the ctxt
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		Object instance = super.deserialize(p, ctxt);
		validate(instance);
		return instance;
	}

	/**
	 * Validate.
	 *
	 * @param <T> the generic type
	 * @param t   the t
	 */
	<T> void validate(T t) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(t);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

}