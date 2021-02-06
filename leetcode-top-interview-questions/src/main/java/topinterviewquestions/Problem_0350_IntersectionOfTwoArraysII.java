package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;

public class Problem_0350_IntersectionOfTwoArraysII {

	public static int[] intersect(int[] nums1, int[] nums2) {
		//建立nums1的词频表
		HashMap<Integer, Integer> map1 = new HashMap<>();
		for (int num : nums1) {
			if (!map1.containsKey(num)) {
				map1.put(num, 1);
			} else {
				map1.put(num, map1.get(num) + 1);
			}
		}
		//建立nums2的词频表
		HashMap<Integer, Integer> map2 = new HashMap<>();
		for (int num : nums2) {
			if (!map2.containsKey(num)) {
				map2.put(num, 1);
			} else {
				map2.put(num, map2.get(num) + 1);
			}
		}
		ArrayList<Integer> list = new ArrayList<>();
		//遍历 看词频1里面的哪些数 在词频2中 也有
		//同一个key最终重复的次数 以两个表 value较小的为准
		for (int key : map1.keySet()) {
			if (map2.containsKey(key)) {
				int n = Math.min(map1.get(key), map2.get(key));
				for (int i = 0; i < n; i++) {
					list.add(key);
				}
			}
		}
		//再将list 以数组的形式返回
		int[] ans = new int[list.size()];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = list.get(i);
		}
		return ans;
	}

}
