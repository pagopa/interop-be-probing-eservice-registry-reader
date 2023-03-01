/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 27, 2023
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
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;

/**
 * The Class BucketConfig.
 */
public class BucketConfig {

	/** The amazon AWS access key. */
	private String amazonAwsAccessKey;

	/** The amazon AWS secret key. */
	private String amazonAwsSecretKey;
	
	/** The amazon AWS region. */
	private String amazonAwsRegion;


	/** The Constant REGION. */
	private static final String REGION = "amazon.clientS3.region";
	
	
	/**
	 * Instantiates a new bucket config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public BucketConfig() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties(ProjectConstants.PROPERTIES);
		this.amazonAwsAccessKey = configuration.getProperty(ProjectConstants.BUCKET_NAME);
		this.amazonAwsSecretKey = configuration.getProperty(ProjectConstants.BUCKET_KEY);
		this.amazonAwsRegion  = configuration.getProperty(REGION);
	}
	
	/**
	 * Credentials.
	 *
	 * @return the AWS credentials
	 */
	public AWSCredentials credentials() {
		return new BasicAWSCredentials(amazonAwsAccessKey,amazonAwsSecretKey);
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
				.withRegion(amazonAwsRegion)
				.build();
	}



}