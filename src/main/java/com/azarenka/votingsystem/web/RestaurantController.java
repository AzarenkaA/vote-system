package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.repository.IRestaurantAuditRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.MealTo;
import com.azarenka.votingsystem.to.ResponseMessage;
import com.azarenka.votingsystem.to.RestaurantTo;
import com.azarenka.votingsystem.util.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private IRestaurantAuditRepository restaurantAuditRepository;
    @Autowired
    private IMealRepository menuRepository;

    /**
     * Returns all restaurants.
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
     * Saves vote of user.
     *
     * @param id unique identifier of {@link Restaurant}
     * @return instance of {@link ResponseEntity}
     */
    @PostMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN') and" + "@voteValidator.checkData(#id)")
    public ResponseEntity<?> vote(@Valid @PathVariable String id) {
        return new ResponseEntity<>(restaurantService.toVote(id), HttpStatus.ACCEPTED);
    }

    /**
     * Returns all menus by restaurant id.
     *
     * @param id restaurant id
     * @return list of {@link MealTo}
     */
    @GetMapping(value = "/{id}/menu")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<MealTo> getMenuByRestaurant(@PathVariable("id") String id) {
        return menuRepository.getMenusById(id)
            .stream()
            .map(MealTo::new)
            .collect(Collectors.toList());
    }

    /**
     * Saves one menu by restaurant id.
     *
     * @param id   id of {@link Restaurant}
     * @param menu instance of {@link MealTo}
     * @return instance of {@link ResponseEntity}
     */
    @PostMapping(value = "/{id}/menu")
    @PreAuthorize("hasRole('ROLE_ADMIN') and" + "@menuValidator.checkInsertMenuData(#menu, #id)")
    public ResponseEntity<?> saveRestaurantsMenu(@PathVariable("id") String id, @Valid @RequestBody MealTo menu) {
        restaurantService.save(menu);
        return new ResponseEntity<>(new ResponseMessage("Created"), HttpStatus.CREATED);
    }

    /**
     * @param id
     * @param date
     * @return
     */
    @GetMapping(value = "/{id}/history/{date}")
    public ResponseEntity<?> getHistoryMenuByRestaurantIdAndDate(@PathVariable("id") String id, @PathVariable("date")
        String date) {
        return new ResponseEntity<>(restaurantAuditRepository.getByDateAndRestaurantsId(TimeUtil.getDate(date), id),
            HttpStatus.OK);
    }
}
