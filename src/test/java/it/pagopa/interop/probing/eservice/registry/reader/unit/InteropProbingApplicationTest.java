package it.pagopa.interop.probing.eservice.registry.reader.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.google.inject.Guice;
import com.google.inject.Injector;
import it.pagopa.interop.probing.eservice.registry.reader.InteropProbingApplication;
import it.pagopa.interop.probing.eservice.registry.reader.config.BaseModule;
import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;

class InteropProbingApplicationTest {

  private Injector injector = mock(Injector.class);

  private BucketService bucketService = mock(BucketService.class);
  private ServicesSend servicesSend = mock(ServicesSend.class);
  private List<EserviceDTO> listEservices;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    String[] basePath = {"xxx.xxx/xxx", "yyy.yyy/xxx"};
    String[] audience = {"xxx.xxx/xxx", "yyy.yyy/xxx"};
    EserviceDTO eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID())
        .versionId(UUID.randomUUID()).name("Service Name").producerName("Producer Name")
        .state(EserviceState.ACTIVE).technology(EserviceTechnology.REST).basePath(basePath)
        .audience(audience).versionNumber(1).build();
    listEservices = List.of(eServiceDTO, eServiceDTO);
  }

  @Test
  void main() throws Exception {
    try (MockedStatic<Guice> mockedGuice = mockStatic(Guice.class);) {

      mockedGuice
          .when(() -> Guice.createInjector(any(BaseModule.class), any(JacksonMapperConfig.class),
              any(PropertiesLoader.class), any(SqsConfig.class), any(BucketConfig.class)))
          .thenReturn(injector);

      Mockito.when(injector.getInstance(ArgumentMatchers.eq(BucketService.class)))
          .thenReturn(bucketService);
      Mockito.when(injector.getInstance(ArgumentMatchers.eq(ServicesSend.class)))
          .thenReturn(servicesSend);
      Mockito.when(bucketService.readObject()).thenReturn(listEservices);
      Mockito.doNothing().when(servicesSend).sendMessage(any(EserviceDTO.class));
      InteropProbingApplication.main(new String[0]);

      verify(bucketService).readObject();
      verify(servicesSend, times(2)).sendMessage(any(EserviceDTO.class));

    }
  }

}
