package com.azarenka.votingsystem.service.auth;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implements user details service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByEmail(username);
        if (null == user) {
            throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
        }

        return UserPrincipal.build(user);
    }
}
