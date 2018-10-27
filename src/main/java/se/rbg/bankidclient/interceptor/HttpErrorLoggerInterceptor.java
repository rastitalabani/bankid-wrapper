package se.rbg.bankidclient.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class HttpErrorLoggerInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {

    Request request = chain.request();
    Response response = chain.proceed(request);

    String bodyString = response.body().string();

    if (!response.isSuccessful()) {
      log.error("Unsuccessful HTTP call with cause: {}", bodyString);
    }
    if (response.isSuccessful()) {
      log.error("successful with response: {}", bodyString);
    }

    return response
      .newBuilder()
      .body(ResponseBody.create(MediaType.parse(""), bodyString))
      .build();
  }
}
