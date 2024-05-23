package expeditors.backend.adoption.dao;

import expeditors.backend.adoption.classes.Adopter;
import expeditors.backend.adoption.classes.Animal;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcQueryTemplate<T> extends AbstractDAO {
    public JdbcQueryTemplate() {
    }

    public Optional<T> queryGetById(String sql, int id) {
        Optional<T> entity = Optional.empty();
        try (
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    entity = mapItem(resultSet);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    public List<T> queryGetList(String sql) {
        List<T> items = new ArrayList<>();
        try (
                Connection con = getConnection();
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                Optional<T> item = mapItem(resultSet);
                item.ifPresent(items::add);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return items;
    }

    public abstract Optional<T> mapItem(ResultSet resultSet) throws SQLException;
}
