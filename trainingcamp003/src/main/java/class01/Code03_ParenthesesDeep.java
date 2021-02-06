package class01;

public class Code03_ParenthesesDeep {

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	/**
	 *
	 * @param s
	 * @return 返回一个括号字符串中，最长的括号的有效子串的长度
	 */
	public static int maxLength(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] dp = new int[str.length];
		int pre = 0;
		//记录每一个dp的最大值
		int ans = 0;
		// 从1位置开始 默认 dp[0] = 0;
		for (int i = 1; i < str.length; i++) {
			//如果当前字符是 '(' 那就不需要讨论  直接dp[i] = 0
			if (str[i] == ')') {
				//pre =  i - 1 位置 能往前推的距离的再往前一个位置
				pre = i - dp[i - 1] - 1;
				//如果pre < 0 就是 i - 1位置的答案往前推出了左边界
				// 就说明 i位置无效
				if (pre >= 0 && str[pre] == '(') {
					//获得一个至少的结果后 再往前看一个位置的答案
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

	public static void main(String[] args) {
		String test = "((()))";
		System.out.println(deep(test));

	}

}
