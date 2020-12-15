package com.azarenka.votingsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class for Restaurant Entity.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Entity
@Table(name = "restaurant", schema = "main")
public class Restaurant extends BaseEntity {

    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meal_to_restaurant_map", schema = "main", joinColumns = {
        @JoinColumn(name = "restaurant_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "meal_id", referencedColumnName = "id")})
    @JsonIgnoreProperties(value = "restaurants", allowSetters = true)
    private Set<Meal> meals;

    @OneToMany(mappedBy = "restaurant",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        targetEntity = RestaurantAudit.class)
    @JsonIgnoreProperties(value = "restaurant", allowSetters = true)
    private Set<RestaurantAudit> audit;

    public Set<RestaurantAudit> getAudit() {
        return audit;
    }

    public void setAudit(Collection<RestaurantAudit> audit) {
        this.audit = new HashSet<>(audit);
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = new HashSet<>(meals);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMenu(Set<Meal> meals) {
        this.meals = meals;
    }
}
