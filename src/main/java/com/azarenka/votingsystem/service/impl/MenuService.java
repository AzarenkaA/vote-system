package com.azarenka.votingsystem.service.impl;

import com.azarenka.votingsystem.domain.Menu;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.service.api.IMenuService;
import com.azarenka.votingsystem.to.MenuTo;
import com.azarenka.votingsystem.util.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IMenuService}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@Service
public class MenuService implements IMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private IMenuRepository menuRepository;
    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Override
    public MenuTo save(MenuTo menuTo) {
        LOGGER.info("Start create menu with title {} ", menuTo.getTitle());
        menuTo.setId(KeyGenerator.generateUuid());
        Menu menu = new Menu(menuTo);
        Set<String> restaurantsIds = menuTo.getRestaurantsIds();
        if (Objects.nonNull(restaurantsIds)) {
            menu.setRestaurants(restaurantRepository.findAllById(menuTo.getRestaurantsIds()));
        }
        MenuTo createdMenu = new MenuTo(menuRepository.save(menu));
        LOGGER.info("Menu has been created with id {} ", menu.getTitle());
        return createdMenu;
    }

    @Override
    public MenuTo update(MenuTo menuTo) {
        LOGGER.info("Start update menu with id {} ", menuTo.getId());
        Menu menu = new Menu(menuTo);
        menu.setRestaurants(restaurantRepository.findAllById(menuTo.getRestaurantsIds()));
        menu.setUpdatedUser(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
        menu.setUpdatedDate(LocalDateTime.now());
        MenuTo updatedMenu = new MenuTo(menuRepository.save(menu));
        LOGGER.info("Menu has been updated with id{} ", menu.getId());
        return updatedMenu;
    }
}
