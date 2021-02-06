package class07;

import java.util.HashMap;
import java.util.Stack;

public class Code04_LargestComponentSizebyCommonFactor {

	// arr中没有小于1的数
	public static int largestComponentSize(int[] arr) {
		UnionFindSet1 set = new UnionFindSet1(arr.length);
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (gcd(arr[i], arr[j]) != 1) {
					set.union(i, j);
				}
			}
		}
		return set.maxSize();
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	public static class UnionFindSet1 {
		public HashMap<Integer, Integer> fatherMap; // key 的父亲 value
		// key是代表点的时候，才有记录，value是所在集合一共有多少点
		public HashMap<Integer, Integer> sizeMap;

		public UnionFindSet1(int size) {
			fatherMap = new HashMap<>();
			sizeMap = new HashMap<>();
			for (int i = 0; i < size; i++) {
				fatherMap.put(i, i);
				sizeMap.put(i, 1);
			}
		}

		public int size() {
			return sizeMap.size();
		}

		public int maxSize() {
			int ans = 0;
			for (int size : sizeMap.values()) {
				ans = Math.max(ans, size);
			}
			return ans;
		}

		private int findHead(int element) {
			Stack<Integer> path = new Stack<>();
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		public void union(int a, int b) {
			int aF = findHead(a);
			int bF = findHead(b);
			if (aF != bF) {
				int big = sizeMap.get(aF) >= sizeMap.get(bF) ? aF : bF;
				int small = big == aF ? bF : aF;
				fatherMap.put(small, big);
				sizeMap.put(big, sizeMap.get(aF) + sizeMap.get(bF));
				sizeMap.remove(small);
			}

		}
	}

	public static int largestComponentSize2(int[] arr) {
		UnionFindSet2 unionFind = new UnionFindSet2(arr.length);
		// key 是某一个因子，
		// value 是包含该因子的数，在数组中的位置
		//每个因子只记录其中一个位置即可
		HashMap<Integer, Integer> fatorsMap = new HashMap<>();
		
		
		for (int i = 0; i < arr.length; i++) {
			int num = arr[i];
			int limit = (int) Math.sqrt(num); //试 1 ~ 根号num
			for (int j = 1; j <= limit; j++) { // j是现在试的因子
				if (num % j == 0) { // num含有j的因子
					if (j != 1) { // 这个因子不是1
						// 因子表中如果没有j 那么这个因子就是第一次出现
						// 当前数是含有j因子的第一个数
						if (!fatorsMap.containsKey(j)) {
							//加入因子表 j这个因子 i位置拥有
							fatorsMap.put(j, i);
						} else {
							//如果j已经存在于因子表中
							//将之前那个j属于数组上哪个数的哪个位置
							//把它查出来 和当前i位置的数 合并集合
							unionFind.union(fatorsMap.get(j), i);
						}
					}
					//一次能试出一对因子 一个是j 另一个是 other
					int other = num / j; // other * j == num
					if (other != 1) { // num含有other的因子
						if (!fatorsMap.containsKey(other)) {
							fatorsMap.put(other, i);
						} else {
							unionFind.union(fatorsMap.get(other), i);
						}
					}
				}
			}
		}
		//最后返回并查集一共有多少个集合
		return unionFind.maxSize();
	}

	public static class UnionFindSet2 {
		private int[] parents; // parents[i] = j arr[i]的父亲是arr[j]
		private int[] sizes; // sizes[i] = X arr[i]所在的集合大小为X

		public UnionFindSet2(int len) {
			parents = new int[len];
			sizes = new int[len];
			for (int i = 0; i < len; i++) {
				parents[i] = i;
				sizes[i] = 1;
			}
		}

		public int size() {
			int ans = 0;
			for (int i = 0; i < sizes.length; i++) {
				ans += sizes[i] != 0 ? 1 : 0;
			}
			return ans;
		}

		public int maxSize() {
			int ans = 0;
			for (int size : sizes) {
				ans = Math.max(ans, size);
			}
			return ans;
		}

		private int findHead(int element) {
			Stack<Integer> path = new Stack<>();
			while (element != parents[element]) {
				path.push(element);
				element = parents[element];
			}
			while (!path.isEmpty()) {
				parents[path.pop()] = element;
			}
			return element;
		}

		// a 和 b 分别是两个数的位置，不是值
		public void union(int a, int b) {
			int aF = findHead(a);
			int bF = findHead(b);
			if (aF != bF) {
				int big = sizes[aF] >= sizes[bF] ? aF : bF;
				int small = big == aF ? bF : aF;
				parents[small] = big;
				sizes[big] = sizes[aF] + sizes[bF];
				sizes[small] = 0;
			}
		}
	}

	public static void main(String[] args) {
		int[] test = { 2, 3, 6, 7, 4, 12, 21, 39 };
		System.out.println(largestComponentSize2(test));
	}

}
