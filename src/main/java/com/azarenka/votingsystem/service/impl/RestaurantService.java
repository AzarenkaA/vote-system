package com.azarenka.votingsystem.service.impl;

import com.azarenka.votingsystem.domain.Meal;
import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.domain.RestaurantAudit;
import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.Vote;
import com.azarenka.votingsystem.domain.auth.UserPrincipal;
import com.azarenka.votingsystem.repository.IMealRepository;
import com.azarenka.votingsystem.repository.IRestaurantAuditRepository;
import com.azarenka.votingsystem.repository.IRestaurantRepository;
import com.azarenka.votingsystem.repository.IUserRepository;
import com.azarenka.votingsystem.repository.IVoteRepository;
import com.azarenka.votingsystem.service.api.IRestaurantService;
import com.azarenka.votingsystem.to.MealTo;
import com.azarenka.votingsystem.to.ResponseMessage;
import com.azarenka.votingsystem.util.KeyGenerator;
import com.azarenka.votingsystem.util.TimeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IVoteRepository voteRepository;
    @Autowired
    private IRestaurantRepository restaurantRepository;
    @Autowired
    private IMealRepository mealRepository;
    @Autowired
    private IRestaurantAuditRepository auditRepository;

    @Transactional
    public ResponseMessage toVote(String restaurantId) {
        User user = userRepository.getByEmail(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
        List<Vote> votes = voteRepository.findByUserId(user.getId());
        Vote todayVote = getTodayVote(votes);
        if (Objects.nonNull(todayVote)) {
            LocalTime time = LocalTime.of(11, 0);
            if (time.isAfter(getVotedTime(todayVote))) {
                todayVote.setUpdatedUser(user.getEmail());
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

    @Override
    public MealTo save(MealTo mealTo) {
        LOGGER.info("Start create menu with title {} ", mealTo.getTitle());
        mealTo.setId(KeyGenerator.generateUuid());
        Meal meal = new Meal(mealTo);
        meal.setRestaurants(restaurantRepository.findAllById(mealTo.getRestaurantsIds()));
        Meal createdMenu = mealRepository.save(meal);
        writeAudit(createdMenu);
        LOGGER.info("Menu has been created with id {} ", meal.getTitle());
        return new MealTo(createdMenu);
    }

    @Override
    public MealTo update(MealTo mealTo) {
        LOGGER.info("Start update menu with id {} ", mealTo.getId());
        Meal meal = new Meal(mealTo);
        meal.setRestaurants(restaurantRepository.findAllById(mealTo.getRestaurantsIds()));
        meal.setUpdatedUser(Objects.requireNonNull(UserPrincipal.safeGet()).getEmail());
        meal.setUpdatedDate(LocalDateTime.now());
        meal.setRecordVersion(meal.getRecordVersion() + 1);
        MealTo updatedMenu = new MealTo(mealRepository.save(meal));
        Set<RestaurantAudit> auditByMenuId = auditRepository.getAuditByMenuId(meal.getId());
        //auditRepository.saveAll();
        LOGGER.info("Menu has been updated with id {} ", meal.getId());
        return updatedMenu;
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

    private void writeAudit(Meal meal) {
        Set<RestaurantAudit> auditSet = meal.getRestaurants()
            .stream()
            .map(this::createAudit)
            .peek(audit -> audit.setMeals(Collections.singleton(meal)))
            .collect(Collectors.toSet());
        auditRepository.saveAll(auditSet);
    }

    private RestaurantAudit createAudit(Restaurant restaurant) {
        RestaurantAudit audit = new RestaurantAudit();
        audit.setId(KeyGenerator.generateUuid());
        audit.setDate(LocalDate.now());
        audit.setRestaurants(restaurant);
        return audit;
    }
}
