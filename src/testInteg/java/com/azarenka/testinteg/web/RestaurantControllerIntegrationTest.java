package com.azarenka.testinteg.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.azarenka.votingsystem.VotingSystemApplication;
import com.azarenka.votingsystem.service.auth.TokenProvider;
import com.azarenka.votingsystem.util.TimeUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Test of restaurant controller.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 25.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantControllerIntegrationTest extends WebTestHelper{

    private final static String RESTAURANT_ID_1 = "fd7ffad2-426c-42fe-9908-f053a882a4f7";
    private final static String RESTAURANT_ID_2 = "886d694b-3219-4998-a60f-64c54778f4a8";
    private final static String PREFIX_RESTAURANT_URL = "/restaurants/";

    @Before
    public void setUp() {
        createAuthenticationHeader();
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL("/restaurants"), 0).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-restaurants.json"), true));
    }

    @Test
    public void testSaveVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_1), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isAccepted())
            .andExpect(content().json("{\"message\": \"Voted successfully\"}"));
    }

    @Test
    public void testGetMenuByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_1 + "/menu"), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-menu-by-restaurant.json")));
    }

    @Test
    public void testGetVotesById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_1 + "/votes"), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-restaurant-votes.json")));
    }

    @Test
    public void testGetZeroVotesById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_2 + "/votes"), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-restaurant-votes-zero.json"), true));
    }

    @Test
    public void testGetHistoryMenuByRestaurantIdAndDate() throws Exception {
        String currentDate = TimeUtil.dateToString(LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_2 + "/history/" + currentDate), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-history-menu.json")));
    }

    @Test
    public void testGetVotesByIdAndDate() throws Exception {
        String currentDate = TimeUtil.dateToString(LocalDateTime.now());
        mockMvc.perform(MockMvcRequestBuilders
            .get(createURL(PREFIX_RESTAURANT_URL + RESTAURANT_ID_1 + "/votes/" + currentDate), 1).headers(headers))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(loadJsonFile("expected-restaurant-votes.json")));
    }
}
