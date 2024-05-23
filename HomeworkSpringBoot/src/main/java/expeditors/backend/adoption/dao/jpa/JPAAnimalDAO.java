package expeditors.backend.adoption.dao.jpa;

import expeditors.backend.adoption.classes.Animal;
import expeditors.backend.adoption.classes.TypePet;
import expeditors.backend.adoption.dao.AbstractDAO;
import expeditors.backend.adoption.dao.AnimalDAO;
import expeditors.backend.adoption.dao.JdbcQueryTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JPAAnimalDAO extends AbstractDAO implements AnimalDAO {

    private ResultSetExtractor<Animal> getExtractor() {
        return (rs) -> {
            Animal animal = null;
            while(rs.next()) {
                animal = Animal.builder()
                        .id(rs.getInt("animal_id"))
                        .typePet(TypePet.valueOf(rs.getString("type_pet")))
                        .petName(rs.getString("pet_name"))
                        .petBreed(rs.getString("pet_breed"))
                        .build();
            }
            return animal;
        };
    }

    private RowMapper<Animal> getMapper() {
        return (rs, rowNum) -> Animal.builder()
                .id(rs.getInt("animal_id"))
                .typePet(TypePet.valueOf(rs.getString("type_pet")))
                .petName(rs.getString("pet_name"))
                .petBreed(rs.getString("pet_breed"))
                .build();
    }

    @Override
    public Animal insert(Animal animal) {
        String sql = "INSERT INTO animals (type_pet, pet_name, pet_breed) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                con -> {
                    PreparedStatement statement = con.prepareStatement(sql, new String[]{"animal_id"});
                    statement.setString(1, String.valueOf(animal.getTypePet()));
                    statement.setString(2, animal.getPetName());
                    statement.setString(3, animal.getPetBreed());
                    return statement;
                }, keyHolder);

        animal.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return animal;
    }

    @Override
    public boolean update(Animal animal) {
        String sql = "UPDATE animals SET type_pet = ?, pet_name = ?, pet_breed = ? WHERE animal_id = ?";

        return getJdbcTemplate().update(sql,
                String.valueOf(animal.getTypePet()),
                animal.getPetName(),
                animal.getPetBreed(),
                animal.getId()) == 1;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM animals WHERE animal_id = ?";

        return getJdbcTemplate().update(sql, id) == 1;
    }

    @Override
    public Animal findById(int id) {
        String sql = "SELECT * FROM animals WHERE animal_id = ?";

        return getJdbcTemplate().query(sql, getExtractor(), id);
    }

    @Override
    public List<Animal> findAll() {
        String sql = "SELECT * FROM animals";

        return getJdbcTemplate().query(sql, getMapper());
    }
}
