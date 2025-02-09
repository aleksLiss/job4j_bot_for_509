package ru.job4j.repository;

import ru.job4j.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByClientId(Integer clientId);

    Collection<User> findAll();

    boolean deleteById(Integer clientId);
}
