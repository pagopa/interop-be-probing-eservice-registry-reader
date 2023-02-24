/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonMapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;


// TODO: Auto-generated Javadoc
/**
 * The Class BucketService.
 */
public class BucketService {
	

	/** The instance. */
	private static BucketService instance;


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
	 * @throws Exception the exception
	 */
	public List<EserviceDTO> readObject() throws Exception {
		Properties configuration = PropertiesLoader.loadProperties("application.properties");
		String bucketName = configuration.getProperty("amazon.bucketS3.name");
		String bucketKey = configuration.getProperty("amazon.bucketS3.key");
		List<EserviceDTO> listProva = new ArrayList<>();
		S3Object s3Object = BucketConfig.getInstance().amazonS3().getObject(new GetObjectRequest(bucketName, bucketKey));
		listProva = JacksonMapperConfig.getInstance().getObjectMapper().readValue(s3Object.getObjectContent(), new TypeReference<List<EserviceDTO>>() {
		});
		return listProva;
	}
	

}