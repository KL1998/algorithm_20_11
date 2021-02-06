package class03;

import java.util.Arrays;

public class Code01_MaxGap {

	public static int maxGap(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int len = nums.length;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		//遍历出整个数组的最小值和最大值
		for (int i = 0; i < len; i++) {
			min = Math.min(min, nums[i]);
			max = Math.max(max, nums[i]);
		}
		//说明整个数组只有一种数
		if (min == max) {
			return 0;
		}
		// 有 N 个数 就准备N + 1个桶
		// hasNum[i] i号桶是否进来过数字
		boolean[] hasNum =  new boolean[len + 1];
		// maxs[i] i号桶收集的所有数字的最大值
		int[] maxs = new int[len + 1];
		// mins[i] i号桶收集的所有数字的最小值
		int[] mins = new int[len + 1];
		int bid = 0; // 桶号
		for (int i = 0; i < len; i++) {

			//根据当前数字 和一共有的数字  以及数组的最大值和最小值 也就是桶的范围
			//定位出当前数字应该进的桶号
			bid = bucket(nums[i], len, min, max);
			//判断之前这个桶号有没有进来过数字 如果没进来过数字 那么当前数字就成这个桶中的最小值
			//如果进来过数字 和 之前桶中的最小值比较 只留下更小的那个
			mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
			//判断之前这个桶号有没有进来过数字 如果没进来过数字 那么当前数字就成这个桶中的最大值
			//如果进来过数字 和 之前桶中的最大值比较 只留下更大的那个
			maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
			//表示bid号桶进来过数字
			hasNum[bid] = true;
		}
		int res = 0;
		int lastMax = maxs[0]; // 上一个非空桶的最大值
		int i = 1;
		//考察每一组空桶 右侧最近的非空桶的最小值 去减 左侧最近非空桶的最大值
		//其中返回差值最大的答案
		for (; i <= len; i++) {
			if (hasNum[i]) {
				res = Math.max(res, mins[i] - lastMax);
				lastMax = maxs[i];
			}
		}
		return res;
	}

	public static int bucket(long num, long len, long min, long max) {
		return (int) ((num - min) * len / (max - min));
	}

	// for test
	public static int comparator(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		Arrays.sort(nums);
		int gap = Integer.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			gap = Math.max(nums[i] - nums[i - 1], gap);
		}
		return gap;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			if (maxGap(arr1) != comparator(arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
