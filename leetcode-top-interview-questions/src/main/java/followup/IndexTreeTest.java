package followup;

public class IndexTreeTest {

	public static class IndexTree {
		private int[] tree;
		private int N;
		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		//返回 下标 1到index 位置上的累加和
		//如果用户认为下标应该从0开始 求0 ~ index的累加和
		//我们返回 1 ~ index + 1 即可
		//我们构建的辅助数组 0位置是弃之不用的 下标从1开始
		public int sum(int index) {
			int ret = 0;
			//要利用辅助数组求出  0到index 位置上的累加和
			while (index > 0) {
				//先将辅助数组中该位置的数累加到最终的结果里去
				ret += tree[index];
				//index - index & -index 就是抹去最末尾的 1
				//10100 - 00100 = 10000
				index -= index & -index;
			}
			return ret;
		}

		//原数组index位置发生变化 +d 辅助数组中有哪些位置受影响也要 +d
		public void add(int index, int d) {
			while (index <= N) {
				//先将辅助数组中该位置的数 +d
				tree[index] += d;
				//接下来受影响的位置就是 index二进制从最末尾的1 再加1
				//10100 + 00100 = 11000
				//11000 + 01000 = 100000
				index += index & -index;
			}
		}
	}



	//test
	public static class Right {
		private int[] nums;
		private int N;

		public Right(int size) {
			N = size + 1;
			nums = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			for (int i = 1; i <= index; i++) {
				ret += nums[i];
			}
			return ret;
		}

		public void add(int index, int d) {
			nums[index] += d;
		}

	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.out.println("Oops!");
				}
			}
		}
		System.out.println("test finish");
	}

}
