package com.azarenka.votingsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

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
import com.azarenka.votingsystem.to.MealTo;
import com.azarenka.votingsystem.util.KeyGenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(PowerMockRunner.class)
@PrepareForTest({KeyGenerator.class, UserPrincipal.class})
public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;
    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IMealRepository mealRepository;
    @Mock
    private IRestaurantAuditRepository auditRepository;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IVoteRepository voteRepository;
    @Mock
    private UserPrincipal userPrincipal;

    @Test
    public void testToVote() {
        mockStatic(KeyGenerator.class);
        mockStatic(UserPrincipal.class);
        User user = new User();
        user.setEmail("Admin@mail.ru");
        user.setId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        when(UserPrincipal.safeGet()).thenReturn(userPrincipal);
        when(userPrincipal.getEmail()).thenReturn("Admin@mail.ru");
        when(userRepository.getByEmail("Admin@mail.ru")).thenReturn(user);
        when(voteRepository.findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63")).thenReturn(Collections.EMPTY_LIST);
        Vote vote = buildVote(LocalDateTime.now(), null);
        vote.setUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        vote.setCreatedUser("Admin@mail.ru");
        when(voteRepository.save(vote)).thenReturn(vote);
        restaurantService.toVote("74a94833-0504-43be-b64c-49456392969a");
        verify(voteRepository).findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        verify(voteRepository).save(vote);
    }

    @Test
    public void testToUpdateVote() {
        mockStatic(KeyGenerator.class);
        mockStatic(UserPrincipal.class);
        User user = new User();
        user.setEmail("Admin@mail.ru");
        user.setId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        when(UserPrincipal.safeGet()).thenReturn(userPrincipal);
        when(userPrincipal.getEmail()).thenReturn("Admin@mail.ru");
        when(userRepository.getByEmail("Admin@mail.ru")).thenReturn(user);
        LocalTime time = LocalTime.of(10, 0);
        List<Vote> votes = buildVotes(LocalDateTime.of(LocalDate.now(), time), null);
        when(voteRepository.findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63")).thenReturn(votes);
        Vote vote = buildVote(LocalDateTime.of(LocalDate.now(), time), null);
        vote.setUpdatedDate(LocalDateTime.now());
        vote.setUpdatedUser("Admin@mail.ru");
        when(voteRepository.save(votes.get(0))).thenReturn(votes.get(0));
        restaurantService.toVote("74a94833-0504-43be-b64c-49456392969a");
        verify(voteRepository).findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        verify(voteRepository).save(vote);
    }

    @Test
    public void testToVoteAfterTime() {
        mockStatic(KeyGenerator.class);
        mockStatic(UserPrincipal.class);
        User user = new User();
        user.setEmail("Admin@mail.ru");
        user.setId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        when(UserPrincipal.safeGet()).thenReturn(userPrincipal);
        when(userPrincipal.getEmail()).thenReturn("Admin@mail.ru");
        when(userRepository.getByEmail("Admin@mail.ru")).thenReturn(user);
        LocalTime time = LocalTime.of(12, 0);
        List<Vote> votes = buildVotes(LocalDateTime.of(LocalDate.now(), time), LocalDateTime.of(LocalDate.now(), time));
        when(voteRepository.findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63")).thenReturn(votes);
        Vote vote = buildVote(LocalDateTime.now(), null);
        vote.setUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
        vote.setCreatedUser("Admin@mail.ru");
        restaurantService.toVote("74a94833-0504-43be-b64c-49456392969a");
        verify(voteRepository).findByUserId("0ab6bb94-e9d2-4c75-9806-ac78735ebc63");
    }

    @Test
    public void testSave() {
        mockStatic(KeyGenerator.class);
        when(KeyGenerator.generateUuid()).thenReturn("306faca2-2663-401e-b06e-5ff4c98c6b35");
        Meal expectedMeal = buildMeal();
        MealTo mealTo = buildMealTo();
        Meal actualMeal = new Meal(mealTo);
        List<Restaurant> restaurants = Collections.singletonList(buildRestaurant());
        actualMeal.setRestaurants(restaurants);
        assertEquals(expectedMeal, actualMeal);
        when(restaurantRepository.findAllById(mealTo.getRestaurantsIds())).thenReturn(restaurants);
        when(mealRepository.save(actualMeal)).thenReturn(actualMeal);
        Set<RestaurantAudit> expectedRestAudit = buildRestaurantsAudit();
        when(auditRepository.saveAll(expectedRestAudit)).thenReturn(new ArrayList<>(buildRestaurantsAudit()));
        restaurantService.save(mealTo);
        verify(restaurantRepository).findAllById(Collections.singleton("d52a0f16-665a-4134-9bd9-2a6722ef15ed"));
        verify(mealRepository).save(actualMeal);
        verify(auditRepository).saveAll(expectedRestAudit);
    }

    @Test
    public void testUpdate() {
        mockStatic(KeyGenerator.class);
        mockStatic(UserPrincipal.class);
        when(KeyGenerator.generateUuid()).thenReturn("306faca2-2663-401e-b06e-5ff4c98c6b35");
        Meal expectedMeal = buildMeal();
        MealTo mealTo = buildMealTo();
        Meal actualMeal = new Meal(mealTo);
        List<Restaurant> restaurants = Collections.singletonList(buildRestaurant());
        actualMeal.setRestaurants(restaurants);
        assertEquals(expectedMeal, actualMeal);
        when(restaurantRepository.findAllById(mealTo.getRestaurantsIds())).thenReturn(restaurants);
        when(mealRepository.save(actualMeal)).thenReturn(actualMeal);
        Set<RestaurantAudit> expectedRestAudit = buildRestaurantsAudit();
        when(auditRepository.saveAll(expectedRestAudit)).thenReturn(new ArrayList<>(buildRestaurantsAudit()));
        restaurantService.save(mealTo);
        verify(restaurantRepository).findAllById(Collections.singleton("d52a0f16-665a-4134-9bd9-2a6722ef15ed"));
        verify(mealRepository).save(actualMeal);
        verify(auditRepository).saveAll(expectedRestAudit);
    }

    @Test
    public void testGetVotesByRestaurantId() {
        List<Vote> votes = buildVotes(LocalDateTime.now(), null);
        when(voteRepository.findByRestaurantId("74a94833-0504-43be-b64c-49456392969a")).thenReturn(votes);
        Optional<Restaurant> optionalRestaurant = Optional.of(buildRestaurant());
        when(restaurantRepository.findById("74a94833-0504-43be-b64c-49456392969a")).thenReturn(optionalRestaurant);
        restaurantService.getVotesByRestaurantId("74a94833-0504-43be-b64c-49456392969a");
        verify(voteRepository).findByRestaurantId("74a94833-0504-43be-b64c-49456392969a");
        verify(restaurantRepository);
    }

    private MealTo buildMealTo() {
        MealTo mealTo = new MealTo();
        mealTo.setId("306faca2-2663-401e-b06e-5ff4c98c6b35");
        mealTo.setTitle("Title");
        mealTo.setPrice(new BigDecimal("25.5"));
        mealTo.setRestaurantsIds(Collections.singleton("d52a0f16-665a-4134-9bd9-2a6722ef15ed"));
        return mealTo;
    }

    private Meal buildMeal() {
        Meal meal = new Meal();
        meal.setId("306faca2-2663-401e-b06e-5ff4c98c6b35");
        meal.setTitle("Title");
        meal.setPrice(new BigDecimal("25.5"));
        meal.setRestaurants(Collections.singleton(buildRestaurant()));
        return meal;
    }

    private List<Vote> buildVotes(LocalDateTime createdDate, LocalDateTime updatedDate) {
        return Collections.singletonList(buildVote(createdDate, updatedDate));
    }

    private Vote buildVote(LocalDateTime createdDate, LocalDateTime updatedDate) {
        Vote vote = new Vote();
        vote.setRestaurantId("74a94833-0504-43be-b64c-49456392969a");
        vote.setCreatedDate(createdDate);
        vote.setUpdatedDate(updatedDate);
        return vote;
    }

    private Restaurant buildRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("d52a0f16-665a-4134-9bd9-2a6722ef15ed");
        restaurant.setTitle("rest title");
        return restaurant;
    }

    private Set<RestaurantAudit> buildRestaurantsAudit() {
        RestaurantAudit audit = new RestaurantAudit();
        audit.setId("306faca2-2663-401e-b06e-5ff4c98c6b35");
        Meal meal = buildMeal();
        meal.setId("306faca2-2663-401e-b06e-5ff4c98c6b35");
        audit.setHistoryMeals(Collections.singleton(meal));
        audit.setDate(LocalDate.now());
        audit.setRestaurant(buildRestaurant());
        return new HashSet<>(Collections.singleton(audit));
    }
}
