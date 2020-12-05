package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.MenuTo;
import com.azarenka.votingsystem.to.RestaurantTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * This API gives access to restaurants.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private IRestaurantService restaurantService;
    @Autowired
    private IMenuRepository menuRepository;

    /**
     * Returns all restaurants.
     * mapping http://localhost:8080/api/restaurants
     *
     * Example:
     * response: {[
     *      {
     *          "id": "375c16c5-fbbd-484d-83da-4b4f4090d231",
     *          "title": "title"
     *      },
     *      {
     *          "id": "e9bac2db-1f4f-4cee-bd43-548a6862e81a",
     *          "title": "title2"
     *      }
     * ]}
     *
     * @return list of {@link Restaurant}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<RestaurantTo> getRestaurants() {
        return restaurantRepository.findAll()
            .stream()
            .map(RestaurantTo::new)
            .collect(Collectors.toList());
    }

    /**
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and" + "@voteValidator.checkData(#id)")
    public ResponseEntity<?> vote(@Valid @PathVariable String id) {
        return new ResponseEntity<>(restaurantService.toVote(id), HttpStatus.ACCEPTED);
    }

    /**
     * Returns all menus by restaurant id.
     * mapping http://localhost:8080/api/restaurants/{id}/menus
     *
     * JSON example:
     * response: {[
     *      {
     *          "id": "75a001ee-7b67-46a4-80ab-08a66a24ce7c",
     *          "title": "title",
     *          "price": "2.50",
     *          "restaurantId: [
     *              "668bb3c5-72b3-4db8-861a-80ba12a14865",
     *              "c6eb4717-a99c-43f5-9712-8d7b3490de2c
     *          ]
     *      },
     *      {
     *          "id": "4bf12130-fc37-4052-ac60-31442228484d",
     *          "title": "title",
     *          "price": "2.50",
     *          "restaurantId: [
     *              "668bb3c5-72b3-4db8-861a-80ba12a14865",
     *              "c6eb4717-a99c-43f5-9712-8d7b3490de2c
     *          ]
     *      }
     * ]}
     * @param id restaurant id
     * @return list of {@link MenuTo}
     */
    @GetMapping(value = "/{id}/menus")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<MenuTo> getMenuByRestaurant(@PathVariable String id) {
        return menuRepository.getMenusById(id)
            .stream()
            .map(MenuTo::new)
            .collect(Collectors.toList());
    }
}
