public class test1
{


    public static int maxP(int k,int arr[]){
        if (arr.length == 0 || k ==0){
            return 0;
        }
        int N = arr.length;
        int[][]dp = new int[N][k+1];
        int ans = 0;
        int i,j;
        for (j = 1;j<=k;j++){
            int t = dp[0][j - 1] - arr[0];
            for (i = 1;i<N;i++){
                t = Math.max(t,dp[i][j - 1] - arr[i]);
                dp[i][j] = Math.max(t+arr[i],dp[i - 1][j]);
                ans =  Math.max(dp[i][j],ans);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] test = { 4, 1, 231, 21, 12, 312, 312, 3, 5, 2, 423, 43, 146 };
        int K = 3;
        System.out.println(maxP(K, test));
    }
}
