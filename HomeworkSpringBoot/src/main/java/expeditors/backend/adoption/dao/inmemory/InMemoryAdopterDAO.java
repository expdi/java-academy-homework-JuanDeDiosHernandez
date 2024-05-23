package expeditors.backend.adoption.dao.inmemory;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterPets;
import expeditors.backend.adoption.dao.AdopterDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("dev")
public class InMemoryAdopterDAO implements AdopterDAO {

    private Map<Integer, Adopter> adopters = new HashMap<>();
    private int nextId = 1;

    @Override
    public Adopter insert(Adopter adopter) {
        int id = nextId++;
        adopter.setId(id);
        adopters.put(adopter.getId(), adopter);
        return adopter;
    }

    @Override
    public boolean update(Adopter entity) {
        return adopters.replace(entity.getId(), entity) != null;
    }

    @Override
    public boolean delete(int id) {
        return adopters.remove(id) != null;
    }

    @Override
    public Adopter findById(int id) {
        return adopters.get(id);
    }

    @Override
    public List<Adopter> findAll() {
        return new ArrayList<>(adopters.values());
    }

    @Override
    public Adopter findByName(String name) {
        return adopters.entrySet().stream()
                .filter(adopterFilter -> Objects.equals(adopterFilter.getValue().getName(), name))
                .findFirst()
                .map(Map.Entry::getValue).orElse(null);
    }

    @Override
    public List<Adopter> findAllSortedByName() {
        var adopters = findAll();
        adopters.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        return adopters;
    }

    @Override
    public Adopter insertWithPet(AdopterPets adopterPets) {
        return null;
    }
}
