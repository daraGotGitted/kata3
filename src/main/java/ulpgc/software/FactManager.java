package ulpgc.software;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FactManager {
    private static final String API_URL = "https://uselessfacts.jsph.pl/api/v2/facts/";

    public static Fact getRandomFact() throws IOException, InterruptedException {
        HttpClient c = HttpClient.newHttpClient();
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/random", API_URL)))
                .build();

        HttpResponse<String> res = c.send(r, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        return gson.fromJson(res.body(), Fact.class);
    }

    public static Fact getTodayFact() throws IOException, InterruptedException {
        HttpClient c = HttpClient.newHttpClient();
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/today", API_URL)))
                .build();

        HttpResponse<String> res = c.send(r, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        return gson.fromJson(res.body(), Fact.class);
    }
}
