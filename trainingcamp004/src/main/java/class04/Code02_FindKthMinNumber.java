package class04;

import java.util.Arrays;

public class Code02_FindKthMinNumber {

	public static int findKthNum(int[] arr1, int[] arr2, int kth) {
		if (arr1 == null || arr2 == null) {
			return -1;
		}
		if (kth < 1 || kth > arr1.length + arr2.length) {
			return -1;
		}
		//判断这两个数组哪个是长数组哪个是短数组
		int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
		int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
		int l = longs.length;
		int s = shorts.length;
		//当k<=短数组长度时
		if (kth <= s) {
			//分别在长短数组中拿出前k个数 找出这2k个数的上中位数 就是答案
			return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
		}
		//当k>长数组长度时 也就是3）种可能性
		if (kth > l) {
			//手动在长短数组中各自排除两个数
			if (shorts[kth - l - 1] >= longs[l - 1]) {
				return shorts[kth - l - 1];
			}
			if (longs[kth - s - 1] >= shorts[s - 1]) {
				return longs[kth - s - 1];
			}
			//找出剩下的数的上中位数 就是答案
			return getUpMedian(shorts, kth - l, s - 1, longs, kth - s, l - 1);
		}
		// 短数组长度 < k <= 长数组长度
		//也就是2）种可能性 单独验证长数组中的所拿出来的第一个数
		if (longs[kth - s - 1] >= shorts[s - 1]) {
			return longs[kth - s - 1];
		}
		//找出剩下的数的上中位数 就是答案
		return getUpMedian(shorts, 0, s - 1, longs, kth - s, kth - 1);
	}

	// A[s1...e1]
	// B[s2...e2]
	// 这两段一定等长且都有序
	// 求这两段整体的上中位数，上中位数值返回
	public static int getUpMedian(int[] A, int s1, int e1, int[] B, int s2, int e2) {
		int mid1 = 0;
		int mid2 = 0;
		//用缩小边界的方式 代替递归的过程
		while (s1 < e1) {
			mid1 = (s1 + e1) / 2;
			mid2 = (s2 + e2) / 2;
			if (A[mid1] == B[mid2]) {
				return A[mid1];
			}
			//判断奇数偶数
			//(e1 - s1 + 1) 数组的长度 = 右边界 - 左边界 + 1
			// ((e1 - s1 + 1) & 1) == 1 是奇数  ==0 是偶数
			if (((e1 - s1 + 1) & 1) == 1) { // 奇数长度
				if (A[mid1] > B[mid2]) {
					if (B[mid2] >= A[mid1 - 1]) {
						return B[mid2];
					}
					//用缩小边界的方式 代替递归的过程
					e1 = mid1 - 1;
					s2 = mid2 + 1;
				} else { // A[mid1] < B[mid2]
					if (A[mid1] >= B[mid2 - 1]) {
						return A[mid1];
					}
					e2 = mid2 - 1;
					s1 = mid1 + 1;
				}
			} else { // 偶数长度
				if (A[mid1] > B[mid2]) {
					e1 = mid1;
					s2 = mid2 + 1;
				} else {
					e2 = mid2;
					s1 = mid1 + 1;
				}
			}
		}
		return Math.min(A[s1], B[s2]);
	}

	// For test, this method is inefficient but absolutely right
	public static int[] getSortedAllArray(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			throw new RuntimeException("Your arr is invalid!");
		}
		int[] arrAll = new int[arr1.length + arr2.length];
		int index = 0;
		for (int i = 0; i != arr1.length; i++) {
			arrAll[index++] = arr1[i];
		}
		for (int i = 0; i != arr2.length; i++) {
			arrAll[index++] = arr2[i];
		}
		Arrays.sort(arrAll);
		return arrAll;
	}

	public static int[] generateSortedArray(int len, int maxValue) {
		int[] res = new int[len];
		for (int i = 0; i != len; i++) {
			res[i] = (int) (Math.random() * (maxValue + 1));
		}
		Arrays.sort(res);
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len1 = 10;
		int len2 = 23;
		int maxValue1 = 20;
		int maxValue2 = 100;
		int[] arr1 = generateSortedArray(len1, maxValue1);
		int[] arr2 = generateSortedArray(len2, maxValue2);
		printArray(arr1);
		printArray(arr2);
		int[] sortedAll = getSortedAllArray(arr1, arr2);
		printArray(sortedAll);
		int kth = 17;
		System.out.println(findKthNum(arr1, arr2, kth));
		System.out.println(sortedAll[kth - 1]);

	}

}
