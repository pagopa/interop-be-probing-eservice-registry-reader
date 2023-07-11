package it.pagopa.interop.probing.eservice.registry.reader.config;

import com.google.inject.AbstractModule;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.impl.LoggerImpl;

public class BaseModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Logger.class).to(LoggerImpl.class);
    bind(BucketService.class);
  }

}
