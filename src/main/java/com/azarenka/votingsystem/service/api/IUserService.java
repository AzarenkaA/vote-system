package com.azarenka.votingsystem.service.api;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.SignUpForm;

/**
 * Interface for user service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
public interface IUserService {

    /**
     * Saves user.
     *
     * @param user user
     */
    void save(SignUpForm user);

    /**
     * Returns user by email.
     *
     * @param email email
     * @return {@link User}.
     */
    User getByEmail(String email);
}
