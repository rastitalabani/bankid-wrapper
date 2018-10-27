package se.rbg.bankidclient.domain.response.collect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectResponse {

  private String status;
  private String hintCode;
  private CompletionData completionData;
}
