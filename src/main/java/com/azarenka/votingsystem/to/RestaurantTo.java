package com.azarenka.votingsystem.to;

import com.azarenka.votingsystem.domain.Restaurant;

/**
 * Transfer object for {@link Restaurant}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 05.12.2020
 */
public class RestaurantTo {

    private String id;
    private String name;

    /**
     * Default constructor.
     */
    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getTitle();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
