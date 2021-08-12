package demo.config2;

import demo.AtlasAbstractHttpClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClient2RetrofitConfig}<br/>
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
public class HelloClient2RetrofitConfig extends AtlasAbstractHttpClientConfiguration {

    @Override
    public String prefix() {
        return "hello2WebClientBuilder";
    }

    @Override
    public WebClient.Builder createBuilder() {
        return null;
    }

    //@Qualifier("helloWebClientBuilder2")
    //@Bean
    WebClient.Builder helloWebClientBuilder2() {
        final WebClient.Builder builder = super.buildHttpBuilder();
            /*
            customize builder
             */
        return builder;
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
}
