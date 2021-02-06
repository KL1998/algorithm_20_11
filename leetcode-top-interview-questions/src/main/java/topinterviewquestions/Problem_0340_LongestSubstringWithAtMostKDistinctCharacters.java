package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {

	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (s == null || s.length() == 0 || k < 1) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		//记账表
		//下标表示字母 下标上的值表示 收集到的该字符的个数
		int[] count = new int[256];
		//收集到字符种类的数量
		int diff = 0;
		int R = 0;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			// i 为 窗口的左边界
			// R 窗口的右边界
			while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
				diff += count[str[R]] == 0 ? 1 : 0;
				count[str[R++]]++;
			}
			// R 来到违规的第一个位置
			ans = Math.max(ans, R - i);
			//L 窗口左边界往右缩
			//count[str[i]] == 1 表示此时窗口内只有1个该字符 窗口往右缩一个后 形成的新窗口中就没有该字符了
			//所以 新形成的窗口中的字符种数 diff 应 -1  否侧 diff应不减
			diff -= count[str[i]] == 1 ? 1 : 0;
			//窗口往右缩 该字符的数量就 --
			count[str[i]]--;
		}
		return ans;
	}


	public int lengthOfLongestSubstringKDistinct2(String s, int k) {
		if(s == null || s.length() == 0 || k < 1){
			return 0;
		}
		int N = s.length();
		char[] str = s.toCharArray();
		Map<Character,Integer> map = new HashMap<>();

		int r = 0;
		int ans = 0;
		int diff = 0;
		for(int i = 0; i < N ; i++){

			while(r < N &&  (diff<k || (diff==k && map.containsKey(str[r])))){
				if(map.containsKey(str[r])){
					map.put(str[r],map.get(str[r]) + 1);
				}else{
					map.put(str[r],1);
					diff++;
				}

				r++;
			}
			ans = Math.max(ans,r - i);
			if(map.get(str[i]) == 1){
				map.remove(str[i]);
				diff--;
			}else{
				map.put(str[i],map.get(str[i]) - 1);
			}
		}
		return ans;
	}

}
