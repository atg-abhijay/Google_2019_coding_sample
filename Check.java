import java.util.PriorityQueue;

public class Check {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] B = new int[] {-1, 0, 1, 2, 3};
        sol.solution(2, B);
        System.out.println();
        int[] A = new int[] {-1, 0, 4, 2, 1};
        sol.solution(3, A);
    }
}