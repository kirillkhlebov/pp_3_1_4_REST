package ru.kata.SpirngSecurityApp.services;

import org.springframework.stereotype.Service;
import ru.kata.SpirngSecurityApp.models.User;
import ru.kata.SpirngSecurityApp.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInt{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public boolean isExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void saveAndFlush(User user) {
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public boolean isEmailCanNotBeChanged(User user, long id) {
        return isExist(user.getEmail()) && id != findUserByEmail(user.getEmail()).get().getId();
    }
}
