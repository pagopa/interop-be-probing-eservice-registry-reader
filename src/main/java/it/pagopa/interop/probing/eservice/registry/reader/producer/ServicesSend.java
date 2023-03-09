/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Mar 2, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.producer
* File Name   : ServicesSend.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.producer;

import java.io.IOException;
import java.util.Objects;

import com.amazonaws.services.sqs.model.SendMessageRequest;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class ServicesSend.
 */

/** The Constant log. */
@Slf4j
public class ServicesSend {

	/** The sqs url services. */
	private String sqsUrlServices;

	/** The instance. */
	private static ServicesSend instance;

	private static final String SQS_URL = "SQS_ENDPOINT_SERVICES_QUEUE";

	/**
	 * Gets the single instance of ServicesSend.
	 * @return single instance of ServicesSend
	 * @throws IOException 
	 */
	public static ServicesSend getInstance() throws IOException{
		if (Objects.isNull(instance)) {
			instance = new ServicesSend();
		}
		return instance;
	}

	/**
	 * Instantiates a new services send.
	 * @throws IOException 
	 */
	private ServicesSend() throws IOException {
		this.sqsUrlServices = PropertiesLoader.getInstance().getKey(SQS_URL);
	}

	/**
	 * Send a new message to the sqs queue which will be used for the legal fact
	 * generation.
	 *
	 * @param service the service
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void sendMessage(EserviceDTO service) throws IOException {
		SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(sqsUrlServices)
				.withMessageBody(JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(service));
		SqsConfig.getInstance().getAmazonSQSAsync().sendMessage(sendMessageRequest);
		log.info("Service " + service.getEserviceId() + " with version " + service.getVersionId()
				+ " has been published in SQS.");
	}
}
