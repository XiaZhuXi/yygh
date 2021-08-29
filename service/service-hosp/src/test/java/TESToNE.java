public class TESToNE {
    public static void main(String[] args) {
        int arr[] = {9, 6, 11, 3, 5, 12, 8, 7, 10, 15, 14, 4, 1, 13, 2};
        for (int k = arr.length/2; k >0; k/=2) {
            for (int i = k; i < arr.length; i++) {
                for (int j = i; j > k-1; j-=k) {
                    if(arr[j]<arr[j-k]){
                        swap(arr, j, j - k);
                    }
                }
            }
        }
       // System.out.println("\t");
        for (int l = 0; l < arr.length; l++) {
            System.out.print(arr[l] + " ");
        }
    }

    static void swap(int arr[], int i, int j) {
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
