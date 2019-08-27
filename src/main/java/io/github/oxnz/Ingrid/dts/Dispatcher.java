package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Dispatcher {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, Set<DestSpec>> dispatches = new HashMap<>();

    private String dispatchKey(String state, String city) {
        Objects.requireNonNull(state, "state should no be null");
        Objects.requireNonNull(city, "city should not be null");
        return String.join("/", state, city);
    }

    private Set<String> dispatchKeys(String state, String city) {
        return Set.of(
                dispatchKey("", ""),
                dispatchKey(state, ""),
                dispatchKey(state, city));

    }

    public void register(DestSpec destSpec) {
        Objects.requireNonNull(destSpec, "destSpec should not be null");
        Class<? extends DestSpec> clazz = destSpec.getClass();
        Assert.isTrue(clazz.isAnnotationPresent(Region.class), "region annotation required");
        Region region = clazz.getAnnotation(Region.class);
        String key = dispatchKey(region.state(), region.city());
        if (!dispatches.containsKey(key))
            dispatches.put(key, new HashSet<>());
        dispatches.get(key).add(destSpec);
    }

    public Set<DestSpec> dispatch(TxRecord record) {
        Objects.requireNonNull(record, "record should not be null");
        log.debug("dispatch: {}", record);
        return dispatch(record.getState(), record.getCity(), record.getCat());
    }

    public Set<DestSpec> dispatch(String state, String city, TxCategory cat) {
        Objects.requireNonNull(cat, "category should not be null");
        Set<String> keys = dispatchKeys(state, city);
        return keys.stream()
                .filter(dispatches::containsKey)
                .map(dispatches::get)
                .flatMap(Set::stream)
                .filter(destSpec -> destSpec.isInterested(cat))
                .collect(Collectors.toSet());
    }
}
