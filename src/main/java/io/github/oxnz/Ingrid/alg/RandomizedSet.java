package io.github.oxnz.Ingrid.alg;

import java.util.*;

class RandomizedSet {

    private final Random random;
    private final Map<Integer, Integer> positions;
    private final List<Integer> list;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        random = new Random();
        positions = new HashMap<>();
        list = new ArrayList<>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (positions.containsKey(val)) return false;
        positions.put(val, list.size());
        list.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (! positions.containsKey(val)) return false;
        int pos = positions.get(val);
        int elem = list.get(list.size()-1);
        list.set(pos, elem);
        positions.put(elem, pos);
        positions.remove(val);
        list.remove(list.size()-1);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        int pos = random.nextInt(list.size());
        return list.get(pos);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
