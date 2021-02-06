package class04;

public class Code04_LIS {

	public static int[] lis1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] dp = getdp1(arr);
		return generateLIS(arr, dp);
	}

	public static int[] getdp1(int[] arr) {
		int[] dp = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			dp[i] = 1;
			for (int j = 0; j < i; j++) {
				if (arr[i] > arr[j]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
		}
		return dp;
	}

	public static int[] generateLIS(int[] arr, int[] dp) {
		int len = 0;
		int index = 0;
		for (int i = 0; i < dp.length; i++) {
			if (dp[i] > len) {
				len = dp[i];
				index = i;
			}
		}
		int[] lis = new int[len];
		lis[--len] = arr[index];
		for (int i = index; i >= 0; i--) {
			if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
				lis[--len] = arr[i];
				index = i;
			}
		}
		return lis;
	}

	public static int[] lis2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[] dp = getdp2(arr);
		return generateLIS(arr, dp);
	}

	public static int[] getdp2(int[] arr) {
		int[] dp = new int[arr.length];
		int[] ends = new int[arr.length];
		ends[0] = arr[0];
		dp[0] = 1;
		//表示有效区
		int right = 0; // 0....right   right往右无效
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < arr.length; i++) {
			//二分 找到大于等于当前数最左的位置
			l = 0;
			r = right;
			while (l <= r) {
				m = (l + r) / 2;
				//当 arr[i] > ends[m] 说明没有在ends数组中找到大于等于当前数的值
				//因为 r = right 当 while循环在 l > r 时停止 如果没找到 是因为 l在增加 所导致的 l > r 所以l一定会来到 right + 1 的位置
				//如果找到了  while循环在 l > r时停止  是因为 r 在减小 所导致的 l > r  l不变
				if (arr[i] > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			// l 就是 大于等于 当前数最左的位置
			// 如果没有找到 大于等于当前数最左的位置 那么l就一定会 来到right+1位置 表示要扩充有效区
			// 如果找到了 将原来的 ends[l] 值覆盖 更新即可

			right = Math.max(right, l);

			ends[l] = arr[i];
			//其左边一共包括自己一共 l + 1个数
			dp[i] = l + 1;
		}
		return dp;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 2,8, 5, 3, 6, 4, 8, 9, 7 };
		printArray(arr);
		printArray(lis1(arr));
		printArray(lis2(arr));

	}
}