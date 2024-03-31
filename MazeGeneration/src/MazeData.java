//Limitation
//Only one fixed entrance, and one fixed exit
//Only one solution
//Path is continuous
//Wall and road is only one unit
//Squared canvas

public class MazeData {
    
    public static final char ROAD = ' '; 
    public static final char WALL = '#';
    private int N, M;
    public char[][] maze;
    private int entranceX, entranceY;
    private int exitX, exitY;
    public boolean[][] visited;
    public boolean[][] inMist;
    
    public MazeData(int N, int M){
        
        if (N % 2 == 0 || M % 2 == 0)
            throw new IllegalArgumentException("Our maze must have odd number of length");
        
        this.N = N;
        this.M = M;
        
        maze = new char[N][M];
        visited = new boolean[N][M];
        inMist = new boolean[N][M];
        
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                if (i % 2 == 1 && j % 2 == 1){
                    maze[i][j] = ROAD;
                } else {
                    maze[i][j] = WALL;
                }
                
                inMist[i][j] = true;
            }
        }
        
        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;
        
        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }
    
    public int N(){
        return N;
    }
    
    public int M(){
        return M;
    }
    
    public int getEntranceX(){
        return entranceX;
    }

    public int getEntranceY(){
        return entranceY;
    }

    public int getExitX(){
        return exitX;
    }

    public int getExitY(){
        return exitY;
    }
    
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
    
    public void openMist(int x, int y){
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if (inArea(i, j))
                    inMist[i][j] = false;
            }
        }
    }
}