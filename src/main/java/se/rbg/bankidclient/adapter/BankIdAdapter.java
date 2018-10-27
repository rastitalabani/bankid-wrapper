package se.rbg.bankidclient.adapter;

import org.springframework.http.ResponseEntity;
import se.rbg.bankidclient.domain.request.AuthRequest;
import se.rbg.bankidclient.domain.request.CollectRequest;
import se.rbg.bankidclient.domain.request.SignRequest;
import se.rbg.bankidclient.domain.response.AuthResponse;
import se.rbg.bankidclient.domain.response.collect.CollectResponse;

public interface BankIdAdapter {
  ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest);

  ResponseEntity<AuthResponse> sign(SignRequest signRequest);

  ResponseEntity<CollectResponse> collect(CollectRequest orderRef);
}
