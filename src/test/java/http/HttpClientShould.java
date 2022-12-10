package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;
import player.Players;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

public class HttpClientShould {

    public static final String GET_URI = "https://farnamjava.free.beeceptor.com/players";
    public static final String POST_URI = "https://farnamjava.free.beeceptor.com/players/add";

    private Http http;

    @BeforeEach
    void setUp() {
        http = new Http();
    }

    @Test
    void call_a_web_server_and_request_information() {
        HttpRequest request = http.requestGet(GET_URI).orElseThrow();
        HttpResponse<String> response = http.response(request).orElseThrow();
        List<Player> players = http.getFromJason(response, Players.class).getPlayers();

        assertThat(players).containsExactlyInAnyOrder(new Player("Ali Karimi", 250), new Player("Ali Daei", 200));
    }


    @Test
    void send_a_request_to_add_a_player() {
        HttpRequest request = http.requestPost(POST_URI, new Player("Karim Bagheri", 150)).orElseThrow();
        HttpResponse<String> response = http.response(request).orElseThrow();
        String status = http.getFromJason(response, ResponseStatus.class).getStatus();

        assertThat(status).isEqualTo("200");
    }

    @Test
    void do_async_calls() throws ExecutionException, InterruptedException {
        HttpRequest getRequest = http.requestGet(GET_URI).orElseThrow();
        HttpRequest postRequest = http.requestPost(POST_URI).orElseThrow();

        HttpResponse<String> getResponse = http.responseAsync(getRequest).get();
        HttpResponse<String> postResponse = http.responseAsync(postRequest).get();

        System.out.println(postResponse);
        System.out.println(getResponse);
    }
}
