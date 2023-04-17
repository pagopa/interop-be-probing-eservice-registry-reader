package it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper;

import java.util.Objects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.BeanDeserializerModifierWithValidation;

public class JacksonMapperConfig {

	private static JacksonMapperConfig instance;

	public static JacksonMapperConfig getInstance() {
		if (Objects.isNull(instance)) {
			instance = new JacksonMapperConfig();
		}
		return instance;
	}

	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule validationModule = new SimpleModule();
		validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
		mapper.registerModule(validationModule);
		return mapper;
	}

}
