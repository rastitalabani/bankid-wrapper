package se.rbg.bankidclient.domain.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CollectRequest {
  private UUID orderRef;
}
