package http;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import player.Player;
import player.Players;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class HttpClientShould {

    @Test
    void call_a_web_server_and_request_information() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://javafarnam.free.beeceptor.com/players")).build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        Gson gson = new Gson();
        List<Player> players = gson.fromJson(response.body(), Players.class).getPlayers();

        List<Player> topScorers = players.stream()
                .filter(player -> player.getGoal() > 225)
                .collect(Collectors.toUnmodifiableList());
        assertThat(topScorers).containsExactly(new Player("Ali Karimi", 250));
    }
}
