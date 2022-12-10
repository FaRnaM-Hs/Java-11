package http;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;

public class HttpClientShould {

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
    }

    @Test
    void call_a_web_server_and_request_information() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://javafarnam.free.beeceptor.com/players"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        List<Player> players = gson.fromJson(response.body(), Players.class).getPlayers();

        assertThat(players).containsExactlyInAnyOrder(new Player("Ali Karimi", 250), new Player("Ali Daei", 200));
    }

    @Test
    void send_a_request_to_add_a_player() throws URISyntaxException, IOException, InterruptedException {
        String player = gson.toJson(new Player("Karim Bagheri", 150));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://javafarnam.free.beeceptor.com/players/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(player))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        ResponseStatus responseStatus = gson.fromJson(response.body(), ResponseStatus.class);

        assertThat(responseStatus.getStatus()).isEqualTo("200");
    }
}
