package io.github.oxnz.Ingrid.alg;

import java.util.*;
import java.util.stream.Collectors;

public class LeetCode {

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int n : nums) counter.put(n, counter.getOrDefault(n, 0) + 1);
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(counter::get).reversed());
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) pq.add(entry.getKey());
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; ++i) result.add(pq.poll());
        return result;
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < k; ++i) pq.add(nums[i]);
        for (int i = k; i < nums.length; ++i) {
            int n = nums[i];
            if (n > pq.peek()) {
                pq.poll();
                pq.add(n);
            }
        }
        return pq.peek();
    }

    public int thirdMax(int[] nums) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        if (nums.length < 3) return max(nums);
        pq.add(nums[0]);
        int i = 1;
        for (i = 1; i < nums.length && pq.size() < 3; ++i)
            if (!pq.contains(nums[i])) pq.add(nums[i]);
        for (; i < nums.length; ++i)
            if (nums[i] > pq.peek()) {
                pq.poll();
                pq.add(nums[i]);
            }
        return pq.peek();
    }

    private int max(int[] nums) {
        int m = nums[0];
        for (int n : nums)
            if (n > m) m = n;
        return m;
    }

    public String[] findOcurrences(String text, String first, String second) {
        String[] words = text.split(" ");
        List<String> result = new ArrayList<>();
        for (int i = 0; i + 2 < words.length; ++i) {
            if (words[i].equals(first) && words[i + 1].equals(second)) {
                result.add(words[i + 2]);
                i += 2;
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public boolean divisorGame(int N) {
        switch (N) {
            case 1:
                return false;
            case 2:
                return true;
            case 3:
                return false;
            case 4:
                return true;
            case 5:
                return false;
            case 6:
                return true;
            case 7:
                return false;
            case 8:
                return true;
        }
        return N % 2 == 0;
    }

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) return result;
        Set<TreeNode> currLevel = new HashSet<>();
        Set<TreeNode> nextLevel = new HashSet<>();
        currLevel.add(root);
        while (!currLevel.isEmpty()) {
            long sum = 0;
            for (TreeNode node : currLevel) {
                sum += node.val;
                if (node.left != null) nextLevel.add(node.left);
                if (node.right != null) nextLevel.add(node.right);
            }
            result.add(sum * 1.0 / currLevel.size());
            currLevel.clear();
            Set<TreeNode> t = currLevel;
            currLevel = nextLevel;
            nextLevel = t;
        }
        return result;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public String removeDuplicates(String s) {
        Deque<Character> deque = new LinkedList<>();
        for (Character c : s.toCharArray()) {
            if (deque.peekLast() == c) deque.removeLast();
            else deque.addLast(c);
        }
        return deque.stream().map(String::valueOf).collect(Collectors.joining());
    }
}
