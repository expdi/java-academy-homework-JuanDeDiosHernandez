package expeditors.backend.adoption.service.controller.expensive;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdopterControllerClientTest {

    @LocalServerPort
    private int port;
    private RestClient restClient;
    private String baseUrl;
    private String rootUrl;
    private String uriIdAdopter;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
        rootUrl = "/adopterJPA";
        uriIdAdopter = rootUrl + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void testGetAdopters() {
        ParameterizedTypeReference<List<Adopter>> ptr = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Adopter>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAdopterById() {
        ResponseEntity<Adopter> response = restClient.get()
                .uri(uriIdAdopter, 1)
                .retrieve()
                .toEntity(Adopter.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Adopter adopter = response.getBody();
        assertNotNull(adopter);
        assertEquals(1, adopter.getId());
    }

    @Test
    public void testGetAdopterNonExistentById() {
        ResponseEntity<?> response = restClient.get()
                .uri(uriIdAdopter, 9999)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateAdopter() throws URISyntaxException {
        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(adopter)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").getFirst();
        assertNotNull(locHdr);
    }

    @Test
    public void testUpdateAdopter() {
        Adopter adopter = restClient.get()
                .uri(uriIdAdopter, 1)
                .retrieve()
                .body(Adopter.class);

        assertNotNull(adopter);
        adopter.setName("Juan");

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(adopter)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.OK, result.getStatusCode());

        adopter = restClient.get()
                .uri(uriIdAdopter, 1)
                .retrieve()
                .body(Adopter.class);

        assertNotNull(adopter);
        assertEquals("Juan", adopter.getName());
    }

    @Test
    public void testUpdateNonExistentAdopter() {
        Adopter adopter = restClient.get()
                .uri(uriIdAdopter, 1)
                .retrieve()
                .body(Adopter.class);

        assertNotNull(adopter);
        adopter.setName("Juan");
        adopter.setId(9999);

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(adopter)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {})
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteAdopter() {
        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(adopter)
                .retrieve()
                .toBodilessEntity();

        String locHdr = result.getHeaders().get("Location").getFirst();

        ResponseEntity<Adopter> newResult = restClient.get()
                .uri(locHdr)
                .retrieve()
                .toEntity(Adopter.class);

        assertEquals(newResult.getStatusCode(), HttpStatus.OK);
        Adopter newAdopter = newResult.getBody();
        assertNotNull(newAdopter);

        result = restClient.delete()
                .uri(locHdr)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteNonExistentAdopter() {
        ResponseEntity<?> result = restClient.delete()
                .uri(uriIdAdopter, 9999)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
