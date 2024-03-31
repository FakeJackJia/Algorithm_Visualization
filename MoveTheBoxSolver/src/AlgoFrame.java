import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class AlgoFrame extends JFrame{

    private int canvasWidth;
    private int canvasHeight;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight){

        super(title);

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);

        setResizable(false);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public AlgoFrame(String title){

        this(title, 1024, 768);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    private GameData data;
    public void render(GameData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        private HashMap<Character, Color> colorMap;
        private ArrayList<Color> colorlist;
        
        public AlgoCanvas(){
            super(true);
            
            colorMap = new HashMap<>();
            colorlist = new ArrayList<>();
            colorlist.add(AlgoVisHelper.Red);
            colorlist.add(AlgoVisHelper.Purple);
            colorlist.add(AlgoVisHelper.Blue);
            colorlist.add(AlgoVisHelper.Teal);
            colorlist.add(AlgoVisHelper.LightGreen);
            colorlist.add(AlgoVisHelper.Lime);
            colorlist.add(AlgoVisHelper.Amber);
            colorlist.add(AlgoVisHelper.DeepOrange);
            colorlist.add(AlgoVisHelper.Brown);
            colorlist.add(AlgoVisHelper.BlueGrey);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            Board showBoard = data.getShowBoard();
            int w = canvasWidth / showBoard.M();
            int h = canvasHeight / showBoard.N();
            
            for (int i = 0; i < showBoard.N(); i++){
                for (int j = 0; j < showBoard.M(); j++){
                    char c = showBoard.getData(i, j);
                    if (c != Board.EMPTY){
                        if (!colorMap.containsKey(c)){
                            int sz = colorMap.size();
                            colorMap.put(c, colorlist.get(sz));
                        }
                        
                        Color color = colorMap.get(c);
                        AlgoVisHelper.setColor(g2d, color);
                        AlgoVisHelper.fillRectangle(g2d, j * w, i * h, w, h);
                        AlgoVisHelper.setColor(g2d, AlgoVisHelper.White);
                        String text = String.format("(%d, %d)", i, j);
                        AlgoVisHelper.drawText(g2d, text, j * w + w / 2, i * h + h / 2);
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}