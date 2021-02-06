package class01;

import java.util.LinkedList;

public class Code02_AllLessNumSubArray {

	public static int getNum(int[] arr, int num) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		LinkedList<Integer> qmin = new LinkedList<Integer>();
		LinkedList<Integer> qmax = new LinkedList<Integer>();
		int L = 0;
		int R = 0;
		//规当L == R 时窗口内无数  左闭右开
		// [L..R) -> [0,0) 窗口内无数 [1,1)
		// [0,1) -> [0~0]
		//R 是最后一个达标的位置 再往下一个位置
		//也就是说R是第一个不达标的位置
		int res = 0;
		while (L < arr.length) { // L是开头位置，尝试每一个开头

			// 如果此时窗口的开头是L,下面的while工作:R向右扩到违规为止

			//R - 1 是最后一个达标的位置
			//扩到最后一个不达标的位置停
			while (R < arr.length) { // R是最后一个达标位置的再下一个
				//当R位置的数进来时，最小的更新结构的更新方式
				while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[R]) {
					qmin.pollLast();
				}
				qmin.addLast(R);
				// R -> arr[R],
				//当R位置的数进来时，最大的更新结构的更新方式
				while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
					qmax.pollLast();
				}
				qmax.addLast(R);

				//一旦发现不达标
				if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
					//从这个while循环中跳出来
					//此时R就来到了一个不达标的位置
					break;
				}
				//如果达标就一直扩
				R++;
			}

			// R是最后一个达标位置的再下一个，第一个违规的位置
			res += R - L;

			//L要向右移动  当前位置过期
			//判断队列中头步位置的数过没过期
			if (qmin.peekFirst() == L) {
				qmin.pollFirst();
			}
			if (qmax.peekFirst() == L) {
				qmax.pollFirst();
			}

			L++;

		}
		return res;
	}


	// for test
	public static int[] getRandomArray(int len) {
		if (len < 0) {
			return null;
		}
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 10);
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[] arr = getRandomArray(30);
		int num = 5;
		printArray(arr);
		System.out.println(getNum(arr, num));

	}

}
