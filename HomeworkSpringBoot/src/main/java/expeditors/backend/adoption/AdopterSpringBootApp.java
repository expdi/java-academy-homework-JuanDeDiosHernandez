package expeditors.backend.adoption;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.db.InitDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class AdopterSpringBootApp {
    public static void main(String[] args) {
        SpringApplication.run(AdopterSpringBootApp.class, args);
    }
}

//@Component
//class DBInitializer implements CommandLineRunner
//{
//    @Autowired
//    private InitDB initDB;
//
//    @Override
//    public void run(String... args) throws Exception {
//        initDB.doIt();
//    }
//}

//@Component
//class MyRunner implements CommandLineRunner
//{
//    @Autowired
//    private AdopterService adopterService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("MyRunner called");
//
//        List<Adopter> adopters = adopterService.getAdopters();
//        System.out.println("adopters: " + adopters.size());
//        System.out.println(adopters);
//    }
//}