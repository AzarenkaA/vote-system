package com.azarenka.votingsystem.service.validator.menu;

import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.to.MealTo;

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

    private final IMealRepository menuRepository;

    /**
     * Constructor.
     *
     * @param menuRepository instance of {@link IMealRepository}
     */
    public UpdateMenuValidator(IMealRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean validate(MealTo mealTo) throws ValidatorException {
        if (validateFieldsToNull(mealTo) && menuIsExist(mealTo.getId())) {
            return true;
        }
        throw new ValidatorException("Json has not valid data");
    }

    private boolean menuIsExist(String menuId) {
        return menuRepository.findById(menuId).isPresent();
    }

    private boolean validateFieldsToNull(MealTo mealTo) {
        return mealTo.getId() != null
            && mealTo.getRestaurantsIds() != null
            && mealTo.getTitle() != null
            && mealTo.getPrice() != null;
    }
}
