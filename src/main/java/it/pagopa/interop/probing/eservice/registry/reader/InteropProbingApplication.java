package it.pagopa.interop.probing.eservice.registry.reader;

import java.util.List;

import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.producer.ServicesSend;
import it.pagopa.interop.probing.eservice.registry.reader.service.BucketService;

public class InteropProbingApplication {
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		List<EserviceDTO> services= BucketService.getInstance().readObject();
		for(EserviceDTO eservice : services) {
			ServicesSend.getInstance().sendMessage(eservice);
		}
	}

}
