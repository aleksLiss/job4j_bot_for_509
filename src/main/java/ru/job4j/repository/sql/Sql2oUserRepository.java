package ru.job4j.repository.sql;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public User save(User user) {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("INSER INTO users (first_name, last_name) VALUES (:firstName, :lastName)",
                            true)
                    .addParameter("firstName", user.getFirstName())
                    .addParameter("lastName", user.getLastName());
            int generatedId = sql.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return user;
        }
    }

    @Override
    public Optional<User> findByClientId(Long clientId) {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("SELECT * FROM users WHERE client_id = :clientId")
                    .addParameter("clientId", clientId);
            User foundUser = sql.executeAndFetchFirst(User.class);
            return Optional.ofNullable(foundUser);
        }
    }

    @Override
    public Collection<User> findAll() {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("SELECT * FROM users");
            return sql.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }

    @Override
    public void deleteById(Long clientId) {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("DELETE FROM users WHERE client_id = :clientId")
                    .addParameter("clientId", clientId);
            sql.executeUpdate();
        }
    }
}
