package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxRecord;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Dispatcher {

    private final Map<String, Set<DestSpec>> dispatches = new HashMap<>();

    public List<DestSpec> dispatch(TxRecord record) {
        List<DestSpec> destSpecs = new ArrayList<>();
        destSpecs.add(new WuHanPSBDestSpec());
        return destSpecs;
    }

    private String dispatchKey(String state, String city) {
        if (state == null) state = "";
        if (city == null) city = "";
        return String.join("/", state, city);
    }

    private Set<String> dispatchKeys(String state, String city) {
        return Set.of(
                dispatchKey(null, null),
                dispatchKey(state, null),
                dispatchKey(state, city));

    }

    public void register(DestSpec destSpec) {
        Class<? extends DestSpec> clazz = destSpec.getClass();
        Assert.isTrue(clazz.isAnnotationPresent(Region.class), "region annotation is missing");
        Region region = clazz.getAnnotation(Region.class);
        String key = dispatchKey(region.state(), region.city());
        if (!dispatches.containsKey(key))
            dispatches.put(key, new HashSet<>());
        dispatches.get(key).add(destSpec);
    }

    public Set<DestSpec> dispatch(String state, String city) {
        Set<String> keys = dispatchKeys(state, city);
        return keys.stream().map(dispatches::get).flatMap(Set::stream).collect(Collectors.toSet());
    }
}
