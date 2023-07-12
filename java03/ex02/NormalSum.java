package ex02;

public class NormalSum {
    private int[] arr;
    private int size;
  
    public NormalSum(int[] arr, int size) {
        this.arr = arr;
        this.size = size;
    }
  
    public int sum() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += arr[i];
        }
        return sum;
    }
    
}
