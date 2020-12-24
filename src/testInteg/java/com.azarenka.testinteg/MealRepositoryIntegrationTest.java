package com.azarenka.testinteg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.azarenka.votingsystem.domain.BaseEntity;
import com.azarenka.votingsystem.domain.Meal;
import com.azarenka.votingsystem.repository.IMealRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;

/**
 * Test for Meal Repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 23.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MealRepositoryIntegrationTest {

    @Resource
    private IMealRepository mealRepository;

    @Test
    public void testGetMenusById() {
        List<Meal> expectedMeals = Arrays.asList(
            buildMeal("59b80d53-7a70-4a97-835d-2154187eeebb", "Салат из капусты", "2.50"),
            buildMeal("dd234ebe-0f35-4a49-9433-c7359be1e299", "Салат ореховый", "4.44"),
            buildMeal("b50009f2-ede7-4d33-9038-d307d8865ccb", "Ципилины", "7.99"),
            buildMeal("a2a0021a-4851-453a-bef5-eb7e40112bdc", "Картофельное пюре", "1.99"),
            buildMeal("5a7f36f9-9dd9-48e7-9be4-60103c3d862c", "Суп из курицы", "2.00"),
            buildMeal("a1490a58-b5e5-4264-ac5e-286f655679ed", "Кола", "5.00"),
            buildMeal("53f2216f-f64d-4c81-abb4-9298fca97828", "Кофе", "3.45"),
            buildMeal("d5f1696a-d89c-4112-9f10-5df048e683a6", "Мясо по французки", "10.50"),
            buildMeal("a6a84e78-c667-4346-9c44-0c44d2782f4d", "Салат из море продуктов", "10.30"));
        List<Meal> actualMeal = mealRepository.getMenusById("fd7ffad2-426c-42fe-9908-f053a882a4f7");
        expectedMeals.sort(Comparator.comparing(BaseEntity::getId));
        actualMeal.sort(Comparator.comparing(BaseEntity::getId));
        assertEquals(expectedMeals.size(), actualMeal.size());
        IntStream.range(0, expectedMeals.size()).forEach(i -> {
            verifyMeal(expectedMeals.get(i), actualMeal.get(i));
        });
    }

    @Test
    public void testFindByTitle() {
        assertTrue(mealRepository.findByTitle("Салат из капусты").isPresent());
        assertTrue(mealRepository.findByTitle("Салат из море продуктов").isPresent());
        assertTrue(mealRepository.findByTitle("Салат из мяса птицы").isPresent());
        assertTrue(mealRepository.findByTitle("Салат ореховый").isPresent());
        assertTrue(mealRepository.findByTitle("Селдь под шубой").isPresent());
        assertTrue(mealRepository.findByTitle("Драники").isPresent());
        assertTrue(mealRepository.findByTitle("Картофельное пюре").isPresent());
        assertTrue(mealRepository.findByTitle("Жареный картофель").isPresent());
        assertTrue(mealRepository.findByTitle("Макароны отварные").isPresent());
        assertTrue(mealRepository.findByTitle("Овощи тушеные").isPresent());
        assertTrue(mealRepository.findByTitle("Суп из курицы").isPresent());
        assertTrue(mealRepository.findByTitle("Борщ").isPresent());
        assertTrue(mealRepository.findByTitle("Холодник").isPresent());
        assertTrue(mealRepository.findByTitle("Рассольник").isPresent());
        assertTrue(mealRepository.findByTitle("Щи").isPresent());
        assertTrue(mealRepository.findByTitle("Мясо по французки").isPresent());
        assertTrue(mealRepository.findByTitle("Ципилины").isPresent());
        assertTrue(mealRepository.findByTitle("Филе курицы в сыре").isPresent());
        assertTrue(mealRepository.findByTitle("Биточки из свиннины").isPresent());
        assertTrue(mealRepository.findByTitle("Стрипсы").isPresent());
        assertTrue(mealRepository.findByTitle("БигМак").isPresent());
        assertTrue(mealRepository.findByTitle("Цезарь ролл").isPresent());
        assertTrue(mealRepository.findByTitle("Кола").isPresent());
        assertTrue(mealRepository.findByTitle("Кофе").isPresent());
    }

    private Meal buildMeal(String id, String title, String price) {
        Meal meal = new Meal();
        meal.setId(id);
        meal.setTitle(title);
        meal.setPrice(new BigDecimal(price));
        return meal;
    }

    private void verifyMeal(Meal expectedMeal, Meal actualMeal) {
        assertEquals(expectedMeal, actualMeal);
    }
}
