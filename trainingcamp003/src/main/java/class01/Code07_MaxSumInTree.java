package class01;

public class Code07_MaxSumInTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int val) {
			value = val;
		}
	}

	public static int maxSum = Integer.MIN_VALUE;

	//1)第一种方式
	public static int maxPath(Node head) {
		maxSum = Integer.MIN_VALUE;
		p(head, 0);
		return maxSum;
	}

	// 之前的路径和，为pre
	public static void p(Node x, int pre) {
		//只有叶子节点的时机 设置maxSum
		if (x.left == null && x.right == null) {
			maxSum = Math.max(maxSum, pre + x.value);
		}
		if (x.left != null) {
			p(x.left, pre + x.value);
		}
		if (x.right != null) {
			p(x.right, pre + x.value);
		}
	}

	public static int maxDis(Node head) {
		if (head == null) {
			return 0;
		}
		return process2(head);
	}

	// 1)第二种方法： 二叉树的递归套路
	// x为头的整棵树上，最大路径和是多少，返回。
	// 路径要求，一定从x出发，到叶节点，算做一个路径
	public static int process2(Node x) {
		if (x.left == null && x.right == null) {
			return x.value;
		}
		int next = Integer.MIN_VALUE;
		if (x.left != null) {
			next = process2(x.left);
		}
		if (x.right != null) {
			next = Math.max(next, process2(x.right));
		}
		return x.value + next;
	}

	//2)
	public static int maxSum2(Node head) {
		if (head == null) {
			return 0;
		}
		return f2(head).allTreeMaxSum;
	}

	//返回的两个信息
	public static class Info {
		//整树最和
		public int allTreeMaxSum;
		//从头最和
		public int fromHeadMaxSum;

		public Info(int all, int from) {
			allTreeMaxSum = all;
			fromHeadMaxSum = from;
		}
	}

	// 1）X无关的时候， 1， 左树上的整体最大路径和 2， 右树上的整体最大路径和
	// 2) X有关的时候 3， x自己 4， x往左走 5，x往右走
	public static Info f2(Node x) {
		if (x == null) {
			return null;
		}
		//假设左右都能给出信息
		Info leftInfo = f2(x.left);
		Info rightInfo = f2(x.right);
		//5种可能性
		int p1 = Integer.MIN_VALUE;
		//如果左树上真能给出信息
		if (leftInfo != null) {
			//第一种可能性 左树上的整体最大路径和
			p1 = leftInfo.allTreeMaxSum;
		}
		int p2 = Integer.MIN_VALUE;
		//如果右树上真能给出信息
		if (rightInfo != null) {
			//第二种可能性 右树上的整体最大路径和
			p2 = rightInfo.allTreeMaxSum;
		}
		//第三种可能性 只有X自己
		int p3 = x.value;
		int p4 = Integer.MIN_VALUE;
		//如果左树上真能给出信息
		if (leftInfo != null) {
			//当前头节点 再加上左树上从头节点出发的最大路径和
			p4 = x.value + leftInfo.fromHeadMaxSum;
		}
		int p5 = Integer.MIN_VALUE;
		//如果右树上真能给出信息
		if (rightInfo != null) {
			//当前头节点 再加上右树上从头节点出发的最大路径和
			p5 = x.value + rightInfo.fromHeadMaxSum;
		}
		//整树上的最大路径和
		int allTreeMaxSum = Math.max(Math.max(Math.max(p1, p2), p3), Math.max(p4, p5));
		//从头节点出发的最大路径和
		int fromHeadMaxSum = Math.max(Math.max(p3, p4), p5);
		return new Info(allTreeMaxSum, fromHeadMaxSum);
	}

	//3)
	// 1）X无关的时候， 1， 左树上的整体最大路径和 2， 右树上的整体最大路径和
	// 2) X有关的时候 3， x自己 4， x往左走 5，x往右走 6, 既往左，又往右
	public static Info f3(Node x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = f3(x.left);
		Info rightInfo = f3(x.right);
		int p1 = Integer.MIN_VALUE;
		if (leftInfo != null) {
			p1 = leftInfo.allTreeMaxSum;
		}
		int p2 = Integer.MIN_VALUE;
		if (rightInfo != null) {
			p2 = rightInfo.allTreeMaxSum;
		}
		int p3 = x.value;
		int p4 = Integer.MIN_VALUE;
		if (leftInfo != null) {
			p4 = x.value + leftInfo.fromHeadMaxSum;
		}
		int p5 = Integer.MIN_VALUE;
		if (rightInfo != null) {
			p5 = x.value + rightInfo.fromHeadMaxSum;
		}

		int p6 = Integer.MIN_VALUE;
		if (leftInfo != null && rightInfo != null) {
			p6 = x.value + leftInfo.fromHeadMaxSum + rightInfo.fromHeadMaxSum;
		}

		int allTreeMaxSum = Math.max(Math.max(Math.max(p1, p2), p3), Math.max(Math.max(p4, p5), p6));
		//在从头出发的情况下 不能将P6算进来
		int fromHeadMaxSum = Math.max(Math.max(p3, p4), p5);
		return new Info(allTreeMaxSum, fromHeadMaxSum);
	}

	public static int max = Integer.MIN_VALUE;

	//4)
	public static int bigShuai(Node head) {
		if (head.left == null && head.right == null) {
			//到叶子节点时也有权更新max
			max = Math.max(max, head.value);
			return head.value;
		}
		int nextMax = 0;
		//如果左树不为空
		if (head.left != null) {
			//从左孩子出发到左树叶子节点的最大路径和
			nextMax = bigShuai(head.left);
		}
		//如果右树不为空  最大路径和重新决策一下
		if (head.right != null) {
			nextMax = Math.max(nextMax, bigShuai(head.right));
		}
		int ans = head.value + nextMax;
		max = Math.max(max, ans);
		return ans;
	}

}
