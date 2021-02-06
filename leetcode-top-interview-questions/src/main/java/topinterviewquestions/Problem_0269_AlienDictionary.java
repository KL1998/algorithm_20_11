package topinterviewquestions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0269_AlienDictionary {

	public static String alienOrder(String[] words) {
		if (words == null || words.length == 0) {
			return "";
		}
		int N = words.length;
		//建立一张入度表 在生成边的同时将该字母的总入度也记录下来
		HashMap<Character, Integer> indegree = new HashMap<>();
		//先将每个字符串的每个字母 入度都先设置成0
		for (int i = 0; i < N; i++) {
			for (char c : words[i].toCharArray()) {
				indegree.put(c, 0);
			}
		}
		//生成图 例: a -> b  a -> c  key:a value{b , c}
		HashMap<Character, HashSet<Character>> graph = new HashMap<>();
		 //  i 来到0位置 0位置和1位置比较  当i来到倒数第二个位置时 和倒数第一个位置比较
		// 所以 i不用来到 N - 1 位置  所以 i < N - 1
		for (int i = 0; i < N - 1; i++) {
			char[] cur = words[i].toCharArray();
			char[] nex = words[i + 1].toCharArray();
			//最多比较的长度  如果cur 7长度 nex 9 长度  那么两个字符串 只需要比对 7 长度
			int len = Math.min(cur.length, nex.length);
			int j = 0;
			for (; j < len; j++) {
				//如果当前比对的字符不同，那么就找到了一条边
				// 找到了 s -> t 的边
				if (cur[j] != nex[j]) {
					//图表中没有 s这个key
					if (!graph.containsKey(cur[j])) {
						//先将初始化 s的set表 建一张空表出来  key: s value: {}
						graph.put(cur[j], new HashSet<>());
					}
					// s 这个set表中有没有t
					if (!graph.get(cur[j]).contains(nex[j])) {
						//将 t 加入 s的set表
						graph.get(cur[j]).add(nex[j]);
						//然后将 t 的入度 +1
						indegree.put(nex[j], indegree.get(nex[j]) + 1);
					}
					//找到一条边就break 两个字符串后面的部分不用比对了
					break;
				}
			}
			//例如: cur: abct nex: abc  cur比nex长 但又排在nex前面  出现这种情况一定不存在字典序 直接返回空
			if (j < cur.length && j == nex.length) {
				return "";
			}
		}
		//图和入度表 建立完成后 开始拓扑排序
		StringBuilder ans = new StringBuilder();
		Queue<Character> q = new LinkedList<>();
		//入度为0 的先到队列中去
		for (Character key : indegree.keySet()) {
			if (indegree.get(key) == 0) {
				q.offer(key);
			}
		}
		//队列中弹出一个字符 收集到结果中去  并消除 其影响
		//例: a->b  当a 弹出后  消除对 b的影响 b的入度 -1
		while (!q.isEmpty()) {
			char cur = q.poll();
			ans.append(cur);
			//找到图中关于这个字符的所有邻居 消除其影响
			if (graph.containsKey(cur)) {
				for (char next : graph.get(cur)) {
					indegree.put(next, indegree.get(next) - 1);
					//如果此时邻居的入度 -1 后变成了0 就将其放到队列中去
					if (indegree.get(next) == 0) {
						q.offer(next);
					}
				}
			}
		}
		//如果结果的长度 并不等于入度表的size 就说明 拓扑排序并没有完成
		//说明图中出现循环依赖 不能生成有效的字典序
		return ans.length() == indegree.size() ? ans.toString() : "";
	}

}
