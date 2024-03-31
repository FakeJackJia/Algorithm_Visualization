import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {


    private InsertionSortData data;
    private AlgoFrame frame;
    private static int DELAY = 10;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        data = new InsertionSortData(N, sceneHeight - 1);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Insertion Sort Visualization", sceneWidth, sceneHeight);

            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(0, -1);

        for (int i = 0; i < data.N(); i++){
            setData(i, i);
            for (int j = i; j > 0; j--){
                if (data.get(j) < data.get(j - 1)) {
                    data.swap(j, j - 1);
                    setData(i + 1, j - 1);
                } else break;
            }

            setData(i + 1, -1);
        }

        setData(data.N(), -1);
    }

    private void setData(int orderedIndex, int currentIndex){
        data.orderedIndex = orderedIndex;
        data.currentIndex = currentIndex;

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