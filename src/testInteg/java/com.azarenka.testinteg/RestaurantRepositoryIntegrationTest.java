package com.azarenka.testinteg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.azarenka.votingsystem.domain.Restaurant;
import com.azarenka.votingsystem.repository.IRestaurantRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

/**
 * Test for Restaurant Repository.
 *
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 17.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RestaurantRepositoryIntegrationTest {

    @Resource
    private IRestaurantRepository restaurantRepository;

    @Test
    public void testFindOneByIds() {
        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId("fd7ffad2-426c-42fe-9908-f053a882a4f7");
        expectedRestaurant.setTitle("Brevis");
        List<String> ids = Collections.singletonList("fd7ffad2-426c-42fe-9908-f053a882a4f7");
        List<Restaurant> allById = restaurantRepository.findAllById(ids);
        assertEquals(1, allById.size());
        verifyRestaurant(expectedRestaurant, allById.get(0));
    }

    @Test
    public void testFindALLByIds() {
        Restaurant expectedRestaurant = new Restaurant();
        expectedRestaurant.setId("fd7ffad2-426c-42fe-9908-f053a882a4f7");
        expectedRestaurant.setTitle("Brevis");
        List<String> ids = Arrays.asList(
            "fd7ffad2-426c-42fe-9908-f053a882a4f7", "0143508d-8658-4741-85cf-682a5d4bc344",
            "1d4e58fd-6cff-46ff-832f-4be66ee7948a", "19549004-fd0b-4a73-b7e3-4e2d73b846e7");
        List<Restaurant> allById = restaurantRepository.findAllById(ids);
        assertEquals(4, allById.size());
    }

    private void verifyRestaurant(Restaurant expectedRestaurant, Restaurant actualRestaurant) {
        assertEquals(expectedRestaurant.getId(), actualRestaurant.getId());
        assertEquals(expectedRestaurant.getTitle(), actualRestaurant.getTitle());
    }
}
