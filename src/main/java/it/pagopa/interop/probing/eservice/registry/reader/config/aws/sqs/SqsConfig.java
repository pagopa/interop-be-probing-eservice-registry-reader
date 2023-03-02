/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 2, 2023
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

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;

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

	/** The environment. */
	private String environment;

	
	/** The Constant ACCESS_KEY. */
	private static final String ACCESS_KEY = "amazon.sqs.credentials.accessKey";
	
	/** The Constant SECRET_KEY. */
	private static final String SECRET_KEY = "amazon.sqs.credentials.accessKey";

	/** The Constant REGION. */
	private static final String REGION_KEY = "amazon.sqs.region.static";
	
	/** The Constant URL. */
	private static final String URL_KEY = "amazon.sqs.end-point.services-queue";
	
	/** The Constant PROFILE. */
	private static final String ENV_KEY = "environment";
	
	/** The Constant PROD. */
	private static final String PROD_KEY = "prod";
	
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
	private SqsConfig() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties(ProjectConstants.PROPERTIES);
		this.accessKey = configuration.getProperty(ACCESS_KEY);
		this.secretKey = configuration.getProperty(SECRET_KEY);
		this.region = configuration.getProperty(REGION_KEY);
		this.sqsUrlServices = configuration.getProperty(URL_KEY);
		this.environment = configuration.getProperty(ENV_KEY);
	}

	/**
	 * Amazon SQS async.
	 *
	 * @return the amazon SQS async
	 */
	public AmazonSQSAsync amazonSQSAsync() {
		return environment.equals(PROD_KEY) ? AmazonSQSAsyncClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrlServices, region)).build()
				: AmazonSQSAsyncClientBuilder.standard()
						.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsUrlServices, region))
						.withCredentials(
								new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
						.build();
	}


}
