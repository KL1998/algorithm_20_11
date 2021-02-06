package class05;

public class Code03_CompleteTreeNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 请保证head为头的树，是完全二叉树
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	// node在第level层，h是总的深度（h永远不变，全局变量
	// 以node为头的完全二叉树，节点个数是多少
	public static int bs(Node node, int Level, int h) {
		//当前以node节点在最深层
		//那么以node为头的完全二叉树，节点个数是它自己 就一个
		if (Level == h) {
			return 1;
		}
		//以node为头的完全二叉树的右树的最左节点 是否到了最后一层
		if (mostLeftLevel(node.right, Level + 1) == h) {
			//1 << (h - Level) = 2的(h - Level)次方 其左树再加上头节点一共多少个节点
			//再递归求出右树
			return (1 << (h - Level)) + bs(node.right, Level + 1, h);
		} else {
			//(h - Level - 1) -1是因为 右树满的情况是要比左树满的情况少一层的
			//再递归求出左树
			return (1 << (h - Level - 1)) + bs(node.left, Level + 1, h);
		}
	}

	// 给出node在第level层，
	// 求以node为头的子树，最大深度是多少
	// node为头的子树，一定是完全二叉树
	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}

}
