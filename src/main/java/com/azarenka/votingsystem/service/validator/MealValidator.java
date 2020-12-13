package com.azarenka.votingsystem.service.validator;

import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.service.validator.menu.InsertMenuValidator;
import com.azarenka.votingsystem.service.validator.menu.UpdateMenuValidator;
import com.azarenka.votingsystem.to.MealTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

import sun.security.validator.ValidatorException;

/**
 * Validator of data.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@Service("menuValidator")
public class MealValidator {

    @Autowired
    private IMealRepository menuRepository;

    public boolean checkInsertMenuData(MealTo mealTo, String id) throws ValidatorException {
        mealTo.setRestaurantsIds(Collections.singleton(id));
        InsertMenuValidator insertMenuValidator = new InsertMenuValidator(menuRepository);
        return insertMenuValidator.validate(mealTo);
    }

    public boolean checkInsertMenuDataWithMultipleRest(MealTo mealTo) throws ValidatorException {
        InsertMenuValidator insertMenuValidator = new InsertMenuValidator(menuRepository);
        return insertMenuValidator.validate(mealTo);
    }

    public boolean checkUpdateMenuDataWithMultipleRest(MealTo mealTo) throws ValidatorException {
        UpdateMenuValidator insertMenuValidator = new UpdateMenuValidator(menuRepository);
        return insertMenuValidator.validate(mealTo);
    }
}
