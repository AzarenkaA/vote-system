package com.azarenka.testinteg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.azarenka.votingsystem.domain.Meal;
import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.domain.RestaurantAudit;
import com.azarenka.votingsystem.repository.IRestaurantAuditRepository;
import com.azarenka.votingsystem.util.TimeUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

/**
 * Test for Restaurant Audit Repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 24.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:changelog/audit-test-data.xml")
public class RestaurantAuditRepositoryIntegrationTest {

    @Resource
    private IRestaurantAuditRepository auditRepository;

    @Test
    public void testGetByDateAndRestaurantId() {
        RestaurantAudit actualAudit =
            auditRepository.getByDateAndRestaurantId(TimeUtil.getDate(TimeUtil.dateToString(LocalDateTime.now())),
                "fd7ffad2-426c-42fe-9908-f053a882a4f7");
        assertNotNull(actualAudit);
    }

    @Test
    public void testGetAllByDate() {
        Set<RestaurantAudit> actualAudit = auditRepository.getAllByDate(TimeUtil.getDate("2020-12-12"));
        assertEquals(1, actualAudit.size());
    }

    @Test
    public void testSaveAll() {
        RestaurantAudit audit = new RestaurantAudit();
        audit.setId("aa4819cf-a87f-4af1-bd7c-2eb6acb3bd5b");
        audit.setDate(TimeUtil.getDate("2020-12-01"));
        audit.setRestaurant(buildRestaurant());
        audit.setHistoryMeals(buildMeals());
        audit.setCreatedUser("SYSTEM");
        assertEquals(0, auditRepository.getAllByDate(TimeUtil.getDate("2020-12-01")).size());
        Set<RestaurantAudit> auditSet = new HashSet<>();
        auditSet.add(audit);
        auditRepository.saveAll(auditSet);
        assertEquals(1, auditRepository.getAllByDate(TimeUtil.getDate("2020-12-01")).size());
    }

    private Set<Meal> buildMeals() {
        Set<Meal> expectedMeals = new HashSet<>();
        expectedMeals.add(buildMeal("59b80d53-7a70-4a97-835d-2154187eeebb"));
        expectedMeals.add(buildMeal("a6a84e78-c667-4346-9c44-0c44d2782f4d"));
        return expectedMeals;
    }

    private Meal buildMeal(String id) {
        Meal meal = new Meal();
        meal.setId(id);
        return null;
    }

    private Restaurant buildRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("15fa1690-9522-4a00-938f-c4a6d4e3cf73");
        return restaurant;
    }
}
