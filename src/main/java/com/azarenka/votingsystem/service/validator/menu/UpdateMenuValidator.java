package com.azarenka.votingsystem.service.validator.menu;

import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.to.MenuTo;

import sun.security.validator.ValidatorException;

/**
 * Validator for update menu data.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 02.12.2020
 */
public class UpdateMenuValidator {

    private IMenuRepository menuRepository;

    /**
     * Constructor.
     *
     * @param menuRepository
     */
    public UpdateMenuValidator(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean validate(MenuTo menuTo) throws ValidatorException {
        if (validateFieldsToNull(menuTo) && menuIsExist(menuTo.getId())) {
            return true;
        }
        throw new ValidatorException("Json has not valid data");
    }

    private boolean menuIsExist(String menuId) {
        return menuRepository.findById(menuId).isPresent();
    }

    private boolean validateFieldsToNull(MenuTo menuTo) {
        return menuTo.getId() != null
            && menuTo.getRestaurantsIds() != null
            && menuTo.getTitle() != null
            && menuTo.getPrice() != null;
    }
}
