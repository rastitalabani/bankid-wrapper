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

    String bodyAsString = response.body().string();

    if (response.isSuccessful()) {
      log.error("successful with response: {}", bodyAsString);
      if (bodyAsString.contains("failed")) {
        return new Response.Builder()
          .code(500)
          .request(chain.request())
          .build();
      }
    } else {
      log.error("Unsuccessful HTTP call with cause: {}", bodyAsString);
    }

    return response
      .newBuilder()
      .body(ResponseBody.create(MediaType.parse(""), bodyAsString))
      .build();
  }
}
