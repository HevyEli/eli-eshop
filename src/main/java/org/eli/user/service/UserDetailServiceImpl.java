package org.eli.user.service;

import org.eli.user.entity.User;
import org.eli.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + "  not found in db");
        }
         boolean isActive = "Y".equals(user.getActiveInd());
        List<String> allowedRoles = List.of("ADMIN", "SUPERUSER");
        List<GrantedAuthority> authorities = allowedRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                isActive,
                true,
                true,
                true,
                authorities);

    }
}
