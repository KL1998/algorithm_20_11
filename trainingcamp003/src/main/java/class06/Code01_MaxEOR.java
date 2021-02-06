package class06;

public class Code01_MaxEOR {

	// O(N^2)
	public static int maxXorSubarray1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 准备一个前缀异或和数组eor
		// eor[i] = arr[0...i]的异或结果
		int[] eor = new int[arr.length];
		eor[0] = arr[0];
		// 生成eor数组，eor[i]代表arr[0..i]的异或和
		for (int i = 1; i < arr.length; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		int max = Integer.MIN_VALUE;
		//以位置结尾的情况下有哪些异或和
		//0...j
		//1...j
		//2...j
		//[i...j] = eor[j] ^ eor [i - 1]
		for (int j = 0; j < arr.length; j++) {
            // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
			for (int i = 0; i <= j; i++) {
				max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
			}
		}
		return max;
	}

	// 前缀树的节点类型，每个节点向下只可能有走向0或1的路
	// node.nexts[0] == null 0方向没路
	// node.nexts[0] != null 0方向有路
	public static class Node {
		public Node[] nexts = new Node[2];
	}

	// 基于本题，定制前缀树的实现
	public static class NumTrie {
		// 头节点
		public Node head = new Node();

		// 把某个数字newNum加入到这棵前缀树里
		// num是一个32位的整数，所以加入的过程一共走32步
		public void add(int newNum) {
			Node cur = head;
			//从最高位到最低位0位置
			for (int move = 31; move >= 0; move--) {
				// 从高位到低位，取出每一位的状态，如果当前状态是0，
				// path(int) = 0
				// 如果当前状态是1
				// path(int) = 1
				//((newNum >> move) & 1) 拿出第move位的状态 是0还是1
				int path = ((newNum >> move) & 1);
				// 无路新建、有路复用
				cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
				cur = cur.nexts[path];
			}
		}

		// 该结构之前收集了一票数字，并且建好了前缀树
		// sum,和 谁 ^ 最大的结果（把结果返回）
		//sum = eor[0...i]的异或和
		public int maxXor(int sum) {
			Node cur = head;
			int res = 0;
			for (int move = 31; move >= 0; move--) {
				//sum在第move位的状态  path = 0或1
				int path = (sum >> move) & 1;
				// 期待的路
				//move == 31在最高位上期待和sum的最高位一样的状态
				//如果不在最高位上 总是期待与sum相反的状态
				int best = move == 31 ? path : (path ^ 1);
				// 实际走的路
				//如果期待的路不为空 就走期待的路
				//如果为空，只能走相反的路
				best = cur.nexts[best] != null ? best : (best ^ 1);
				// (path ^ best) 当前位位异或完的结果
				res |= (path ^ best) << move;
				cur = cur.nexts[best];
			}
			return res;
		}
	}

	public static int maxXorSubarray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		// 0..i 异或和
		int eor = 0;
		// 前缀树 -> numTrie
		NumTrie numTrie = new NumTrie();
		// 一个数也没有的时候，异或和是0
		//先将0 加入前缀树
		numTrie.add(0);
		for (int i = 0; i < arr.length; i++) {
			// eor 是 0..i异或和
			eor ^= arr[i];
			// X, 0~0 , 0~1, .., 0~i-1
			// numTrie.maxXor(eor) 判断当前eor[0..i]与前缀树上的哪个数异或在一起能最大
			//返回最大值并和之前的最大值比较 更新最大值
			max = Math.max(max, numTrie.maxXor(eor));
			//将当前eor[0..i]加入前缀树
			numTrie.add(eor);
		}
		return max;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int comp = maxXorSubarray1(arr);
			int res = maxXorSubarray2(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
		//
		// // int[] arr = generateRandomArray(6, maxValue);
		// int[] arr = { 3, -28, -29, 2};
		//
		// for (int i = 0; i < arr.length; i++) {
		// System.out.println(arr[i] + " ");
		// }
		// System.out.println("=========");
		// System.out.println(maxXorSubarray(arr));
		// System.out.println((int) (-28 ^ -29));

	}
}
