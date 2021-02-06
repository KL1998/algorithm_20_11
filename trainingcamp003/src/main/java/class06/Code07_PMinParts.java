package class06;

public class Code07_PMinParts {

	public static int minParts(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (s.length() == 1) {
			return 1;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		//判断i~j范围的字符串是否是回文串的预处理结构 范围上的尝试
		boolean[][] isP = new boolean[N][N];
		//先填对角线
		for (int i = 0; i < N; i++) {
			isP[i][i] = true;
		}
		//再填倒数第二条对角线
		//倒数第二条对角线是没有 N-1位置的 0 ~ n-2
		for (int i = 0; i < N - 1; i++) {
			//i到i+1范围的字符串是否是回文串
			//这个范围只有两个字符 相等就是回文 不相等就不是
			isP[i][i + 1] = str[i] == str[i + 1];
		}
		//N-1和N-2位置的对角线都已经填好
		//普遍位置从n-3位置开始填
		for (int row = N - 3; row >= 0; row--) {
			//从row + 2列开始往上填对角线
			for (int col = row + 2; col < N; col++) {
				//两个可能性 1) i位置字符等于j位置字符
				//          2) i+1位置到j-1位置 整体是回文串
				isP[row][col] = str[row] == str[col] && isP[row + 1][col - 1];
			}
		}
		int[] dp = new int[N + 1];
		for (int i = 0; i <= N; i++) {
			dp[i] = Integer.MAX_VALUE;
		}
		dp[N] = 0;
		//从右往左推
		for (int i = N - 1; i >= 0; i--) {
			for (int end = i; end < N; end++) {
				// i..end 这个范围上得是一个回文
				if (isP[i][end]) {
					dp[i] = Math.min(dp[i], 1 + dp[end + 1]);
				}
			}
		}
		return dp[0];
	}

	public static void main(String[] args) {
		String test = "aba12321412321TabaKFK";
		System.out.println(minParts(test));
	}

}
