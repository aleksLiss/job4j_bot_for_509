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
import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;
    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oUserRepository.class);

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public User save(User user) {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("INSERT INTO users (first_name, last_name) VALUES (:firstName, :lastName)",
                            true)
                    .addParameter("firstName", user.getFirstName())
                    .addParameter("lastName", user.getLastName());
            int generatedId = sql.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return user;
    }

    @Override
    public Optional<User> findByClientId(Long clientId) {
        User foundUser = null;
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("SELECT * FROM users WHERE client_id = :clientId")
                    .addParameter("clientId", clientId);
            foundUser = sql.executeAndFetchFirst(User.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return Optional.ofNullable(foundUser);
    }

    @Override
    public Collection<User> findAll() {
        Collection<User> users = List.of();
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("SELECT * FROM users");
            users = sql.executeAndFetch(User.class);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return users;
    }

    @Override
    public void deleteById(Long clientId) {
        try (Connection connection = sql2o.open()) {
            Query sql = connection.createQuery("DELETE FROM users WHERE client_id = :clientId")
                    .addParameter("clientId", clientId);
            sql.executeUpdate();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
