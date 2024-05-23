package expeditors.backend.adoption.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDAO {
    String url = "jdbc:postgresql://localhost:5434/adopterdb";
    String username = "larku";
    String password = System.getenv("DB_PASSWORD");

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    protected DriverManagerDataSource getDataSource() {
        return new DriverManagerDataSource(url, username, password);
    }

    protected JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }
}
