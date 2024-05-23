package expeditors.backend.adoption.dao.inmemory;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.dao.AnimalDAO;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryAnimalDAO implements AnimalDAO {

    private Map<Integer, Animal> animals = new HashMap<>();
    private int nextId = 1;

    @Override
    public Animal insert(Animal adopter) {
        int id = nextId++;
        adopter.setId(id);
        animals.put(adopter.getId(), adopter);
        return adopter;
    }

    @Override
    public boolean update(Animal entity) {
        return animals.replace(entity.getId(), entity) != null;
    }

    @Override
    public boolean delete(int id) {
        return animals.remove(id) != null;
    }

    @Override
    public Animal findById(int id) {
        return animals.get(id);
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(animals.values());
    }
}
