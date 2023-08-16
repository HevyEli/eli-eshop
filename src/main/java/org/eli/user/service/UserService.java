package org.eli.user.service;

import org.eli.user.entity.User;
import org.eli.user.repository.UserRepository;
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
        return userJpaRepository.findByUsername(username);
    }
    public Optional<User> getUserById(long id) {
        return userJpaRepository.findById(id);
    }

    public User createUser(User user) {
        String passwordHash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwordHash);
        user.setActiveInd("Y");
        return userJpaRepository.save(user);
    }

    public User disableUserById(long id) {
        Optional<User> optUser = userJpaRepository.findById(id);
        optUser.ifPresent(user -> {
            user.setActiveInd("N");
        });
        return null;
    }

    public boolean isUserActive (long id) {
        Optional<User> user = userJpaRepository.findById(id);
        return user.isPresent() && "Y".equals(user.get().getActiveInd());
        }
}
