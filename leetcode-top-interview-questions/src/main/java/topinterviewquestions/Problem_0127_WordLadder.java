package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0127_WordLadder {

	public static int ladderLength1(String start, String to, List<String> list) {
		list.add(start);
		HashMap<String, ArrayList<String>> nexts = getNexts(list);
		HashMap<String, Integer> distanceMap = new HashMap<>();
		distanceMap.put(start, 1);
		HashSet<String> set = new HashSet<>();
		set.add(start);
		Queue<String> queue = new LinkedList<>();
		queue.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			Integer distance = distanceMap.get(cur);
			for (String next : nexts.get(cur)) {
				if (next.equals(to)) {
					return distance + 1;
				}
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
					distanceMap.put(next, distance + 1);
				}
			}

		}
		return 0;
	}

	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		HashSet<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	// 应该根据具体数据状况决定用什么来找邻居
	// 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
	// 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
	public static ArrayList<String> getNext(String word, HashSet<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			for (int i = 0; i < chs.length; i++) {
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


	public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
		HashSet<String> dict = new HashSet<>(wordList);
		if (!dict.contains(endWord)) {
			return 0;
		}
		HashSet<String> startSet = new HashSet<>();
		HashSet<String> endSet = new HashSet<>();
		HashSet<String> visit = new HashSet<>();
		startSet.add(beginWord);
		endSet.add(endWord);
		//在startSet和endSet中初始已经各有一个，有两个单词了，
		//所以想要从start走向end起码长度是2
		//先从start开始走
		for (int len = 2; !startSet.isEmpty(); len++) {
			HashSet<String> nextSet = new HashSet<>();
			//start中的每个单词  每一位都去变换一下 去字典中找到变化一个字符能形成的字符串
			for (String w : startSet) {
				for (int j = 0; j < w.length(); j++) {
					char[] ch = w.toCharArray();
					for (char c = 'a'; c <= 'z'; c++) {
						//假设s = "abc" 第一个位置的字符尝试a~z但别尝试a 第二个位置的字符尝试a~z 但别尝试b
						if (c != w.charAt(j)) {
							ch[j] = c;
							String next = String.valueOf(ch);
							//如果找到了 返回此时的长度
							if (endSet.contains(next)) {
								return len;
							}
							if (dict.contains(next) && !visit.contains(next)) {
								//下一层把next加进去
								nextSet.add(next);
								//避免将一个单词重复加入 所以维持一个visit 去重的机制
								visit.add(next);
							}
						}
					}
				}
			}
			//层序的优化  从下一层单词数交短的一头 定义为start
			startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
			endSet = (startSet == nextSet) ? endSet : nextSet;
		}
		return 0;
	}

}
