package com.azarenka.votingsystem.to;

import com.azarenka.votingsystem.domain.BaseEntity;
import com.azarenka.votingsystem.domain.Meal;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Transfer object for {@link Meal}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
public class MealTo {

    private String id;
    private String title;
    private BigDecimal price;
    private Set<String> restaurantsIds;

    /**
     * Default constructor.
     */
    public MealTo() {
    }

    public MealTo(Meal meal) {
        this.id = meal.getId();
        this.title = meal.getTitle();
        this.price = meal.getPrice();
        this.restaurantsIds = meal.getRestaurants().stream()
            .map(BaseEntity::getId)
            .collect(Collectors.toSet());
    }

    public Set<String> getRestaurantsIds() {
        return restaurantsIds;
    }

    public void setRestaurantsIds(Set<String> restaurantsIds) {
        this.restaurantsIds = restaurantsIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
