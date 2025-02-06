package ru.job4j.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SimpleUserRepository implements UserRepository {

    private final Map<Long, User> userStorage;

    public SimpleUserRepository() {
        this.userStorage = new HashMap<>();
    }

    @Override
    public User save(User user) {
        return userStorage.put(user.getClientId(), user);
    }

    @Override
    public User findByClientId(Long clientId) {
        return userStorage.get(clientId);
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.values();
    }
}
