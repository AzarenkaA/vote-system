package com.azarenka.votingsystem.service.validator;

import com.azarenka.votingsystem.repository.IMenuRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.to.VoteTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IMenuRepository menuRepository;
    @Autowired
    private IRestaurantRepository restaurantRepository;

    public boolean checkVote(VoteTo voteTo) {
        if (restaurantRepository.findById(voteTo.getRestaurantId()).isPresent()
            && menuRepository.findById(voteTo.getUserId()).isPresent()) {
            return true;
        }
        return false;
    }
}
