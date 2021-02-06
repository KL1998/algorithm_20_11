package class02;

public class Code02_PackingMachine {

	public static int MinOps(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		//机器的总数量
		int size = arr.length;
		//累加和
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += arr[i];
		}
		if (sum % size != 0) {
			return -1;
		}
		//每台机器应有的包裹数量
		int avg = sum / size;
		//最边一共有的数量
		int leftSum = 0;
		int ans = 0;
		// 每个位置都求各自的
		for (int i = 0; i < arr.length; i++) {
			// i号机器，是中间机器，左(0~i-1) i 右(i+1~N-1)
			//左侧的累加和减去左侧的需要   结果为负 需要输入    结果为正 需要输出
			int leftRest = leftSum - i * avg;
			//(sum - leftSum - arr[i]) ： 右测的累加和
			//(size - i - 1) * avg ：左侧应需要的数量
			//右侧的累加和减去右侧的需要
			int rightRest =  (sum - leftSum - arr[i]) -  (size - i - 1) * avg;
			//只有小于0的时候是两个绝对值相加
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else { //其他任何时候都是 |左| 和 |右|  取一个最大值
				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}
			leftSum += arr[i];
		}
		return ans;
	}

}
