import java.util.Arrays;

public class TestJsSort {
    public static void main(String[] args) {
        int arr[] = {132, 432, 221, 345, 211, 167};
        int[] res = jsSort(arr);
        System.out.println(Arrays.toString(res));
    }

    static int[] jsSort(int arr[]) {
        int[] result = new int[arr.length];
        int[] count = new int[10];
        for (int i = 0; i < 3; i++) {
            int division = (int) Math.pow(10, i);
            for (int j = 0; j < arr.length; j++) {
                int num = arr[j] / division % 10;
                count[num]++;
            }
            for (int k = 1; k < count.length; k++) {
                count[k] = count[k] + count[k - 1];
            }
            System.out.println(Arrays.toString(count));
            for (int m = arr.length - 1; m >= 0; m--) {
                int num = arr[m] / division % 10;
                result[--count[num]] = arr[m];
            }
            System.out.println(Arrays.toString(result));
            System.arraycopy(result ,0,arr,0,arr.length);
            Arrays.fill(count,0);
        }
        return result;
    }
}
