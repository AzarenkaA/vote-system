package com.azarenka.votingsystem.domain;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "menu_to_restaurant_map", joinColumns = {
        @JoinColumn(name = "restaurant_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private Set<Menu> menu;

    public Set<Menu> getMenu() {
        return menu;
    }

    public void setMenu(Collection<Menu> menu) {
        this.menu = new HashSet<>(menu);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMenu(Set<Menu> menu) {
        this.menu = menu;
    }
}
