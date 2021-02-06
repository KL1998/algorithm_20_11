package topinterviewquestions;

public class Problem_0348_DesignTicTacToe {

	class TicTacToe {
		private int[][] rows;
		private int[][] cols;
		private int[] leftUp;
		private int[] rightUp;
		private boolean[][] matrix;
		private int N;

		public TicTacToe(int n) {
			//0位置不用 只记录每一行玩家1 和 玩家 2 分别下了几个棋子
			rows = new int[n][3]; // 1 2
			//0列不用 只记录每一列玩家1 和 玩家 2 分别下了几个棋子
			cols = new int[n][3];
			//玩家1 玩家2 在左对角线上分别下了几个棋子
			leftUp = new int[3]; //  1 2
			//玩家1 玩家2 在右对角线上分别下了几个棋子
			rightUp = new int[3]; // 1 2
			//判断该位置下没下过
			matrix = new boolean[n][n];
			N = n;
		}

		public int move(int row, int col, int player) {
			//该位置下过了 此刻没有人赢 并且当下这步棋无效
			if (matrix[row][col]) {
				return 0;
			}
			matrix[row][col] = true;
			//该玩家在row行 下的棋子数+1
			rows[row][player]++;
			//该玩家在col行 下的棋子数+1
			cols[col][player]++;
			//左对角线
			if (row == col) {
				leftUp[player]++;
			}
			//右对角线
			if (row + col == N - 1) {
				rightUp[player]++;
			}
			//只要 某行或者某列 或者某对角线收集齐 返回该玩家胜
			if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
				return player;
			}
			//否则 此刻没有人胜出
			return 0;
		}

	}

}
