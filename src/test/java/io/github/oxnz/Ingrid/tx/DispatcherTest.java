package io.github.oxnz.Ingrid.tx;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DispatcherTest {

    @Test
    public void dispatch() {
        Map<String, Set<String>> dispatches = new HashMap<>();
        Set<String> allDispatches = new HashSet<>();
        allDispatches.add("all-1");
        allDispatches.add("all-2");
        Set<String> stateDispatches = new HashSet<>();
        stateDispatches.add("state-1");
        stateDispatches.add("state-2");
        stateDispatches.add("state-3");
        Set<String> cityDisps = new HashSet<>();
        cityDisps.add("city-1");
        cityDisps.add("city-2");
        dispatches.put("/", allDispatches);
        dispatches.put("ca/", stateDispatches);
        dispatches.put("ca/sf", cityDisps);
        Set<String> keys = new HashSet<>();
        keys.add("/");
        keys.add("ca/");
        keys.add("ca/sf");
        List<String> disps = keys.stream().map(dispatches::get)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
        System.out.println(disps);
    }
}