package topinterviewquestions;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
public class Problem_0289_GameOfLife {

	public static void gameOfLife(int[][] board) {
		int N = board.length;
		int M = board[0].length;
		//遍历每一个位置
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//求出该位置的邻居数量
				int neighbors = neighbors(board, i, j);
				if (neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) {
					//设置该位置成1状态
					set(board, i, j);
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				//得到该位置向右移动一位后的数字 得到每个位置的下一代的结果
				board[i][j] = get(board, i, j);
			}
		}
	}

	public static int neighbors(int[][] board, int i, int j) {
		int count = 0;
		// 判断 8个位置 得到邻居数量
		count += ok(board, i - 1, j - 1) ? 1 : 0;
		count += ok(board, i - 1, j) ? 1 : 0;
		count += ok(board, i - 1, j + 1) ? 1 : 0;
		count += ok(board, i, j - 1) ? 1 : 0;
		count += ok(board, i, j + 1) ? 1 : 0;
		count += ok(board, i + 1, j - 1) ? 1 : 0;
		count += ok(board, i + 1, j) ? 1 : 0;
		count += ok(board, i + 1, j + 1) ? 1 : 0;
		return count;
	}

	public static boolean ok(int[][] board, int i, int j) {
		//在不越界的情况下 看邻居二进制的最后一位 是0还是1 来判断其是0还是1  (board[i][j] & 1) == 1
		return i >= 0 && i < board.length && j >= 0 && j < board[0].length && (board[i][j] & 1) == 1;
	}

	public static void set(int[][] board, int i, int j) {
		//二进制 倒数第二位设置成 1
		//00001 | 00010 = 00010
		//00000 | 00010 = 00010
		board[i][j] |= 2;
	}

	public static int get(int[][] board, int i, int j) {
		return board[i][j] >> 1;
	}

}
