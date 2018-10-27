package se.rbg.bankidclient.config;

import okhttp3.OkHttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import se.rbg.bankidclient.interceptor.HttpErrorLoggerInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;


@Configuration
public class RestTemplateConfig {

  @Value("${bankid.client.cert_pass}")
  private char[] password;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {

    SSLContext sslContext = SSLContextBuilder
      .create()
      .loadTrustMaterial(ResourceUtils.getFile("classpath:keystore/server_crt.jks"), password)
      .loadKeyMaterial(ResourceUtils.getFile("classpath:keystore/client_crt.jks"), password, password)
      .build();

    SSLSocketFactory sslSocketFactory;
    sslSocketFactory = sslContext.getSocketFactory();

    OkHttpClient client = new OkHttpClient.Builder()
      .addInterceptor(new HttpErrorLoggerInterceptor())
      .sslSocketFactory(sslSocketFactory, new TrustManager()).build();

    return new RestTemplate(new OkHttp3ClientHttpRequestFactory(client));
  }
}
