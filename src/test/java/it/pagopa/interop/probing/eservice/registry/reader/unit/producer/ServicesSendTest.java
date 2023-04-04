package it.pagopa.interop.probing.eservice.registry.reader.unit.producer;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;

import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;

@ExtendWith(MockitoExtension.class)
class ServicesSendTest {

	@Mock
	private SqsConfig sqs;
	@Mock
	private AmazonSQSAsync amazonSQS;
	@Mock
	private PropertiesLoader propertiesLoader;

	private EserviceDTO eServiceDTO;

	private static final String SQS_GROUP_ID = "services-group";

	@BeforeEach
	void setup() {
		String[] basePath = { "basePath1", "basePath2" };

		eServiceDTO = EserviceDTO.builder().eserviceId(UUID.randomUUID()).versionId(UUID.randomUUID())
				.name("Service Name").producerName("Producer Name").state(EserviceState.ACTIVE)
				.technology(EserviceTechnology.REST).basePath(basePath).versionNumber("1").build();

	}

	@Test
	@DisplayName("The sendMessage method of ServicesSend class is tested.")
	void testSendMessage_whenGivenValidEServiceAndUrl_thenProducerWriteOnQueue() throws IOException {

		String url = "http://queue/test-queue";

		try (MockedStatic<SqsConfig> cacheManagerMock = mockStatic(SqsConfig.class);
				MockedStatic<PropertiesLoader> propertiesManagerMock = mockStatic(PropertiesLoader.class)) {
			cacheManagerMock.when(SqsConfig::getInstance).thenReturn(sqs);
			propertiesManagerMock.when(PropertiesLoader::getInstance).thenReturn(propertiesLoader);
			when(sqs.getAmazonSQSAsync()).thenReturn(amazonSQS);
			when(amazonSQS.sendMessage(Mockito.any())).thenReturn(null);
			when(propertiesLoader.getKey(Mockito.any())).thenReturn(url);
			ServicesSend.getInstance().sendMessage(eServiceDTO);
			SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(url)
					.withMessageGroupId(SQS_GROUP_ID).withMessageBody(
							JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(eServiceDTO));
			verify(amazonSQS).sendMessage(sendMessageRequest);
		}
	}
}
