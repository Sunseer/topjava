package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger userCounter = new AtomicInteger(0);

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    {
        save(new User(1, "User", "user@mail.ru", "pw", Role.ROLE_USER));
        save(new User(2, "Admin", "admin@mail.ru", "ad", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return userRepository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(userCounter.incrementAndGet());
        }
        return userRepository.put(user.getId(), user);
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return userRepository.get(id) == null ? null : userRepository.get(id);
    }

    @Override
    public Collection<User> getAll () {
        return userRepository.values();
    }

    @Override
    public User getByEmail (String email){
        LOG.info("getByEmail " + email);
        return getAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
        }
    }