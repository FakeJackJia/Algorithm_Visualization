import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {


    private SelectionSortData data;
    private AlgoFrame frame;
    private static int DELAY = 10;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        data = new SelectionSortData(N, sceneHeight - 1);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Selection Sort Visualization", sceneWidth, sceneHeight);

            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(0, -1, -1);

        for (int i = 0; i < data.N(); i++){
            int min = i;
            setData(i, -1, min);
            for (int j = i + 1; j < data.N(); j++){
                setData(i, j, min);

                if (data.get(j) < data.get(min)) {
                    min = j;
                    setData(i, j, min);
                }
            }

            data.swap(min, i);
            setData(i + 1, -1, -1);
        }

        setData(data.N(), -1, -1);
    }

    private void setData(int orderedIndex, int currentCompareIndex, int currentMinIndex){
        data.orderedIndex = orderedIndex;
        data.currentCompareIndex = currentCompareIndex;
        data.currentMinIndex = currentMinIndex;

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