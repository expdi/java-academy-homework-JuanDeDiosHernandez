package expeditors.backend.adoption.service.controller.unit;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.controller.AdopterJPAServiceController;
import expeditors.backend.adoption.service.AdopterJPAService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AdopterControllerUnitTest {

    @Mock
    private UriCreator uriCreator;

    @Mock
    private AdopterJPAService adopterJPAService;

    @InjectMocks
    private AdopterJPAServiceController adopterController;

    List<Adopter> adoptersList;

    @BeforeEach
    public void beforeEach() {
        adoptersList = new ArrayList<>(List.of(
                Adopter.builder().id(1).name("Jennifer Quiteria").phone("111-1111").dateAdoption(LocalDate.parse("2024-05-14"))
                        .animal(new ArrayList<Animal>(List.of(Animal.builder().id(1).typePet(TypePet.CAT).petName("Fuchsia").petBreed("Aegean").build()))).build(),
                Adopter.builder().id(2).name("Amelia Amarilis").phone("111-1112").dateAdoption(LocalDate.parse("2024-05-13"))
                        .animal(new ArrayList<Animal>(List.of(Animal.builder().id(2).typePet(TypePet.CAT).petName("Alsha").petBreed("Bengal").build()))).build(),
                Adopter.builder().id(3).name("Arleth Salvador").phone("111-1113").dateAdoption(LocalDate.parse("2024-05-12"))
                        .animal(new ArrayList<Animal>(List.of(Animal.builder().id(3).typePet(TypePet.CAT).petName("Kobe").petBreed("Bombay").build()))).build()
        ));
    }

    @Test
    public void testGetAdopters() {
        Mockito.when(adopterJPAService.getAdopters()).thenReturn(adoptersList);

        List<Adopter> result = adopterController.getAll();

        assertFalse(result.isEmpty());

        Mockito.verify(adopterJPAService).getAdopters();
    }

    @Test
    public void testGetAdopterById() {
        int idAdopter = adoptersList.getFirst().getId();
        Mockito.when(adopterJPAService.getAdopter(idAdopter)).thenReturn(adoptersList.getFirst());

        ResponseEntity<?> result = adopterController.getAdopter(idAdopter);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(adopterJPAService).getAdopter(idAdopter);
    }

    @Test
    public void testGetAdopterNonExistentById() {
        int idAdopter = 9999;
        Mockito.when(adopterJPAService.getAdopter(idAdopter)).thenReturn(null);

        ResponseEntity<?> result = adopterController.getAdopter(idAdopter);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(adopterJPAService).getAdopter(idAdopter);
    }

    @Test
    public void testCreateAdopter() throws URISyntaxException {
        Adopter adopter = adoptersList.getFirst();
        Mockito.when(adopterJPAService.createAdopter(adopter)).thenReturn(adopter);

        String uriStr = STR."http://localhost:8080/adopterJPA\{adopter.getId()}";
        URI uri = new URI(uriStr);
        Mockito.when(uriCreator.getURI(adopter.getId())).thenReturn(uri);

        ResponseEntity<?> result = adopterController.addAdopter(adopter);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        String locHdr = result.getHeaders().get("Location").getFirst();
        assertEquals(uriStr, locHdr);

        Mockito.verify(adopterJPAService).createAdopter(adopter);
        Mockito.verify(uriCreator).getURI(adopter.getId());
    }

    @Test
    public void testUpdateAdopter() {
        Adopter adopter = adoptersList.getFirst();
        Mockito.when(adopterJPAService.updateAdopter(adopter)).thenReturn(true);

        ResponseEntity<?> result = adopterController.updateAdopter(adopter);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(adopterJPAService).updateAdopter(adopter);
    }

    @Test
    public void testUpdateNonExistentAdopter() {
        Adopter adopter = adoptersList.getFirst();
        Mockito.when(adopterJPAService.updateAdopter(adopter)).thenReturn(false);

        ResponseEntity<?> result = adopterController.updateAdopter(adopter);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(adopterJPAService).updateAdopter(adopter);
    }

    @Test
    public void testDeleteAdopter() {
        int idAdopter = adoptersList.getFirst().getId();
        Mockito.when(adopterJPAService.deleteAdopter(idAdopter)).thenReturn(true);

        ResponseEntity<?> result = adopterController.deleteAdopter(idAdopter);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        Mockito.verify(adopterJPAService).deleteAdopter(idAdopter);
    }

    @Test
    public void testDeleteNonExistentAdopter() {
        Mockito.when(adopterJPAService.deleteAdopter(9999)).thenReturn(false);

        ResponseEntity<?> result = adopterController.deleteAdopter(9999);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        Mockito.verify(adopterJPAService).deleteAdopter(9999);
    }
}
