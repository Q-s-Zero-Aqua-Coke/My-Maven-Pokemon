import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

public class Main {
    private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon/%d";
    private static final String POKEMON_SPECIES_API_URL = "https://pokeapi.co/api/v2/pokemon-species/%d";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException, InterruptedException {
        int pokemonID = rand.nextInt(151) + 1; // 포켓몬 ID는 1부터 151까지입니다.

        // 포켓몬 정보 가져오기
        Pokemon pokemon = fetchPokemon(pokemonID);
        if (pokemon != null && pokemon.sprites != null && pokemon.sprites.front_default != null) {
            System.out.println(pokemon.sprites.front_default);
        } else {
            System.out.println("포켓몬 이미지를 찾을 수 없습니다. (ID: " + pokemonID + ")");
            return;
        }

        // 포켓몬 종 정보 가져오기
        PokemonSpecies pokemonSpecies = fetchPokemonSpecies(pokemonID);
        if (pokemonSpecies != null) {
            String koreanName = pokemonSpecies.names
                    .stream()
                    .filter(el -> el.language.name.equals("ko"))
                    .map(el -> el.name)
                    .findFirst()
                    .orElse("이름을 찾을 수 없습니다."); // 한국어 이름을 찾지 못했을 경우 메시지 출력
            System.out.println(koreanName);
        } else {
            System.out.println("포켓몬 종 정보를 찾을 수 없습니다. (ID: " + pokemonID + ")");
        }
    }

    // 포켓몬 정보를 가져오는 메소드
    private static Pokemon fetchPokemon(int pokemonID) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format(POKEMON_API_URL, pokemonID))).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), Pokemon.class);
        }
        return null;
    }

    // 포켓몬 종 정보를 가져오는 메소드
    private static PokemonSpecies fetchPokemonSpecies(int pokemonID) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(String.format(POKEMON_SPECIES_API_URL, pokemonID))).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), PokemonSpecies.class);
        }
        return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Pokemon {
        @JsonIgnoreProperties(ignoreUnknown = true) // 추가
        public static class Sprites {
            @JsonProperty("front_default")
            public String front_default;
        }
        public Sprites sprites;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class PokemonSpecies {
        @JsonIgnoreProperties(ignoreUnknown = true) // 추
        public static class Name {
            public String name;
            @JsonIgnoreProperties(ignoreUnknown = true) //
            public static class Language {
                public String name;
            }
            public Language language;
        }
        public List<Name> names;
    }
}