package io.github.robwin.service.proxy;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.robwin.model.Repo;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GitHubService {

  @CircuitBreaker(name = "backendA")
  @GET("users/{user}/repos")
  Observable<List<Repo>> listRepos(@Path("user") String user);

  /*@Headers({
      "Accept: application/vnd.github.v3.full+json",
      "User-Agent: Retrofit-Sample-App"
  })
  @GET("users/{username}")
  Call<User> getUser(@Path("username") String username);*/
}