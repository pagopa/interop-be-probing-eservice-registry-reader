package it.pagopa.interop.probing.eservice.registry.reader.util.logging;

import java.net.URI;
import java.util.UUID;

public interface Logger {

  void logMessagePushedToQueue(UUID eserviceId, UUID versionId, URI queueUrl, String queueGroupId);

  void logS3BucketRead(String bucketName, String bucketKey);

  void logNumberEserviceRetrieved(Integer numberEservicesRetrieved);
}
