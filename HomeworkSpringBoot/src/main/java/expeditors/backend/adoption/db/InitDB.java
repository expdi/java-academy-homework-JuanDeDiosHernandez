package expeditors.backend.adoption.db;

import expeditors.backend.adoption.dao.AbstractDAO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class InitDB extends AbstractDAO {

    public void doIt() {
        DriverManagerDataSource dataSource = getDataSource();
        String schemaFile = "postgres/jpa/01_CREATE_SCHEMA.sql";
        String dataFile = "postgres/jpa/02_INSERT_DATA.sql";

        JdbcClient jdbcClient = JdbcClient.create(dataSource);

        runSchemaFile(dataSource, schemaFile);
        runSchemaFile(dataSource, dataFile);
    }

    public void runSchemaFile(DataSource dataSource, String schemaFile) {
        try (Connection conn = dataSource.getConnection()) {

            System.err.println("Running schemaFile: " + schemaFile);
            ScriptUtils.executeSqlScript(conn, new ClassPathResource(schemaFile));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
