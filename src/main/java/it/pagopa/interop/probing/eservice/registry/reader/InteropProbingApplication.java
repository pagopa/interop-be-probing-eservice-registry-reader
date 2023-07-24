package it.pagopa.interop.probing.eservice.registry.reader;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import com.amazonaws.xray.AWSXRay;
import com.google.inject.Guice;
import com.google.inject.Injector;
import it.pagopa.interop.probing.eservice.registry.reader.config.BaseModule;
import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InteropProbingApplication {

  public static void main(String[] args) throws Exception {
    log.info("Eservice-Registry-Reader started at: {}", LocalDateTime.now(ZoneOffset.UTC));
    Injector injector = Guice.createInjector(new BaseModule(), new JacksonMapperConfig(),
        new PropertiesLoader(), new SqsConfig(), new BucketConfig());
    BucketService bucketService = injector.getInstance(BucketService.class);
    ServicesSend servicesSend = injector.getInstance(ServicesSend.class);

    List<EserviceDTO> services = bucketService.readObject();
    for (EserviceDTO eservice : services) {
      AWSXRay.beginSegment(ProjectConstants.APPLICATION_NAME);
      servicesSend.sendMessage(eservice);
      AWSXRay.endSegment();
    }
    log.info("Eservice-Registry-Reader ended at: {}", LocalDateTime.now(ZoneOffset.UTC));
  }

}
