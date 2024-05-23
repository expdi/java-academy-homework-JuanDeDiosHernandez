package expeditors.backend.adoption.service;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.dao.AnimalDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalDAO animalDAO;

    public Animal createAnimal(Animal animal) {
        Animal insertedAnimal = animalDAO.insert(animal);
        return insertedAnimal;
    }

    public boolean deleteAnimal(int id) {
        return animalDAO.delete(id);
    }

    public boolean updateAnimal(Animal animal) {
        return animalDAO.update(animal);
    }

    public Animal getAnimal(int id) {
        return animalDAO.findById(id);
    }

    public List<Animal> getAnimals() {
        return animalDAO.findAll();
    }
}
