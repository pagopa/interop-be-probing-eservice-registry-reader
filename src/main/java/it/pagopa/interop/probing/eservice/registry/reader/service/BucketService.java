package it.pagopa.interop.probing.eservice.registry.reader.service;

import java.io.IOException;
import java.util.List;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;

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

  public List<EserviceDTO> readObject() throws IOException {
    S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, bucketKey));
    return mapper.readValue(s3Object.getObjectContent(), new TypeReference<List<EserviceDTO>>() {});
  }

}
