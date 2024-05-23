package expeditors.backend.adoption.dao.jpa;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.AdopterPets;
import expeditors.backend.adoption.dao.AbstractDAO;
import expeditors.backend.adoption.dao.AdopterDAO;
import expeditors.backend.adoption.dao.JdbcQueryTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
@Profile("prod")
public class JPAAdopterDAO extends AbstractDAO implements AdopterDAO {

    private ResultSetExtractor<Adopter> getExtractor() {
        return (rs) -> {
            Adopter adopter = null;
            while(rs.next()) {
                adopter = Adopter.builder()
                        .id(rs.getInt("adopter_id"))
                        .name(rs.getString("name"))
                        .phone(rs.getString("phone"))
                        .dateAdoption(rs.getDate("date_adoption").toLocalDate())
//                        .animal_id(rs.getInt("animal_id"))
                        .build();
            }
            return adopter;
        };
    }

    private RowMapper<Adopter> getMapper() {
        return (rs, rowNum) -> Adopter.builder()
                .id(rs.getInt("adopter_id"))
                .name(rs.getString("name"))
                .phone(rs.getString("phone"))
                .dateAdoption(rs.getDate("date_adoption").toLocalDate())
//                .animal_id(rs.getInt("animal_id"))
                .build();
    }

    @Override
    public Adopter insert(Adopter adopter) {
        String sql = "INSERT INTO adopters (name, phone, date_adoption, animal_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                con -> {
                    PreparedStatement statement = con.prepareStatement(sql, new String[]{"adopter_id"});
                    statement.setString(1, adopter.getName());
                    statement.setString(2, adopter.getPhone());
                    statement.setDate(3, Date.valueOf(adopter.getDateAdoption()));
//                    statement.setInt(4, adopter.getAnimal_id());
                    return statement;
                }, keyHolder);

        adopter.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return adopter;
    }

    @Override
    public boolean update(Adopter adopter) {
        String sql = "UPDATE adopters SET name = ?, phone = ?, date_adoption = ?, animal_id = ? WHERE adopter_id = ?";

        return getJdbcTemplate().update(sql,
                adopter.getName(),
                adopter.getPhone(),
                adopter.getDateAdoption(),
//                adopter.getAnimal_id(),
                adopter.getId()) == 1;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM adopters WHERE adopter_id = ?";

        return getJdbcTemplate().update(sql, id) == 1;
    }

    @Override
    public Adopter findById(int id) {
        String sql = "SELECT * FROM adopters WHERE adopter_id = ?";

        return getJdbcTemplate().query(sql, getExtractor(), id);
    }

    @Override
    public List<Adopter> findAll() {
        String sql = "SELECT * FROM adopters";

        return getJdbcTemplate().query(sql, getMapper());
    }

    @Override
    public Adopter findByName(String name) {
        String sql = "SELECT * FROM adopters WHERE name LIKE ?";
        name = "%" + name + "%";

        return getJdbcTemplate().query(sql, getExtractor(), name);
    }

    @Override
    public List<Adopter> findAllSortedByName() {
        String sql = "SELECT * FROM adopters ORDER BY name";

        return getJdbcTemplate().query(sql, getMapper());
    }


    public Adopter insertWithPet(AdopterPets adopterPets) {
        String sqlAnimal = "INSERT INTO animals (type_pet, pet_name, pet_breed) VALUES (?, ?, ?)";
        KeyHolder keyHolderAnimal = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                con -> {
                    PreparedStatement statement = con.prepareStatement(sqlAnimal, new String[]{"animal_id"});
                    statement.setString(1, String.valueOf(adopterPets.getAnimal().getTypePet()));
                    statement.setString(2, adopterPets.getAnimal().getPetName());
                    statement.setString(3, adopterPets.getAnimal().getPetBreed());
                    return statement;
                }, keyHolderAnimal);

//        adopterPets.getAdopter().setAnimal_id(Objects.requireNonNull(keyHolderAnimal.getKey()).intValue());
        String sqlAdopter = "INSERT INTO adopters (name, phone, date_adoption, animal_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolderAdopter = new GeneratedKeyHolder();

        getJdbcTemplate().update(
                con -> {
                    PreparedStatement statement = con.prepareStatement(sqlAdopter, new String[]{"adopter_id"});
                    statement.setString(1, adopterPets.getAdopter().getName());
                    statement.setString(2, adopterPets.getAdopter().getPhone());
                    statement.setDate(3, Date.valueOf(adopterPets.getAdopter().getDateAdoption()));
//                    statement.setInt(4, adopterPets.getAdopter().getAnimal_id());
                    return statement;
                }, keyHolderAdopter);

        adopterPets.getAdopter().setId(Objects.requireNonNull(keyHolderAdopter.getKey()).intValue());
        return adopterPets.getAdopter();
    }
}
