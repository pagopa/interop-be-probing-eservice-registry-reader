package it.pagopa.interop.probing.eservice.registry.reader.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.BeanDeserializerModifierWithValidation;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;

@ExtendWith(MockitoExtension.class)
class BucketServiceImplTest {
	
	@InjectMocks 
	private BucketService bucketServiceImpl = new BucketService();
	@Mock 
	private JacksonMapperConfig jacksonMapperConfig;
	@InjectMocks 
	private ObjectMapper mapper;
	@Mock 
	private BucketConfig bucketConfig;
	@Mock 
	private AmazonS3 s3Client;
	
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
		eServiceDTO.setType("REST");
		String[] basePath = { "xxx.xxx/xxx", "yyy.yyy/xxx" };
		eServiceDTO.setBasePath(basePath);
		listEservices.add(eServiceDTO);
	}

	@Test
	@DisplayName("readObject method is executed")
	void testReadObject_whenGivenValidS3Object_thenReturnValidObjectList() throws Exception {
		String stringList = mapper.writeValueAsString(listEservices);
		S3Object s3Object = new S3Object();
		s3Object.setBucketName("bucket-name-test");
		s3Object.setKey("bucket-key-test");
		InputStream targetStream = new ByteArrayInputStream(stringList.getBytes());
		s3Object.setObjectContent(targetStream);
		
		Mockito.when(jacksonMapperConfig.getObjectMapper()).thenReturn(mapper);
		Mockito.when(bucketConfig.amazonS3()).thenReturn(s3Client);
		Mockito.when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(s3Object);
				
		List<EserviceDTO> resp = bucketServiceImpl.readObject();
		assertEquals(listEservices.size(), resp.size());
		assertEquals(stringList, jacksonMapperConfig.getObjectMapper().writeValueAsString(resp));
	}

	@Test
	@DisplayName("Validate Jackson read object")
	void testValidateJacksonReadObject_whenValidObject_thenReturnValidObjectList() throws Exception {
		Mockito.when(jacksonMapperConfig.getObjectMapper()).thenReturn(mapper);
		jacksonMapperConfig.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule validationModule = new SimpleModule();
		validationModule.setDeserializerModifier(new BeanDeserializerModifierWithValidation());
		jacksonMapperConfig.getObjectMapper().registerModule(validationModule);

		String stringDto = jacksonMapperConfig.getObjectMapper().writeValueAsString(listEservices.get(0));

		EserviceDTO resultMap = jacksonMapperConfig.getObjectMapper().readValue(stringDto, EserviceDTO.class);
		assertEquals(listEservices.get(0).getEserviceId(), resultMap.getEserviceId());
	}

}
