package expeditors.backend.adoption.dao;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterPets;

import java.util.List;

public interface AdopterDAO {
    Adopter insert(Adopter adopter);

    boolean update(Adopter adopter);

    boolean delete(int id);

    Adopter findById(int id);

    List<Adopter> findAll();

    Adopter findByName(String name);

    List<Adopter> findAllSortedByName();

    Adopter insertWithPet(AdopterPets adopterPets);
}
