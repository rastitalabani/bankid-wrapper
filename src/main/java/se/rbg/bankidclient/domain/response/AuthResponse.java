package se.rbg.bankidclient.domain.response;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponse {
  private UUID orderRef;
  private UUID autoStartToken;
}
