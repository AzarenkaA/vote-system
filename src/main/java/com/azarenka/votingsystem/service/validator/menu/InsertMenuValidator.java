package com.azarenka.votingsystem.service.validator.menu;

import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.to.MealTo;

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

    private final IMealRepository menuRepository;

    /**
     * Constructor.
     *
     * @param menuRepository instance of {@link IMealRepository}
     */
    public InsertMenuValidator(IMealRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean validate(MealTo mealTo) throws ValidatorException {
        if (validateFieldsToNull(mealTo) && menuIsExist(mealTo)) {
            return true;
        }
        throw new ValidatorException("Json has not valid data");
    }

    private boolean validateFieldsToNull(MealTo mealTo) {
        return Objects.nonNull(mealTo.getRestaurantsIds())
            && Objects.nonNull(mealTo.getTitle())
            && Objects.nonNull(mealTo.getPrice())
            && mealTo.getRestaurantsIds().size() > 0;
    }

    private boolean menuIsExist(MealTo mealTo) {
        if (Objects.nonNull(mealTo.getTitle())) {
            return !menuRepository.findById(mealTo.getId()).isPresent()
                && !menuRepository.findByTitle(mealTo.getTitle()).isPresent();
        }
        return false;
    }
}
