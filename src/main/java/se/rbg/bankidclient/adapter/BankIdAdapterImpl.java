package se.rbg.bankidclient.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.rbg.bankidclient.domain.request.AuthRequest;
import se.rbg.bankidclient.domain.request.CollectRequest;
import se.rbg.bankidclient.domain.request.SignRequest;
import se.rbg.bankidclient.domain.response.AuthResponse;
import se.rbg.bankidclient.domain.response.collect.CollectResponse;

import java.util.Base64;


@Slf4j
@Service
public class BankIdAdapterImpl implements BankIdAdapter {

  @Value("${bankid.adapter.base_url}")
  private String BASE_URL;
  private final HttpHeaders headers = new HttpHeaders();
  private final RestTemplate restTemplate;
  private HttpEntity<SignRequest> request;

  public BankIdAdapterImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ResponseEntity<AuthResponse> authenticate(AuthRequest authRequest) {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<AuthRequest> request = new HttpEntity<>(authRequest, headers);

    try {
      return restTemplate.postForEntity(BASE_URL + "auth", request, AuthResponse.class);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<AuthResponse> sign(SignRequest signRequest) {

    String userDataEncoded = Base64.getEncoder().encodeToString(signRequest.getUserVisibleData().getBytes());
    signRequest.setUserVisibleData(userDataEncoded);

    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<SignRequest> request = new HttpEntity<>(signRequest, headers);

    try {
      return restTemplate.postForEntity(BASE_URL + "sign", request, AuthResponse.class);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<CollectResponse> collect(CollectRequest orderRef) {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<CollectRequest> request = new HttpEntity<>(orderRef, headers);

    try {
      return restTemplate.postForEntity(BASE_URL + "collect", request, CollectResponse.class);
    } catch (HttpClientErrorException hcee) {
      String errorBody = hcee.getResponseBodyAsString();
      if (errorBody.contains("No such order"))
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      else {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
