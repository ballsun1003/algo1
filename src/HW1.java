import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class Sort {

    // 배열의 모든 요소를 출력하는 메소드
    public void show(Comparable[] arr, int k) {
        int count = 0;
        // 배열의 각 요소를 순회하며 출력
        for (Comparable c : arr) {
            if (k >= 0 && count >= k) break;
            System.out.print(c + " ");
            count++;
        }
        // 출력 후 줄바꿈
        System.out.println();
    }

    // 두 요소를 비교하는 메소드: 첫 번째 인자가 두 번째 인자보다 작은지 확인합니다.
    protected boolean less(Comparable v, Comparable w) {
        // compareTo() 메소드는 v가 w보다 작으면 음수, 같으면 0, 크면 양수를 반환합니다.
        return v.compareTo(w) < 0;
    }

    // 배열 내의 두 요소의 위치를 교환하는 메소드
    protected void exch(Comparable[] arr, int i, int j) {
        // 임시 변수 t를 사용하여 두 요소의 값을 교환합니다.
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public int isSorted(Comparable[] arr, int k) {
        // 배열의 첫 번째 원소부터 순회하면서 이전 원소보다 작은 경우가 있는지 확인
        for (int i = 1; i < arr.length && i < k; i++) {
            // 만약 현재 원소가 이전 원소보다 작으면 정렬되지 않은 것으로 판단하여 인덱스 반환
            if (less(arr[i], arr[i - 1])) return i;
        }
        // 모든 원소가 올바른 순서이면 -1 반환
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
            // i번째 이후 원소 중 최소값을 찾습니다.
            for (int j = i + 1; j < n; j++) {
                if (less(arr[j], arr[minIndex])) {
                    minIndex = j;
                }
            }
            // i번째 원소와 찾은 최소값 원소를 교환합니다.
            exch(arr,i,minIndex);
        }
    }
}

// QuickSort 클래스: AbstractSort 클래스를 상속받아 빠른 정렬(Quick Sort)을 구현합니다.
class EnhancedQuickSort extends Sort{

    public void sort(Comparable[] arr, int k) {
        int n = arr.length;
        if(k == -1) k = n;
        if(n == 0) throw new IllegalArgumentException("배열이 비었습니다.");
        if(k > n || k < 0) throw new IllegalArgumentException("k 범위 초과");
        quickSort(arr, 0, arr.length - 1, k);
    }

    // quickSort 메소드: 재귀적으로 배열을 정렬하는 Quick Sort 알고리즘의 핵심 메소드
    // 매개변수 low와 high는 정렬할 배열 부분의 시작과 끝 인덱스입니다.
    private void quickSort(Comparable[] arr, int low, int high, int k) {
        // 정렬할 부분이 2개 이상의 원소를 포함할 때만 수행
        if (low < high) {
            // partition 메소드를 호출하여 피벗의 올바른 위치를 찾습니다.
            int pivotIndex = partition(arr, low, high);
            // 피벗보다 작은 부분 배열을 재귀적으로 정렬
            quickSort(arr, low, pivotIndex - 1, k);
            // 피벗보다 큰 부분에 k가 있으면 배열을 재귀적으로 정렬
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

    // partition 메소드: 배열을 피벗을 기준으로 두 부분으로 분할하는 역할을 합니다.
    // 피벗은 배열의 마지막 원소로 선택되며, 피벗보다 작은 원소들은 왼쪽으로, 큰 원소들은 오른쪽으로 이동시킵니다.
    private int partition(Comparable[] arr, int low, int high) {
        int mid = low + (high - low) / 2;
        threeSort(arr, low, mid, high);
        if(high-low<=2) return mid;//범위가 3미만이면 threeSort로 정렬 끝

        exch(arr, mid, high-1);
        // 피벗 값 설정: 배열의 마지막 원소
        Comparable pivot = arr[high-1];
        // i는 피벗보다 작은 값들이 위치할 영역의 경계를 나타내며, 초기값은 low - 1입니다.
        int i = low;
        // low+1부터 high-2까지의 원소를 순회하며 피벗과 비교
        for (int j = low+1; j < high-1; j++) {
            // 현재 원소가 피벗보다 작으면
            if (less(arr[j], pivot)) {
                // 작은 값의 영역을 확장하기 위해 i를 증가시키고
                i++;
                // 현재 원소와 작은 값의 영역의 다음 위치의 원소를 교환합니다.
                exch(arr, i, j);
            }
        }
        // 피벗을 자신의 위치(i+1)로 이동시켜 피벗을 중심으로 작은 값과 큰 값으로 분할합니다.
        exch(arr, i + 1, high-1);
        // 피벗의 최종 위치를 반환합니다.
        return i + 1;
    }
}


public class HW1 {
    public static void main(String[] args) {
        try {
            BufferedReader br;
            while(true) {
                try {
                    // 파일 이름 입력
                    System.out.println("파일 이름 입력");
                    Scanner sc = new Scanner(System.in);
                    String fileName = sc.nextLine();
                    br = new BufferedReader(new FileReader(fileName));
                    break;
                }catch (FileNotFoundException e){
                    System.out.println("지정된 파일을 찾을 수 없습니다.");
                }
            }

            // 첫번째 줄: 현재 위치의 x,y좌표 읽기
            String line = br.readLine();
            if (line == null) throw new Exception("현재 위치 좌표가 포함된 줄이 없습니다.");
            String[] currentPos = line.trim().split("\\s+");//문자열 공백 제거 및 나누기
            if (currentPos.length != 2) throw new Exception("현재 위치 좌표가 올바르지 않습니다.");
            double currentX = Double.parseDouble(currentPos[0]);
            double currentY = Double.parseDouble(currentPos[1]);

            // 두번째 줄: k값 읽기
            line = br.readLine();
            if (line == null) throw new Exception("k 값이 포함된 줄이 없습니다.");
            int k = Integer.parseInt(line.trim());

            // 세번째 줄: 좌표의 수 n 읽기
            line = br.readLine();
            if (line == null) throw new Exception("좌표의 수 n이 포함된 줄이 없습니다.");
            int n = Integer.parseInt(line.trim());

            // 거리 값을 저장할 배열 생성
            Comparable[] distances = new Comparable[n];

            // 다음 n개의 줄에서 좌표쌍을 읽으며 거리 계산
            for (int i = 0; i < n; i++) {

                line = br.readLine();

                // 파일 끝에 도달하면 반복문 종료 (예외 처리)
                if (line == null) {
                    System.out.println("경고: 파일에 명시된 좌표 수보다 실제 좌표 수가 적습니다.");
                    n = i+1;
                    break;
                }

                String[] coords = line.trim().split("\\s+");
                if (coords.length < 2) {
                    System.out.println("경고: 좌표 데이터가 올바르지 않습니다, 해당 좌표는 건너뜁니다.");
                    n--;
                    continue;
                }

                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);

                // 유클리드 거리 계산: sqrt((x - currentX)^2 + (y - currentY)^2)
                double distance = Math.sqrt(Math.pow(x - currentX, 2) + Math.pow(y - currentY, 2));
                distances[i] = distance;
            }
            br.close(); // 파일 닫기

            if(k == 0){
                System.out.println("출력할 원소가 없습니다.");
                return;
            }

            long startTime = System.currentTimeMillis();
            if(k > 5 || k < 0) {
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}