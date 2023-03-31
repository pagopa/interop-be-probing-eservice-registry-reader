package it.pagopa.interop.probing.eservice.registry.reader.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;

@ExtendWith(MockitoExtension.class)
class BucketServiceTest {

	@Mock
	private BucketConfig bucketConfig;
	@Mock
	private AmazonS3 s3Client;

	private List<EserviceDTO> listEservices;

	@BeforeEach
	void setup() {
		listEservices = new ArrayList<>();
		String[] basePath = { "xxx.xxx/xxx", "yyy.yyy/xxx" };
		EserviceDTO eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID().toString())
				.versionId(UUID.randomUUID().toString()).name("Service Name").producerName("Producer Name")
				.state("ACTIVE").technology("REST").basePath(basePath).versionNumber("1").build();
		listEservices.add(eServiceDTO);
	}

	@Test
	@DisplayName("readObject method is executed")
	void testReadObject_whenGivenValidS3Object_thenReturnValidObjectList() throws Exception {
		String stringList = JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(listEservices);
		S3Object s3Object = new S3Object();
		s3Object.setBucketName("bucket-name-test");
		s3Object.setKey("bucket-key-test");
		InputStream targetStream = new ByteArrayInputStream(stringList.getBytes());
		s3Object.setObjectContent(targetStream);
		try (MockedStatic<BucketConfig> bucketManagerMock = mockStatic(BucketConfig.class)) {
			bucketManagerMock.when(BucketConfig::getInstance).thenReturn(bucketConfig);
			Mockito.when(bucketConfig.amazonS3()).thenReturn(s3Client);
			Mockito.when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);
			List<EserviceDTO> resp = BucketService.getInstance().readObject();
			assertEquals(listEservices.size(), resp.size());
			assertEquals(stringList, JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(resp));
		}
	}

}
