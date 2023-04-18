package it.pagopa.interop.probing.eservice.registry.reader.producer;

import java.io.IOException;
import java.util.Objects;

import com.amazonaws.services.sqs.model.SendMessageRequest;

import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs.SqsConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.jacksonmapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.impl.LoggerImpl;


public class ServicesSend {

  private final Logger logger = new LoggerImpl();
  private String sqsUrlServices;

  private static ServicesSend instance;

  private static final String SQS_URL = "amazon.sqs.endpoint.services-queue";

  private static final String SQS_GROUP_ID = "services-group";

  public static ServicesSend getInstance() throws IOException {
    if (Objects.isNull(instance)) {
      instance = new ServicesSend();
    }
    return instance;
  }

  private ServicesSend() throws IOException {
    this.sqsUrlServices = PropertiesLoader.getInstance().getKey(SQS_URL);
  }

  public void sendMessage(EserviceDTO service) throws IOException {
    SendMessageRequest sendMessageRequest =
        new SendMessageRequest().withQueueUrl(sqsUrlServices).withMessageGroupId(SQS_GROUP_ID)
            .withMessageBody(
                JacksonMapperConfig.getInstance().getObjectMapper().writeValueAsString(service));
    SqsConfig.getInstance().getAmazonSQSAsync().sendMessage(sendMessageRequest);
    logger.logMessagePushedToQueue(service.getEserviceId(), service.getVersionId(),
        sqsUrlServices, SQS_GROUP_ID);
  }
}
