package class08;

public class Code02_MoneyWays {

	public static int moneyWays(int[] arbitrary, int[] onlyone, int money) {
		if (money < 0) {
			return 0;
		}
		if ((arbitrary == null || arbitrary.length == 0) && (onlyone == null || onlyone.length == 0)) {
			return money == 0 ? 1 : 0;
		}
		//先完成各自的动态规划的二维表
		// 普通币 可以使用任意张
		int[][] dparb = getDpArb(arbitrary, money);
		//纪念币 只能用一张
		int[][] dpone = getDpOne(onlyone, money);
		if (dparb == null) {
			//如果普通币的数组什么都没有 就只能用纪念币的数组解决所有money
			return dpone[dpone.length - 1][money];
		}
		if (dpone == null) {
			//如果纪念币的数组什么都没有 就只能用普通币的数组解决所有money
			return dparb[dparb.length - 1][money];
		}
		int res = 0;
		//两种货币分别能搞定0~目标钱数所有的方法数
		//都在两张二维表的最后一行存着
		for (int i = 0; i <= money; i++) {
			//普通币搞定0元的方法数  * 纪念币搞定10元的方法数
			//普通币搞定1元 * 纪念币搞定9元
			//           ...
			//普通币搞定10元 * 纪念币搞定0元
			res += dparb[dparb.length - 1][i] * dpone[dpone.length - 1][money - i];
		}
		return res;
	}

	public static int[][] getDpArb(int[] arr, int money) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] dp = new int[arr.length][money + 1];
		// dp[i][j] 0..i券 自由选择张数， 搞定j元， 有多少方法？

		for (int i = 0; i < arr.length; i++) {
			//第一列 都有1种方法 就是什么货币也不用 搞定0元
			dp[i][0] = 1;
		}
		// [0] 5元 0元 5元 10元 15元 20元
		for (int j = 1; arr[0] * j <= money; j++) {
			//第一行 只有0号货币的整数倍的钱数 才有1种方法 剩下的都是0种方法
			dp[0][arr[0] * j] = 1;
		}
		// 0行 0列 填完了
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
			}
		}
		return dp;
	}

	public static int[][] getDpOne(int[] arr, int money) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		int[][] dp = new int[arr.length][money + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		if (arr[0] <= money) {
			dp[0][arr[0]] = 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= money; j++) {
				dp[i][j] = dp[i - 1][j];
				dp[i][j] += j - arr[i] >= 0 ? dp[i - 1][j - arr[i]] : 0;
			}
		}
		return dp;
	}

}
