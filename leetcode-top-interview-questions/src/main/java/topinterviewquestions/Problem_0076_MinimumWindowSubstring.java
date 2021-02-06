package topinterviewquestions;

public class Problem_0076_MinimumWindowSubstring {

	public static String minWindow(String s, String t) {
		if (s.length() < t.length()) {
			return "";
		}
		char[] str = s.toCharArray();
		char[] target = t.toCharArray();
		int[] map = new int[256];
		for (char cha : target) {
			map[cha]++;
		}
		int all = target.length;
		int L = 0;
		int R = 0;
		// -1(从来没找到过合法的)
		int minLen = -1;
		int ansl = -1;
		int ansr = -1;
		// [L..R)   [0,0)  R
		while (R != str.length) {
			map[str[R]]--;
			//说明有效还款
			if (map[str[R]] >= 0) {
				all--;
			}
			//当还款完成 L需要向右移动
			if (all == 0) {
				//如果此时L上的字符在欠账表中是<0的 说明根本不需要这个字符
				while (map[str[L]] < 0) {
					//L上的字符在欠账表中 词频++ L向右移动
					map[str[L++]]++;
				}
				//更新答案
				//minLen == -1  表示从来没收集过答案
				//minLen > R - L + 1 此时能收集到更好的答案
				if (minLen == -1 || minLen > R - L + 1) {
					minLen = R - L + 1;
					ansl = L;
					ansr = R;
				}
				//此时L位置上的字符在表中不是<0的，就说明L向右移动一定会重新欠账
				all++;		
				map[str[L++]]++;
			}
			R++;
		}
		return minLen == -1 ? "" : s.substring(ansl, ansr + 1);
	}

}
