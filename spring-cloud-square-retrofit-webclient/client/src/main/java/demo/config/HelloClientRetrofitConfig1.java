package demo.config;

import demo.AtlasAbstractHttpClientConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClientRetrofitConfig1}<br/>
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
//@Configuration
public class HelloClientRetrofitConfig1 extends AtlasAbstractHttpClientConfiguration {

    @Override
    public String prefix() {
        return "helloWebClientBuilder";
    }

    @Override
    public WebClient.Builder createBuilder() {
        return null;
    }

    //@Qualifier("helloWebClientBuilder")
    //@Bean
    WebClient.Builder helloWebClientBuilder() {
        final WebClient.Builder builder = super.buildHttpBuilder();
            /*
            customize builder
             */
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
