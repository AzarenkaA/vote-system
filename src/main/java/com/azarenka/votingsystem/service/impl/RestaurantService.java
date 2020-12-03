package com.azarenka.votingsystem.service.impl;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.Vote;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IUserRepository;
import com.azarenka.votingsystem.repository.IVoteRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.util.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IVoteRepository voteRepository;

    public void toVote(String restaurantId) {
        User user = userRepository.getByEmail(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
        Vote vote = voteRepository.findByUserId(user.getId());
        if (Objects.nonNull(vote)) {
            System.out.println("ERROR");
        } else {
            vote = new Vote(user.getId(), restaurantId);
            vote.setId(KeyGenerator.generateUuid());
            vote.setCreatedUser(user.getEmail());
            vote.setCreatedDate(LocalDateTime.now());
            voteRepository.save(vote);
        }
    }
}
