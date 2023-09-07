package ru.kata.SpirngSecurityApp.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.SpirngSecurityApp.models.Role;
import ru.kata.SpirngSecurityApp.services.RoleServiceInt;


@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleServiceInt roleService;

    public RoleInitializer(RoleServiceInt roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ROLE_USER");
        createRoleIfNotExists("ROLE_ADMIN");
    }

    private void createRoleIfNotExists(String name) {
        if (!roleService.isExist(name)) {
            Role saveRole = new Role(name);
            roleService.save(saveRole);
        }
    }
}

