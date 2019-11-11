package io.github.oxnz.Ingrid.leetcode

object Solution {
  def twoSum(nums: Array[Int], target: Int): Array[Int] = {
    val index = nums.zipWithIndex.toMap
    val m = nums.find(n => index.contains(target-n)).get
    Array(index(target - m), index(m))
  }
}
