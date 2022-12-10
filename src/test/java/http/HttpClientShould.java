package http;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;
import player.Players;
import player.ResponseStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class HttpClientShould {

    private Gson gson;
    private URI uri;

    @BeforeEach
    void setUp() throws URISyntaxException {
        gson = new Gson();
        uri = new URI("https://farnamjava.free.beeceptor.com/players");
    }

    @Test
    void call_a_web_server_and_request_information() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        List<Player> players = gson.fromJson(getResponse(request).body(), Players.class).getPlayers();

        assertThat(players).containsExactlyInAnyOrder(new Player("Ali Karimi", 250), new Player("Ali Daei", 200));
    }


    @Test
    void send_a_request_to_add_a_player() throws IOException, InterruptedException {
        String player = gson.toJson(new Player("Karim Bagheri", 150));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(player))
                .build();

        ResponseStatus responseStatus = gson.fromJson(getResponse(request).body(), ResponseStatus.class);

        assertThat(responseStatus.getStatus()).isEqualTo("200");
    }

    @Test
    void do_async_calls() throws ExecutionException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        System.out.println(getAsyncResponse(postRequest).get());
        System.out.println(getAsyncResponse(getRequest).get());
    }

    private CompletableFuture<HttpResponse<String>> getAsyncResponse(HttpRequest request) {
        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
    }
}
