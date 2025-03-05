import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/%s";
        HttpClient client = HttpClient.newHttpClient();

        Random rand = new Random();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl.formatted(rand.nextInt(152)))).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
