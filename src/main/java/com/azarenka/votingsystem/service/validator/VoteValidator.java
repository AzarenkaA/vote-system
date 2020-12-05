package com.azarenka.votingsystem.service.validator;

import com.azarenka.votingsystem.repository.IRestaurantRepository;

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
 * Date 02.12.2020
 */
@Service("voteValidator")
public class VoteValidator {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    public boolean checkData(String restaurantId) throws ValidatorException {
        if (restaurantRepository.findById(restaurantId).isPresent()) {
            return true;
        }
        throw new ValidatorException("Json has not valid data");
    }
}
