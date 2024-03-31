import java.awt.*;
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

    private MinesweeperData data;
    public void render(MinesweeperData data){
        this.data = data;
        repaint();
    }

    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            super(true);
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
            
            int w = canvasWidth / data.M();
            int h = canvasHeight / data.N();
            
            for (int i = 0; i < data.N(); i++){
                for (int j = 0; j < data.M(); j++){
                    if (data.open[i][j]){
                        if (data.isMine(i, j))
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MinesweeperData.mineImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MinesweeperData.numberImageURL(data.getNumber(i, j)));
                    } else {
                        if (data.flags[i][j])
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MinesweeperData.flagImageURL);
                        else
                            AlgoVisHelper.putImage(g2d, j * w, i * h, MinesweeperData.blockImageURL);
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