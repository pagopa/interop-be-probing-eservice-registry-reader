package it.pagopa.interop.probing.eservice.registry.reader.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.logging.Logger;

@Singleton
public class BucketService {

  @Inject
  @Named("amazon.bucketS3.name")
  private String bucketName;

  @Inject
  @Named("amazon.bucketS3.key")
  private String bucketKey;

  @Inject
  private AmazonS3 s3Client;

  @Inject
  private ObjectMapper mapper;

  @Inject
  private Logger logger;

  public List<EserviceDTO> readObject() throws IOException {
    logger.logS3BucketRead(bucketName, bucketKey);
    S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, bucketKey));
    List<EserviceDTO> services =
        mapper.readValue(s3Object.getObjectContent(), new TypeReference<List<EserviceDTO>>() {});
    logger.logNumberEserviceRetrieved(Objects.nonNull(services) ? services.size() : 0);
    return services;
  }

}
