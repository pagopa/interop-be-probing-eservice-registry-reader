/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 2, 2023
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

import java.util.Objects;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
/**
 * The Class BucketConfig.
 */
public class BucketConfig {

	/** The instance. */
	private static BucketConfig instance;

	/**
	 * Gets the single instance of BucketConfig.
	 *
	 * @return single instance of BucketConfig
	 */
	public static BucketConfig getInstance() {
		if (Objects.isNull(instance)) {
			instance = new BucketConfig();
		}
		return instance;
	}

	/**
	 * Amazon S 3.
	 *
	 * @return the amazon S 3
	 */
	public AmazonS3 amazonS3() {
		AWSCredentialsProvider provider = new DefaultAWSCredentialsProviderChain();
		return AmazonS3ClientBuilder.standard().withCredentials(provider.GetCredentials()).build();
	}

}