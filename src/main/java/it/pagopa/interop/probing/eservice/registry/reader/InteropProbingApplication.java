package it.pagopa.interop.probing.eservice.registry.reader;

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

public class InteropProbingApplication {

  public static void main(String[] args) throws Exception {
    Injector injector = Guice.createInjector(new BaseModule(), new JacksonMapperConfig(),
        new PropertiesLoader(), new SqsConfig(), new BucketConfig());
    BucketService bucketService = injector.getInstance(BucketService.class);
    ServicesSend servicesSend = injector.getInstance(ServicesSend.class);
    AWSXRay.beginSegment(ProjectConstants.APPLICATION_NAME);
    List<EserviceDTO> services = bucketService.readObject();
    for (EserviceDTO eservice : services) {
      servicesSend.sendMessage(eservice);
    }
    AWSXRay.endSegment();
  }

}
