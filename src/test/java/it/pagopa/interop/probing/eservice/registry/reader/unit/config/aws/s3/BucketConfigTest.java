package it.pagopa.interop.probing.eservice.registry.reader.unit.config.aws.s3;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;

import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;

 class BucketConfigTest {

	@InjectMocks
	private BucketConfig bucketConfig;
	
	@BeforeEach
	void setup() throws IOException {
		bucketConfig = new BucketConfig();
	}
	
	@Test
	void testAmazonS3_IsNotNull() throws IOException {
		assertNotNull(bucketConfig.amazonS3());
	}
	
	@Test
	void testAmazonS3_SdkClientException() throws IOException {
		AmazonS3 amazonS3 = bucketConfig.amazonS3();
		GetObjectRequest objectRequest = new GetObjectRequest("bucket-test", "bucket-test");
		Assertions.assertThrows(AmazonS3Exception.class, () -> amazonS3.getObject(objectRequest));
	}
}
