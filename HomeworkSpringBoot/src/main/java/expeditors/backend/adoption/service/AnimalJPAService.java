package expeditors.backend.adoption.service;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.dao.repository.AnimalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AnimalJPAService {
    private final AnimalRepository animalRepository;

    public AnimalJPAService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public boolean deleteAnimal(int id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateAnimal(Animal animal) {
        Animal oldAnimal = animalRepository.findById(animal.getId()).orElse(null);
        if (oldAnimal != null) {
            animalRepository.save(animal);
            return true;
        }
        return false;
    }

    public Animal getAnimal(int id) {
        return animalRepository.findById(id).orElse(null);
    }

    public List<Animal> getAnimals() {
        return animalRepository.findAll();
    }
}
