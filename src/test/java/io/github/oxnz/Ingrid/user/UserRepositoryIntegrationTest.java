package io.github.oxnz.Ingrid.user;

import io.github.oxnz.Ingrid.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@SpringApplicationConfiguration(classes = Application.class)
//@WebIntegrationTest
public class UserRepositoryIntegrationTest {

    @Autowired private UserRepository userRepository;
    TestRestTemplate restTemplate = new TestRestTemplate();
    private final String baseURL = "http://localhost:8080/";

    @Test
    public void findOne() {
        long id = 123L;
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(baseURL + "user/{id}", null, User.class, id);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void save() {
    }

    @Test
    public void findAll() {
    }
}