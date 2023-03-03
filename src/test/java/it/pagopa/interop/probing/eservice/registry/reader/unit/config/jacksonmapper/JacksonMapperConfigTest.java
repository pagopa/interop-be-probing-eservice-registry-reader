package it.pagopa.interop.probing.eservice.registry.reader.unit.config.jacksonmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.BeanDeserializerModifierWithValidation;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;

class JacksonMapperConfigTest {

	private List<EserviceDTO> listEservices;

	@BeforeEach
	void setup() {
		listEservices = new ArrayList<>();
		EserviceDTO eServiceDTO = new EserviceDTO();
		eServiceDTO.setEserviceId(UUID.randomUUID().toString());
		eServiceDTO.setVersionId(UUID.randomUUID().toString());
		eServiceDTO.setName("Service Name");
		eServiceDTO.setProducerName("Producer Name");
		eServiceDTO.setState("ACTIVE");
		eServiceDTO.setTechnology("REST");
		String[] basePath = { "xxx.xxx/xxx", "yyy.yyy/xxx" };
		eServiceDTO.setBasePath(basePath);
		listEservices.add(eServiceDTO);
	}

	@Test
	@DisplayName("Test JacksonMapper mapper build")
	void testGetObjectMapper_thenReturnValidObjectMapper() throws IOException {
		ObjectMapper mapper = JacksonMapperConfig.getInstance().getObjectMapper();
		assertNotNull(mapper);
		Assertions.assertFalse(mapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
	}

	@Test
	@DisplayName("Validate Jackson read object")
	void testValidateJacksonReadObject_whenValidObject_thenReturnValidObjectList() throws Exception {
		JacksonMapperConfig.getInstance().getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		SimpleModule validationModule = new SimpleModule();
		validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
		JacksonMapperConfig.getInstance().getObjectMapper().registerModule(validationModule);

		String stringDto = JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(listEservices.get(0));

		EserviceDTO resultMap = JacksonMapperConfig.getInstance().getObjectMapper().readValue(stringDto,
				EserviceDTO.class);
		assertEquals(listEservices.get(0).getEserviceId(), resultMap.getEserviceId());
	}
}
