package expeditors.backend.adoption.service.controller.unit;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.controller.AnimalJPAServiceController;
import expeditors.backend.adoption.service.AnimalJPAService;
import expeditors.backend.adoption.utils.UriCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class AnimalControllerUnitTest {

    @Mock
    private UriCreator uriCreator;

    @Mock
    private AnimalJPAService animalJPAService;

    @InjectMocks
    private AnimalJPAServiceController animalController;

    List<Animal> animalList;

    @BeforeEach
    public void beforeEach() {
        animalList = new ArrayList<>(List.of(
                Animal.builder().id(1).typePet(TypePet.CAT).petName("Fuchsia").petBreed("Aegean").build(),
                Animal.builder().id(2).typePet(TypePet.CAT).petName("Alsha").petBreed("Bengal").build(),
                Animal.builder().id(3).typePet(TypePet.CAT).petName("Kobe").petBreed("Bombay").build()
        ));
    }

    @Test
    public void testGetAAnimals() {
        Mockito.when(animalJPAService.getAnimals()).thenReturn(animalList);

        List<Animal> result = animalController.getAll();

        assertFalse(result.isEmpty());

        Mockito.verify(animalJPAService).getAnimals();
    }

    @Test
    public void testGetAnimalById() {
        int idAnimal = animalList.getFirst().getId();
        Mockito.when(animalJPAService.getAnimal(idAnimal)).thenReturn(animalList.getFirst());

        ResponseEntity<?> result = animalController.getAnimal(idAnimal);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(animalJPAService).getAnimal(idAnimal);
    }

    @Test
    public void testGetAnimalNonExistentById() {
        int idAnimal = 9999;
        Mockito.when(animalJPAService.getAnimal(idAnimal)).thenReturn(null);

        ResponseEntity<?> result = animalController.getAnimal(idAnimal);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(animalJPAService).getAnimal(idAnimal);
    }

    @Test
    public void testCreateAnimal() throws URISyntaxException {
        Animal animal = animalList.getFirst();
        Mockito.when(animalJPAService.createAnimal(animal)).thenReturn(animal);

        String uriStr = STR."http://localhost:8080/animalJPA\{animal.getId()}";
        URI uri = new URI(uriStr);
        Mockito.when(uriCreator.getURI(animal.getId())).thenReturn(uri);

        ResponseEntity<?> result = animalController.addAnimal(animal);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").getFirst();
        assertEquals(uriStr, locHdr);

        Mockito.verify(animalJPAService).createAnimal(animal);
        Mockito.verify(uriCreator).getURI(animal.getId());
    }

    @Test
    public void testUpdateAnimal() {
        Animal animal = animalList.getFirst();
        Mockito.when(animalJPAService.updateAnimal(animal)).thenReturn(true);

        ResponseEntity<?> result = animalController.updateAnimal(animal);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(animalJPAService).updateAnimal(animal);
    }

    @Test
    public void testUpdateNonExistentAnimal() {
        Animal animal = animalList.getFirst();
        Mockito.when(animalJPAService.updateAnimal(animal)).thenReturn(false);

        ResponseEntity<?> result = animalController.updateAnimal(animal);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(animalJPAService).updateAnimal(animal);
    }

    @Test
    public void testDeleteAnimal() {
        int idAnimal = animalList.getFirst().getId();
        Mockito.when(animalJPAService.deleteAnimal(idAnimal)).thenReturn(true);

        ResponseEntity<?> result = animalController.deleteAnimal(idAnimal);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(animalJPAService).deleteAnimal(idAnimal);
    }

    @Test
    public void testDeleteNonExistentAnimal() {
        Mockito.when(animalJPAService.deleteAnimal(9999)).thenReturn(false);

        ResponseEntity<?> result = animalController.deleteAnimal(9999);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(animalJPAService).deleteAnimal(9999);
    }
}
