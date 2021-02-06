package topinterviewquestions;

public class Problem_0148_SortList {

	public static class ListNode {
		int val;
		ListNode next;

		public ListNode(int v) {
			val = v;
		}
	}

	public static ListNode sortList(ListNode head) {
		int N = 0;
		ListNode cur = head;
		while (cur != null) {
			N++;
			cur = cur.next;
		}
		ListNode h = head;
		ListNode teamFirst = head;
		ListNode pre = null;
		//例如 当len为1  从链表的头开始 数到一个节点就为merge的左部分 下一个就是有部分
		//当len为1  : 1->2->5->8->6->3 (1 和 2 merge)  (5 和 8 merge) (6 和 3 merge)
		//当len为2  : (1 -> 2 和 5 -> 8 merge) (3->6 不够形成左右一组的 自己 一 组 不动)
		//当len为4  : (1->2->5->8 和 3->6 merge) 结果 : 1->2->3->5->6->8
		for (int len = 1; len < N; len <<= 1) {
			while (teamFirst != null) {
				//返回一个长度为 5的数组 左部分的头和尾 右部分的头和尾 和 这左右一整组之后接下来的节点 next
				ListNode[] hthtn = hthtn(teamFirst, len);
				//hthtn[0], hthtn[1], hthtn[2], hthtn[3] 左部分的头和尾 右部分的头和尾 去merge
				//返回一个长度为2的数组 返回merge之后整组的头和尾
				ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);
				if (h == teamFirst) {
					h = mhmt[0];
					pre = mhmt[1];
				} else {
					pre.next = mhmt[0];
					pre = mhmt[1];
				}
				teamFirst = hthtn[4];
			}
			teamFirst = h;
			pre = null;
		}
		return h;
	}

	public static ListNode[] hthtn(ListNode teamFirst, int len) {
		ListNode ls = teamFirst;
		ListNode le = teamFirst;
		ListNode rs = null;
		ListNode re = null;
		ListNode next = null;
		int pass = 0;
		while (teamFirst != null) {
			pass++;
			if (pass <= len) {
				le = teamFirst;
			}
			if (pass == len + 1) {
				rs = teamFirst;
			}
			if (pass > len) {
				re = teamFirst;
			}
			if (pass == (len << 1)) {
				break;
			}
			teamFirst = teamFirst.next;
		}
		le.next = null;
		if (re != null) {
			next = re.next;
			re.next = null;
		}
		return new ListNode[] { ls, le, rs, re, next };
	}

	public static ListNode[] merge(ListNode ls, ListNode le, ListNode rs, ListNode re) {
		if (rs == null) {
			return new ListNode[] { ls, le };
		}
		ListNode head = null;
		ListNode pre = null;
		ListNode cur = null;
		ListNode tail = null;
		while (ls != le.next && rs != re.next) {
			if (ls.val <= rs.val) {
				cur = ls;
				ls = ls.next;
			} else {
				cur = rs;
				rs = rs.next;
			}
			if (pre == null) {
				head = cur;
				pre = cur;
			} else {
				pre.next = cur;
				pre = cur;
			}
		}
		if (ls != le.next) {
			while (ls != le.next) {
				pre.next = ls;
				pre = ls;
				tail = ls;
				ls = ls.next;
			}
		} else {
			while (rs != re.next) {
				pre.next = rs;
				pre = rs;
				tail = rs;
				rs = rs.next;
			}
		}
		return new ListNode[] { head, tail };
	}

}
