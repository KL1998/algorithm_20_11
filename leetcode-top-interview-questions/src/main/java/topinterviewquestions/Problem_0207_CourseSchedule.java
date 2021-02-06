package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0207_CourseSchedule {

	// 一个node，就是一个课程
	// name是课程的编号
	// in是课程的入度
	public static class Node {
		public int name;
		public int in;
		//邻居节点
		public ArrayList<Node> nexts;

		public Node(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		//map中只记录有依赖关系的课程  没有依赖关系的课程自然能够学完
		HashMap<Integer, Node> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Node(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Node(from));
			}
			Node t = nodes.get(to);
			Node f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}

		//需要多少个点被弹出
		int needPrerequisiteNums = nodes.size();
		//先将入度为的点 进队列
		Queue<Node> zeroInQueue = new LinkedList<>();
		for (Node node : nodes.values()) {
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			//当前入度为的点弹出
			Node cur = zeroInQueue.poll();
			//实际弹出的点
			count++;
			//当前点的所有邻居 入度 --后如果是0 邻居就进队列
			for (Node next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		//需要弹出的点 如果和实际弹出的点相等  返回true
		return count == needPrerequisiteNums;
	}

}
