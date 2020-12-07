package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.RestaurantAudit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface for Restaurant audit.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 05.12.2020
 */
@Repository
public interface IRestaurantAuditRepository extends JpaRepository<RestaurantAudit, String> {

    @Query("select r from RestaurantAudit r join fetch r.meals mr where mr.id = :id")
    Set<RestaurantAudit> getAuditByMenuId(@Param("id") String id);

    RestaurantAudit getByDateAndRestaurantsId(@Param("date")LocalDate date, @Param("id") String id);
}
