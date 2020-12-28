package com.azarenka.testinteg.web;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.azarenka.votingsystem.VotingSystemApplication;
import com.azarenka.votingsystem.service.auth.TokenProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

/**
 * Test of meal controller.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 26.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MealControllerIntegrationTest extends WebTestHelper{

    private final static String PREFIX_MEAL_URL = "/menus/";

    @Test
    public void testSaveMenuByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .post(createURL(PREFIX_MEAL_URL), 1).contentType(
                MediaType.APPLICATION_JSON).content(loadJsonFile("save-menu.json")).headers(headers))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMenuByRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
            .put(createURL(PREFIX_MEAL_URL), 1).contentType(
                MediaType.APPLICATION_JSON).content(loadJsonFile("update-menu.json")).headers(headers))
            .andDo(print())
            .andExpect(status().isOk());
    }
}
