package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Vote repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 02.12.2020
 */
@Repository
public interface IVoteRepository extends JpaRepository<Vote, String> {

}
