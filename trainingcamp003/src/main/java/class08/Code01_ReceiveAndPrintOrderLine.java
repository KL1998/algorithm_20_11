package class08;

import java.util.HashMap;

public class Code01_ReceiveAndPrintOrderLine {

	public static class Node {
		public String info;
		public Node next;

		public Node(String str) {
			info = str;
		}
	}

	public static class MessageBox {
		private HashMap<Integer, Node> headMap;
		private HashMap<Integer, Node> tailMap;
		//记录当前等待的信息 的编号
		//假如当前 6~7已经串好  此时等待的节点信息就是1号信息
		private int waitPoint;

		public MessageBox() {
			headMap = new HashMap<Integer, Node>();
			tailMap = new HashMap<Integer, Node>();
			waitPoint = 1;
		}

		//对外只提供一个接受信息的方法
		// 消息的编号，info消息的内容, 消息一定从1开始
		//所有的信息规定从1到N
		public void receive(int num, String info) {
			if (num < 1) {
				return;
			}
			//封装节点对象
			Node cur = new Node(info);
			// 先建立num~num的连续区间
			//头表
			headMap.put(num, cur);
			//尾表
			tailMap.put(num, cur);
			// 建立了num~num这个连续区间的头和尾之后
			// 再查询有没有某个连续区间以num-1结尾
			if (tailMap.containsKey(num - 1)) {
				tailMap.get(num - 1).next = cur;
				tailMap.remove(num - 1);
				headMap.remove(num);
			}
			// 再查询有没有某个连续区间以num+1开头的
			if (headMap.containsKey(num + 1)) {
				cur.next = headMap.get(num + 1);
				tailMap.remove(num);
				headMap.remove(num + 1);
			}
			//如果此时来的编号信息是等待的信息 就打印
			if (num == waitPoint) {
				print();
			}
		}

		private void print() {
			//将这个等待的信息从头表中查出
			Node node = headMap.get(waitPoint);
			//打印就要释放
			//先将这个等待信息在头表中删除
			headMap.remove(waitPoint);
			//next指针会断掉
			//意味着当前连续区间已经打印完了
			//waitPoint更新到下一个等待的节点
			while (node != null) {
				System.out.print(node.info + " ");
				node = node.next;
				waitPoint++;
			}
			//比如 1~7打印完 等待信息的编号已经来到 8
			//所以waitPoint-1为当前打印完的结尾编号
			//在尾表中删除此时这条打印完的链表的结尾的信息
			tailMap.remove(waitPoint-1);
			System.out.println();
		}

	}

	public static void main(String[] args) {
		// MessageBox only receive 1~N
		MessageBox box = new MessageBox();

		box.receive(2,"B"); // - 2"
		box.receive(1,"A"); // 1 2 -> print, trigger is 1

		box.receive(4,"D"); // - 4
		box.receive(5,"E"); // - 4 5
		box.receive(7,"G"); // - 4 5 - 7
		box.receive(8,"H"); // - 4 5 - 7 8
		box.receive(6,"F"); // - 4 5 6 7 8
		box.receive(3,"C"); // 3 4 5 6 7 8 -> print, trigger is 3

		box.receive(9,"I"); // 9 -> print, trigger is 9

		box.receive(10,"J"); // 10 -> print, trigger is 10

		box.receive(12,"L"); // - 12
		box.receive(13,"M"); // - 12 13
		box.receive(11,"K"); // 11 12 13 -> print, trigger is 11

	}
}
