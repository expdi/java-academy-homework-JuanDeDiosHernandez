package expeditors.backend.adoption.controller;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.service.AdopterJPAService;
import expeditors.backend.adoption.utils.UriCreator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/adopterJPA")
public class AdopterJPAServiceController {

    @Autowired
    private AdopterJPAService adopterJPAService;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    public List<Adopter> getAll() {
        return adopterJPAService.getAdopters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdopter(@PathVariable("id") int id) {
        Adopter adopter = adopterJPAService.getAdopter(id);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + id);
        }
        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> addAdopter(@RequestBody @Valid Adopter adopter) {
        Adopter newAdopter = adopterJPAService.createAdopter(adopter);

        URI newResource = uriCreator.getURI(newAdopter.getId());

        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdopter(@PathVariable("id") int id) {
        boolean result = adopterJPAService.deleteAdopter(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + id);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateAdopter(@RequestBody Adopter adopter, Errors errors) {
        boolean result = adopterJPAService.updateAdopter(adopter);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Id: " + adopter.getId());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/GetByName/{name}")
    public ResponseEntity<?> getByName(@PathVariable("name") String name) {
        Adopter adopter = adopterJPAService.getAdopterByName(name);
        if (adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adopter Not Found - Name: " + name);
        }
        return ResponseEntity.ok(adopter);
    }

    @GetMapping("/GetAllSortedByName")
    public List<Adopter> getAllSortedByName() {
        return adopterJPAService.getAdoptersSortedByName();
    }
}
