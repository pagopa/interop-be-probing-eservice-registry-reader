/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 24, 2023
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
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.sqs.model.SendMessageRequest;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonMapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;


/**
 * The Class ServicesSend.
 */
public class ServicesSend {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicesSend.class);

    
	/** The instance. */
	private static ServicesSend instance;

	
    /** The sqs url services. */
    private String sqsUrlServices;

	/**
	 * Gets the single instance of ServicesSend.
	 *
	 * @return single instance of ServicesSend
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ServicesSend getInstance() throws IOException {
		if (instance == null) {
			instance = new ServicesSend();
		}
		return instance;
	}
	
	/**
	 * Instantiates a new services send.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ServicesSend() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties("application.properties");
		String sqsUrlServices = configuration.getProperty("amazon.sqs.end-point.services-queue");
		this.sqsUrlServices = sqsUrlServices;
	}
	
	/**
	 * Send a new message to the sqs queue which will be used for the legal fact generation.
	 *
	 * @param service the service
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void sendMessage(EserviceDTO service) throws IOException {
		SqsConfig sqs = SqsConfig.getInstance();
		SendMessageRequest sendMessageRequest = null;
            sendMessageRequest = new SendMessageRequest().withQueueUrl(sqsUrlServices)
                    .withMessageBody(JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(service));
            sqs.amazonSQSAsync().sendMessage(sendMessageRequest);
            LOGGER.info("Service has been published in SQS.");
	}
}
