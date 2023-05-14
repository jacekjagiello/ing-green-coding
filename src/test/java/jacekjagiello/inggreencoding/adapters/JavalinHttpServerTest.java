package jacekjagiello.inggreencoding.adapters;

import io.javalin.Javalin;
import jacekjagiello.inggreencoding.usecase.ProcessTransactions;
import jacekjagiello.inggreencoding.usecase.CalculateAtmsOrder;
import jacekjagiello.inggreencoding.usecase.GenerateEventQueue;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavalinHttpServerTest {
    Javalin app = new JavalinHttpServer(new CalculateAtmsOrder(), new GenerateEventQueue(), new ProcessTransactions()).getServer();

    @Test
    public void allEndpointsRespondToRequests() {
        JavalinTest.test(app, (server, client) -> {
            String atmsRequestBody = "[{\"region\":4,\"requestType\":\"STANDARD\",\"atmId\":1},{\"region\":1,\"requestType\":\"STANDARD\",\"atmId\":1}]";
            String onlineGameRequestBody = "{\"groupCount\":6,\"clans\":[{\"numberOfPlayers\":4,\"points\":50},{\"numberOfPlayers\":2,\"points\":70}]}";
            String transactionsRequestBody = "[{\"debitAccount\":\"12345\",\"creditAccount\":\"54321\",\"amount\":11.9}]";

            // Correctness of output is covered by use case tests. Here we test only HTTP contract
            Response atmsResponse = client.post("/atms/calculateOrder", atmsRequestBody);
            assertThat(atmsResponse.code()).isEqualTo(200);
            assertThat(atmsResponse.header("Content-type")).isEqualTo("application/json");

            Response onlinegameResponse = client.post("/onlinegame/calculate", onlineGameRequestBody);
            assertThat(onlinegameResponse.code()).isEqualTo(200);
            assertThat(onlinegameResponse.header("Content-type")).isEqualTo("application/json");

            Response transactionsResponse = client.post("/transactions/report", transactionsRequestBody);
            assertThat(transactionsResponse.code()).isEqualTo(200);
            assertThat(transactionsResponse.header("Content-type")).isEqualTo("application/json");
        });
    }
}