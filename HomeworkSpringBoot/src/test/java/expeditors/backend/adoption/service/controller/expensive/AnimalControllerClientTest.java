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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalControllerClientTest {

    @LocalServerPort
    private int port;
    private RestClient restClient;
    private String baseUrl;
    private String rootUrl;
    private String rootUrlAdopter;
    private String uriIdAnimal;
    private String uriIdAdopter;

    @PostConstruct
    public void init() {
        baseUrl = "http://localhost:" + port;
        rootUrl = "/animalJPA";
        rootUrlAdopter = "/adopterJPA";
        uriIdAnimal = rootUrl + "/{id}";
        uriIdAdopter = rootUrlAdopter + "/{id}";

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Test
    public void testGetAAnimals() {
        ParameterizedTypeReference<List<Animal>> ptr = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Animal>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAnimalById() {
        ResponseEntity<Animal> response = restClient.get()
                .uri(uriIdAnimal, 1)
                .retrieve()
                .toEntity(Animal.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Animal animal = response.getBody();
        assertNotNull(animal);
        assertEquals(1, animal.getId());
    }

    @Test
    public void testGetAnimalNonExistentById() {
        ResponseEntity<?> response = restClient.get()
                .uri(uriIdAnimal, 9999)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateAnimal() {
        ResponseEntity<Adopter> response = restClient.get()
                .uri(uriIdAdopter, 1)
                .retrieve()
                .toEntity(Adopter.class);
        Adopter adopter = response.getBody();
        adopter.setAnimal(null);
        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(animal)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").getFirst();
        assertNotNull(locHdr);
    }

    @Test
    public void testUpdateAnimal() {
        Animal animal = restClient.get()
                .uri(uriIdAnimal, 1)
                .retrieve()
                .body(Animal.class);

        assertNotNull(animal);
        animal.setPetName("Leo");

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(animal)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.OK, result.getStatusCode());

        animal = restClient.get()
                .uri(uriIdAnimal, 1)
                .retrieve()
                .body(Animal.class);

        assertNotNull(animal);
        assertEquals("Leo", animal.getPetName());
    }

    @Test
    public void testUpdateNonExistentAnimal() {
        Animal animal = restClient.get()
                .uri(uriIdAnimal, 1)
                .retrieve()
                .body(Animal.class);

        assertNotNull(animal);
        animal.setPetName("Leo");
        animal.setId(9999);

        ResponseEntity<?> result = restClient.put()
                .uri(rootUrl)
                .body(animal)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {})
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteAnimal() {
        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build();
        ResponseEntity<Void> result = restClient.post()
                .uri(rootUrl)
                .body(animal)
                .retrieve()
                .toBodilessEntity();

        String locHdr = result.getHeaders().get("Location").getFirst();

        ResponseEntity<Animal> newResult = restClient.get()
                .uri(locHdr)
                .retrieve()
                .toEntity(Animal.class);

        assertEquals(newResult.getStatusCode(), HttpStatus.OK);
        Animal newAnimal = newResult.getBody();
        assertNotNull(newAnimal);

        result = restClient.delete()
                .uri(locHdr)
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteNonExistentAnimal() {
        ResponseEntity<?> result = restClient.delete()
                .uri(uriIdAnimal, 9999)
                .retrieve()
                .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, resp) -> {
                })
                .toBodilessEntity();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
