package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query("select v from Vote v where v.userId = :id")
    List<Vote> findByUserId(@Param("id")String id);

    @Query("select v from Vote v where v.restaurantId = :id")
    List<Vote> findByRestaurantId(@Param("id") String id);
}
