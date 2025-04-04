//22211996 박종선
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

class Sort {


    public void show(Comparable[] arr, int k) {
        int count = 0;
        for (Comparable c : arr) {
            if (k >= 0 && count >= k) break;
            System.out.print(c + " ");
            count++;
        }
        System.out.println();
    }


    protected boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }


    protected void exch(Comparable[] arr, int i, int j) {
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public int isSorted(Comparable[] arr, int k) {
        for (int i = 1; i < arr.length && i < k; i++) {
            if (less(arr[i], arr[i - 1])) return i;
        }
        return -1;
    }
}

class SelectionSortK extends Sort{

    public void sort(Comparable[] arr, int k) {
        int n = arr.length;
        if(k == -1) k = n;
        if(n == 0) throw new IllegalArgumentException("배열이 비었습니다.");
        if(k > n || k < 0) throw new IllegalArgumentException("k 범위 초과");

        for (int i = 0; i < k; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }

            exch(arr,i,minIndex);
        }
    }
}


class EnhancedQuickSort extends Sort{

    public void sort(Comparable[] arr, int k) {
        int n = arr.length;
        if(k == -1) k = n;
        if(n == 0) throw new IllegalArgumentException("배열이 비었습니다.");
        if(k > n || k < 0) throw new IllegalArgumentException("k 범위 초과");
        quickSort(arr, 0, arr.length - 1, k);
    }



    private void quickSort(Comparable[] arr, int low, int high, int k) {

        if (low < high) {
            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1, k);

            if (pivotIndex < k-1){
            quickSort(arr, pivotIndex + 1, high, k);
            }
        }
    }

    private void threeSort(Comparable[] arr, int low, int mid, int high){
        if(less(arr[mid],arr[low])) exch(arr, low, mid);
        if(less(arr[high],arr[mid])) exch(arr, mid, high);
        if(less(arr[mid],arr[low])) exch(arr, low, mid);
    }



    private int partition(Comparable[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        threeSort(arr, low, mid, high);
        if(high-low<=2) return mid;

        exch(arr, mid, high-1);
        Comparable pivot = arr[high-1];
        int i = low;
        for (int j = low+1; j < high-1; j++) {
            if (less(arr[j], pivot)) {
                i++;
                exch(arr, i, j);
            }
        }
        exch(arr, i + 1, high-1);

        return i + 1;
    }
}


public class HW1 {
    public static void main(String[] args) {
        try {
            BufferedReader br;
            Scanner sc = new Scanner(System.in);
            while(true) {
                try {
                    System.out.println("파일 이름 입력");
                    String fileName = sc.nextLine();
                    br = new BufferedReader(new FileReader(fileName));
                    break;
                }catch (FileNotFoundException e){
                    System.out.println("지정된 파일을 찾을 수 없습니다.");
                }
            }

            String line = br.readLine();
            if (line == null) throw new Exception("현재 위치 좌표가 포함된 줄이 없습니다.");
            String[] currentPos = line.trim().split("\\s+");
            if (currentPos.length != 2) throw new Exception("현재 위치 좌표가 올바르지 않습니다.");
            double currentX = Double.parseDouble(currentPos[0]);
            double currentY = Double.parseDouble(currentPos[1]);

            line = br.readLine();
            if (line == null) throw new Exception("k 값이 포함된 줄이 없습니다.");
            int k = Integer.parseInt(line.trim());

            line = br.readLine();
            if (line == null) throw new Exception("좌표의 수 n이 포함된 줄이 없습니다.");
            int n = Integer.parseInt(line.trim());
            boolean incorrectN = false;

            Comparable[] distances = new Comparable[n];

            for (int i = 0; i < n; i++) {

                line = br.readLine();

                if (line == null) {
                    System.out.println("경고: 파일에 명시된 좌표 수보다 실제 좌표 수가 적습니다.");
                    n = i+1;
                    incorrectN = true;
                    break;
                }

                String[] coords = line.trim().split("\\s+");
                if (coords.length < 2) {
                    System.out.println("경고: 좌표 데이터가 올바르지 않습니다, 해당 좌표는 건너뜁니다.");
                    n--;
                    i--;
                    incorrectN = true;
                    continue;
                }

                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);

                double distance = Math.sqrt(Math.pow(x - currentX, 2) + Math.pow(y - currentY, 2));
                distances[i] = distance;
            }

            if(incorrectN) {
                distances = Arrays.copyOf(distances, n);
            }

            br.close();

            if(k == 0){
                System.out.println("출력할 원소가 없습니다.");
                return;
            }

            long startTime = System.currentTimeMillis();
            if(k > 5 || k == -1) {
                EnhancedQuickSort quickSorter = new EnhancedQuickSort();
                quickSorter.sort(distances, k);
            }
            else {
                SelectionSortK selectionSorter = new SelectionSortK();
                selectionSorter.sort(distances, k);
            }
            long endTime = System.currentTimeMillis();

            Sort sorter = new Sort();
            sorter.show(distances, k);

            System.out.println("소요 시간: " + (endTime - startTime) + "ms");
            int sorted = sorter.isSorted(distances, k);
            if(sorted == -1) {
                System.out.println("정렬여부: 정렬됨");
            }
            else {
                System.out.println("정렬여부: " + (sorted+1) + "번째 원소가 정렬되지 않음");
            }

        }
        catch (NumberFormatException e){
            System.err.println("파일의 형식이 잘못되었습니다.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}