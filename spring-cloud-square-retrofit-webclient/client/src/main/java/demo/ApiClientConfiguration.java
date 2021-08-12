package demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link ApiClientConfiguration}<br/>
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
@Configuration
public class ApiClientConfiguration {

    //exception
    //logging
    //propagation
    @Bean
    ExchangeFilterFunction filter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (response.statusCode().isError()) {
                Mono.empty();
            }
            return Mono.just(response);
        });
    }

    @Bean
    ExchangeFilterFunction filter1() {
        return (a, b) -> Mono.empty();
    }

}
