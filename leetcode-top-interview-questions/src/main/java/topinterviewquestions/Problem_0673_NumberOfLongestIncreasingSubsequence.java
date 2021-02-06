package topinterviewquestions;

import java.util.ArrayList;
import java.util.TreeMap;

public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	public static int findNumberOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}

		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();

		for (int i = 0; i < nums.length; i++) {
			int L = 0;
			//R从 dp中当前形成的表的最后一个表的位置开始
			int R = dp.size() - 1;
			int find = -1;
			//接下来 二分 while循环结束  find就是当前i位置的num 需要加在哪个表中
			//如果没有找到 >=nums[i] 最左的位置 find维持 -1 表示需要添加新的表 dp[i++]
			while (L <= R) {
				int mid = (L + R) / 2;
				//定位以当前i位置的数为结尾的情况下 最长递增子序列的长度
				//以每张表的最小的key定位   找到 >=nums[i] 最左的位置
				//循环结束后的 find 就是 >=nums[i] 最左的位置
				if (dp.get(mid).firstKey() >= nums[i]) {
					find = mid;
					R = mid - 1;
				} else {
					L = mid + 1;
				}
			}

			int num = 1;

			//如果没有找到 >=nums[i] 最左的位置 find维持 -1 表示需要添加新的表 dp[i++]
			int index = find == -1 ? dp.size() : find;

			//如果当前定位到的表 之前还有表
			if (index > 0) {

				//找到当前待加入记录的表的前一个表
				TreeMap<Integer, Integer> lastMap = dp.get(index - 1);
				//得到前一个表的 最小key 的 value ：数量 这个数量为这张表的 >=firstKey() 一共的数量
				num = lastMap.get(lastMap.firstKey());
				//如果之前表里面有 当前待加入数nums[i] 的key
				if (lastMap.ceilingKey(nums[i]) != null) {
					//用之前得到的总数量 - key为nums[i]的value 数量
					num -= lastMap.get(lastMap.ceilingKey(nums[i]));
				}
			}


			//表示需要扩充新表
			if (index == dp.size()) {
				TreeMap<Integer, Integer> newMap = new TreeMap<Integer, Integer>();
				//当前表添加一个记录 value为之前求出的记录
				newMap.put(nums[i], num);
				//将这张表加入dp
				dp.add(newMap);
			} else {
				//如果找到了 一个 >=nums[i] 最左的位置
				TreeMap<Integer, Integer> curMap = dp.get(index);
				//得到当前 这个位置的这张已有的表 的最小的key 的 value 也就是数量
				//添加新的比这个最小key更小的key     value为 之前最小key的value,再加上求出的num
				//假设当前这个位置的这张已有的表 的最小的key 跟待加入key相等 那么也就是一个更新操作
				curMap.put(nums[i], curMap.get(curMap.firstKey()) + num);
			}
		}
		//返回dp中的最后一张表的第一条记录的value 也就是最小的key的value
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	public static int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		for (int i = 0; i < nums.length; i++) {
			int L = 0;
			int R = dp.size() - 1;
			int find = -1;
			while (L <= R) {
				int mid = (L + R) / 2;
				if (dp.get(mid).firstKey() >= nums[i]) {
					find = mid;
					R = mid - 1;
				} else {
					L = mid + 1;
				}
			}

			if (find == -1) {
				dp.add(new TreeMap<>());
				int index = dp.size() - 1;
				TreeMap<Integer, Integer> cur = dp.get(index);
				int size = 1;


				if (index > 0) {
					TreeMap<Integer, Integer> pre = dp.get(index - 1);
					size = pre.get(pre.firstKey());
					if (pre.ceilingKey(nums[i]) != null) {
						size -= pre.get(pre.ceilingKey(nums[i]));
					}
				}
				cur.put(nums[i], size);

			} else {
				int newAdd = 1;
				if (find > 0) {
					TreeMap<Integer, Integer> pre = dp.get(find - 1);
					newAdd = pre.get(pre.firstKey());
					if (pre.ceilingKey(nums[i]) != null) {
						newAdd -= pre.get(pre.ceilingKey(nums[i]));
					}
				}

				// >=nums[i] ?
				TreeMap<Integer, Integer> cur = dp.get(find);
				if (cur.firstKey() == nums[i]) {
					cur.put(nums[i], cur.get(nums[i]) + newAdd);
				} else {
					int preNum = cur.get(cur.firstKey());
					cur.put(nums[i], newAdd + preNum);
				}
			}

		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

}
