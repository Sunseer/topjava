package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

/**
 * Created by grin on 18.08.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Autowired
    private UserMealService service;

}
