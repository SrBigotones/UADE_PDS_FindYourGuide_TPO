package com.uade.pds.findyourguide.service;

import com.uade.pds.findyourguide.model.User;
import com.uade.pds.findyourguide.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired UserRepository userRepository;

    public User saveUser(User user) {
        // TODO check no esta

        return userRepository.save(user);
    }


}
