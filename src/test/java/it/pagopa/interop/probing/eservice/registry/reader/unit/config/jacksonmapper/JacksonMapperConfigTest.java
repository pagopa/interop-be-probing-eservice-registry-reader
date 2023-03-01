package it.pagopa.interop.probing.eservice.registry.reader.unit.config.jacksonmapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;

@ExtendWith(MockitoExtension.class)
 class JacksonMapperConfigTest {
	
	@InjectMocks JacksonMapperConfig jacksonMapperConfig;

	@Test
	@DisplayName("Test JacksonMapper mapper build")
	void testGetObjectMapper_thenReturnValidObjectMapper() {
		ObjectMapper mapper = jacksonMapperConfig.getObjectMapper();
		assertNotNull(mapper);
		Assertions.assertFalse(mapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
	}
	
}
