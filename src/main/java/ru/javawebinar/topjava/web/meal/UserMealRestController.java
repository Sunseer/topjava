package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealRestController {

    @Autowired
    private UserMealServiceImpl service;

    public UserMeal get(int id) {
        return service.get(id, LoggedUser.id());
    }

    public void delete(int id) {
        service.delete(id, LoggedUser.id());
    }

    List<UserMealWithExceed> getAll() {
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }

    public UserMeal update(UserMeal meal, int id) {
        meal.setId(id);
        return service.update(meal, LoggedUser.id());
    }

    public UserMeal create(UserMeal meal) {
        meal.setId(null);
        return service.save(meal, LoggedUser.id());
    }

    public List<UserMealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, LoggedUser.id()),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, LoggedUser.getCaloriesPerDay());
    }
}
