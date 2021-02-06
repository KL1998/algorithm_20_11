package topinterviewquestions;

public class Problem_0283_MoveZeroes {

	public static void moveZeroes(int[] nums) {
		int to = -1;
		for (int b = 0; b < nums.length; b++) {
			if (nums[b] != 0) {
				swap(nums, ++to, b);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}





	public static void main(String[] args) {
		int[] A = {0,1,0,3,12};
		 moveZeroes(A);
		 for(int i : A){
			 System.out.println(i + " ");
		 }
	}
}
