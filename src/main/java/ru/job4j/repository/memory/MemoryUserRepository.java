package ru.job4j.repository.memory;

import org.springframework.stereotype.Repository;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<Integer, User> userStorage;

    public MemoryUserRepository() {
        this.userStorage = new HashMap<>();
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.ofNullable(userStorage.put(user.getId(), user));
    }

    @Override
    public Optional<User> findByClientId(Integer clientId) {
        return Optional.ofNullable(userStorage.get(clientId));
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.values();
    }

    @Override
    public boolean deleteById(Integer clientId) {
        userStorage.remove(clientId);
        return true;
    }
}
