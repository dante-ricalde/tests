package demo;

import demo.config.HelloClient;
import demo.config.HelloClientConfiguration;
import demo.config.HelloClientGlobalEnableConfiguration;
import demo.config2.HelloClient2;
import demo.config2.HelloClient2Configuration;
import demo.config2.HelloClient2GlobalEnableConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.square.retrofit.webclient.EnableRetrofitClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Olga Maciaszek-Sharma
 */
@SpringBootApplication
//@EnableRetrofitClients
@RestController
public class HelloClientApplication {

	//@Qualifier("HelloServerClient")
	@Autowired
	//HelloClientGlobalEnableConfiguration.HelloClient client;
	//HelloClientConfiguration.HelloClient client;
	HelloClient client;

	//@Qualifier("HelloServer2Client")
	@Autowired
	//HelloClient2GlobalEnableConfiguration.HelloClient2 client2;
	//HelloClient2Configuration.HelloClient2 client2;
	HelloClient2 client2;

	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class, args);
	}

	//@Bean
	//@LoadBalanced
	/*public WebClient.Builder builder() {
		return WebClient.builder();
	}*/

	/*class DefaultClientRetrofitConfig {
		@Bean
		public WebClient.Builder defaultWebClientBuilder() {
			HttpClient httpClient = HttpClient.create()
					.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15500)
					.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(20)));


			return WebClient.builder()
					.baseUrl("http://localhost:7111")
					.clientConnector(new ReactorClientHttpConnector(httpClient));
		}
	}*/


	@GetMapping("/")
	public Mono<String> hello() {
		return client.hello();
	}

	@GetMapping("/hello2")
	public Mono<String> hello2() {
		return client2.hello();
	}
}
