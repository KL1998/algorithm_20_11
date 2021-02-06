package topinterviewquestions;

// 提交时把类名和构造函数名从Problem_0308_RangeSumQuery2DMutable改成NumMatrix
public class Problem_0308_RangeSumQuery2DMutable {
	private int[][] tree;
	private int[][] nums;
	private int N;
	private int M;

	public Problem_0308_RangeSumQuery2DMutable(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return;
		}
		N = matrix.length;
		M = matrix[0].length;
		tree = new int[N + 1][M + 1];
		nums = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				update(i, j, matrix[i][j]);
			}
		}
	}

	// 用户给我的row，col不能越界
	//利用indexTree解决，二维数组累加和问题
	//要利用辅助数组求出  0到index 位置上的累加和
	private int sum(int row, int col) {
		int sum = 0;
		//两个维度 row，col 每次都抹去最右侧的1
		//index - index & -index 就是抹去最末尾的 1
		//10100 - 00100 = 10000
		//构建的辅助数组 下标从1开始  用户需要0 ~ index 我们返回1~index + 1 即可 没有影响
		for (int i = row + 1; i > 0; i -= i & (-i)) {
			for (int j = col + 1; j > 0; j -= j & (-j)) {
				//先将辅助矩阵数组中该位置的数累加到最终的结果里去
				sum += tree[i][j];
			}
		}
		return sum;
	}

	// 用户给我的row，col不能越界
	public void update(int row, int col, int val) {
		if (N == 0 || M == 0) {
			return;
		}
		int add = val - nums[row][col];
		nums[row][col] = val;
		//两个维度 row，col 每次都最右侧的1都再加1即可
		for (int i = row + 1; i <= N; i += i & (-i)) {
			//构建的辅助数组 下标从1开始  用户需要0 ~ index 我们返回1~index + 1 即可 没有影响
			for (int j = col + 1; j <= M; j += j & (-j)) {
				tree[i][j] += add;
			}
		}
	}

	public int sumRegion(int row1, int col1, int row2, int col2) {
		if (N == 0 || M == 0) {
			return 0;
		}
		return  sum(row2, col2) +
				sum(row1 - 1, col1 - 1) -
				sum(row1 - 1, col2) -
				sum(row2, col1 - 1);
	}

}
