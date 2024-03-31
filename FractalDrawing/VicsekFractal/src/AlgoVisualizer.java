import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AlgoVisualizer {

    private static int DELAY = 5;
    private FractalData data;
    private AlgoFrame frame;
    private int maxDepth = 6;

    public AlgoVisualizer(){
        data = new FractalData(maxDepth);
        int sceneWidth = (int)Math.pow(3, maxDepth);
        int sceneHeight = (int)Math.pow(3, maxDepth);

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Vicsek Fractal", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(0);
    }
    
    private void setData(int depth){
        data.depth = depth;
        
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    private class AlgoKeyListener extends KeyAdapter{ 
        @Override
        public void keyReleased(KeyEvent event){
            if (event.getKeyChar() >= '0' && event.getKeyChar() <= '9'){
                int depth = event.getKeyChar() - '0';
                setData(depth);
            }
        }
    }

    public static void main(String[] args) {
        //press 0 to 6 to show each stage
        AlgoVisualizer visualizer = new AlgoVisualizer();
    }
}