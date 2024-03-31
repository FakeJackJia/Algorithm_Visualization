import java.awt.*;

public class AlgoVisualizer {
    
    private static int DELAY = 5;
    private static int blockSide = 8;
    private MazeData data;
    private AlgoFrame frame;
    private static final int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public AlgoVisualizer(int N, int M){
        data = new MazeData(N, M);
        int sceneWidth = data.M() * blockSide;
        int sceneHeight = data.N() * blockSide;

        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Random Maze Generation", sceneWidth, sceneHeight);
            
            new Thread(() -> {
                run();
            }).start();
        });
    }

    private void run(){
        setData(-1, -1);
        
        go(data.getEntranceX(), data.getEntranceY() + 1);
        
        setData(-1, -1);
    }
    
    private void go(int x, int y){
        RandomQueue<Integer> queue = new RandomQueue<>();
        data.visited[x][y] = true;
        queue.add(x * data.M() + y);
        data.openMist(x, y);

        while (!queue.isEmpty()){
            int cur = queue.remove();
            int curX = cur / data.M();
            int curY = cur % data.M();
            
            for (int i = 0; i < 4; i++){
                int newX = curX + dirs[i][0] * 2;
                int newY = curY + dirs[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]){
                    queue.add(newX * data.M() + newY);
                    data.visited[newX][newY] = true;

                    data.openMist(newX, newY);
                    setData(curX + dirs[i][0], curY + dirs[i][1]);
                }
            }
        }
    }
    
    public void setData(int x, int y){
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;
        
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int N = 101;
        int M = 101;

        AlgoVisualizer visualizer = new AlgoVisualizer(N, M);
    }
}