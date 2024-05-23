package expeditors.backend.adoption.classes;

import java.time.LocalDate;

public record AdopterSmallDTO(int id, String name, String phone, LocalDate dateAdoption) {
}
