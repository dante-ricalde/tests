package demo.config2;

import demo.AtlasAbstractHttpClientConfiguration;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClient2GlobalEnableConfiguration}<br/>
 *
 * @author dante<br />
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>Dante Raphael Ricalde Delgado. (DRD)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 * <li>Aug 06, 2021 (DRD) Creation class.</li>
 * </ul>
 * @version 1.0
 */
//@Configuration
public class HelloClient2GlobalEnableConfiguration {

    //@RetrofitClient(name = "HelloServer2", url = "http://localhost:7112", configuration = HelloClientRetrofitConfig2.class)
    public interface HelloClient2 {
        @GET("/")
        Mono<String> hello();
    }

    class HelloClientRetrofitConfig2 extends AtlasAbstractHttpClientConfiguration {
        @Override
        public String prefix() {
            return "hello2WebClientBuilder";
        }

        @Override
        public WebClient.Builder createBuilder() {
            return null;
        }

        //@Bean
        WebClient.Builder helloWebClientBuilder2() {
            final WebClient.Builder builder = super.buildHttpBuilder();

            //customize builder

            return builder;
        }
    }

        /*@Bean
        WebClient.Builder helloWebClientBuilder() {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 9500)
                    .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(20)));


            return WebClient.builder()
                    .baseUrl("http://localhost:7111")
                    .clientConnector(new ReactorClientHttpConnector(httpClient));
        }*/
    //}

}
