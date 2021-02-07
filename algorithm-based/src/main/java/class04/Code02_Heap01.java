package class04;

/**
 * 大根堆的建立
 */
public class Code02_Heap01 {

	public static class MyMaxHeap {
		private int[] heap;
		private final int limit;
		private int heapSize;

		public MyMaxHeap(int limit) {
			heap = new int[limit];
			this.limit = limit;
			heapSize = 0;
		}

		public boolean isEmpty() {
			return heapSize == 0;
		}

		public boolean isFull() {
			return heapSize == limit;
		}

		public void push(int value) {
			if (heapSize == limit) {
				throw new RuntimeException("heap is full");
			}
			heap[heapSize] = value;
			// value  heapSize
			heapInsert(heap, heapSize++);
		}

		// 用户此时，让你返回最大值，并且在大根堆中，把最大值删掉
		// 剩下的数，依然保持大根堆组织
		public int pop() {
			int ans = heap[0];
			swap(heap, 0, --heapSize);
			heapify(heap, 0, heapSize);
			return ans;
		}

		//找到当前加入值，在堆中正确的位置
		private void heapInsert(int[] arr, int index) {
			// arr[index] 向上看
			// arr[index] 不比 arr[index父]大了 ， 停
			// index = 0;
			while (arr[index] > arr[(index - 1) / 2]) {
				swap(arr, index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		//如果当树中一个值变小 ，要重新组织堆 将这个节点向下沉
		//堆排序就是 每次 将堆顶的节点和最后一个叶子节点交换，然后heapsize - 1 然后重新调整堆
		//被换到最后位置的节点就不动了 每次找到一个最大值
		//从index位置，往下看，不断的下沉，
		//停：我的孩子都不再比我大；已经没孩子了
		private void heapify(int[] arr, int index, int heapSize) {
			int left = index * 2 + 1;
			//如果左孩子下表不越界 那么就说明有左孩子 如果左孩子的下标越界 那么 右孩子下标一定也越界 因为 right = index * 2 + 2
			while (left < heapSize) {
				// 左右两个孩子中，谁大，谁把自己的下标给largest
				// 右  ->  1) 有右孩子   && 2）右孩子的值比左孩子大才行
				// 否则，左
				int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
				//在找到左右孩子中的最大值后 和自己比较 最终将 largest的下标 给更大的值
				largest = arr[largest] > arr[index] ? largest : index;
				if (largest == index) {
					break;
				}
				swap(arr, largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}

		private void swap(int[] arr, int i, int j) {
			int tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}

	}

	public static class RightMaxHeap {
		private int[] arr;
		private final int limit;
		private int size;

		public RightMaxHeap(int limit) {
			arr = new int[limit];
			this.limit = limit;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public boolean isFull() {
			return size == limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("heap is full");
			}
			arr[size++] = value;
		}

		public int pop() {
			int maxIndex = 0;
			for (int i = 1; i < size; i++) {
				if (arr[i] > arr[maxIndex]) {
					maxIndex = i;
				}
			}
			int ans = arr[maxIndex];
			arr[maxIndex] = arr[--size];
			return ans;
		}

	}

	public static void main(String[] args) {
		int value = 1000;
		int limit = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			int curLimit = (int) (Math.random() * limit) + 1;
			MyMaxHeap my = new MyMaxHeap(curLimit);
			RightMaxHeap test = new RightMaxHeap(curLimit);
			int curOpTimes = (int) (Math.random() * limit);
			for (int j = 0; j < curOpTimes; j++) {
				if (my.isEmpty() != test.isEmpty()) {
					System.out.println("Oops!");
				}
				if (my.isFull() != test.isFull()) {
					System.out.println("Oops!");
				}
				if (my.isEmpty()) {
					int curValue = (int) (Math.random() * value);
					my.push(curValue);
					test.push(curValue);
				} else if (my.isFull()) {
					if (my.pop() != test.pop()) {
						System.out.println("Oops!");
					}
				} else {
					if (Math.random() < 0.5) {
						int curValue = (int) (Math.random() * value);
						my.push(curValue);
						test.push(curValue);
					} else {
						if (my.pop() != test.pop()) {
							System.out.println("Oops!");
						}
					}
				}
			}
		}
		System.out.println("finish!");

	}

}
