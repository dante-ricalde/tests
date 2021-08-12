package demo.common;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link DefaultClientRetrofitConfig}<br/>
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
public class DefaultClientRetrofitConfig {

    //@Bean
    public WebClient.Builder defaultWebClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15500)
                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(20)));

        return WebClient.builder()
                .baseUrl("http://localhost:7111")
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
