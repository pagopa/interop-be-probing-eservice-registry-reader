package it.pagopa.interop.probing.eservice.registry.reader;

import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;
import java.util.List;

public class InteropProbingApplication {

  public static void main(String[] args) throws Exception {

    List<EserviceDTO> services = BucketService.getInstance().readObject();
    for (EserviceDTO eservice : services) {
      ServicesSend.getInstance().sendMessage(eservice);
    }
  }

}
