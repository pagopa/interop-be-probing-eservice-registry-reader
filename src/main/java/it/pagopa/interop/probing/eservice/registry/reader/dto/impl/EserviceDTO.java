package it.pagopa.interop.probing.eservice.registry.reader.dto.impl;

import java.util.UUID;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import it.pagopa.interop.probing.eservice.registry.reader.annotations.ValidateStringArraySize;
import it.pagopa.interop.probing.eservice.registry.reader.dto.Dto;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class EserviceDTO implements Dto {

  @NotBlank(message = "must not be blank")
  @Size(max = 255, message = "must not be longer than 255 chars")
  private String name;

  @NotNull(message = "must not be null")
  private UUID eserviceId;

  @NotNull(message = "must not be null")
  private UUID versionId;

  @NotNull(message = "must not be null")
  private EserviceTechnology technology;

  @NotNull(message = "must not be null")
  private EserviceState state;

  @NotEmpty(message = "list cannot be empty")
  @ValidateStringArraySize(maxSize = 2048)
  private String[] basePath;

  @NotBlank(message = "must not be blank")
  @Size(max = 255, message = "must not be longer than 255 chars")
  private String producerName;

  @NotNull(message = "must not be null")
  @Min(value = 1, message = "must be at least 1")
  private Integer versionNumber;

  @NotEmpty(message = "list cannot be empty")
  @ValidateStringArraySize(maxSize = 2048)
  private String[] audience;

}
