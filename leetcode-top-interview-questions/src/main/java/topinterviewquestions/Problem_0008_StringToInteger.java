package topinterviewquestions;

public class Problem_0008_StringToInteger {

	public static int myAtoi(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		s = removeHeadZero(s.trim());
		if (s == null || s.equals("")) {
			return 0;
		}
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		// str 是符合日常书写的，正经整数形式
		// 判断是否为正数
		boolean posi = str[0] == '-' ? false : true;
		int minq = Integer.MIN_VALUE / 10;
		int minr = Integer.MIN_VALUE % 10;
		int res = 0;
		int cur = 0;
		for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
			//将正数转换成负数处理
			cur = '0' - str[i];
			if ((res < minq) || (res == minq && cur < minr)) {
				//如果是个正数中途溢出了 直接返回系统最大
				//如果是个负数中途溢出了 直接返回系统最小
				return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			res = res * 10 + cur;
		}
		// res 负
		//如果原本的符号是正数 而处理的过程中是按负数处理的 在之前的过程中都不会溢出
		//例如"-2147483648" -> 改回原本的正数就溢出了
		//此时返回系统最大 "2147483647"
		if (posi && res == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE;
		}
		//改回原本的符号
		return posi ? -res : res;
	}

	public static String removeHeadZero(String str) {
		//如果开头有+号或-号 就从1位置开始删除0
		//"+00001" -> "+1"
		boolean r = (str.startsWith("+") || str.startsWith("-"));
		int s = r ? 1 : 0;
		for (; s < str.length(); s++) {
			if (str.charAt(s) != '0') {
				break;
			}
		}
		// s 到了第一个不是'0'字符的位置
		// e 从右往左找 最左的不是数字字符的位置
		// "+0001234_jd"
		// s 到了 "1" 位置
		// e 到了 "_" 位置
		//substr 方法 左闭右开 sub[s,e) 正好能将"1234"提取出来
		int e = -1;
		// 左<-右
		for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				e = i;
			}
		}
		// e 到了最左的 不是数字字符的位置
		//如果前面有+或-号最后就加上 如果没有 最后前面加个空字符串
		//如果 e = -1; 就说明后面没有不是数字的字符 从s到最后都要
		//否则 最闭右开
		return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
	}

	//判断是否是一个合法的数字字符串
	public static boolean isValid(char[] chas) {
		//如果开头不是 - 也不是 + 也不是数字字符 那么不合法
		if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
			return false;
		}
		//如果开头是一个-或+号 而数组长度只有1 那么不合法
		if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
			return false;
		}
		// 0 +... -... num
		for (int i = 1; i < chas.length; i++) {
			//从1位置往后检查 只要出现一个非数字字符 那么无效
			if (chas[i] < '0' || chas[i] > '9') {
				return false;
			}
		}
		return true;
	}

}
