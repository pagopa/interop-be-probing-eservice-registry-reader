package it.pagopa.interop.probing.eservice.registry.reader.unit.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import it.pagopa.interop.probing.eservice.registry.reader.dto.impl.EserviceDTO;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceState;
import it.pagopa.interop.probing.eservice.registry.reader.util.EserviceTechnology;

@ExtendWith(MockitoExtension.class)
class EserviceDTOTest {

  private EserviceDTO eServiceDTO;

  @BeforeEach
  void setup() {
    String[] basePath = {"basePath1", "basePath2"};
    String[] audience = {"audience1", "audience2"};

    eServiceDTO =
        EserviceDTO.builder().eserviceId(UUID.fromString("0b37ac73-cbd8-47f1-a14c-19bcc8f8f8e7"))
            .versionId(UUID.fromString("226574b8-82a1-4844-9484-55fffc9c15ef")).name("Service Name")
            .producerName("Producer Name").state(EserviceState.ACTIVE)
            .technology(EserviceTechnology.REST).basePath(basePath).audience(audience)
            .versionNumber(1).build();

  }

  @Test
  @DisplayName("Test the utility toString of lombok.")
  void testToString_whenGivenValidEServiceDto_thenValidEquals() throws IOException {
    String serviceString =
        "EserviceDTO(name=Service Name, eserviceId=0b37ac73-cbd8-47f1-a14c-19bcc8f8f8e7, versionId=226574b8-82a1-4844-9484-55fffc9c15ef, technology=REST, state=ACTIVE, basePath=[basePath1, basePath2], producerName=Producer Name, versionNumber=1, audience=[audience1, audience2])";
    assertEquals(eServiceDTO.toString(), serviceString);
  }

  @Test
  @DisplayName("Test the utility Equals and HashCode of lombok.")
  void testEqualsHashCode_whenGivenValidEServiceDto_thenValidEquals() throws IOException {

    String[] basePath = {"basePath1", "basePath2"};
    String[] audience = {"audience1", "audience2"};

    EserviceDTO copy =
        EserviceDTO.builder().eserviceId(UUID.fromString("0b37ac73-cbd8-47f1-a14c-19bcc8f8f8e7"))
            .versionId(UUID.fromString("226574b8-82a1-4844-9484-55fffc9c15ef")).name("Service Name")
            .producerName("Producer Name").state(EserviceState.ACTIVE)
            .technology(EserviceTechnology.REST).basePath(basePath).audience(audience)
            .versionNumber(1).build();

    assertEquals(true, eServiceDTO.equals(copy));
    assertEquals(true, eServiceDTO.hashCode() == copy.hashCode());
  }
}
