import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgoVisualizer {
    
    private static int DELAY = 5;
    private static int blockSide = 32;
    private MinesweeperData data;
    private AlgoFrame frame;
    private AlgoMouseListener mouseListener;

    public AlgoVisualizer(int N, int M, int mineNumber){
        data = new MinesweeperData(N, M, mineNumber);
        int sceneWidth = M * blockSide;
        int sceneHeight = N * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Minesweeper", sceneWidth, sceneHeight);
            mouseListener = new AlgoMouseListener();
            frame.addMouseListener(mouseListener);
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(false, -1, -1);
    }
    
    private void setData(boolean isLeftClicked, int x, int y){
        if (data.inArea(x, y)){
            if (isLeftClicked){
                if (data.isMine(x, y)) {
                    //Game Over
                    data.open[x][y] = true;
                    frame.removeMouseListener(mouseListener);
                } else {
                    data.open(x, y);
                }
            } else{
                data.flags[x][y] = !data.flags[x][y];
            }
        }
        
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    
    private class AlgoMouseListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent event){
            event.translatePoint(
                    -(int)(frame.getBounds().width - frame.getCanvasWidth()),
                    -(int)(frame.getBounds().height - frame.getCanvasHeight())
            );
            
            Point pos = event.getPoint();
            
            int x = pos.y / blockSide;
            int y = pos.x / blockSide;
            
            if (SwingUtilities.isLeftMouseButton(event)){
                setData(true, x, y);
            } else {
                setData(false, x, y);
            }
        }
    }

    public static void main(String[] args) {

        int N = 20;
        int M = 20;
        int mineNumber = 30;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M, mineNumber);
    }
}