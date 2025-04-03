
abstract class AbstractSort{
    public abstract void sort(Comparable[] arr);

    protected void show(Comparable[] arr){
        for(Comparable c:arr){
            System.out.print(c+" ");
        }
        System.out.println();
    }

    protected boolean less(Comparable v,Comparable w){
        return v.compareTo(w)<0;
    }

    protected void exch(Comparable[] arr,int i,int j){
        Comparable t=arr[i];
        arr[i]=arr[j];
        arr[j]=t;
    }

    protected boolean isSorted(Comparable[] arr){
        for(int i=1;i<arr.length;i++){
            if(less(arr[i],arr[i-1])) return false;
        }
        return true;
    }
}

class QuickSort extends AbstractSort{
    @Override
    public void sort(Comparable[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(Comparable[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private int partition(Comparable[] arr, int low, int high) {
        Comparable pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (less(arr[j], pivot)) {
                i++;
                exch(arr, i, j);
            }
        }
        exch(arr, i + 1, high);
        return i + 1;
    }
}


public class HW1 {
    public static void main(String[] args) {


    }
}