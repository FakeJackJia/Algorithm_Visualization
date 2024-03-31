import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AlgoVisualizer {

    private static int DELAY = 1;
    private static int blockSide = 8;
    private MazeData data;
    private AlgoFrame frame;
    private static final int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public AlgoVisualizer(String MazeFile) {
        data = new MazeData(MazeFile);
        
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;
        
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);
            new Thread(() -> {
                run();
            }).start();
        });
    }
    
    public void run(){
        setData(-1, -1, false);
        
        if (!goDFS(data.getEntranceX(), data.getEntranceY()))
            System.out.println("The maze has no solution");
        
        setData(-1, -1, false);
    }
    
    private boolean goDFS(int x, int y){
        data.visited[x][y] = true;
        setData(x, y, true);
        
        if (x == data.getExitX() && y == data.getExitY())
            return true;
        
        for (int i = 0; i < 4; i++){
            int newX = x + dirs[i][0];
            int newY = y + dirs[i][1];
            
            if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.ROAD && !data.visited[newX][newY]){
                if (goDFS(newX, newY))
                    return true;
            }
        }
        
        setData(x, y, false);
        return false;
    }
    
    private boolean goDFS2(int x, int y){
        Stack<Integer> stack = new Stack<>();
        data.visited[x][y] = true;
        setData(x, y, true);
        stack.push(x * data.M() + y);
        
        while (!stack.isEmpty()){
            int cur = stack.pop();
            int curX = cur / data.M();
            int curY = cur % data.M();

            setData(curX, curY, true);
            
            if (curX == data.getExitX() && curY == data.getExitY()){
                findPath(curX, curY);
                return true;
            }
            
            for (int i = 0; i < 4; i++){
                int newX = curX + dirs[i][0];
                int newY = curY + dirs[i][1];
                
                if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.ROAD && !data.visited[newX][newY]){
                    data.pre[newX][newY] = cur;
                    stack.push(newX * data.M() + newY);
                    data.visited[newX][newY] = true;
                }
            }
        }
        
        return false;
    }
    
    private boolean goBFS(int x, int y){
        Queue<Integer> queue = new LinkedList<>();
        data.visited[x][y] = true;
        setData(x, y, true);
        queue.add(x * data.M() + y);

        while (!queue.isEmpty()){
            int cur = queue.poll();
            int curX = cur / data.M();
            int curY = cur % data.M();

            setData(curX, curY, true);

            if (curX == data.getExitX() && curY == data.getExitY()){
                findPath(curX, curY);
                return true;
            }

            for (int i = 0; i < 4; i++){
                int newX = curX + dirs[i][0];
                int newY = curY + dirs[i][1];

                if (data.inArea(newX, newY) && data.getMaze(newX, newY) == MazeData.ROAD && !data.visited[newX][newY]){
                    data.pre[newX][newY] = cur; 
                    queue.add(newX * data.M() + newY);
                    data.visited[newX][newY] = true;
                }   
            }
        }

        return false;
    }
    
    private void findPath(int x, int y){
        int curX = x;
        int curY = y;
        
        data.result[curX][curY] = true;
        
        while (curX != data.getEntranceX() || curY != data.getEntranceY()){
            int pre = data.pre[curX][curY];
            
            curX = pre / data.M();
            curY = pre % data.M();
            
            data.result[curX][curY] = true;
        }
    }
    
    private void setData(int x, int y, boolean isPath){
        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }
        
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }
    
    public static void main(String[] args) {
        String MazeFile = "./Maze/maze.txt";
        AlgoVisualizer visualizer = new AlgoVisualizer(MazeFile);
    }
}