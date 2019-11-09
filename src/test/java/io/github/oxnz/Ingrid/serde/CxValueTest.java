package io.github.oxnz.Ingrid.serde;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class CxValueTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
//            .enableDefaultTyping()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @Test
    public void serde() throws IOException {
        Map<String, Object> user =new HashMap<>();
        user.putAll(Map.of("name", "Max.Z", "age", 10000L));
        Map<String, String> addr = new HashMap<>();
        addr.putAll(Map.of("state", "CA", "city", "LA"));
        user.put("address", addr);
        String s = OBJECT_MAPPER.writeValueAsString(user);
        System.out.println("S:" + s);
        user = OBJECT_MAPPER.readValue(s, HashMap.class);
        System.out.println(user);
    }

}