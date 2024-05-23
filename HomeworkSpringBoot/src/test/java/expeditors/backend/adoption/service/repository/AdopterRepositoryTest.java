package expeditors.backend.adoption.service.repository;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.dao.repository.AdopterRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdopterRepositoryTest {

    @Autowired
    private AdopterRepository adopterRepository;

    @Test
    public void testGetAdopters() {
        List<Adopter> result =  adopterRepository.findAll();
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetAdopterById() {
        Adopter result = adopterRepository.findById(1).orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testCreateAdopter() {
        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
        adopterRepository.save(adopter);

        assertTrue(adopter.getId() > 0);
    }

    @Test
    public void testUpdateAdopter() {
        Adopter adopter = adopterRepository.findById(1).orElse(null);
        assertNotNull(adopter);
        String newPhone = "211-1111";
        adopter.setPhone(newPhone);

        adopterRepository.save(adopter);

        Adopter updatedAdopter = adopterRepository.findById(1).orElse(null);
        assertNotNull(updatedAdopter);
        assertEquals(newPhone, updatedAdopter.getPhone());
    }

    @Test
    public void testDeleteAdopter() {
        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
        adopterRepository.save(adopter);

        assertTrue(adopter.getId() > 0);

        adopterRepository.delete(adopter);

        Adopter deletedAdopter = adopterRepository.findById(adopter.getId()).orElse(null);
        assertNull(deletedAdopter);
    }
}
