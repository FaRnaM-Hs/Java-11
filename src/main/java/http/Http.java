package http;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Http {

    private final Gson gson;

    public Http() {
        this.gson = new Gson();
    }

    public Optional<HttpRequest> requestGet(String uri) {
        try {
             return Optional.ofNullable(HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build());
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    public <T> Optional<HttpRequest> requestPost(String uri) {
        try {
            return Optional.ofNullable(HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build());
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    public <T> Optional<HttpRequest> requestPost(String uri, T t) {
        try {
            return Optional.ofNullable(HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(t)))
                    .build());
        } catch (URISyntaxException e) {
            return Optional.empty();
        }
    }

    public Optional<HttpResponse<String>> response(HttpRequest request) {
        try {
            return Optional.ofNullable(HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()));
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }

    public CompletableFuture<HttpResponse<String>> responseAsync(HttpRequest request) {
        return HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public <T> T getFromJason(HttpResponse<String> response, Class<T> classType) {
        return gson.fromJson(response.body(), classType);
    }


}
