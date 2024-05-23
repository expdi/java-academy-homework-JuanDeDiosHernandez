package expeditors.backend.adoption.jconfig;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.dao.AbstractDAO;
import expeditors.backend.adoption.dao.AdopterDAO;
import expeditors.backend.adoption.dao.AnimalDAO;
import expeditors.backend.adoption.dao.inmemory.InMemoryAdopterDAO;
import expeditors.backend.adoption.dao.inmemory.InMemoryAnimalDAO;
import expeditors.backend.adoption.dao.jpa.JPAAdopterDAO;
import expeditors.backend.adoption.dao.jpa.JPAAnimalDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

@Configuration
@ComponentScan({"expeditors.backend.adoption"})
public class SpringConfig extends AbstractDAO {
    List<Adopter> students = List.of(
            Adopter.builder().name("Juan")
                    .phone("111-1111")
                    .dateAdoption(LocalDate.now())
                    .build(),
            Adopter.builder().name("Erick")
                    .phone("222-2222")
                    .dateAdoption(LocalDate.now())
                    .build()
    );
    List<Animal> animals = List.of(
            Animal.builder()
                    .typePet(TypePet.TURTLE)
                    .petName("Donatello")
                    .petBreed("Red Eared")
                    .build(),
            Animal.builder()
                    .typePet(TypePet.DOG)
                    .build()
    );

    @Bean
    @Profile("dev")
    public AdopterDAO adopterDAO() {
        var dao = new InMemoryAdopterDAO();
        students.forEach(dao::insert);
        return dao;
    }

    @Bean("adopterDAO")
    @Profile("prod")
    public AdopterDAO jpaStudentDAO() {
        var dao = new JPAAdopterDAO();
//        students.forEach(dao::insert);
        return dao;
    }

    @Bean
    @Profile("dev")
    public AnimalDAO animalDAO() {
        var dao = new InMemoryAnimalDAO();
        animals.forEach(dao::insert);
        return dao;
    }

    @Bean("animalDAO")
    @Profile("prod")
    public AnimalDAO jpaAnimalDAO() {
        var dao = new JPAAnimalDAO();
//        animals.forEach(dao::insert);
        return dao;
    }

//    @Bean
//    public DataSource dataSource() {
////        String url = "jdbc:postgresql://localhost:5433/larku";
////        String user = "larku";
////        String pw = System.getenv("DB_PASSWORD");
////        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);
////        return dataSource;
//        return getDataSource();
//    }
}
