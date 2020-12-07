package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.Meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Menu repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@Repository
public interface IMealRepository extends JpaRepository<Meal, String> {

    @Query("select m from Meal m join fetch m.restaurants mr where mr.id = :id")
    List<Meal> getMenusById(@Param("id") String id);

    Optional<Meal> findByTitle(String title);
}
