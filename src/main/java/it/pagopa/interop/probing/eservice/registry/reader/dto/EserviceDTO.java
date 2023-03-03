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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import it.pagopa.interop.probing.eservice.registry.reader.annotations.ValidateEnum;
import it.pagopa.interop.probing.eservice.registry.reader.annotations.ValidateStringArraySize;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	@NotBlank(message = "must not be blank")
	@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "must respect the UUID regex")
	private String eserviceId;

	/** The version id. */
	@Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$", message = "must respect the UUID regex")
	@NotBlank(message = "must not be blank")
	private String versionId;

	/** The type. */
	@NotBlank(message = "must not be blank")
	@ValidateEnum(enumClass = EserviceTechnology.class)
	private String technology;

	/** The state. */
	@NotBlank(message = "must not be blank")
	@ValidateEnum(enumClass = EserviceState.class)
	private String state;

	/** The base path. */
	@NotEmpty(message = "list cannot be empty")
	@ValidateStringArraySize(maxSize = 255)
	private String[] basePath;

	/** The producer name. */
	@NotBlank(message = "must not be blank")
	@Size(max = 255, message = "must not be longer than 255 chars")
	private String producerName;

}
