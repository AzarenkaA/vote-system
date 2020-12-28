package com.azarenka.testinteg.web;

import com.azarenka.votingsystem.VotingSystemApplication;
import com.azarenka.votingsystem.service.auth.TokenProvider;

import org.junit.Before;
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
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Description
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 26.12.2020
 */
@SpringBootTest(classes = VotingSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:changelog/web/web-test-data.xml")
@AutoConfigureMockMvc
public abstract class WebTestHelper {

    protected final HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;
    @Value("${app.admin.username}")
    private String username;
    @Value("${app.admin.password}")
    private String password;
    @Value("${app.test.json_file_path}")
    private String filePath;
    @Value("${app.test.host}")
    private String host;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        createAuthenticationHeader();
    }

    protected String createURL(String uri) {
        return host + port + uri;
    }

    protected void createAuthenticationHeader() {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authority = "Bearer " + tokenProvider.generateJwtToken(authentication);
        headers.add("authorization", authority);
    }

    protected String loadJsonFile(String fileName) throws IOException {
        File jsonFile = new File(filePath + fileName).getAbsoluteFile();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        while (reader.ready()) {
            builder.append(reader.readLine());
        }
        return builder.toString();
    }
}
