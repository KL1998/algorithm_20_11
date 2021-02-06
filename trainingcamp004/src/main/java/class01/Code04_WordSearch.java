package class01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Code04_WordSearch {

	public static class TrieNode {
		public TrieNode[] nexts;
		public int pass;
		public int end;

		public TrieNode() {
			nexts = new TrieNode[26];
			pass = 0;
			end = 0;
		}

	}

	public static void fillWord(TrieNode head, String word) {
		head.pass++;
		char[] chs = word.toCharArray();
		int index = 0;
		TrieNode node = head;
		for (int i = 0; i < chs.length; i++) {
			index = chs[i] - 'a';
			if (node.nexts[index] == null) {
				node.nexts[index] = new TrieNode();
			}
			node = node.nexts[index];
			node.pass++;
		}
		node.end++;
	}

	public static String generatePath(LinkedList<Character> path) {
		char[] str = new char[path.size()];
		int index = 0;
		for (Character cha : path) {
			str[index++] = cha;
		}
		return String.valueOf(str);
	}

	public static List<String> findWords(char[][] board, String[] words) {
		TrieNode head = new TrieNode(); // 前缀树最顶端的头
		HashSet<String> set = new HashSet<>();
		//加入前缀树
		for (String word : words) {
			if (!set.contains(word)) {
				fillWord(head, word);
				set.add(word);
			}
		}
		// 答案
		List<String> ans = new ArrayList<>();
		// 沿途走过的字符，收集起来，存在path里
		LinkedList<Character> path = new LinkedList<>();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				// 枚举在board中的所有位置
				// 每一个位置出发的情况下，答案都收集
				//path记录沿途走过的字符的路径
				//head总是要传进前缀树的头节点 来使用前缀树
				//ans将能走出来的字符串填到ans中
				process(board, row, col, path, head, ans);
			}
		}
		return ans;
	}




	// 如果找到words中的某个str，就记录在 res里
	// 返回值，从row,col 出发，一共找到了多少个words中字符串
	public static int process(
			char[][] board,
			//当前来到 board中的(row,col)位置
			//从board[row][col]位置的字符出发，
			int row, int col,
			// 之前的路径上，走过的字符，记录在path里
			LinkedList<Character> path,
			// cur还没有登上，有待检查能不能登上去的前缀树的节点
			//也就是当前选择的出发点 有没有必要向上、下、左、右四个方向尝试
			//如果此时选择的出发点是f 而words中并没有f开头的字符串，那么自然是没必要的
			TrieNode cur,
			//如果真的走出了某个字符串 将path拼成字符串加入res
			List<String> res) {
		char cha = board[row][col];

		// 这个row col位置是之前走过的位置 避免走回头路的机制
		if (cha == 0) {
			return 0;
		}
		// (row,col) 不是回头路   cha 有效
		int index = cha - 'a';
		//如果当前前缀树节点，没有这个方向的路，或者这条路上最终的字符串之前加入过结果里
		if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
			return 0;
		}
		// 没有走回头路且能登上去
		//也就是假如board[row][col]是a 而前缀树上确实有一个a开头的路径
		//前缀树节点往下走一个
		cur = cur.nexts[index];
		// 当前位置的字符加到路径里去 并且要加到最后的位置
		path.addLast(cha);
		// 从row和col位置出发，后续一共搞定了多少答案
		int fix = 0;
		// 当来到row col位置，在向上、下、左、右四个方向尝试之前
		// 先判断是否已经走出了一个答案
		if (cur.end > 0) { 
			res.add(generatePath(path));
			cur.end--;
			fix++;
		}
		// 往上、下、左、右，四个方向尝试
		board[row][col] = 0;
		if (row > 0) {
			fix += process(board, row - 1, col, path, cur, res);
		}
		if (row < board.length - 1) {
			fix += process(board, row + 1, col, path, cur, res);
		}
		if (col > 0) {
			fix += process(board, row, col - 1, path, cur, res);
		}
		if (col < board[0].length - 1) {
			fix += process(board, row, col + 1, path, cur, res);
		}
		board[row][col] = cha;
		//在路径加进来之后 在返回之前要弹出
		//标准的深度优先遍历的恢复现场的做法
		path.pollLast();
		//当前节点的pass值要减去以当前节点出发能搞定的答案
		cur.pass -= fix;
		return fix;
	}

}
