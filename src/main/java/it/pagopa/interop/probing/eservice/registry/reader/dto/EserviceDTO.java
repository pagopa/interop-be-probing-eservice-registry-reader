/**************************************************************************
*
* Copyright 2023 (C) DXC
*
* Created on  : Feb 20, 2023
* Author      : dxc technology
* Project Name: interop-be-probing 
* Package     : it.pagopa.interop.probing.dto
* File Name   : EserviceDTO.java
*
*-----------------------------------------------------------------------------
* Revision History (Release )
*-----------------------------------------------------------------------------
* VERSION     DESCRIPTION OF CHANGE
*-----------------------------------------------------------------------------
** --/1.0  |  Initial Create.
**---------|------------------------------------------------------------------
***************************************************************************/
package it.pagopa.interop.probing.eservice.registry.reader.dto;

import javax.validation.constraints.*;
import it.pagopa.interop.probing.eservice.registry.reader.annotations.ValidateStringArraySize;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Gets the producer name.
 *
 * @return the producer name
 */
@Getter

/**
 * Sets the producer name.
 *
 * @param producerName the new producer name
 */
@Setter

/**
 * Instantiates a new eservice DTO.
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class EserviceDTO {

	/** The name. */
	@NotBlank(message = "must not be blank")
	@Size(max = 255, message = "must not be longer than 255 chars")
	private String name;

	/** The eservice id. */
	@NotNull(message = "must not be null")
	private UUID eserviceId;

	/** The version id. */
	@NotNull(message = "must not be null")
	private UUID versionId;

	/** The type. */
	@NotNull(message = "must not be null")
	private EserviceTechnology technology;

	/** The state. */
	@NotNull(message = "must not be null")
	private EserviceState state;

	/** The base path. */
	@NotEmpty(message = "list cannot be empty")
	@ValidateStringArraySize(maxSize = 2048)
	private String[] basePath;

	/** The producer name. */
	@NotBlank(message = "must not be blank")
	@Size(max = 255, message = "must not be longer than 255 chars")
	private String producerName;

}
