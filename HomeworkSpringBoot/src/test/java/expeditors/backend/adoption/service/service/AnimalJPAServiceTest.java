package expeditors.backend.adoption.service.service;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.service.AdopterJPAService;
import expeditors.backend.adoption.service.AnimalJPAService;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimalJPAServiceTest {

    @Autowired
    private AdopterJPAService adopterJPAService;

    @Autowired
    private AnimalJPAService animalJPAService;

    @Test
    public void testGetAAnimals() {
        List<Animal> animals = animalJPAService.getAnimals();
        assertFalse(animals.isEmpty());
    }

    @Test
    public void testGetAnimalById() {
        Animal result = animalJPAService.getAnimal(1);
        assertNotNull(result);
    }

    @Test
    public void testCreateAnimal() {
        List<Animal> adopters = animalJPAService.getAnimals();
        int maxId = adopters.stream().mapToInt(Animal::getId).max().orElse(0);

        Adopter adopter = adopterJPAService.getAdopter(2);
        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
        animalJPAService.createAnimal(animal);

        assertEquals(maxId + 1, animal.getId());
    }

    @Test
    public void testUpdateAnimal() {
        Animal animal = animalJPAService.getAnimal(1);
        assertNotNull(animal);
        String newName = "Leo";
        animal.setPetName(newName);

        animalJPAService.updateAnimal(animal);

        Animal updatedAdopter = animalJPAService.getAnimal(1);
        assertEquals(newName, updatedAdopter.getPetName());
    }

    @Test
    public void testDeleteAnimal() {
        Adopter adopter = adopterJPAService.getAdopter(1);
        Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
        animalJPAService.createAnimal(animal);

        assertTrue(animal.getId() > 0);

        animalJPAService.deleteAnimal(animal.getId());

        Animal deletedAdopter = animalJPAService.getAnimal(animal.getId());
        assertNull(deletedAdopter);
    }
}
