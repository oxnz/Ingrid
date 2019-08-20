package io.github.oxnz.Ingrid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oxnz.Ingrid.user.User;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private static final String KEY = "user";
    private ObjectMapper objectMapper;
    private StringRedisTemplate redisTemplate;
    private HashOperations hashOperations;

    public UserRepository(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    private User readUser(String s) {
        try {
            return objectMapper.readValue(s, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String writeUser(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(long id) {
        String s = (String) hashOperations.get(KEY, String.valueOf(id));
        return readUser(s);
    }

    public List<User> list() {
        List<String> ss = hashOperations.values(KEY);
        return ss
                .stream()
                .map(this::readUser)
                .collect(Collectors.toList());
    }

    public void put(User user) {
        String s = writeUser(user);
        hashOperations.put(KEY, String.valueOf(user.id()), s);
    }

}
