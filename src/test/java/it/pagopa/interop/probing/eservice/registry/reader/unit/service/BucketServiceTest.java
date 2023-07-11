package it.pagopa.interop.probing.eservice.registry.reader.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;

@ExtendWith(MockitoExtension.class)
class BucketServiceTest {
  private BucketService bucketService;

  private ObjectMapper mapperMock = mock(ObjectMapper.class);

  private AmazonS3 s3Client = mock(AmazonS3.class);

  private List<EserviceDTO> listEservices;

  @BeforeEach
  void setup() {
    String[] basePath = {"xxx.xxx/xxx", "yyy.yyy/xxx"};
    String[] audience = {"xxx.xxx/xxx", "yyy.yyy/xxx"};
    EserviceDTO eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID())
        .versionId(UUID.randomUUID()).name("Service Name").producerName("Producer Name")
        .state(EserviceState.ACTIVE).technology(EserviceTechnology.REST).basePath(basePath)
        .audience(audience).versionNumber(1).build();
    listEservices = List.of(eServiceDTO);

    Injector injector = Guice.createInjector(new AbstractModule() {
      @Override
      protected void configure() {
        bind(AmazonS3.class).toInstance(s3Client);
        bind(ObjectMapper.class).toInstance(mapperMock);
        bind(BucketService.class);
        bind(String.class).annotatedWith(Names.named("amazon.bucketS3.name"))
            .toInstance("mocked-name");
        bind(String.class).annotatedWith(Names.named("amazon.bucketS3.key"))
            .toInstance("mocked-key");
      }
    });

    bucketService = injector.getInstance(BucketService.class);

  }

  @Test
  @DisplayName("readObject method is executed")
  void testReadObject_whenGivenValidS3Object_thenReturnValidObjectList() throws Exception {
    String json = listEservices.toString();
    List<EserviceDTO> expectedList =
        mapperMock.readValue(json, new TypeReference<List<EserviceDTO>>() {});
    S3Object s3Object = new S3Object();
    InputStream inputStream = new ByteArrayInputStream(json.getBytes());
    s3Object.setObjectContent(inputStream);

    when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);

    List<EserviceDTO> actualList = bucketService.readObject();

    assertEquals(expectedList, actualList);

    verify(s3Client, times(1)).getObject(any(GetObjectRequest.class));
  }
}
