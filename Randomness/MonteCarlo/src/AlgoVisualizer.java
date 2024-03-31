import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class AlgoVisualizer {


    private Circle circle;
    private LinkedList<Point> points;
    private AlgoFrame frame;
    private int N = 20000;
    private static int DELAY = 40;
    private int insideCircle = 0;

    public AlgoVisualizer(int sceneWidth, int sceneHeight){
        if (sceneWidth != sceneHeight)
            throw new IllegalArgumentException("Must be a square");

        circle = new Circle(sceneWidth / 2, sceneHeight / 2, sceneWidth / 2);
        points = new LinkedList<>();

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Monte Carlo", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        for (int i = 0; i < N; i++){
            //monitering speed
            if (i % 1000 == 0) {
                frame.render(circle, points);
                AlgoVisHelper.pause(DELAY);

                int circleArea = insideCircle;
                int squareArea = points.size();
                double piEstimation = 4 * (double) circleArea / squareArea;
                System.out.println(piEstimation);
            }

            int x = (int) (Math.random() * frame.getCanvasWidth());
            int y = (int) (Math.random() * frame.getCanvasHeight());

            Point p = new Point(x, y);
            points.add(p);
            if (circle.contain(p))
                insideCircle++;
        }
    }

    private class AlgoKeyListener extends KeyAdapter{ }
    private class AlgoMouseListener extends MouseAdapter{ }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;

        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight);
    }
}