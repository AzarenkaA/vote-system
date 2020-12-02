package com.azarenka.votingsystem.repository;

import com.azarenka.votingsystem.domain.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Restaurant repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, String> {

}
