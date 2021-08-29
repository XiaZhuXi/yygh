public class TestTwo {
    public static void main(String[] args) {
        int arr[] = {1, 4, 6, 9,};
        sort(arr,0,arr.length-1);
        print(arr);
    }

    static void sort(int arr[], int left, int right) {
//        if (left == right) return;
//        //分两半
//        int mid = left + (right - left) / 2;
//        //左边排序
//        sort(arr, left, mid);
//        //右边排序
//        sort(arr, mid + 1, right);
//        merge(arr, left, mid + 1, right);
        if (left>=right)return;
       int mind= quick(arr,left,right);
       sort(arr,left,mind-1);
       sort(arr,mind+1,right);

    }

    static int quick(int arr[], int leftPrt, int rightBound) {
        int mid = arr[rightBound];
        int left = leftPrt;
        int right = rightBound - 1;
        while (left < right) {
            while (left <= right&&arr[left] <= mid) left++;
            System.out.println("left:=="+left);
            while (left < right&&arr[right] > mid) right--;
            System.out.println("right:=="+right);
            if (left < right) swap(arr, left, right);
        }
        System.out.println("right bound=="+rightBound);
        if(arr[left]>arr[rightBound]){
            swap(arr,left,rightBound);
        }
        return left;
    }

    static void merge(int arr[], int leftPtr, int rightPtr, int rightBound) {
        int mid = rightPtr - 1;
        int[] temp = new int[rightBound - leftPtr + 1];
        int i = leftPtr;
        int j = rightPtr;
        int k = 0;
        while (i <= mid && j <= rightBound) {
            temp[k++] = arr[i] < arr[j] ? arr[i++] : arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];//如果左边(leftPtr  ---> mid)有剩余的数直接放在temp中
        while (j <= rightBound) temp[k++] = arr[j++];//如果右边(rightPtr  ---> rightBound))有剩余的数直接放在temp中
        for (int l = 0; l < temp.length; l++) {
            arr[leftPtr + l] = temp[l];
        }
    }

    static void print(int arr[]) {
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
