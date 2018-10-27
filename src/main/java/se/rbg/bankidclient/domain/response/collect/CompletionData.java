package se.rbg.bankidclient.domain.response.collect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletionData {
  private User user;
  private Device device;
  private Cert cert;
  private String signature;
  private String ocspResponse;

  @Data
  class User {
    private Long personalNumber;
    private String name;
    private String givenName;
    private String surname;
  }

  @Data
  class Device {
    private String ipAddress;
  }

  @Data
  class Cert {
    private Date notBefore;
    private Date notAfter;
  }
}
