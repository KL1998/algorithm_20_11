package class05;

import java.util.Arrays;

/**
 * 基数排序
 * O(N)
 * 实际上 ： O(N * log10^max)
 */
public class Code04_RadixSort {

	// only for no-negative value
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		//在 arr 0 ~ R上排序  得到最大值 是几位数
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}

	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	//代码  实际并没有使用 10个队列 10个桶
	// arr[l..r]排序  ,   最大值 是多少位 ：digit
	// l..r    3 56 17 100    3
	public static void radixSort(int[] arr, int L, int R, int digit) {
		//因为数据状况都是10进制的数  所以以10为基底
		final int radix = 10;
		int i = 0, j = 0;
		// 有多少个数准备多少个辅助空间
		int[] help = new int[R - L + 1];
		//按 第1位 。。。第digit位 入桶然后再倒出
		for (int d = 1; d <= digit; d++) { // 有多少位就进出几次
			// 10个空间
		    // count[0] 当前位(d位)是0的数字有多少个
			// count[1] 当前位(d位)是(0和1)的数字有多少个
			// count[2] 当前位(d位)是(0、1和2)的数字有多少个
			// count[i] 当前位(d位)是(0~i)的数字有多少个
			int[] count = new int[radix]; // count[0..9]

			//取出digit位上的数
			for (i = L; i <= R; i++) {
				// 103  1   3
				// 209  1   9
				j = getDigit(arr[i], d);
				count[j]++;
			}
			//将count 处理成 count' 也就是累加和形式
			for (i = 1; i < radix; i++) {
				count[i] = count[i] + count[i - 1];
			}
			// 原数组 i 从 最后一个位置出发 往前遍历
			for (i = R; i >= L; i--) {
				//取出当前遍历到的arr[i]的digit位上的数
				j = getDigit(arr[i], d);
				//根据 现在digit位的数字j 判断其应该在哪个桶
				//并 让其 来到 help数组的 count[j] - 1位置
				//count[j] ：累加和数组中记录的值 此时为词频 的累加和
				// count[j] = 5  表示在原数组中 digit位是j的 <= j 的 有5个
				//说明arr[i] 应该在 0~4 范围上 而队列是先进先出
				//所以arr[i] 应该是 最后一个出桶的 所以是这5个数的最后一个
				//help数组4位置上记录 arr[i]
				help[count[j] - 1] = arr[i];
				//词频--
				count[j]--;
			}
			//然后将help 刷回arr
			for (i = L, j = 0; i <= R; i++, j++) {
				arr[i] = help[j];
			}
		}
		
		
		
		
	}

	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
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
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100000;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			radixSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		radixSort(arr);
		printArray(arr);

	}

}
