package se.rbg.bankidclient.domain.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class AuthRequest {
  @JsonSerialize(using = ToStringSerializer.class)
  private Long personalNumber;
  private String endUserIp;
}
