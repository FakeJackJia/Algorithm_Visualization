import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class AlgoVisualizer {

    private static int DELAY = 40;
    private MergeSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        data = new MergeSortData(N, sceneHeight);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("MergeSort Visualization", sceneWidth, sceneHeight);

            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(-1, -1, -1);
        merge(0, data.N() - 1);
        setData(0, data.N() - 1, data.N() - 1);
    }

    private void merge(int l, int r){
        if (l == r) return;

        setData(l, r, -1);
        int mid = l + (r - l) / 2;

        merge(l, mid);
        merge(mid + 1, r);
        sort(l, mid, r);
    }

    private void sort(int l, int mid, int r){
        int[] tempArr = Arrays.copyOfRange(data.numbers, l, r + 1);

        int k = l;
        int i = l;
        int j = mid + 1;
        while (i <= mid && j <= r){
            if (tempArr[i - l] > tempArr[j - l]){
                data.numbers[k] = tempArr[j - l];
                j++;
            } else {
                data.numbers[k] = tempArr[i - l];
                i++;
            }

            k++;

            setData(l, r, k);
        }

        while (i <= mid){
            data.numbers[k++] = tempArr[i - l];
            i++;

            setData(l, r, k);
        }

        while (j <= r){
            data.numbers[k++] = tempArr[j - l];
            j++;

            setData(l, r, k);
        }
    }

    private void setData(int l, int r, int mergeIndex){
        data.l = l;
        data.r = r;
        data.mergeIndex = mergeIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N);
    }
}