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
import java.util.Properties;

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
	private ServicesSend() throws IOException {
		Properties configuration = PropertiesLoader.loadProperties("application.properties");
		this.sqsUrlServices = configuration.getProperty("amazon.sqs.end-point.services-queue");
	}
	
	/**
	 * Send a new message to the sqs queue which will be used for the legal fact generation.
	 *
	 * @param service the service
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void sendMessage(EserviceDTO service) throws IOException {
		SendMessageRequest sendMessageRequest = null;
            sendMessageRequest = new SendMessageRequest().withQueueUrl(sqsUrlServices)
                    .withMessageBody(JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(service));
            SqsConfig.getInstance().amazonSQSAsync().sendMessage(sendMessageRequest);
           log.info("Service has been published in SQS.");
	}
}
