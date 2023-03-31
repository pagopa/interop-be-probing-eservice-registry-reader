package it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3;

import java.util.Objects;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class BucketConfig {

	private static BucketConfig instance;

	public static BucketConfig getInstance() {
		if (Objects.isNull(instance)) {
			instance = new BucketConfig();
		}
		return instance;
	}

	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).build();
	}

}