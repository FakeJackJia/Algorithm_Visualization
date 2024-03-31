import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    private static int DELAY = 40;
    private QuickSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        data = new QuickSortData(N, sceneHeight);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Quick Sort Visualization", sceneWidth, sceneHeight);

            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(-1, -1, -1, -1, -1);
        quickSort(0, data.N() - 1);
        setData(-1, -1, -1, -1, -1);
    }

    private void quickSort(int l, int r){
        if (l > r) return;

        if (l == r) {
            setData(l, r, l, -1, -1);
            return;
        }

        setData(l, r, -1, -1, -1);
        int p = partition(l, r);
        //int p = partition2Way(l, r);
        quickSort(l, p - 1);
        quickSort(p + 1, r);
    }

    private int partition(int l, int r){
        int p = (int) (Math.random() * (r - l)) + l;
        setData(l, r, -1, p, -1);
        data.swap(l, p);
        setData(l, r, -1, l, -1);

        int i = l + 1;
        int j = r;

        while (i <= j){
            setData(l, r, -1, l, i);
            if (data.get(i) <= data.get(l)){
                i++;
            } else {
                data.swap(i, j);
                j--;

                setData(l, r, -1, l, i);
            }
        }

        data.swap(l, j);
        setData(l, r, j, -1, -1);

        return j;
    }

    private int partition2Way(int l, int r){
        int p = (int) (Math.random() * (r - l)) + l;
        setData(l, r, -1, p, -1);
        data.swap(l, p);
        setData(l, r, -1, l, -1);

        int i = l + 1;
        int j = r;

        while (i <= j){
            if (data.get(i) >= data.get(l) && data.get(j) <= data.get(l)){
                data.swap(i, j);
                i++;
                j--;
            } else {
                if (data.get(i) < data.get(l))
                    i++;
                if (data.get(j) > data.get(l))
                    j--;
            }
        }

        data.swap(l, j);
        setData(l, r, j, -1, -1);

        return j;
    }

    private void setData(int l, int r, int fixedPivot, int curPivot, int curElement){
        data.l = l;
        data.r = r;
        if (fixedPivot != -1)
            data.fixedPivots[fixedPivot] =  true;
        data.curElement = curElement;
        data.curPivot = curPivot;

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