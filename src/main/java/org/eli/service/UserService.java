package org.eli.service;

import org.eli.entity.User;
import org.eli.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userJpaRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userJpaRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userJpaRepository.findAll();
    }
    public User getUserByUsername(String username) {
        return userJpaRepository.findUserByUserName(username);
    }
    public Optional<User> getUserById(long id) {
        return userJpaRepository.findById(id);
    }

    public User createUser(User user) {
        String passwordHash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwordHash);
        return userJpaRepository.save(user);
    }
}
