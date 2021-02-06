
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

public static int getMaxSum(int[] arr){
   int N =  arr.length;
   if (N == 0){
       return 0;
   }
   int[] dp = new int[N];
   dp[0] = arr[0];
   int max = Integer.MIN_VALUE;
   for (int i = 1; i < N; i++){
       int p1 = arr[i];
       int p2 = arr[i] + dp[i - 1];
       dp[i] = Math.max(p1,p2);
       max = Math.max( max,dp[i]);
   }
   return max;
}
public static int getMaxSumFollowUp(int[] arr){
    int N = arr.length;
    if(N == 0){
        return 0;
    }
    if (N == 1){
        return arr[0];
    }
    if (N == 2){
        return Math.max(arr[0],arr[1]);
    }
    int[] dp = new  int[N];
    dp[0] = arr[0];
    dp[1] = Math.max(arr[0], arr[1]);
    for (int i = 2; i < N; i++){
        int p1 = arr[i];
        int p2 = arr[i] + dp[i - 2];
        int p3 = dp[i - 1];
        dp[i] = Math.max(Math.max(p1,p2),p3);
    }
    return dp[N - 1];
}

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(date));

        int[] arr = {1,9,6,1,0,0,0,0,0};
        System.out.println(getMaxSum(arr));
        System.out.println(getMaxSumFollowUp(arr));
    }

}


