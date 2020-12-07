package com.azarenka.votingsystem.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.SignUpForm;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IUserRepository;
import com.azarenka.votingsystem.util.KeyGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(PowerMockRunner.class)
@PrepareForTest({KeyGenerator.class, UserPrincipal.class})
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository repository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    public void testSave() {
        mockStatic(KeyGenerator.class);
        when(KeyGenerator.generateUuid()).thenReturn("7a70b1e7-0021-4393-9271-aaa4ec8b87ff");
        when(encoder.encode("password")).thenReturn("password");
        User user = getUser();
        when(repository.save(user)).thenReturn(user);
        userService.save(getForm());
        verify(repository).save(getUser());
    }

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setId("7a70b1e7-0021-4393-9271-aaa4ec8b87ff");
        user.setPassword("password");
        return user;
    }

    public SignUpForm getForm() {
        SignUpForm form = new SignUpForm();
        form.setUsername("name");
        form.setPassword("password");
        return form;
    }
}