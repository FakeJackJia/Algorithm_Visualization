import java.awt.*;

public class AlgoVisualizer {

    private static int blockSide = 80;
    private static int DELAY = 5;
    private GameData data;
    private AlgoFrame frame;

    public AlgoVisualizer(String filename){
        data = new GameData(filename);
        int sceneWidth = data.getStarterBoard().M() * blockSide;
        int sceneHeight = data.getStarterBoard().N() * blockSide;
        
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Move the Box Solver", sceneWidth, sceneHeight);
            
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData();
        
        if (data.solve())
            System.out .println("Has a solution");
        else 
            System.out.println("No solution");
    }

    private void setData(){
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    } 

    public static void main(String[] args) {
        
        String filename = "level/boston_09.txt";

        AlgoVisualizer visualizer = new AlgoVisualizer(filename);
    }
}