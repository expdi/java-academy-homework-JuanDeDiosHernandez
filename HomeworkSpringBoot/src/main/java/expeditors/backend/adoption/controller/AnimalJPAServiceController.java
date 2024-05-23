package expeditors.backend.adoption.controller;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.service.AnimalJPAService;
import expeditors.backend.adoption.utils.UriCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/animalJPA")
public class AnimalJPAServiceController {

    @Autowired
    private AnimalJPAService animalJPAService;

    @Autowired
    private UriCreator uriCreator;

    @GetMapping
    public List<Animal> getAll() {
        return animalJPAService.getAnimals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimal(@PathVariable("id") int id) {
        Animal animal = animalJPAService.getAnimal(id);
        if (animal == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal Not Found - Id: " + id);
        }
        return ResponseEntity.ok(animal);
    }

    @PostMapping
    public ResponseEntity<?> addAnimal(@RequestBody Animal animal) {
        Animal newAnimal = animalJPAService.createAnimal(animal);

        URI newResource = uriCreator.getURI(newAnimal.getId());

        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("id") int id) {
        boolean result = animalJPAService.deleteAnimal(id);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal Not Found - Id: " + id);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateAnimal(@RequestBody Animal animal) {
        boolean result = animalJPAService.updateAnimal(animal);
        if (!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal Not Found - Id: " + animal.getId());
        }

        return ResponseEntity.ok().build();
    }
}
