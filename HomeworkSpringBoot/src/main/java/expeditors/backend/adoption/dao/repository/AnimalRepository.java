package expeditors.backend.adoption.dao.repository;

import expeditors.backend.adoption.classes.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
}
