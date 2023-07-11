package it.pagopa.interop.probing.eservice.registry.reader.producer;

import java.io.IOException;
import java.net.URI;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.ProjectConstants;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;

public class ServicesSend {

  @Inject
  private Logger logger;

  @Inject
  private AmazonSQSAsync sqs;

  @Inject
  private ObjectMapper mapper;

  @Inject
  @Named("amazon.sqs.endpoint.services-queue")
  private String sqsUrlServices;

  public void sendMessage(EserviceDTO service) throws IOException {
    SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(sqsUrlServices)
        .withMessageGroupId(ProjectConstants.SQS_GROUP_ID)
        .withMessageBody(mapper.writeValueAsString(service));
    sqs.sendMessage(sendMessageRequest);
    logger.logMessagePushedToQueue(service.getEserviceId(), service.getVersionId(),
        URI.create(sqsUrlServices), ProjectConstants.SQS_GROUP_ID);
  }
}
