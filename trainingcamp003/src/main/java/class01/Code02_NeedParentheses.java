package class01;

public class Code02_NeedParentheses {

	public static boolean valid(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static int needParentheses(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else { // 遇到的是')'
				if (count == 0) {
					//在count -- 前是0 那么减之后就是-1 need++ 即可 不用减了
					//省去count恢复成0的步骤
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

}
