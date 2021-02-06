package topinterviewquestions;

public class Problem_0334_IncreasingTripletSubsequence {

	public static boolean increasingTriplet(int[] arr) {
		if (arr == null || arr.length < 3) {
			return false;
		}
		int[] ends = new int[3];
		ends[0] = arr[0];
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < arr.length; i++) {
			l = 0;
			r = right;
			//二分  在ends[]数组中 找>= arr[i] 最左的位置
			while (l <= r) {
				m = (l + r) / 2;
				if (arr[i] > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			//ends[] 下标超过 1  0 1 2 就说明 数组中存在长度为3的递增子序列
			if (right > 1) {
				return true;
			}
			ends[l] = arr[i];
		}
		return false;
	}

}
