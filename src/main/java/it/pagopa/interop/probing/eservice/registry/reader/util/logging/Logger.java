package it.pagopa.interop.probing.eservice.registry.reader.util.logging;

import java.util.UUID;

public interface Logger {

  void logMessagePushedToQueue(UUID eserviceId, UUID versionId, String queueUrl, String queueGroupId);
}
