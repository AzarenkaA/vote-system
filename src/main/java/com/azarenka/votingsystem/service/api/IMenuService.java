package com.azarenka.votingsystem.service.api;

import com.azarenka.votingsystem.to.MenuTo;

import java.util.List;

/**
 * Interface for menu service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
public interface IMenuService {

    MenuTo save(MenuTo menuTo);

    MenuTo update(MenuTo menuTo);
}
