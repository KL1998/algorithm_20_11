package topinterviewquestions;

public class Problem_0251_Flatten2DVector {

	public static class Vector2D {
		private int[][] matrix;
		private int row;
		private int col;
		private boolean curUse;

		public Vector2D(int[][] v) {
			matrix = v;
			//先将0行 -1列 设为true 表示该位置的数已经调用迭代器遍历过
			//而没有遍历过的值都是false 这样 就可以完成hasnext的功能
			row = 0;
			col = -1;
			curUse = true;
			hasNext();
		}

		public int next() {
			int ans = matrix[row][col];
			//表示当前数已经遍历过  使用过了
			curUse = true;
			//调用该方法 定位好下一个该使用的数
			hasNext();
			//给用户返回当前遍历到的位置的数
			return ans;
		}

		public boolean hasNext() {
			//row 来到了越界的位置
			if (row == matrix.length) {
				return false;
			}
			//如果在一个位置 重复的调用hasNext 直接返回 row跟col都不要动
			if (!curUse) {
				return true;
			}
			// 当前数已经是遍历过的  (row，col)用过了
			if (col < matrix[row].length - 1) {
				col++;
			} else {
				col = 0;
				do {
					row++; //因为有可能下面一行是空的  所以要跳过空行 一直++到有数的一行
				} while (row < matrix.length && matrix[row].length == 0);
			}
			// 来到了 新的位置(row，col) 如果row的位置不越界 将curUser设为false表示没有遍历到当前位置的数
			// 当前位置的数没有使用过 返回true 表示有下一个元素
			if (row != matrix.length) {
				//将当前数的curUse属性设为false 表示没有遍历过
				//此时 (row，col) 已经来到了 NEXT()方法下一个需要遍历的位置
				curUse = false;
				//返回true 表示当前NEXT方法遍历到的位置的数的下一个位置还有数
				return true;
			} else {
				//如果row来到越界的位置
				return false;
			}
		}

	}

}
