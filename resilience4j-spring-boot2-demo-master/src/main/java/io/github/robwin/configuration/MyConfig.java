package io.github.robwin.configuration;

import io.github.robwin.service.proxy.GitHubService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpLogging;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author dante on 2020-04-23.
 * @project resilience4j-spring-boot2-demo-master
 */
@Slf4j
@Configuration
public class MyConfig {

  @Bean
  OkHttpClient.Builder okHttpClientBuilder() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(log::trace)
        .setLevel(Level.BODY);
    builder.addInterceptor(interceptor);
    return builder;
  }

  @Bean
  GitHubService gitHubService(OkHttpClient.Builder clientBuilder) {
    Retrofit retrofit = new Retrofit.Builder().client(clientBuilder.build())
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        //.addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create())
        .build();

    GitHubService service = retrofit.create(GitHubService.class);
    return service;
  }

}
