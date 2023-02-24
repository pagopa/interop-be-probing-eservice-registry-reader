/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs
* File Name   : SqsConfig.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs;

import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;

/**
 * The Class SqsConfig.
 */
public class SqsConfig {

	/** The region. */
	private String region;

	/** The access key. */
	private String accessKey;

	/** The secret key. */
	private String secretKey;

	/** The sqs url services. */
	private String sqsUrlServices;

	/** The profile. */
	private String profile;

	/** The instance. */
	private static SqsConfig instance;

	/**
	 * Gets the single instance of SqsConfig.
	 *
	 * @return single instance of SqsConfig
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static SqsConfig getInstance() throws IOException {
		if (instance == null) {
			instance = new SqsConfig();
		}
		return instance;
	}

	/**
	 * Instantiates a new sqs config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public SqsConfig() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties("application.properties");
		String accessKey = configuration.getProperty("amazon.sqs.credentials.accessKey");
		String secretKey = configuration.getProperty("amazon.sqs.credentials.secretKey");
		String amazonAWSRegion = configuration.getProperty("amazon.sqs.region.static");
		String sqsUrlServices = configuration.getProperty("amazon.sqs.end-point.services-queue");
		String profile = configuration.getProperty("profile.active");
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.region = amazonAWSRegion;
		this.sqsUrlServices = sqsUrlServices;
		this.profile = profile;
	}

	/**
	 * Amazon SQS async.
	 *
	 * @return the amazon SQS async
	 */
	public AmazonSQSAsync amazonSQSAsync() {
		AmazonSQSAsync client = profile.equals("prod") ? AmazonSQSAsyncClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrlServices, region)).build()
				: AmazonSQSAsyncClientBuilder.standard()
						.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrlServices, region))
						.withCredentials(
								new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
						.build();
		return client;
	}

	/**
	 * Queue messaging template.
	 *
	 * @return the queue messaging template
	 */
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

}
