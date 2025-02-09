package ru.job4j.repository;

import ru.job4j.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByClientId(Long clientId);

    Collection<User> findAll();

    void deleteById(Long clientId);
}
