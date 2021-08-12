package demo.config2;

import org.springframework.cloud.square.retrofit.core.RetrofitClient;
import reactor.core.publisher.Mono;
import retrofit2.http.GET;

/**
 * [Description].
 * <br/>
 * <b>Class</b>: {@link HelloClient2}<br/>
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
@RetrofitClient(name = "HelloServer2", url = "http://localhost:7112", configuration = HelloClient2Configuration.HelloClient2RetrofitConfig.class)
//@RetrofitClient(name = "HelloServer2", url = "http://localhost:7112", qualifier = "HelloServer2Client", configuration = HelloClient2RetrofitConfig.class)
//@RetrofitClient(name = "HelloServer2", url = "http://localhost:7112", qualifier = "HelloServer2Client")
public interface HelloClient2 {

    @GET("/")
    Mono<String> hello();
}
