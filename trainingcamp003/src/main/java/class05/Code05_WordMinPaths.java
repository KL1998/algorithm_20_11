package class05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Code05_WordMinPaths {

	public static List<List<String>> findMinPaths(
			String start, 
			String end,
			List<String> list) {
		//先将start也加入list
		list.add(start);
		//生成邻居表
		HashMap<String, ArrayList<String>> nexts = getNexts(list);
		//求所有字符串到start字符串的最短距离
		//任何一个字符串  key 到start的距离  记在value上
		//通过宽度优先遍历生成距离表
		HashMap<String, Integer> distances = getDistances(start, nexts);

		HashMap<Object,Object> map = new HashMap<>();

		//沿途走过的路径
		LinkedList<String> pathList = new LinkedList<>();
        //收集所有的答案
		List<List<String>> res = new ArrayList<>();
		//深度优先遍历
		//从start出发 目的地是end 借助生成的距离表和邻居表
		//一开始路径上没有任何一个字符串pathList为空 结果也是空的
		getShortestPaths(start, end, nexts, distances, pathList, res);

		return res;
	}

	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		// List 所有东西放入 set
		Set<String> dict = new HashSet<>(words);
		//封装邻居表
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();

		for (int i = 0; i < words.size(); i++) {
			//对于list中的每一个字符串words.get(i)
			//将其邻居列表生成好 getNext(words.get(i), dict)
			//list中所有的字符串都在dict 这个hashset中
			//给出具体的一个单词，返回其在hashset中的所有邻居
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}

		return nexts;
	}

	private static ArrayList<String> getNext(String word, Set<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		//该字符串的每一个位置遍历从a到z的可能性
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
				//不要遍历当前字符串该位置上的字符
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return res;
	}

	//图的宽度优先遍历
	public static HashMap<String, Integer> getDistances(String start,
			HashMap<String, ArrayList<String>> nexts) {
		HashMap<String, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		Queue<String> queue = new LinkedList<String>();
		queue.add(start);
		//为了不让重复的字符串进出队列
		HashSet<String> set = new HashSet<String>();
		set.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			for (String next : nexts.get(cur)) {
				if (!set.contains(next)) {
					distances.put(next, distances.get(cur) + 1);
					queue.add(next);
					set.add(next);
				}
			}
		}
		return distances;
	}

	//深度优先遍历
	// 现在来到了什么：cur
	// 目的地：end
	// 邻居表：nexts
	// 最短距离表：distances
	// 沿途走过的路径：path上{....}
	// 答案往res里放，收集所有的最短路径
	private static void getShortestPaths(
			String cur, String to,
			HashMap<String, ArrayList<String>> nexts,
			HashMap<String, Integer> distances,
			LinkedList<String> path,
			List<List<String>> res) {
		path.add(cur);
		//如果当前来到的字符串和目标字符串是一个
		if (to.equals(cur)) {
			//将这一条路径统一加入结果中去
			res.add(new LinkedList<String>(path));
		} else {
			//如果当前字符串不是目标字符串
			//找出当前cur的所有邻居
			for (String next : nexts.get(cur)) {
				//此条支路必须严格为距离加1的邻居 才能继续向下走
				if (distances.get(next) == distances.get(cur) + 1) {
					getShortestPaths(next, to, nexts, distances, path, res);
				}
			}
		}
		//弹出  擦掉轨迹 使得能重新尝试其他支路    a -> b
		//                                     a -> c
		path.pollLast();
	}

	public static void main(String[] args) {
		String start = "abc";
		String end = "cab";
		String[] test = { "abc", "cab", "acc", "cbc", "ccc", "cac", "cbb",
				"aab", "abb" };
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < test.length; i++) {
			list.add(test[i]);
		}
		List<List<String>> res = findMinPaths(start, end, list);
		for (List<String> obj : res) {
			for (String str : obj) {
				System.out.print(str + " -> ");
			}
			System.out.println();
		}

	}

}
