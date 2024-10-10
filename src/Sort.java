import java.util.Arrays;
import java.util.Random;

public class Sort {
    private static final int SIZE = 7000;
    private static int comparisonCount;
    private static int swapCount;

    public static void main(String[] args) {
        double[] array = generateArray(SIZE);
        double[] copyForSelectionSort = Arrays.copyOf(array, SIZE);
        double[] copyForInsertionSort = Arrays.copyOf(array, SIZE);
        double[] copyForQuickSort = Arrays.copyOf(array, SIZE);
        double[] copyForMergeSort = Arrays.copyOf(array, SIZE);

        // Таблица для простых методов сортировки
        System.out.println("Простые методы сортировки:");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-20s | %-10s | %-10s | %-10s |\n", "Метод", "Сравнения", "Перестановки", "Время (нс)");
        System.out.println("-------------------------------------------------------------");

        // Сортировка выбором
        resetCounters();
        long startTime = System.nanoTime();
        selectionSort(copyForSelectionSort);
        long endTime = System.nanoTime();
        System.out.printf("| %-20s | %-10d | %-10d | %-10d |\n", "Сортировка выбором", comparisonCount, swapCount, (endTime - startTime));

        // Сортировка вставками
        resetCounters();
        startTime = System.nanoTime();
        insertionSort(copyForInsertionSort);
        endTime = System.nanoTime();
        System.out.printf("| %-20s | %-10d | %-10d | %-10d |\n", "Сортировка вставками", comparisonCount, swapCount, (endTime - startTime));

        System.out.println("-------------------------------------------------------------");

        // Таблица для сложных методов сортировки
        System.out.println("\nСложные методы сортировки:");
        System.out.println("---------------------------------------------");
        System.out.printf("| %-20s | %-20s |\n", "Метод", "Время (нс)");
        System.out.println("---------------------------------------------");

        // Быстрая сортировка
        startTime = System.nanoTime();
        quickSort(copyForQuickSort, 0, SIZE - 1);
        endTime = System.nanoTime();
        System.out.printf("| %-20s | %-20d |\n", "Быстрая сортировка", (endTime - startTime));

        // Сортировка слиянием
        startTime = System.nanoTime();
        mergeSort(copyForMergeSort, 0, SIZE - 1);
        endTime = System.nanoTime();
        System.out.printf("| %-20s | %-20d |\n", "Сортировка слиянием", (endTime - startTime));

        System.out.println("---------------------------------------------");
    }

    // Генерация массива
    private static double[] generateArray(int size) {
        Random rand = new Random();
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            double uniform1 = rand.nextDouble();
            double uniform2 = rand.nextDouble();
            array[i] = uniform1 * uniform2;
        }
        return array;
    }

    // Сортировка выбором
    private static void selectionSort(double[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                comparisonCount++;
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(array, i, minIndex);
            }
        }
    }

    // Сортировка вставками
    private static void insertionSort(double[] array) {
        for (int i = 1; i < array.length; i++) {
            double key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                comparisonCount++;
                array[j + 1] = array[j];
                j--;
                swapCount++;
            }
            array[j + 1] = key;
        }
    }

    // Быстрая сортировка
    private static void quickSort(double[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static int partition(double[] array, int low, int high) {
        double pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    // Сортировка слиянием
    private static void mergeSort(double[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private static void merge(double[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        double[] leftArray = new double[n1];
        double[] rightArray = new double[n2];

        for (int i = 0; i < n1; ++i) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // Метод для обмена элементов
    private static void swap(double[] array, int i, int j) {
        double temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        swapCount++;
    }

    // Сброс счетчиков
    private static void resetCounters() {
        comparisonCount = 0;
        swapCount = 0;
    }
}
