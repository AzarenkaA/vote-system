package com.azarenka.votingsystem.service.validator.menu;

import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.to.MenuTo;

import java.util.Objects;

import sun.security.validator.ValidatorException;

/**
 * Validator for insert menu data.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 02.12.2020
 */
public class InsertMenuValidator {

    private IMenuRepository menuRepository;

    /**
     * Constructor.
     *
     * @param menuRepository
     */
    public InsertMenuValidator(IMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean validate(MenuTo menuTo) throws ValidatorException {
        if (validateFieldsToNull(menuTo) && menuIsExist(menuTo)) {
            return true;
        }
        throw new ValidatorException("Json has not valid data");
    }

    private boolean validateFieldsToNull(MenuTo menuTo) {
        return Objects.nonNull(menuTo.getRestaurantsIds())
            && Objects.nonNull(menuTo.getTitle())
            && Objects.nonNull(menuTo.getPrice());
    }

    private boolean menuIsExist(MenuTo menuTo) {
        if (Objects.nonNull(menuTo.getTitle())) {
            return menuRepository.findById(menuTo.getId()).isPresent()
                && menuRepository.findByTitle(menuTo.getTitle()).isPresent();
        }
        return false;
    }
}
