package com.azarenka.votingsystem.service.validator;

import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.service.validator.menu.InsertMenuValidator;
import com.azarenka.votingsystem.service.validator.menu.UpdateMenuValidator;
import com.azarenka.votingsystem.to.MenuTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.security.validator.ValidatorException;

/**
 * Validator data.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.11.2020
 */
@Service("menuValidator")
public class MenuValidator {

    @Autowired
    private IMenuRepository menuRepository;

    public boolean checkToInsertMenu(MenuTo menuTo) throws ValidatorException {
        InsertMenuValidator insertMenuValidator = new InsertMenuValidator(menuRepository);
        return insertMenuValidator.validate(menuTo);
    }

    public boolean checkToUpdateMenu(MenuTo menuTo) throws ValidatorException {
        UpdateMenuValidator insertMenuValidator = new UpdateMenuValidator(menuRepository);
        return insertMenuValidator.validate(menuTo);
    }
}
