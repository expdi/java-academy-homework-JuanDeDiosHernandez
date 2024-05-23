package expeditors.backend.adoption.dao.repository;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterSmallDTO;
import expeditors.backend.adoption.classes.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Integer> {

    @Query("select a from Adopter a left join fetch a.animal an")
    List<Adopter> findAllWithAnimal();

    @Query("select a from Animal a join fetch a.adopter ad where UPPER(ad.name) = UPPER(:name)")
    Adopter findByName(String name);

    @Query("select a from Animal a join a.adopter ad order by a.petName")
    List<Adopter> findAllWithAnimalSortedByName();

    @Query("select new expeditors.backend.adoption.classes.AdopterSmallDTO(a.id, a.name, a.phone, a.dateAdoption) from Adopter a where a.id = :id")
    AdopterSmallDTO findSmallDTOById(int id);
}
