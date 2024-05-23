package expeditors.backend.adoption.dao;

import expeditors.backend.adoption.classes.Animal;

import java.util.List;

public interface AnimalDAO {
    Animal insert(Animal entity);

    boolean update(Animal entity);

    boolean delete(int id);

    Animal findById(int id);

    List<Animal> findAll();
}
