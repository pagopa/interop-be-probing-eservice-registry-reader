package it.pagopa.interop.probing.eservice.registry.reader.unit.config.aws.sqs;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.sqs.AmazonSQSAsync;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;

 class SqsConfigTest {
	
	@InjectMocks
	private SqsConfig sqsConfig;
	
	
	@Test
	@DisplayName("Test AmazonSQS client build")
	 void testAmazonSQSAsync_thenReturnAmazonSQSAsyncIsNotNull() {
		assertNotNull(sqsConfig.amazonSQSAsync());
	}
	
	@Test
	@DisplayName("Test AmazonSQS client connection to non-existent queue")
	void testAmazonSQSAsync_NotValidSqsUrl_thenThrowSdkClientException(){
		AmazonSQSAsync client = sqsConfig.amazonSQSAsync();
		String url = "http://queue/test-queue";
		Assertions.assertThrows(SdkClientException.class, () -> client.getQueueUrl(url));
	}
	
}
