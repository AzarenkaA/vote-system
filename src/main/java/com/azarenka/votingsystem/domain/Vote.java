package com.azarenka.votingsystem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for Vote.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 01.12.2020
 */
@Entity
@Table(name = "vote", schema = "main")
public class Vote extends BaseEntity {

    @Column(name = "user_id")
    private String userId;
    @Column(name = "restaurant_id")
    private String restaurantId;

    /**
     * Default constructor.
     */
    public Vote() {
    }

    public Vote(String userId, String restaurantId) {
        this.userId = userId;
        this.restaurantId=restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
