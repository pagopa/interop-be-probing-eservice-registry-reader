package it.pagopa.interop.probing.eservice.registry.reader.unit.config.jacksonmapper;

import static org.junit.Assert.assertFalse;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.BeanDeserializerModifierWithValidation;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;

class JacksonMapperConfigTest {

  private List<EserviceDTO> listEservices;

  private Injector injector;
  private JacksonMapperConfig jacksonMapperConfig;

  @BeforeEach
  void setup() {
    injector = Guice.createInjector(new JacksonMapperConfig());
    jacksonMapperConfig = injector.getInstance(JacksonMapperConfig.class);

    listEservices = new ArrayList<>();
    String[] basePath = {"xxx.xxx/xxx", "yyy.yyy/xxx"};
    String[] audience = {"xxx.xxx/xxx", "yyy.yyy/xxx"};

    EserviceDTO eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID())
        .versionId(UUID.randomUUID()).name("Service Name").producerName("Producer Name")
        .state(EserviceState.ACTIVE).technology(EserviceTechnology.REST).basePath(basePath)
        .audience(audience).versionNumber(1).build();

    listEservices.add(eServiceDTO);
  }

  @Test
  void testGetObjectMapper() {
    ObjectMapper objectMapper = jacksonMapperConfig.getObjectMapper();

    assertNotNull(objectMapper);
    assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
  }

  @Test
  @DisplayName("Test JacksonMapper mapper build")
  void testGetObjectMapper_thenReturnValidObjectMapper() throws IOException {
    ObjectMapper objectMapper = jacksonMapperConfig.getObjectMapper();
    assertNotNull(objectMapper);
    Assertions
        .assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    Assertions.assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
  }

  @Test
  @DisplayName("Validate Jackson read object")
  void testValidateJacksonReadObject_whenValidObject_thenReturnValidObjectList() throws Exception {
    ObjectMapper objectMapper = jacksonMapperConfig.getObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleModule validationModule = new SimpleModule();
    validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
    objectMapper.registerModule(validationModule);
    String stringDto = objectMapper.writeValueAsString(listEservices.get(0));
    EserviceDTO resultMap = objectMapper.readValue(stringDto, EserviceDTO.class);
    assertEquals(listEservices.get(0).getEserviceId(), resultMap.getEserviceId());
  }
}
