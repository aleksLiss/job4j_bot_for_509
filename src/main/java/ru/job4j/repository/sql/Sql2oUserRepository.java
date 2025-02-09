package ru.job4j.repository.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;
    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oUserRepository.class);

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            String sql = """
                    INSERT INTO users (client_id, first_name, last_name)
                    VALUES (:clientId, :firstName, :lastName)
                    """;
            Query query = connection.createQuery(sql,
                            true)
                    .addParameter("clientId", user.getClientId())
                    .addParameter("firstName", user.getFirstName())
                    .addParameter("lastName", user.getLastName());
            int generatedId = query.executeUpdate().getResult();
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            ex.getMessage();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByClientId(Integer clientId) {
        try (Connection connection = sql2o.open()) {
            String sql = "SELECT * FROM users WHERE client_id = :clientId";
            Query query = connection.createQuery(sql)
                    .addParameter("clientId", clientId);
            return Optional.ofNullable(query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetchFirst(User.class));
        }
    }

    @Override
    public Collection<User> findAll() {
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("SELECT * FROM users");
            Collection<User> users = query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
            return users;
        }
    }

    @Override
    public boolean deleteById(Integer clientId) {
        boolean isDeleted;
        try (Connection connection = sql2o.open()) {
            Query query = connection.createQuery("DELETE FROM users WHERE client_id = :clientId")
                    .addParameter("clientId", clientId);
            query.executeUpdate();
            isDeleted = connection.getResult() != 0;
        }
        return isDeleted;
    }
}
