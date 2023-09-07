package ru.kata.SpirngSecurityApp.services;

import ru.kata.SpirngSecurityApp.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleServiceInt {

    Optional<Role> findByName(String name);

    List<Role> findAll();

    boolean isExist(String name);

    void save(Role role);
}
