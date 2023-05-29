package it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.BeanDeserializerModifierWithValidation;

public class JacksonMapperConfig extends AbstractModule {

  @Override
  protected void configure() {
    bind(JacksonMapperConfig.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  public ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.registerModule(new JavaTimeModule());
    SimpleModule validationModule = new SimpleModule();
    validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
    mapper.registerModule(validationModule);
    return mapper;
  }

}
