package ulpgc.software;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FactManager {
    private static final String API_BASE_URL = "https://uselessfacts.jsph.pl/api/v2/facts/";

    public enum enumType {
        TODAY,
        RANDOM
    }

    public static Fact getFact(enumType type) throws IOException, InterruptedException {
        String API_URI = type == enumType.TODAY ? String.format("%s/today", API_BASE_URL) : String.format("%s/random", API_BASE_URL);
        HttpClient c = HttpClient.newHttpClient();
        HttpRequest r = HttpRequest.newBuilder()
                .uri(URI.create(API_URI))
                .build();

        HttpResponse<String> res = c.send(r, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        return gson.fromJson(res.body(), Fact.class);
    }

    public static String getFacts(enumType type, int number) throws IOException, InterruptedException {
        JsonArray fArray = new JsonArray();
        for(int i = 0; i<number; i++) {
            JsonObject obj = new JsonObject();
            obj.addProperty("id", i+1);
            obj.addProperty("fact", getFact(type).text());
            fArray.add(obj);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(fArray);
    }
}
