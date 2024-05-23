package expeditors.backend.adoption.controller;

import expeditors.backend.adoption.service.AdopterService;
import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterPets;
import expeditors.backend.adoption.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/adopter")
public class AdopterServiceController {

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    public List<Adopter> getAll() {
        return adopterService.getAdopters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdopter(@PathVariable("id") int id) {
        Adopter adopter = adopterService.getAdopter(id);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + id);
        }
        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> addAdopter(@RequestBody Adopter adopter) {
        Adopter newAdopter = adopterService.createAdopter(adopter);

        URI newResource = uriCreator.getURI(newAdopter.getId());

        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdopter(@PathVariable("id") int id) {
        boolean result = adopterService.deleteAdopter(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + id);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateAdopter(@RequestBody Adopter adopter) {
        boolean result = adopterService.updateAdopter(adopter);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + adopter.getId());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/GetByName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        Adopter adopter = adopterService.getAdopterByName(name);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Name: " + name);
        }
        return ResponseEntity.ok(adopter);
    }

    @GetMapping("/GetAllSortedByName")
    public List<Adopter> getAllSortedByName() {
        return adopterService.getAdoptersSortedByName();
    }

    @PostMapping("/AddAdopterWithPet")
    public ResponseEntity<?> addAdopterWithPet(@RequestBody AdopterPets adopterPets) {
        Adopter newAdopter = adopterService.createAdopterWithPet(adopterPets);

        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(adopterPets.getAdopter().getId())
                .toUri();

        return ResponseEntity.created(newResource).build();
    }
}
