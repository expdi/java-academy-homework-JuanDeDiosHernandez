package expeditors.backend.adoption.classes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
//@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_pet")
    private TypePet typePet;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "pet_breed")
    private String petBreed;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
    @JsonBackReference
//    @JsonView(Views.Adopter.class)
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    public Animal() {

    }

    public Animal(int id, TypePet typePet, String petName, String petBreed, Adopter adopter) {
        this.id = id;
        this.typePet = typePet;
        this.petName = petName;
        this.petBreed = petBreed;
        this.adopter = adopter;
    }

    @Override
    public String toString() {
        return STR."Id:\{this.id}\nType Pet: \{this.typePet}\nPet Name: \{this.petName}\nPet Breed: \{this.petBreed}\nAdopters: \{this.adopter.toString()}\n";
    }
}
