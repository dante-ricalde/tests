package demo;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.function.Function;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link AtlasAbstractHttpClientConfiguration}<br/>
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
public abstract class AtlasAbstractHttpClientConfiguration {

    ObjectProvider<ExchangeFilterFunction> exchangeFilterFunction;

    @Autowired
    public final void setExchangeFilterFunction(ObjectProvider<ExchangeFilterFunction> exchangeFilterFunction) {
        this.exchangeFilterFunction = exchangeFilterFunction;
    }

    public abstract String prefix();

    public abstract WebClient.Builder createBuilder();

    //ApplicationContext context;

    protected final WebClient.Builder buildHttpBuilder() {
        System.out.println("Available filters for prefix => " + prefix());
        System.out.println(exchangeFilterFunction.stream().count());
        HttpClient httpClient = null;
        if (prefix().equals("helloWebClientBuilder")) {
            httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5500)
                    .option(ChannelOption.AUTO_READ, Boolean.TRUE)
                    .option(ChannelOption.AUTO_CLOSE, Boolean.TRUE)
                    .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(10)));
        } else if (prefix().equals("hello2WebClientBuilder")) {
            httpClient = HttpClient.create()
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 9300)
                    .option(ChannelOption.AUTO_READ, Boolean.FALSE)
                    .option(ChannelOption.AUTO_CLOSE, Boolean.FALSE)
                    .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(20)));
        }
        final ReactorClientHttpConnector delegate = new ReactorClientHttpConnector(httpClient);
        final WebClient.Builder builder = WebClient.builder()
                .baseUrl("http://localhost:7111")
                //.clientConnector(new ReactorClientHttpConnector(httpClient));
                .clientConnector((method, uri, requestCallback) -> delegate.connect(method, uri, requestCallback)
                        /*.flatMap(response -> {
                            if (response.getStatusCode().isError()) {
                                return Mono.error(new RuntimeException("failed"));
                            }
                            return Mono.just(response);
                        })*/
                        .onErrorMap(throwable -> {
                            return new RuntimeException("failed");
                        }));

        if (prefix().equals("helloWebClientBuilder")) {
            builder.filters(filters -> exchangeFilterFunction.stream().forEach(filters::add));
        } else if (prefix().equals("hello2WebClientBuilder")) {

        }

        return builder;
    }

    /*@Override
    public void afterPropertiesSet() throws Exception {

        AnnotationConfigApplicationContext genericWebApplicationContext = (AnnotationConfigApplicationContext) context;
        genericWebApplicationContext.registerBean(prefix(), WebClient.Builder.class, () -> builder);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }*/
}
