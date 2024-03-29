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

  @Override
  public void logS3BucketRead(String bucketName, String bucketKey) {
    log.info("Reading from S3 bucket. bucketName={}, bucketKey={}", bucketName, bucketKey);
  }

  @Override
  public void logNumberEserviceRetrieved(Integer numberEservicesRetrieved) {
    log.info("Number of Eservices retrieved: {}", numberEservicesRetrieved);
  }

}
