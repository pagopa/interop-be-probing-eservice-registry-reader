package it.pagopa.interop.probing.eservice.registry.reader.config.aws.s3;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

@Singleton
public class BucketConfig extends AbstractModule {

  @Override
  protected void configure() {
    bind(BucketConfig.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  public AmazonS3 provideAmazonS3() {
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain()).build();
  }

}
