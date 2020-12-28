package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.MealTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * This controller gives access to menu.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealController {

    @Autowired
    private IRestaurantService menuService;
    @Autowired
    private IMealRepository menuRepository;

    /**
     * Saves new menu in database.
     * Permission has for only ADMIN role.
     * Method uses validator of incoming data to check exist in DB and valid data.

     * @param menu instance of {@link MealTo}
     * @return instance of {@link ResponseEntity< MealTo >}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') and" + "@menuValidator.checkInsertMenuDataWithMultipleRest(#menu)")
    public ResponseEntity<MealTo> setMenuByRestaurant(@Valid @RequestBody MealTo menu) {
        return new ResponseEntity<>(menuService.save(menu), HttpStatus.OK);
    }

    /**
     * Updates menu.
     * Permission has for only ADMIN role.
     * Method uses validator of incoming data to check exist in DB and valid data.

     * @param menu instance
     * @return instance of {@link ResponseEntity<MealTo>}
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') and" + "@menuValidator.checkUpdateMenuDataWithMultipleRest(#menu)")
    public ResponseEntity<MealTo> updateMenuByRestaurant(@Valid @RequestBody MealTo menu) {
        return new ResponseEntity<>(menuService.update(menu), HttpStatus.OK);
    }
}
