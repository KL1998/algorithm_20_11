package topinterviewquestions;

public class Problem_0007_ReverseInteger {

	public static int reverse(int x) {
		//判断是否为负数
		boolean neg = ((x >>> 31) & 1) == 1;
		//将x都转为负数处理
		x = neg ? x : -x;
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {
			//判断溢出
			if (res < m || (res == m && x % 10 < o)) {
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

	public static void main(String[] args) {
		int x = -3215;
		System.out.println(reverse(x));
	}

}
