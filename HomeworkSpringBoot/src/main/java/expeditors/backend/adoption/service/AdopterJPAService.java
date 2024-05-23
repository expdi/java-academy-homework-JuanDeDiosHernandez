package expeditors.backend.adoption.service;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterSmallDTO;
import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.dao.repository.AdopterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterJPAService {
    private final AdopterRepository adopterRepository;

    public AdopterJPAService(AdopterRepository adopterRepository) {
        this.adopterRepository = adopterRepository;
    }

    public Adopter createAdopter(Adopter adopter) {
        adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
        return adopterRepository.save(adopter);
    }

    public boolean deleteAdopter(int id) {
        Adopter adopter = adopterRepository.findById(id).orElse(null);
        if (adopter != null) {
            adopterRepository.delete(adopter);
            return true;
        }
        return false;
    }

    public boolean updateAdopter(Adopter adopter) {
        Adopter oldAdopter = adopterRepository.findById(adopter.getId()).orElse(null);
        if (oldAdopter != null) {
            adopter.getAnimal().forEach(animal -> animal.setAdopter(adopter));
            adopterRepository.save(adopter);
            return true;
        }
        return false;
    }

    public Adopter getAdopter(int id) {
        return adopterRepository.findById(id).orElse(null);
    }


    public AdopterSmallDTO getAdopterSmallDTO(int id) {
        return adopterRepository.findSmallDTOById(id);
    }

    public List<Adopter> getAdopters() {
        return adopterRepository.findAllWithAnimal();
    }

    public Adopter getAdopterByName(String name) {
        return adopterRepository.findByName(name);
    }

    public List<Adopter> getAdoptersSortedByName() {
        return adopterRepository.findAllWithAnimalSortedByName();
    }
}
