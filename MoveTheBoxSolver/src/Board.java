public class Board {
    
    public static char EMPTY = '.';
    private int N, M;
    private char[][] data;
    private int[][] dirs = {{-1, 0}, {0, 1}};
    private Board preBoard = null;
    private String swapString = "";
    
    public Board(String[] lines){
        N = lines.length;
        if (N == 0)
            throw new IllegalArgumentException("empty line");
        
        M = lines[0].length();
        
        data = new char[N][M];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                data[i][j] = lines[i].charAt(j);
            }
        }
    }
    
    public Board(Board board, Board preBoard, String swapString){
        this.N = board.N();
        this.M = board.M();
        this.data = new char[N][M];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                this.data[i][j] = board.getData(i, j);
            }
        }
        
        this.preBoard = preBoard;
        this.swapString = swapString;
    }
    
    public Board(Board board){
        this(board, null, "");
    }
    
    public char getData(int x, int y){
        return data[x][y];
    }
    
    public int N(){
        return N;
    }
    
    public int M(){
        return M;
    }
    
    public boolean inArea(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
    
    public void print(){
        for (int i = 0; i < N; i++)
            System.out.println(String.valueOf(data[i]));
    }
    
    public boolean isWin(){
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                if (data[i][j] != EMPTY)
                    return false;
            }
        }
        
        printSwapInfo();
        return true;
    }
    
    public void swap(int x1, int y1, int x2, int y2){
        char t = data[x1][y1];
        data[x1][y1] = data[x2][y2];
        data[x2][y2] = t;
    }
    
    public void run(){
        //match & drop
        do{
            drop();
        } while(match());
    }
    
    private void drop(){
        for (int j = 0; j < M; j++){
            int cur = N - 1;
            for (int i = N - 1; i >= 0; i--){
                if (data[i][j] != EMPTY){
                    swap(i, j, cur, j);
                    cur--;
                }
            }
        }
    }
    
    private boolean match(){
        boolean[][] tag = new boolean[N][M];
        boolean isMatched = false;
        
        for (int x = 0; x < N; x++){
            for (int y = 0; y < M; y++){
                if (data[x][y] != EMPTY){
                    for (int i = 0; i < 2; i++){
                        int newX1 = x + dirs[i][0];
                        int newY1 = y + dirs[i][1];
                        int newX2 = newX1 + dirs[i][0];
                        int newY2 = newY1 + dirs[i][1];
                        
                        if (inArea(newX1, newY1) && inArea(newX2, newY2) && 
                                data[newX1][newY1] == data[x][y] &&
                                data[newX2][newY2] ==  data[x][y]){
                            tag[x][y] = true;
                            tag[newX1][newY1] = true;
                            tag[newX2][newY2] = true;
                            
                            isMatched = true;
                        }
                    }
                }
            }
        }

        for (int x = 0; x < N; x++){
            for (int y = 0; y < M; y++) {
                if (tag[x][y])
                    data[x][y] = EMPTY;
            }
        }
        
        return isMatched;
    }
    
    public void printSwapInfo(){
        if (preBoard != null){
            preBoard.printSwapInfo();
        }
        
        System.out.println(swapString);
    }
}