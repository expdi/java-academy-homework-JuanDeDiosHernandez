package expeditors.backend.adoption.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = true)
    private int id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "date_adoption")
    @PastOrPresent
    private LocalDate dateAdoption;
    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Animal> animal = new ArrayList<>();

    public Adopter(){

    }

    public Adopter(int id, String name, String phone, LocalDate dateAdoption, List<Animal> animal) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.dateAdoption = dateAdoption;
        if (animal != null) {
            this.animal.addAll(animal);
        }
    }

    public Adopter(int id, String name, String phone, LocalDate dateAdoption, Animal animal) {
        this(id, name, phone, dateAdoption, List.of(animal));
    }

    public Adopter(String name, String phone, LocalDate dateAdoption, List<Animal> animal) {
        this(0, name, phone, dateAdoption, animal);
    }

    @Override
    public String toString() {
        return STR."Id: \{this.id}\nName: \{this.name}\nPhone Number: \{this.phone}\nDate of Adoption: \{this.dateAdoption}\n";
    }
}
