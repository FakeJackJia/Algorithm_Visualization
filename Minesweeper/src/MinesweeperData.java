import java.util.Random;

public class MinesweeperData {
    
    public static final String blockImageURL = "resources/block.png";
    public static final String flagImageURL = "resources/flag.png";
    public static final String mineImageURL = "resources/mine.png";
    
    public static String numberImageURL(int num){
        return "resources/" + num + ".png";
    }
    
    private int N, M;
    private boolean[][] mines;
    private int[][] numbers;
    private Random rnd;
    public boolean[][] open;
    public boolean[][] flags;
    
    public MinesweeperData(int N, int M, int mineNumber){
        if (N <= 0 || M <= 0)
            throw new IllegalArgumentException("Minesweeper size is invalid");
        
        if (mineNumber < 0 || mineNumber > N * M)
            throw new IllegalArgumentException("Mine number larger than the size");
        
        this.N = N;
        this.M = M;
        
        mines = new boolean[N][M];
        open = new boolean[N][M];
        flags = new boolean[N][M];
        numbers = new int[N][M];
        rnd = new Random();
        
        generateMines(mineNumber);
        calculateNumbers();
    }
    
    public int N(){
        return N;
    }
    
    public int M(){
        return M;
    }
    
    public boolean isMine(int x, int y){
        if (!inArea(x, y))
            throw new IllegalArgumentException("Invalid position");
        
        return mines[x][y];
    }
    
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
    
    private void generateMines(int mineNumber){
        for (int i = 0; i < mineNumber; i++){
            int x = i / M;
            int y = i % M;
            
            mines[x][y] = true;
        }
        
        for (int i = N * M -1; i >= 0; i--){
            int curX = i / M;
            int curY = i % M;
            
            int randNum = rnd.nextInt(i + 1);
            
            int newX = randNum / M;
            int newY = randNum % M;
            
            swap(curX, curY, newX, newY);
        }
    }
    
    private void swap(int x1, int y1, int x2, int y2){
        boolean t = mines[x1][y1];
        mines[x1][y1] = mines[x2][y2];
        mines[x2][y2] = t;
    }
    
    private void calculateNumbers(){
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                for (int ii = i - 1; ii <= i + 1; ii++){
                    for (int jj = j - 1; jj <= j + 1; jj++){
                        if (inArea(ii, jj) && mines[ii][jj])
                            numbers[i][j]++;
                    }
                }
            }
        }
    }
    
    public int getNumber(int x, int y){
        return numbers[x][y];
    }
    
    public void open(int x, int y){
        open[x][y] = true;
        
        if (numbers[x][y] > 0)
            return;
        
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if (inArea(i, j) && !open[i][j]){
                    open(i, j);
                }
            }
        }
    }
}