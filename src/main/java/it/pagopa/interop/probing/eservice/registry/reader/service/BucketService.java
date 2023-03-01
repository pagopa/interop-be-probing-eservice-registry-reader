/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 27, 2023
* Author      : dxc technology
* Project Name: interop-be-probing-eservice-registry-reader 
* Package     : it.pagopa.interop.probing.eservice.registry.reader.service
* File Name   : BucketService.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.service;

import java.io.IOException;
import java.util.List;

import it.pagopa.interop.probing.eservice.registry.reader.dto.EserviceDTO;

/**
 * The Interface BucketService.
 */
public interface  BucketService {
	
	/**
	 * Read object.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	List<EserviceDTO> readObject() throws IOException;
}
