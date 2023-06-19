package it.pagopa.interop.probing.eservice.registry.reader.config.aws.sqs;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.handlers.TracingHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class SqsConfig extends AbstractModule {

  @Override
  protected void configure() {
    bind(SqsConfig.class).asEagerSingleton();
  }

  @Provides
  @Singleton
  public AmazonSQSAsync provideAmazonSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .withRequestHandlers(new TracingHandler(AWSXRay.getGlobalRecorder())).build();
  }
}
