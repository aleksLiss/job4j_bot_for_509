package ru.job4j.repository.sql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.config.DatasourceConfig;
import ru.job4j.model.User;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepository() throws Exception {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.source.url");
        String username = properties.getProperty("database.source.username");
        String password = properties.getProperty("database.source.password");
        DatasourceConfig configuration = new DatasourceConfig();
        DataSource datasource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.databaseClient(datasource);
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
        User user = new User();
        user.setClientId(1L);
        user.setFirstName("Vova");
        user.setLastName("Vovkin");
        sql2oUserRepository.save(user);
    }

    @AfterEach
    public void deleteUsers() {
        Collection<User> users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getClientId());
        }
    }

    @Test
    public void whenEmptyTableAndFindAllThenReturnEmptyList() {
        Collection<User> result = sql2oUserRepository.findAll();
        assertThat(result).hasSize(0).isEqualTo(result);
    }

    private User getDefUser() {
        return new User(Long.valueOf(123123L), "Lexa", "Lexus");
    }
}