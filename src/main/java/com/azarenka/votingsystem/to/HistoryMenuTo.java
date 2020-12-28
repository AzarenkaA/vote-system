package com.azarenka.votingsystem.to;

import com.azarenka.votingsystem.domain.RestaurantAudit;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * History menu transfer object.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 14.12.2020
 */
public class HistoryMenuTo {

    private String restaurantTitle;
    private Set<MealTo> mealTos;

    public HistoryMenuTo(RestaurantAudit audit) {
        if(Objects.nonNull(audit)) {
            this.restaurantTitle = audit.getRestaurant().getTitle();
            this.mealTos = audit.getHistoryMeals().stream().map(MealTo::new).collect(Collectors.toSet());
        }
    }

    public String getRestaurantTitle() {
        return restaurantTitle;
    }

    public void setRestaurantTitle(String restaurantTitle) {
        this.restaurantTitle = restaurantTitle;
    }

    public Set<MealTo> getMealTos() {
        return mealTos;
    }

    public void setMealTos(Set<MealTo> mealTos) {
        this.mealTos = mealTos;
    }
}
