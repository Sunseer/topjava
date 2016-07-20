package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    @Autowired
    private ProxyUserMealRepository userMealProxy;

    @Autowired
    private ProxyUserRepository userProxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        }
        userMeal.setUser(userProxy.getOne(userId));
        return userMealProxy.save(userMeal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return userMealProxy.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return userMealProxy.get(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return userMealProxy.getAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return userMealProxy.getBetween(startDate, endDate, userId);
    }
}
