package class03;

import java.util.HashSet;

public class Code02_WorldBreak {
	/*
	 * 
	 * 假设所有字符都是小写字母. 大字符串是str. arr是去重的单词表, 每个单词都不是空字符串且可以使用任意次.
	 * 使用arr中的单词有多少种拼接str的方式. 返回方法数.
	 * 
	 */

	public static int ways(String str, String[] arr) {
		HashSet<String> set = new HashSet<>();
		for (String candidate : arr) {
			set.add(candidate);
		}
		return process(str, 0, set);
	}

	// 所有的贴纸，都已经放在了set中
	// str[i....] 能够被set中的贴纸分解的话，返回分解的方法数
	//枚举所有的前缀串为第一张贴纸的可能性
	public static int process(String str, int i, HashSet<String> set) {
		//当i来到终止位置
		if (i == str.length()) {
			//一种方法  这种方法叫什么也不用
			return 1;
		}
		//记录总的方法数
		int ways = 0;
		// 从[i ... end] 是前缀串 要枚举每一个前缀串
		for (int end = i; end < str.length(); end++) {
			//拿到 i ... end 的前缀串pre
			//end + 1 是因为substring 方法左闭右开  [)
			String pre = str.substring(i, end + 1);
			//前缀串有效 在set中有
			if (set.contains(pre)) {
				ways += process(str, end + 1, set);
			}
		}
		return ways;
	}
// str[i...] 被分解的方法数，返回

	//每来到一个i位置 前缀树都回到开头
	public static int g(char[] str, Node root, int i) {
		if (i == str.length) {
			return 1;
		}
		int ways = 0;
		Node cur = root;
		// i...end
		for (int end = i; end < str.length; end++) {
			//一共26条路 0对应a 1对应b .. 25对应z
			int path = str[end] - 'a';
			//如果后面没路了直接break 后面的串都不可能是有效前缀串
			if (cur.nexts[path] == null) {
				break;
			}
			//如果有路 就来到下一个节点
			cur = cur.nexts[path];
			//如果当前节点 是某一个字符串的结尾
			if (cur.end) {
				// 就代表 从i到end这个字符串在词典中有
				// 且是作为目标字符串第一张贴纸的可能 然后累加上后续的可能
				ways += g(str, root, end + 1);
			}
		}
		return ways;
	}


	public static int ways1(String str, String[] arr) {
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<String> map = new HashSet<>();
		for (String s : arr) {
			map.add(s);
		}
		return f(str, map, 0);
	}

	public static int f(String str, HashSet<String> map, int index) {
		if (index == str.length()) {
			return 1;
		}
		int ways = 0;
		for (int end = index; end < str.length(); end++) {
			if (map.contains(str.substring(index, end + 1))) {
				ways += f(str, map, end + 1);
			}
		}
		return ways;
	}

	public static int ways2(String str, String[] arr) {
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		HashSet<String> map = new HashSet<>();
		for (String s : arr) {
			map.add(s);
		}
		int N = str.length();
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			for (int end = i; end < N; end++) {
				if (map.contains(str.substring(i, end + 1))) {
					dp[i] += dp[end + 1];
				}
			}
		}
		return dp[0];
	}

	public static class Node {
		public boolean end;
		public Node[] nexts;

		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}

	public static int ways3(String str, String[] arr) {
		if (str == null || str.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		Node root = new Node();
		for (String s : arr) {
			char[] chs = s.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		return g(str.toCharArray(), root, 0);
	}



	public static int ways4(String s, String[] arr) {
		if (s == null || s.length() == 0 || arr == null || arr.length == 0) {
			return 0;
		}
		Node root = new Node();
		for (String str : arr) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.end = true;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[] dp = new int[N + 1];
		dp[N] = 1;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {
					break;
				}
				cur = cur.nexts[path];
				if (cur.end) {
					dp[i] += dp[end + 1];
				}
			}
		}
		return dp[0];
	}

	// 以下的逻辑都是为了测试
	public static class RandomSample {
		public String str;
		public String[] arr;

		public RandomSample(String s, String[] a) {
			str = s;
			arr = a;
		}
	}

	// 随机样本产生器
	public static RandomSample generateRandomSample(char[] candidates, int num, int len, int joint) {
		String[] seeds = randomSeeds(candidates, num, len);
		HashSet<String> set = new HashSet<>();
		for (String str : seeds) {
			set.add(str);
		}
		String[] arr = new String[set.size()];
		int index = 0;
		for (String str : set) {
			arr[index++] = str;
		}
		StringBuilder all = new StringBuilder();
		for (int i = 0; i < joint; i++) {
			all.append(arr[(int) (Math.random() * arr.length)]);
		}
		return new RandomSample(all.toString(), arr);
	}

	public static String[] randomSeeds(char[] candidates, int num, int len) {
		String[] arr = new String[(int) (Math.random() * num) + 1];
		for (int i = 0; i < arr.length; i++) {
			char[] str = new char[(int) (Math.random() * len) + 1];
			for (int j = 0; j < str.length; j++) {
				str[j] = candidates[(int) (Math.random() * candidates.length)];
			}
			arr[i] = String.valueOf(str);
		}
		return arr;
	}

	public static void main(String[] args) {
		char[] candidates = { 'a', 'b' };
		int num = 20;
		int len = 4;
		int joint = 5;
		int testTimes = 30000;
		boolean testResult = true;
		for (int i = 0; i < testTimes; i++) {
			RandomSample sample = generateRandomSample(candidates, num, len, joint);
			int ans1 = ways1(sample.str, sample.arr);
			int ans2 = ways2(sample.str, sample.arr);
			int ans3 = ways3(sample.str, sample.arr);
			int ans4 = ways4(sample.str, sample.arr);
			if (ans1 != ans2 || ans3 != ans4 || ans2 != ans4) {
				testResult = false;
			}
		}
		System.out.println(testTimes + "次随机测试是否通过：" + testResult);
	}

}
