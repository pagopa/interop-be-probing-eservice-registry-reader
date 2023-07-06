package it.pagopa.interop.probing.eservice.registry.reader.util;

public class ProjectConstants {

  private ProjectConstants() {
    super();
  }

  public static final String TRACE_ID_PLACEHOLDER = "trace_id";
  public static final String SQS_GROUP_ID = "services-group";
  public static final String TRACE_ID_XRAY_PLACEHOLDER = "AWS-XRAY-TRACE-ID";
  public static final String TRACE_ID_XRAY_MDC_PREFIX = "- [TRACE_ID= ";
  public static final String APPLICATION_NAME = "Interop-be-probing-eservice-registry-reader";

}
