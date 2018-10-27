package se.rbg.bankidclient.domain.request;

import lombok.Data;

@Data
public class SignRequest extends AuthRequest {
  private String userVisibleData;
}
