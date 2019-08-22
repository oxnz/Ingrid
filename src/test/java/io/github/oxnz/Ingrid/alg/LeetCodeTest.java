package io.github.oxnz.Ingrid.alg;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class LeetCodeTest {

    private final LeetCode leetCode = new LeetCode();

    @Test
    public void topKFrequent() {
        int[] nums = new int[]{1};
        int k = 1;
        int[] expecteds = new int[]{1};
        int[] actuals = leetCode.topKFrequent(nums, k).stream().mapToInt(Integer::intValue).toArray();
        assertArrayEquals(expecteds, actuals);

        nums = new int[]{1,1,1,2,2,3};
        k = 2;
        expecteds = new int[]{1, 2};
        actuals = leetCode.topKFrequent(nums, k).stream().mapToInt(Integer::intValue).toArray();
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void findKthLargest() {
        int[] nums = new int[]{3,2,1,5,6,4};
        int k = 2;
        int expected = 5;
        int actual = leetCode.findKthLargest(nums, k);
        assertEquals(expected, actual);

        nums = new int[]{3,2,3,1,2,4,5,5,6};
        k = 4;
        expected = 4;
        actual = leetCode.findKthLargest(nums, k);
        assertEquals(expected, actual);
    }
}