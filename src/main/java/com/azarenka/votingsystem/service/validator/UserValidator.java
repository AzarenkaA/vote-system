package com.azarenka.votingsystem.service.validator;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.SignUpForm;
import com.azarenka.votingsystem.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

/**
 * Checks user authentication.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Service("userEvaluator")
public class UserValidator {
    @Autowired
    private IUserRepository repository;

    public boolean checkNew(SignUpForm registrationUser) throws BadCredentialsException {
        User user = repository.getByEmail(registrationUser.getUsername());
        if (null == user) {
            return true;
        }
        throw new BadCredentialsException(String.format("User %s already exist.", user.getEmail()));
    }
}
