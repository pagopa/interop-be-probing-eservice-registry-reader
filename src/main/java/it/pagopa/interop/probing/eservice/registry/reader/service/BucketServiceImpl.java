/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 27, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.service
* File Name   : BucketServiceImpl.java
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
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;
import jakarta.inject.Inject;


/**
 * The Class BucketService.
 */
public class BucketServiceImpl implements BucketService{
	

	/** The jackson mapper config. */
	@Inject
	JacksonMapperConfig  jacksonMapperConfig;

	/** The bucket config. */
	@Inject
	BucketConfig  bucketConfig;

	/**
	 * Read object.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@Override
	public List<EserviceDTO> readObject() throws Exception {
		Properties configuration = PropertiesLoader.loadProperties(ProjectConstants.PROPERTIES);
		String bucketName = configuration.getProperty(ProjectConstants.BUCKET_NAME);
		String bucketKey = configuration.getProperty(ProjectConstants.BUCKET_KEY);
		List<EserviceDTO> listProva = new ArrayList<>();
		S3Object s3Object = bucketConfig.amazonS3().getObject(new GetObjectRequest(bucketName, bucketKey));
		listProva = jacksonMapperConfig.getObjectMapper().readValue(s3Object.getObjectContent(), new TypeReference<List<EserviceDTO>>() {
		});
		return listProva;
	}
	

}