/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3
* File Name   : BucketConfig.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3;

import java.io.IOException;
import java.util.Properties;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;

/**
 * The Class BucketConfig.
 */
public class BucketConfig {

	/** The amazon AWS access key. */
	private String amazonAWSAccessKey;

	/** The amazon AWS secret key. */
	private String amazonAWSSecretKey;
	
	/** The amazon AWS region. */
	private String amazonAWSRegion;

	/** The instance. */
	private static BucketConfig instance;


	/**
	 * Gets the single instance of BucketConfig.
	 *
	 * @return single instance of BucketConfig
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BucketConfig getInstance() throws IOException {
		if (instance == null) {
			instance = new BucketConfig();
		}
		return instance;
	}
	
	
	/**
	 * Instantiates a new bucket config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public BucketConfig() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties("application.properties");
		String bucketName = configuration.getProperty("amazon.bucketS3.name");
		String bucketKey = configuration.getProperty("amazon.bucketS3.key");
		String amazonAWSRegion = configuration.getProperty("amazon.clientS3.region");
		this.amazonAWSAccessKey = bucketName;
		this.amazonAWSSecretKey = bucketKey;
		this.amazonAWSRegion = amazonAWSRegion;
	}
	
	/**
	 * Credentials.
	 *
	 * @return the AWS credentials
	 */
	public AWSCredentials credentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey,amazonAWSSecretKey);
	}

	/**
	 * Amazon S 3.
	 *
	 * @return the amazon S 3
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public AmazonS3 amazonS3() throws IOException {

		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials()))
				.withRegion(amazonAWSRegion)
				.build();
	}



}