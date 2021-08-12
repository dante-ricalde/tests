package demo.config2;

import demo.AtlasAbstractHttpClientConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import org.springframework.cloud.square.retrofit.webclient.EnableRetrofitClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClient2Configuration}<br/>
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
/*@EnableAutoConfiguration
@ComponentScan("demo.config2")
@EnableRetrofitClients(basePackages = "demo.config2")//(defaultConfiguration = HelloClient2Configuration.class)*/
@EnableRetrofitClients
//@EnableRetrofitClients(defaultConfiguration = HelloClient2RetrofitConfig.class)
//@EnableRetrofitClients
@Configuration
//public class HelloClient2Configuration extends AtlasAbstractHttpClientConfiguration {
public class HelloClient2Configuration {

    /*@RetrofitClient(name = "HelloServer2", url = "http://localhost:7112", qualifier = "HelloServer2Client", configuration = HelloClientRetrofitConfig2.class)
    public interface HelloClient2 {
        @GET("/")
        Mono<String> hello();
    }*/

    /*@Override
    public String prefix() {
        return "hello2WebClientBuilder";
    }

    @Bean
    WebClient.Builder helloWebClientBuilder2() {
        final WebClient.Builder builder = super.buildHttpBuilder();

        //customize builder

        return builder;
    }*/

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

    static class HelloClient2RetrofitConfig extends AtlasAbstractHttpClientConfiguration {

        @Override
        public String prefix() {
            return "hello2WebClientBuilder";
        }

        @Bean(name = "hello2WebClientBuilder")
        public WebClient.Builder createBuilder() {
            final WebClient.Builder builder = super.buildHttpBuilder();
            builder.baseUrl("http://localhost:7116");
            //customize builder

            return builder;
        }

        /*@Bean
        WebClient.Builder helloWebClientBuilder() {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5500)
                    .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10)));


            return WebClient.builder()
                    .baseUrl("http://localhost:7111")
                    .clientConnector(new ReactorClientHttpConnector(httpClient));
        }*/
    }

}
