package com.azarenka.votingsystem.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for Audit of meal.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 05.12.2020
 */
@Entity
@Table(name = "restaurant_audit", schema = "main")
public class RestaurantAudit extends BaseEntity {

    @Column(name = "menu_date")
    private LocalDate date;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurant_audit_to_meal_map", schema = "main", joinColumns = {
        @JoinColumn(name = "audit_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "meal_id", referencedColumnName = "id")})
    private Set<Meal> meals;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate dates) {
        this.date = dates;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Collection<Meal> audit) {
        this.meals = new HashSet<>(audit);
    }
}
