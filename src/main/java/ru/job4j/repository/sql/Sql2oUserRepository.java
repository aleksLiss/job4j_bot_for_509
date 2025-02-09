package ru.job4j.repository.sql;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Collection;
import java.util.List;

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
                    .addParameter(user.getFirstName())
                    .addParameter(user.getLastName());
            int generatedId = sql.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return user;
        }
    }

    @Override
    public User findByClientId(Long clientId) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long clientId) {

    }
}
