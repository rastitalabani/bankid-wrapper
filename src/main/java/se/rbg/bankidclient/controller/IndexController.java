package se.rbg.bankidclient.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.rbg.bankidclient.adapter.BankIdAdapter;
import se.rbg.bankidclient.domain.request.AuthRequest;
import se.rbg.bankidclient.domain.request.CollectRequest;
import se.rbg.bankidclient.domain.request.SignRequest;
import se.rbg.bankidclient.domain.response.AuthResponse;
import se.rbg.bankidclient.domain.response.collect.CollectResponse;


@RestController
@Api(value = "BankID", description = "a client for swedish BankID", tags = "BankID")
public class IndexController {

  private BankIdAdapter bankIdAdapter;

  public IndexController(BankIdAdapter bankIdAdapter) {
    this.bankIdAdapter = bankIdAdapter;
  }

  @RequestMapping(value = "/authenticate",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("Start authentication process")
  public ResponseEntity<AuthResponse> postAuthenticate(@RequestBody AuthRequest authRequest) {
    return bankIdAdapter.authenticate(authRequest);
  }

  @RequestMapping(value = "/sign",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("Start signature process")
  public ResponseEntity<AuthResponse> postSignature(@RequestBody SignRequest signRequest) {
    return bankIdAdapter.sign(signRequest);
  }

  @RequestMapping(value = "/collect",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("Check process status, it might timeout sometimes due to latency from bankid")
  public ResponseEntity<CollectResponse> postCollect(@RequestBody CollectRequest collectRequest) {
    return bankIdAdapter.collect(collectRequest);
  }
}
