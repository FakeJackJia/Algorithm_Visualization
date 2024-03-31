import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {

    private static int DELAY = 40;
    private HeapSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        data = new HeapSortData(N, sceneHeight);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("HeapSort", sceneWidth, sceneHeight);

            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(data.N());

        for (int i = (data.N() - 2) / 2; i >= 0; i--){
            siftDown(i, data.N());
        }

        for (int j = data.N() - 1; j >= 0; j--){
            data.swap(0, j);
            siftDown(0, j);
            setData(j);
        }
    }

    private void siftDown(int index, int n){
        int k;

        while (2 * index + 1 < n){
            k = 2 * index + 1;

            if (k + 1 < n && data.get(k + 1) > data.get(k)){
                k = 2 * index + 2;
            }

            if (data.get(index) >= data.get(k))
                break;
            else{
                data.swap(index, k);
                index = k;
            }
        }
    }

    private void setData(int heapIndex){
        data.heapIndex = heapIndex;

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