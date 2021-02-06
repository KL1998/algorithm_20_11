package class03;

public class Code04_LCSubsequence {

	public static int lcs(String s1, String s2) {
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;
		return process(str1, str2, N - 1, M - 1);
	}

	// str1[0....i1] 与 str2[0......i2]的最长公共子序列长度是多少？
	public static int process(char[] str1, char[] str2, int i1, int i2) {
		if (i1 == 0 && i2 == 0) {
			return str1[i1] == str2[i2] ? 1 : 0;
		}
		// i1 和 i2 不同时为0
		if (i1 == 0) { // str1[0..0] str2[0...i2 - 1]
			return ((str1[i1] == str2[i2]) || process(str1, str2, i1, i2 - 1) == 1) ? 1 : 0;
		}

		if (i2 == 0) {
			return ((str1[i1] == str2[i2]) || process(str1, str2, i1 - 1, i2) == 1) ? 1 : 0;
		}
		// i1 和 i2 都不是0
		//四种可能性
		// 1.最长公共子序列结尾，不是以str1[i1]与str2[i2]结尾的
		int p1 = process(str1, str2, i1 - 1, i2 - 1);
		//2.以str1[i1]结尾 但不以str2[i2]结尾的
		int p2 = process(str1, str2, i1, i2 - 1);
		//3.以str2[i1]结尾 但不以str1[i2]结尾的
		int p3 = process(str1, str2, i1 - 1, i2);
		//4.既以str1[i1]结尾 又以str2[i2]结尾的
		int p4 = 0;
		//当str1[i1] == str2[i2] 时 有这种可能性
		if (str1[i1] == str2[i2]) {
			p4 = p1 + 1;
		}
		return Math.max(Math.max(p1, p2), Math.max(p3, p4));
	}

	public static int dp(String s1, String s2) {

		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int N = str1.length;
		int M = str2.length;

		int[][] dp = new int[N][M];

		//base case
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		//第一行
		for (int j = 1; j < M; j++) {
			//如果相等就是1 不相等的话 之前是1 现在还是1 之前是0 现在还是0
			dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
		}
		//第一列
		for (int i = 1; i < N; i++) {
			dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp[N - 1][M - 1];
	}

	public static String lcse(String str1, String str2) {
		if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
			return "";
		}
		char[] chs1 = str1.toCharArray();
		char[] chs2 = str2.toCharArray();
		int[][] dp = getdp(chs1, chs2);
		int m = chs1.length - 1;
		int n = chs2.length - 1;
		char[] res = new char[dp[m][n]];
		int index = res.length - 1;
		while (index >= 0) {
			if (n > 0 && dp[m][n] == dp[m][n - 1]) {
				n--;
			} else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
				m--;
			} else {
				res[index--] = chs1[m];
				m--;
				n--;
			}
		}
		return String.valueOf(res);
	}

	public static int[][] getdp(char[] str1, char[] str2) {
		int[][] dp = new int[str1.length][str2.length];
		dp[0][0] = str1[0] == str2[0] ? 1 : 0;
		for (int i = 1; i < str1.length; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
		}
		for (int j = 1; j < str2.length; j++) {
			dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
		}
		for (int i = 1; i < str1.length; i++) {
			for (int j = 1; j < str2.length; j++) {
				dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				if (str1[i] == str2[j]) {
					dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
				}
			}
		}
		return dp;
	}

	public static void main(String[] args) {
		String str1 = "A1BC23Z4";
		String str2 = "12O3YU4P";
//		System.out.println(lcse(str1, str2));

		System.out.println(lcs(str1, str2));
		System.out.println(dp(str1, str2));

	}
}