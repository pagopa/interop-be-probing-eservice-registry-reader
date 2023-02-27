package it.pagopa.interop.probing.eservice.registry.reader;

import java.util.List;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

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
		Weld weld = new Weld();
		WeldContainer container = weld.initialize();
		BucketService service = container.select(BucketService.class).get();
		List<EserviceDTO> services=service.readObject();
		ServicesSend queue = container.select(ServicesSend.class).get();
		for(EserviceDTO eservice : services) {
			queue.sendMessage(eservice);
		}
		
		weld.shutdown();
	}

}
