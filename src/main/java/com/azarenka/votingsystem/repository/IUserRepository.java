package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for User repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Repository
public interface IUserRepository extends CrudRepository<User, String> {

    User getByEmail(String userName);
}
