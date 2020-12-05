package com.azarenka.votingsystem.service.impl;

import com.azarenka.votingsystem.domain.Role;
import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.SignUpForm;
import com.azarenka.votingsystem.repository.IUserRepository;
import com.azarenka.votingsystem.service.api.IUserService;
import com.azarenka.votingsystem.util.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implementation of {@link IUserService}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void save(SignUpForm registrationUser) {
        User user = buildUser(registrationUser);
        LOGGER.info("Start creating user with name {}", registrationUser.getUsername());
        user.setPassword(encoder.encode(registrationUser.getPassword()));
        userRepository.save(user);
        LOGGER.info("Finish creating user. User has been created {}", registrationUser.getUsername());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    protected User buildUser(SignUpForm registrationUser) {
        User user = new User();
        user.setId(KeyGenerator.generateUuid());
        user.setEmail(registrationUser.getUsername());
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        return user;
    }
}
