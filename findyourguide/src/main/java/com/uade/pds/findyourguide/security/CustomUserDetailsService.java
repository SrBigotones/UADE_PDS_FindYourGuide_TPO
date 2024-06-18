package com.uade.pds.findyourguide.security;

import com.uade.pds.findyourguide.model.User;
import com.uade.pds.findyourguide.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        User user = userOpt.get();
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("turista")
                .build();
    }

}
