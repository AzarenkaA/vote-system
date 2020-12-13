package com.azarenka.votingsystem.domain;

import com.azarenka.votingsystem.to.MealTo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Menu Entity.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 23.11.2020
 */
@Entity
@Table(name = "meal", schema = "main")
public class Meal extends BaseEntity {

    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meal_to_restaurant_map", schema = "main", joinColumns = {
        @JoinColumn(name = "meal_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
    private Set<Restaurant> restaurants;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "restaurant_audit_to_meal_map", schema = "main", joinColumns = {
        @JoinColumn(name = "meal_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "audit_id", referencedColumnName = "id")})
    private Set<RestaurantAudit> audit;

    public Meal() {
    }

    public Meal(MealTo mealTo) {
        this.setId(mealTo.getId());
        this.title = mealTo.getTitle();
        this.price = mealTo.getPrice();
    }

    public Set<RestaurantAudit> getAudit() {
        return audit;
    }

    public void setAudit(Collection<RestaurantAudit> audit) {
        this.audit = new HashSet<>(audit);
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

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Collection<Restaurant> restaurant) {
        this.restaurants = new HashSet<>(restaurant);
    }
}
