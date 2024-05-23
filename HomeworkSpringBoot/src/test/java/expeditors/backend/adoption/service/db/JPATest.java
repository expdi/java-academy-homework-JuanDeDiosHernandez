package expeditors.backend.adoption.service.db;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JPATest {
    private EntityManagerFactory emf;

    @BeforeEach
    public void beforeEach() {
        String pw = System.getenv("DB_PASSWORD");

        var props = Map.of(
                "jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5434/adopterdb",
                "jakarta.persistence.jdbc.user", "larku",
                "jakarta.persistence.jdbc.password", pw,
                "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",

                "jakarta.persistence.spi.PersistenceProvider", "org.hibernate.jpa.HibernatePersistenceProvider"
        );

        emf = Persistence.createEntityManagerFactory("LarkUPU_SE", props);
    }

    @Test
    public void testGetAnimals() {
        try(EntityManager em = emf.createEntityManager();) {
            TypedQuery<Animal> query = em.createQuery("select sc from Animal sc", Animal.class);
            List<Animal> result = query.getResultList();
            assertFalse(result.isEmpty());
        }
    }

    @Test
    public void testGetAnimalById() {
        try(EntityManager em = emf.createEntityManager();) {
            TypedQuery<Animal> query = em.createQuery("select a from Animal a where a.id = 1", Animal.class);
            Animal result = query.getSingleResult();
            assertNotNull(result);
        }
    }

    @Test
    public void testCreateAnimal() {
        try(EntityManager em = emf.createEntityManager();) {
            TypedQuery<Animal> query = em.createQuery("select a from Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            int maxId = animals.stream().mapToInt(Animal::getId).max().orElse(0);

            em.getTransaction().begin();
            TypedQuery<Adopter> queryAdopter = em.createQuery("select a from Adopter a join fetch a.animal an where a.id = 2", Adopter.class);
            Adopter adopter = queryAdopter.getSingleResult();
            Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
            em.persist(animal);
            em.getTransaction().commit();

//            assertEquals(maxId + 1, animal.getId());
            assertTrue(animal.getId() > 0);
        }
    }

    @Test
    public void testUpdateAnimal() {
        try(EntityManager em = emf.createEntityManager();) {
            Animal animal = em.find(Animal.class, 1);
            String newName = "Aether";
            animal.setPetName(newName);

            em.getTransaction().begin();
            em.persist(animal);
            em.getTransaction().commit();

            Animal updatedAnimal = em.find(Animal.class, 1);
            assertEquals(newName, updatedAnimal.getPetName());
        }
    }

    @Test
    public void testDeleteAnimal() {
        try(EntityManager em = emf.createEntityManager();) {
            em.getTransaction().begin();
            TypedQuery<Adopter> queryAdopter = em.createQuery("select a from Adopter a join fetch a.animal an where a.id = 1", Adopter.class);
            Adopter adopter = queryAdopter.getSingleResult();
            Animal animal = Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").adopter(adopter).build();
            em.persist(animal);
            em.getTransaction().commit();

            assertTrue(animal.getId() > 0);

            em.getTransaction().begin();
            em.remove(animal);
            em.getTransaction().commit();

            Animal deletedAnimal = em.find(Animal.class, animal.getId());
            assertNull(deletedAnimal);
        }
    }

    @Test
    public void testGetAdopters() {
        try(EntityManager em = emf.createEntityManager();) {
            TypedQuery<Adopter> query = em.createQuery("select a from Adopter a join fetch a.animal an", Adopter.class);
            List<Adopter> result = query.getResultList();
            assertFalse(result.isEmpty());
        }
    }

    @Test
    public void testGetAdopterById() {
        try(EntityManager em = emf.createEntityManager();) {
            TypedQuery<Adopter> query = em.createQuery("select a from Adopter a join fetch a.animal an where a.id = 1", Adopter.class);
            Adopter result = query.getSingleResult();
            assertNotNull(result);
        }
    }

    @Test
    public void testCreateAdopter() {
        try(EntityManager em = emf.createEntityManager();) {
//            TypedQuery<Adopter> query = em.createQuery("select a from Adopter a", Adopter.class);
//            List<Adopter> adopters = query.getResultList();
//            int maxId = adopters.stream().mapToInt(Adopter::getId).max().orElse(0);

            em.getTransaction().begin();
            Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                    new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
            adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
            em.persist(adopter);
            em.getTransaction().commit();

            assertTrue(adopter.getId() > 0);
//            assertEquals(maxId + 1, adopter.getId());
        }
    }

    @Test
    public void testUpdateAdopter() {
        try(EntityManager em = emf.createEntityManager();) {
            Adopter adopter = em.find(Adopter.class, 1);
            String newPhone = "211-1111";
            adopter.setPhone(newPhone);

            em.getTransaction().begin();
            em.persist(adopter);
            em.getTransaction().commit();

            Adopter updatedAdopter = em.find(Adopter.class, 1);
            assertEquals(newPhone, updatedAdopter.getPhone());
        }
    }

    @Test
    public void testDeleteAdopter() {
        try(EntityManager em = emf.createEntityManager();) {
            em.getTransaction().begin();
            Adopter adopter = new Adopter("Juan", "111-1111", LocalDate.now(),
                    new ArrayList<Animal>(List.of(Animal.builder().typePet(TypePet.TURTLE).petName("Donatello").petBreed("Red Eared").build())));
            adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
            em.persist(adopter);
            em.getTransaction().commit();

            assertTrue(adopter.getId() > 0);

            em.getTransaction().begin();
            em.remove(adopter);
            em.getTransaction().commit();

            Adopter deletedAdopter = em.find(Adopter.class, adopter.getId());
            assertNull(deletedAdopter);
        }
    }
}
