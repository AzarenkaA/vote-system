package com.azarenka.testinteg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.azarenka.votingsystem.domain.Role;
import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.repository.IUserRepository;

import liquibase.pro.packaged.U;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import javax.annotation.Resource;

/**
 * Test for User Repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 24.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Resource
    private IUserRepository userRepository;

    @Test
    public void testGetByEmail() {
        User expectedUser = buildUser();
        User actualUser = userRepository.getByEmail("admin@mail.ru");
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getName(), actualUser.getName());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getRoles(), actualUser.getRoles());
    }

    @Test
    public void testSave() {
        User expectedUser = new User();
        expectedUser.setId("6cfb27d8-6e60-4146-b5af-a2e685a49680");
        expectedUser.setEmail("testUser@mail.ru");
        expectedUser.setRoles(Collections.singleton(Role.ROLE_USER));
        expectedUser.setPassword("password");
        expectedUser.setName("user");
        userRepository.save(expectedUser);
        User actualUser = userRepository.getByEmail("testUser@mail.ru");
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getName(), actualUser.getName());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getRoles(), actualUser.getRoles());
    }

    private User buildUser() {
        User user = new User();
        user.setId("4993f33d-cd83-4b87-a4d4-57a11e65aa9b");
        user.setEmail("admin@mail.ru");
        user.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        user.setPassword("$2a$10$dMA4vWKr5L7LbVzSb48PdeQLq8DdrQcz2j2Ma7zI7PPQnrUWarYzS");
        user.setName("admin");
        return user;
    }
}
