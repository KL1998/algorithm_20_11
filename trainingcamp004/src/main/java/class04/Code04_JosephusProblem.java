package class04;

public class Code04_JosephusProblem {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node josephusKill1(Node head, int m) {
		if (head == null || head.next == head || m < 1) {
			return head;
		}
		Node last = head;
		while (last.next != head) {
			last = last.next;
		}
		int count = 0;
		while (head != last) {
			if (++count == m) {
				last.next = head.next;
				count = 0;
			} else {
				last = last.next;
			}
			head = last.next;
		}
		return head;
	}







	public static Node josephusKill2(Node head, int m) {
		if (head == null || head.next == head || m < 1) {
			return head;
		}
		Node cur = head.next;
		int size = 1; // tmp -> list size
		//先数一下一共几个节点  得到链表长度
		while (cur != head) {
			size++;
			cur = cur.next;
		}
		int live = getLive(size, m); // tmp -> service node position
		while (--live != 0) {
			head = head.next;
		}
		head.next = head;
		return head;
	}

	// 现在一共有i个节点，数到m就杀死节点，最终会活下来的节点，
	//
	//在长度为i的环形链表中，如果数到m就杀人，哪个编号的节点能活, 请返回它在有i个节点时候的编号
	//杀死一个节点后 它的下一个存活的节点就变成编号为1的节点
	//所以在杀死一个节点后的编号跟初始时的节点编号会不一样 要求根据其新编号 返回其初始时的节点编号
	public static int getLive(int i, int m) {
		//当只剩下一个节点时 此时他的编号就是1号 1号能活
		if (i == 1) {
			return 1;
		}
		// getLive(i - 1, m)   长度为i-1时候，活下来的编号 也就是新编号
		//旧 = (新 + m - 1) % i + 1
		return (getLive(i - 1, m) + m - 1) % i + 1;
	}

	public static void printCircularList(Node head) {
		if (head == null) {
			return;
		}
		System.out.print("Circular List: " + head.value + " ");
		Node cur = head.next;
		while (cur != head) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println("-> " + head.value);
	}

	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = head1;
		printCircularList(head1);
		head1 = josephusKill1(head1, 3);
		printCircularList(head1);

		Node head2 = new Node(1);
		head2.next = new Node(2);
		head2.next.next = new Node(3);
		head2.next.next.next = new Node(4);
		head2.next.next.next.next = new Node(5);
		head2.next.next.next.next.next = head2;
		printCircularList(head2);
		head2 = josephusKill2(head2, 3);
		printCircularList(head2);

	}

}