package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.domain.Menu;
import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.ResponseMessage;

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

import javax.validation.Valid;
import javax.xml.ws.Response;

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
     *
     * @return list of {@link Restaurant}
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // @PreAuthorize("hasAnyRole('USER_ROLE', 'ADMIN')")
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}")
    @PreAuthorize("@voteValidator.checkData(#id)")
    public ResponseEntity<?> vote(@Valid @PathVariable String id) {
        restaurantService.toVote(id);
        return new ResponseEntity<>(new ResponseMessage("User voted successfully"), HttpStatus.ACCEPTED);
    }

    /**
     * Gets all menus by restaurant id.
     *
     * @param id restaurant id
     * @return
     */
    @GetMapping(value = "/{id}/menu/all")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Menu> getMenuByRestaurant(@PathVariable String id) {
        return menuRepository.getMenusById(id);
    }
}
