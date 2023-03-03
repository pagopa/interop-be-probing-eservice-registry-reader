/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 2, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.service
* File Name   : BucketService.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;

/**
 * The Class BucketService.
 */
public class BucketService {

	/** The instance. */
	private static BucketService instance;

	/** The Constant BUCKET_NAME. */
	private static final String BUCKET_NAME = "amazon.bucketS3.name";

	/** The Constant BUCKET_KEY. */
	private static final String BUCKET_KEY = "amazon.bucketS3.key";

	/**
	 * Gets the single instance of BucketService.
	 *
	 * @return single instance of BucketService
	 */
	public static BucketService getInstance() {
		if (instance == null) {
			instance = new BucketService();
		}
		return instance;
	}

	/**
	 * Read object.
	 *
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<EserviceDTO> readObject() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties(ProjectConstants.PROPERTIES);
		String bucketName = configuration.getProperty(BUCKET_NAME);
		String bucketKey = configuration.getProperty(BUCKET_KEY);
		S3Object s3Object = BucketConfig.getInstance().amazonS3()
				.getObject(new GetObjectRequest(bucketName, bucketKey));
		return JacksonMapperConfig.getInstance().getObjectMapper().readValue(s3Object.getObjectContent(),
				new TypeReference<List<EserviceDTO>>() {
				});
	}

}