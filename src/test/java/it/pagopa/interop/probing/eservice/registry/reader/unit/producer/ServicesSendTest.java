package it.pagopa.interop.probing.eservice.registry.reader.unit.producer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;

@ExtendWith(MockitoExtension.class)
class ServicesSendTest {

  private ServicesSend servicesSend;

  private AmazonSQSAsync sqsMock = mock(AmazonSQSAsync.class);

  private ObjectMapper mapperMock = mock(ObjectMapper.class);

  private EserviceDTO eServiceDTO;

  @BeforeEach
  void setup() {
    String[] basePath = {"basePath1", "basePath2"};
    String[] audience = {"audience1", "audience2"};

    eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID()).versionId(UUID.randomUUID())
        .name("Service Name").producerName("Producer Name").state(EserviceState.ACTIVE)
        .technology(EserviceTechnology.REST).basePath(basePath).audience(audience).versionNumber(1)
        .build();

    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(AmazonSQSAsync.class).toInstance(sqsMock);
        bind(ObjectMapper.class).toInstance(mapperMock);
        bind(String.class).annotatedWith(Names.named("amazon.sqs.endpoint.services-queue"))
            .toInstance("http://queue/test-queue");
      }
    });
    servicesSend = injector.getInstance(ServicesSend.class);
  }

  @Test
  @DisplayName("The sendMessage method of ServicesSend class is tested.")
  void testSendMessage_whenGivenValidEServiceAndUrl_thenProducerWriteOnQueue() throws IOException {

    Mockito.when(mapperMock.writeValueAsString(eServiceDTO)).thenReturn(eServiceDTO.toString());

    servicesSend.sendMessage(eServiceDTO);

    verify(sqsMock).sendMessage(Mockito.any(SendMessageRequest.class));
  }

}
