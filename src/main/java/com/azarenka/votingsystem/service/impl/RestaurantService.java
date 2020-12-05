package com.azarenka.votingsystem.service.impl;

import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.Vote;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IUserRepository;
import com.azarenka.votingsystem.repository.IVoteRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.ResponseMessage;
import com.azarenka.votingsystem.util.KeyGenerator;
import com.azarenka.votingsystem.util.TimeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link IRestaurantService}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 04.12.2020
 */
@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IVoteRepository voteRepository;

    @Transactional
    public ResponseMessage toVote(String restaurantId) {
        User user = userRepository.getByEmail(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
        List<Vote> votes = voteRepository.findByUserId(user.getId());
        Vote todayVote = getTodayVote(votes);
        if (Objects.nonNull(todayVote)) {
            LocalTime time = LocalTime.of(11, 0);
            if (time.isAfter(getVotedTime(todayVote))) {
                todayVote.setUpdatedUser(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
                todayVote.setUpdatedDate(LocalDateTime.now());
                voteRepository.save(todayVote);
                return new ResponseMessage("Voted successfully");
            }
        } else {
            Vote vote = new Vote(user.getId(), restaurantId);
            vote.setId(KeyGenerator.generateUuid());
            vote.setCreatedUser(user.getEmail());
            vote.setCreatedDate(LocalDateTime.now());
            voteRepository.save(vote);
            return new ResponseMessage("Voted successfully");
        }
        return new ResponseMessage("Something went wrong");
    }

    private Vote getTodayVote(List<Vote> votes) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return votes.stream()
            .filter(vote -> TimeUtil.dateToString(localDateTime).equalsIgnoreCase(
                TimeUtil.dateToString(vote.getCreatedDate()))).findFirst().orElse(null);

    }

    private LocalTime getVotedTime(Vote vote) {
        return Objects.isNull(vote.getUpdatedDate())
            ? vote.getCreatedDate().toLocalTime()
            : vote.getUpdatedDate().toLocalTime();
    }
}
