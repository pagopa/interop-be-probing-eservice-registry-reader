package it.pagopa.interop.probing.eservice.registry.reader.unit.producer;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;

@ExtendWith(MockitoExtension.class)
class ServicesSendTest {

	@InjectMocks 
	private ServicesSend servicesSend;
	@Mock 
	private JacksonMapperConfig jacksonMapperConfig;
	@Mock 
	private SqsConfig sqs;
	@Mock 
	private AmazonSQSAsync amazonSQS;
	
	private EserviceDTO eServiceDTO;

	@BeforeEach
	void setup() throws IOException {
		eServiceDTO = new EserviceDTO();
		eServiceDTO.setEserviceId(UUID.randomUUID().toString());
		eServiceDTO.setVersionId(UUID.randomUUID().toString());
		eServiceDTO.setName("Service Name");
		eServiceDTO.setProducerName("Producer Name");
		eServiceDTO.setState("ACTIVE");
		eServiceDTO.setType("REST");
		String[] basePath = { "basePath1", "basePath2" };
		eServiceDTO.setBasePath(basePath);
		
		Mockito.when(sqs.amazonSQSAsync()).thenReturn(amazonSQS);
	    Mockito.when(amazonSQS.sendMessage(Mockito.any())).thenReturn(null);
	    Mockito.when(jacksonMapperConfig.getObjectMapper()).thenReturn(new ObjectMapper());
	}

	@Test
	@DisplayName("The sendMessage method of ServicesSend class is tested.")
	 void testSendMessage_whenGivenValidEServiceAndUrl_thenProducerWriteOnQueue() throws IOException {
	    
		String url = "http://queue/test-queue";
		
		SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(url)
                    .withMessageBody(jacksonMapperConfig.getObjectMapper().writeValueAsString(eServiceDTO));
            
        servicesSend.sendMessage(eServiceDTO);
            
		verify(sqs.amazonSQSAsync()).sendMessage(sendMessageRequest);
	}
}
