package com.azarenka.votingsystem.domain;

import com.azarenka.votingsystem.to.MenuTo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
@Table(name = "menu", schema = "main")
public class Menu extends BaseEntity {

    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "menu_to_restaurant_map", schema = "main", joinColumns = {
        @JoinColumn(name = "menu_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "restaurant_id", referencedColumnName = "id")})
    private Set<Restaurant> restaurants;

    public Menu() {
    }

    public Menu(MenuTo menuTo) {
        this.setId(menuTo.getId());
        this.title = menuTo.getTitle();
        this.price = menuTo.getPrice();
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
