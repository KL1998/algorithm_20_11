package class02;

public class Code03_BestTimetoBuyandSellStockFollow {

	public static int dp(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[][] dp = new int[N][K + 1];
		int ans = 0;
		//dp第一行和第一列都是0
		//并且 希望在求出dp[i][j]之后去求dp[i+1][j]
		//dp[2][3] -> dp[3][3] -> dp[4][3]
		//先从上往下 再从左到右 在二维表中竖着推
		for (int j = 1; j <= K; j++) {
			//当求dp[1][j]时  可能性1)dp[1][j] = dp[0][j]
			// 	             可能性2) dp[1][j] =
			// 	 max(dp[1][j - 1] - arr[1] , dp[0][j - 1] - arr[0]) + arr[1])
			//所以先将dp[1][j]的可能性2)的t准备好
			int t = dp[0][j - 1] - prices[0];
			for (int i = 1; i < N; i++) {
				//先用i-1的t和当前的dp[i][j]的可能性2)第一条枚举 求出一个max 更新t
				t = Math.max(t, dp[i][j - 1] - prices[i]);
				//可能性1) 和 可能性2) 求出一个max就是 dp[i][j] 的答案
				dp[i][j] = Math.max(dp[i - 1][j], t + prices[i]);
				ans = Math.max(ans, dp[i][j]);
			}
		}
		return ans;
	}

	public static int maxProfit(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		// dp一维表，做了空间压缩
		int[] dp = new int[N];
		int ans = 0;
		for (int tran = 1; tran <= K; tran++) {
			int pre = dp[0];
			int best = pre - prices[0];
			for (int index = 1; index < N; index++) {
				pre = dp[index];
				dp[index] = Math.max(dp[index - 1], prices[index] + best);
				best = Math.max(best, pre - prices[index]);
				ans = Math.max(dp[index], ans);
			}
		}
		return ans;
	}

	public static int allTrans(int[] prices) {
		int ans = 0;
		for (int i = 1; i < prices.length; i++) {
			ans += Math.max(prices[i] - prices[i - 1], 0);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] test = {4,3,1};
		int K = 1;
		System.out.println(dp(K, test));
		System.out.println(maxProfit(K, test));

	}

}
