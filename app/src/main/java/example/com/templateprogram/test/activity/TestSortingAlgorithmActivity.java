package example.com.templateprogram.test.activity;

import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

import butterknife.OnClick;
import example.com.templateprogram.R;
import example.com.templateprogram.base.BaseActivity;
import example.com.templateprogram.utils.LogUtils;

/**
 * Created by beijixiong on 2019/4/6.
 * 排序算法
 * 1.冒泡排序
 * 2.选择排序
 * 3.插入排序
 * 4.希尔排序
 * 5.快速排序
 * 6.归并排序
 * 7.堆排序
 * 8.基数排序
 */

public class TestSortingAlgorithmActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_sortingalgorithm;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @OnClick({R.id.btn_bubblesort, R.id.btn_selctionsort, R.id.btn_insertionsort,
            R.id.btn_shellsort, R.id.btn_quicksort, R.id.btn_heapsort,
            R.id.btn_mergesort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bubblesort:
                getBubbleSort();
                break;
            case R.id.btn_selctionsort:
                getSelctionSort();
                break;
            case R.id.btn_insertionsort:
                getInsertionSort();
                break;
            case R.id.btn_shellsort:
                getShellSort();
                break;
            case R.id.btn_quicksort:
                int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
                int length = arr.length;
                getQuickSort(arr, 0, length - 1);
                LogUtils.i("快速排序后:  =-= " + Arrays.toString(arr));
                break;
            case R.id.btn_heapsort:
                getHeapSort();
                break;
            case R.id.btn_mergesort:
                int arr1[] = {5, 6, 55, 66, 77, 8, 9, 0};
                int length1 = arr1.length;
                LogUtils.i("原始顺序 =-= " + Arrays.toString(arr1));
                getMergeSort(arr1, 0, length1, new int[length1]);
                LogUtils.i("合并排序后:  =-= " + Arrays.toString(arr1));
                break;
        }
    }


    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 冒泡排序
     * 两个数比较大小，较大的数下沉，较小的数冒起来
     */
    private void getBubbleSort() {
        int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
        int length = arr.length;
        int temp;//临时变量
        boolean flag;//是否交换的标志
        LogUtils.i("原始顺序 =-= " + Arrays.toString(arr));
        //i表示第几趟排序
        for (int i = 0; i < length - 1; i++) {//表示趟数，一共arr.length-1次。
            flag = false;
            //每次都从最后一个开始
            for (int j = length - 1; j > i; j--) {
                //如果后面的比前面的小，就像泡泡一样冒上去
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    flag = true;
                }
            }
            LogUtils.i("第" + i + "趟排序:  =-= " + Arrays.toString(arr));
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 选择排序
     * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
     * 第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     * 。。。
     * 第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成
     * <p>
     * 不稳定的排序方法
     */
    private void getSelctionSort() {
        int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
        int length = arr.length;
        LogUtils.i("原始顺序 =-= " + Arrays.toString(arr));
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                // 找到最小值的那个元素的下标
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 把最小值调换到最前面
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            LogUtils.i("第" + i + "趟排序:  =-= " + Arrays.toString(arr));
        }
    }

    /**
     * 插入排序
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序
     * <p>
     * 1.插入排序不适合对于数据量比较大的排序应用。但是，如果需要排序的数据量很小（eg：量级小于千），那么插入排序是一个不错的选择
     * 2.插入排序是稳定的
     */
    private void getInsertionSort() {
        int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
        int length = arr.length;

        int tmp;//要插入的数据
        int istIndex;//插入位置索引
        LogUtils.i("原始顺序 =-= " + Arrays.toString(arr));
        for (int i = 1; i < length; i++) {
            if (arr[i] < arr[i - 1]) {
                tmp = arr[i];
                istIndex = i;
                while (istIndex > 0 && arr[istIndex - 1] > tmp) {
                    //插入位置往前移，寻找合适位置
                    arr[istIndex] = arr[istIndex - 1];
                    istIndex--;
                }
                arr[istIndex] = tmp;
            }
            LogUtils.i("第" + i + "趟排序:  =-= " + Arrays.toString(arr));
        }
    }

    /**
     * 希尔排序
     * 1.在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
     * 2.然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序
     * 3.希尔排序又称缩小增量排序，是简单插入排序的增强版本
     */
    private void getShellSort() {
        int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
        int length = arr.length;
        int gap;//步长
        int istIndex;//插入位置索引
        int tmp;
        LogUtils.i("原始顺序 =-= " + Arrays.toString(arr));
        //按照步长来分组
        for (gap = length / 2; gap >= 1; gap /= 2) {
            //类似插入排序的方法
            for (int i = gap; i < length; i++) {
                tmp = arr[i];//取出暂存
                istIndex = i;//插入的位置
                while ((istIndex > (gap - 1) && tmp < arr[istIndex - gap])) {
                    //插入位置往前移，寻找合适位置
                    arr[istIndex] = arr[istIndex - gap];
                    istIndex -= gap;
                }
                arr[istIndex] = tmp;
            }
            LogUtils.i("步长为" + gap + "的排序:  =-= " + Arrays.toString(arr));
        }
    }

    /**
     * 快速排序
     * 快速排序和冒泡排序类似，都是基于交换的思想，快速排序对冒泡排序进行了优化，从而更加快速高效
     */
    private void getQuickSort(int[] arr, int left, int right) {
        if (arr == null || left >= right || arr.length <= 1)
            return;
        int mid = partition(arr, left, right);
        getQuickSort(arr, left, mid);
        getQuickSort(arr, mid + 1, right);
    }

    //获取基准值
    private int partition(int[] arr, int left, int right) {
        int temp = arr[left];
        while (right > left) {
            // 先判断基准数和后面的数依次比较
            while (temp <= arr[right] && left < right) {
                right--;
            }
            // 当基准数大于了 arr[right]，则填坑
            if (left < right) {
                arr[left] = arr[right];
                left++;
            }
            // 现在是 arr[right] 需要填坑了
            while (temp >= arr[left] && left < right) {
                left++;
            }
            if (left < right) {
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = temp;
        return left;
    }

    /**
     * 堆排序
     * 1.堆排序算法，基于选择排序的思想，利用堆结构的性质来完成对数据的排序
     * 2.对于第n个结点而言 --- 它的父结点下标 i = （n-1）/ 2，左子结点 left = 2n+1，右子结点 right =2n+2
     */
    private void getHeapSort() {
        int arr[] = {5, 6, 55, 66, 77, 8, 9, 0};
        int length = arr.length;
        int last = length - 1;
        int tmp;
        LogUtils.i("原始顺序 =-= " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            heapConstruct(arr, last);
            //第一个和最后一个交换
            tmp = arr[0];
            arr[0] = arr[last];
            arr[last] = tmp;
            last--;
            LogUtils.i("第" + i + "趟排序:  =-= " + Arrays.toString(arr));
        }
    }

    //建堆
    private void heapConstruct(int[] arr, int last) {
        int tmp;
        for (int parent = (last - 1) / 2; parent >= 0; parent = (last - 1) / 2) {
            if (2 * parent + 1 < last) {
                //第last个节点为右子树
                if (arr[last - 1] < arr[last]) {
                    //大的数据往前移
                    tmp = arr[last];
                    arr[last] = arr[last - 1];
                    arr[last - 1] = tmp;
                } else {
                    last--;
                }
            }
            if (arr[parent] < arr[last]) {
                //大的数据移到父节点
                tmp = arr[last];
                arr[last] = arr[parent];
                arr[parent] = tmp;
            }
            last--;
        }

    }

    /**
     * 合并排序
     * 1.合并排序法是将两个（或两个以上）有序表合并成一个新的有序表，即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列
     * 2.将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。合并排序也叫归并排序
     * 3.归并排序是稳定排序
     */
    private void getMergeSort(int[] arr, int first, int last, int[] sorted) {
        if (first < last - 1) {
            int back = (first + last) / 2;
            getMergeSort(arr, first, back, sorted);
            getMergeSort(arr, back, last, sorted);
            merge(arr, first, back, last, sorted);
        }
    }

    //合并
    private void merge(int[] unsortArr, int frontIndex, int backIndex, int lastIndex, int[] sortArr) {
        int i = frontIndex;//前半段的起始索引
        int j = backIndex;//后半段的起始索引
        int k = 0;
        //合并两个小分组
        while (i < backIndex && j < lastIndex) {
            if (unsortArr[i] < unsortArr[j]) {
                sortArr[k++] = unsortArr[i++];
            } else {
                sortArr[k++] = unsortArr[j++];
            }
        }
        while (i < backIndex) {
            //前半段还有数据
            sortArr[k++] = unsortArr[i++];
        }
        while (j < lastIndex) {
            //后半段还有数据
            sortArr[k++] = unsortArr[j++];
        }
        for (int l = 0; l < k; l++) {
            //将排序好的数放回
            unsortArr[frontIndex + l] = sortArr[l];
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
