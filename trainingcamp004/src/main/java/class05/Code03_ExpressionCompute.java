package class05;

import java.util.LinkedList;

public class Code03_ExpressionCompute {

	public static int getValue(String str) {
		return value(str.toCharArray(), 0)[0];
	}

	// 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
	// 返回两个值，长度为2的数组
	// 0) 负责的这一段的结果是多少
	// 1) 负责的这一段计算到了哪个位置
	public static int[] value(char[] str, int i) {
		LinkedList<String> que = new LinkedList<String>();
		int cur = 0;
		int[] bra = null;
		// 从i出发，开始撸串
		//遇到字符串结尾或者右括号停止
		while (i < str.length && str[i] != ')') {
			//遇到的是数字字符
			if (str[i] >= '0' && str[i] <= '9') {
				//str："37" -> cur = 0 -> 0 * 10 + 3 = 3  cur = 3 -> 3 * 10 + 7 =37
				//之前的cur乘十再加上此时得到的数字 '3'的asc 减去'0'的 asc码
				cur = cur * 10 + str[i++] - '0';
			}
			// 遇到的是运算符号
			else if (str[i] != '(') {
				//先将数字放入容器计算
				addNum(que, cur);
				//再将符号放入容器
				que.addLast(String.valueOf(str[i++]));
				cur = 0;
			}
			// 遇到左括号了
			else {
				bra = value(str, i + 1);
				//子过程返回的两个解
				//第一个解作为当前过程的cur
				//第二个解 让当前过程从子过程的停止位置的下一个位置接着往下算
				cur = bra[0];
				i = bra[1] + 1;
			}
		}
		//因为方法过程中遇到运算符才将运算符前一个数字放入容器
		//所以 最后一个数字最后手动加入
		addNum(que, cur);
		//最后容器中只剩 + -号和数字 返回计算的结果
		return new int[] { getNum(que), i };
	}

	public static void addNum(LinkedList<String> que, int num) {
		if (!que.isEmpty()) {
			int cur = 0;
			String top = que.pollLast();
			if (top.equals("+") || top.equals("-")) {
				que.addLast(top);
			} else {
				cur = Integer.valueOf(que.pollLast());
				num = top.equals("*") ? (cur * num) : (cur / num);
			}
		}
		que.addLast(String.valueOf(num));
	}

	public static int getNum(LinkedList<String> que) {
		int res = 0;
		boolean add = true;
		String cur = null;
		int num = 0;
		while (!que.isEmpty()) {
			cur = que.pollFirst();
			if (cur.equals("+")) {
				add = true;
			} else if (cur.equals("-")) {
				add = false;
			} else {
				num = Integer.valueOf(cur);
				res += add ? num : (-num);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		String exp = "48*((70-65)-43)+8*1";
		System.out.println(getValue(exp));

		exp = "4*(6+78)+53-9/2+45*8";
		System.out.println(getValue(exp));

		exp = "10-5*3";
		System.out.println(getValue(exp));

		exp = "-3*4";
		System.out.println(getValue(exp));

		exp = "3+1*4";
		System.out.println(getValue(exp));

	}

}
