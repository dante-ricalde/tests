package demo.config;

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
 * <b>Class</b>: {@link HelloClientConfiguration}<br/>
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
@ComponentScan("demo.config")
@EnableRetrofitClients(basePackages = "demo.config")//(defaultConfiguration = HelloClientConfiguration.class)*/
//@EnableRetrofitClients(defaultConfiguration = HelloClientRetrofitConfig1.class)
@EnableRetrofitClients
@Configuration
//public class HelloClientConfiguration extends AtlasAbstractHttpClientConfiguration {
public class HelloClientConfiguration {


    /*@Override
    public String prefix() {
        return "helloWebClientBuilder";
    }

    //@Qualifier("helloWebClientBuilder")
    @Bean
    WebClient.Builder helloWebClientBuilder() {
        final WebClient.Builder builder = super.buildHttpBuilder();

            //customize builder
        return builder;
    }*/


        //@RetrofitClient("HelloServer")

    /*class HelloClientRetrofitConfig {

        @Bean
        WebClient.Builder helloWebClientBuilder() {
            HttpClient httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5500)
                    .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10)));


            return WebClient.builder()
                    .baseUrl("http://localhost:7111")
                    .clientConnector(new ReactorClientHttpConnector(httpClient));
        }
    }*/
    static class HelloClientRetrofitConfig extends AtlasAbstractHttpClientConfiguration {

        @Override
        public String prefix() {
            return "helloWebClientBuilder";
        }

        @Bean(name = "helloWebClientBuilder")
        public WebClient.Builder createBuilder() {
            final WebClient.Builder builder = super.buildHttpBuilder();
            builder.baseUrl("http://localhost:7111");
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