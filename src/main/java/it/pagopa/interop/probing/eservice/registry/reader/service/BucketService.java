package it.pagopa.interop.probing.eservice.registry.reader.service;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import it.pagopa.interop.probing.eservice.registry.reader.config.PropertiesLoader;
import it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3.BucketConfig;
import it.pagopa.interop.probing.eservice.registry.reader.config.mapping.mapper.JacksonMapperConfig;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class BucketService {

  private static BucketService instance;

  private static final String BUCKET_NAME = "amazon.bucketS3.name";

  private static final String BUCKET_KEY = "amazon.bucketS3.key";

  public static BucketService getInstance() {
    if (Objects.isNull(instance)) {
      instance = new BucketService();
    }
    return instance;
  }

  public List<EserviceDTO> readObject() throws IOException {
    String bucketName = PropertiesLoader.getInstance().getKey(BUCKET_NAME);
    String bucketKey = PropertiesLoader.getInstance().getKey(BUCKET_KEY);
    S3Object s3Object = BucketConfig.getInstance().amazonS3()
        .getObject(new GetObjectRequest(bucketName, bucketKey));
    return JacksonMapperConfig.getInstance().getObjectMapper()
        .readValue(s3Object.getObjectContent(),
            new TypeReference<List<EserviceDTO>>() {
            });
  }

}
