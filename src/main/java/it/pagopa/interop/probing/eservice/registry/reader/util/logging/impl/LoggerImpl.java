package it.pagopa.interop.probing.eservice.registry.reader.util.logging.impl;

import java.net.URI;
import java.util.UUID;
import com.amazonaws.xray.AWSXRay;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerImpl implements Logger {

  @Override
  public void logMessagePushedToQueue(UUID eserviceId, UUID versionId, URI queueUrl,
      String queueGroupId) {
    log.info(
        "[TRACE_ID= {}] - e-service has been pushed in queue. eserviceId={}, versionId={}, queue={}, groupId={}",
        AWSXRay.getCurrentSegment().getTraceId().toString(), eserviceId, versionId,
        queueUrl.toString(), queueGroupId);
  }
}
