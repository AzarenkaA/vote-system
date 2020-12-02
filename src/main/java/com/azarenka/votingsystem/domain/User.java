package com.azarenka.votingsystem.domain;

import com.azarenka.votingsystem.domain.auth.SignUpForm;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


/**
 * Class for entity User.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
@Entity
@Table(name = "users", schema = "main")
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "enabled")
    private boolean enabled = false;
    @CollectionTable(name = "role", schema = "main", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    /**
     * Constructor.
     *
     * @param id       user id
     * @param email    user email
     * @param password user password
     */
    public User(String id, String email, String password) {
        this.setId(id);
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor to create {@link User} based on {@link SignUpForm}.
     *
     * @param registrationUser instance of {@link SignUpForm}.
     */
    public User(SignUpForm registrationUser) {
        this.email = registrationUser.getUsername();
        this.password = registrationUser.getPassword();
    }

    /**
     * Default constructor.
     */
    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
