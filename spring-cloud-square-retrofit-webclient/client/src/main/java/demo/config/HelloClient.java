package demo.config;

import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClient}<br/>
 *
 * @author dante<br />
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>Dante Raphael Ricalde Delgado. (DRD)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 * <li>Aug 09, 2021 (DRD) Creation class.</li>
 * </ul>
 * @version 1.0
 */
//@RetrofitClient(name = "HelloServer", url = "http://localhost:7111", configuration = HelloClientConfiguration.HelloClientRetrofitConfig.class)
@RetrofitClient(name = "HelloServer", url = "http://localhost:7111", qualifier = "HelloServerClient", configuration = HelloClientConfiguration.class)
//@RetrofitClient(name = "HelloServer", url = "http://localhost:7111", qualifier = "HelloServerClient")
public interface HelloClient {

    @GET("/")
    Mono<String> hello();
}
