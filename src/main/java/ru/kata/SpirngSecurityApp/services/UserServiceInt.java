package ru.kata.SpirngSecurityApp.services;

import ru.kata.SpirngSecurityApp.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInt {

    Optional<User> findUserByEmail(String email);

    List<User> findAll();

    Optional<User> findById(long id);

    boolean isExist(String email);

    void save(User user);

    void saveAndFlush(User user);

    void delete(User user);

    boolean isEmailCanNotBeChanged(User user, long id);
}
