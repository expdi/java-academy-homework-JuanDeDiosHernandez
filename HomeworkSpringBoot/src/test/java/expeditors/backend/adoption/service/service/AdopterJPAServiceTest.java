package expeditors.backend.adoption.service.service;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterSmallDTO;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.service.AdopterJPAService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdopterJPAServiceTest {

    @Autowired
    private AdopterJPAService adopterJPAService;

    @Test
    public void testGetAdopters() {
        List<Adopter> adopters = adopterJPAService.getAdopters();
        assertFalse(adopters.isEmpty());
    }

    @Test
    public void testGetAdopterById() {
        Adopter result = adopterJPAService.getAdopter(1);
        assertNotNull(result);
    }

    @Test
    public void testCreateAdopter() {
        List<Adopter> adopters = adopterJPAService.getAdopters();
        int maxId = adopters.stream().mapToInt(Adopter::getId).max().orElse(0);

        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        adopterJPAService.createAdopter(adopter);

        assertEquals(maxId + 1, adopter.getId());
    }

    @Test
    public void testUpdateAdopter() {
        AdopterSmallDTO adopterSmallDTO = adopterJPAService.getAdopterSmallDTO(1);
        assertNotNull(adopterSmallDTO);
        Adopter adopter = Adopter.builder().id(adopterSmallDTO.id())
                .name(adopterSmallDTO.name())
                .phone(adopterSmallDTO.phone())
                .dateAdoption(adopterSmallDTO.dateAdoption())
                .build();
        String newPhone = "211-1111";
        adopter.setPhone(newPhone);

        adopterJPAService.updateAdopter(adopter);

        Adopter updatedAdopter = adopterJPAService.getAdopter(1);
        assertEquals(newPhone, updatedAdopter.getPhone());
    }

    @Test
    public void testDeleteAdopter() {
        Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                new ArrayList<>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
        adopterJPAService.createAdopter(adopter);

        assertTrue(adopter.getId() > 0);

        adopterJPAService.deleteAdopter(adopter.getId());

        Adopter deletedAdopter = adopterJPAService.getAdopter(adopter.getId());
        assertNull(deletedAdopter);
    }
}
