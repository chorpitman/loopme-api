package com.loopme.api.security;

import com.loopme.api.model.User;
import com.loopme.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.text.MessageFormat.format;

@Service(value = "userService")
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByEmail(username);
        if (Objects.isNull(foundUser)) {
            throw new UsernameNotFoundException(format("User with name: {0} not found.", username));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(foundUser.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(),
                foundUser.getPassword(), grantedAuthorities);
    }
}
