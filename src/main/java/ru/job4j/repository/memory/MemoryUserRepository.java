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

    private final Map<Long, User> userStorage;

    public MemoryUserRepository() {
        this.userStorage = new HashMap<>();
    }

    @Override
    public User save(User user) {
        return userStorage.put(user.getClientId(), user);
    }

    @Override
    public Optional<User> findByClientId(Long clientId) {
        return Optional.ofNullable(userStorage.get(clientId));
    }

    @Override
    public Collection<User> findAll() {
        return userStorage.values();
    }

    @Override
    public void deleteById(Long clientId) {
        userStorage.remove(clientId);
    }
}
