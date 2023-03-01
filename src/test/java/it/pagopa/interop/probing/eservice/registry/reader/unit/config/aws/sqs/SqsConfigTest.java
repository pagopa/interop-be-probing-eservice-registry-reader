package it.pagopa.interop.probing.eservice.registry.reader.unit.config.aws.sqs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.sqs.AmazonSQSAsync;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;

@RunWith(MockitoJUnitRunner.class)
 class SqsConfigTest {
	
	@InjectMocks
	private SqsConfig sqsConfig;
	
	@BeforeEach
	 void setup() throws IOException {
		sqsConfig = new SqsConfig();
	}
	
	@Test
	 void testAmazonSQSAsync_IsNotNull() {
		assertNotNull(sqsConfig.amazonSQSAsync());
	}
	
	@Test
	void testAmazonSQSAsync_SdkClientException(){
		AmazonSQSAsync client = sqsConfig.amazonSQSAsync();
		String url = "http://queue/test-queue";
		Assertions.assertThrows(SdkClientException.class, () -> client.getQueueUrl(url));
	}
	
}
