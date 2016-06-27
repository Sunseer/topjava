package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Comparator<UserMeal> USER_MEAL_COMPARATOR = (um1, um2) -> um2.getDateTime().compareTo(um1.getDateTime());

    {
        UserMealsUtil.MEAL_LIST.forEach(um -> save(um, 1));
        save(new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ обед", 510), 2);
        save(new UserMeal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), 2);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        Integer mealId = userMeal.getId();
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            userMeal.setId(mealId);
        }
        else if (get(mealId, userId) == null) {
            return null;
        }
        Map <Integer, UserMeal> userMealMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMealMap.put(mealId, userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer,UserMeal> userMealMap = repository.get(userId);
        return userMealMap != null && userMealMap.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer,UserMeal> userMealMap = repository.get(userId);
        return userMealMap == null ? null : userMealMap.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.get(userId).values()
                .stream()
                .sorted(USER_MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAll(userId)
                .stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .sorted(USER_MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

