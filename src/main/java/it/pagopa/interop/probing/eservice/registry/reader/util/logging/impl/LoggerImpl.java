package it.pagopa.interop.probing.eservice.registry.reader.util.logging.impl;

import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.UUID;

@Slf4j
public class LoggerImpl implements Logger {

  @Override
  public void logMessagePushedToQueue(UUID eserviceId, UUID versionId, URI queueUrl,
      String queueGroupId) {
    log.info(
        "e-service has been pushed in queue. eserviceId={}, versionId={}, queue={}, groupId={}",
        eserviceId, versionId, queueUrl.toString(), queueGroupId);
  }
}
