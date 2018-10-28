package se.rbg.bankidclient.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.rbg.bankidclient.domain.request.CollectRequest;
import se.rbg.bankidclient.domain.response.collect.CollectResponse;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BankIdAdapterImplTest {

  private final HttpHeaders headers = new HttpHeaders();
  private CollectRequest collectRequest = new CollectRequest();
  private CollectResponse collectResponse =  new CollectResponse();

  @MockBean
  private RestTemplate restTemplate;

  private BankIdAdapterImpl bankIdAdapter;

  @Before
  public void setUp() {

    bankIdAdapter =  new BankIdAdapterImpl(restTemplate);

    headers.setContentType(MediaType.APPLICATION_JSON);
    collectRequest.setOrderRef(UUID.randomUUID());

    collectResponse.setStatus("complete");
    collectResponse.setHintCode(null);
  }

  @Test
  public void collectReturnOk() {
    ResponseEntity<CollectResponse> collectResponseResponseEntity = new ResponseEntity<>(collectResponse, headers, HttpStatus.OK);
    when(restTemplate.postForEntity(anyString(), any(), any(Class.class))).thenReturn(collectResponseResponseEntity);

    assertThat(bankIdAdapter.collect(collectRequest).getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void collectReturnError() {
    when(restTemplate.postForEntity(anyString(), any(), any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

    assertThat(bankIdAdapter.collect(collectRequest).getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}