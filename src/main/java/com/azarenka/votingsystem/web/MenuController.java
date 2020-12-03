package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.domain.Menu;
import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.service.api.IMenuService;
import com.azarenka.votingsystem.to.MenuTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
public class MenuController {

    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRepository menuRepository;

    /**
     * Saves new menu in database.
     *
     * @param menu instance of {@link MenuTo}
     * @return instance of {@link ResponseEntity<MenuTo>}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN') or" + "@menuValidator.checkToInsertMenu(#menu)")
    public ResponseEntity<MenuTo> setMenuByRestaurant(@Valid @RequestBody MenuTo menu) {
        return new ResponseEntity<>(menuService.save(menu), HttpStatus.OK);
    }

    /**
     * Updates menu.
     *
     * @param menu instance
     * @return instance of {@link ResponseEntity<MenuTo>}
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN') or" + "@menuValidator.checkToUpdateMenu(#menu)")
    public ResponseEntity<MenuTo> updateMenuByRestaurant(@Valid @RequestBody MenuTo menu) {
        return new ResponseEntity<>(menuService.update(menu), HttpStatus.OK);
    }

    /**
     * Returns all menus.
     *
     * @return list of {@link Restaurant}
     */
    @GetMapping
    // @PreAuthorize("hasAnyRole('USER_ROLE', 'ADMIN')")
    public ResponseEntity<List<MenuTo>> getRestaurants() {
        return new ResponseEntity<>(menuRepository.findAll()
            .stream()
            .map(MenuTo::new)
            .collect(Collectors.toList()), HttpStatus.OK);
    }
}
