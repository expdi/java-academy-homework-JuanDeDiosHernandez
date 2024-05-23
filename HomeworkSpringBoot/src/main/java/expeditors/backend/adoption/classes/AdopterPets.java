package expeditors.backend.adoption.classes;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AdopterPets {
    Adopter adopter;
    Animal animal;
}
