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

    private FractalData data;
    public void render(FractalData data){
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

            drawFractal(g2d, 0, canvasHeight, canvasWidth, 0);
        }
        
        private void drawFractal(Graphics2D g, int Ax, int Ay, int side, int depth){
            if (side <= 1){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g, Ax, Ay, 1, 1);
                return;
            }
            
            int Bx = Ax + side;
            int By = Ay;
            
            int h = (int)(Math.sin(60.0 * Math.PI / 180.0) * side);
            int Cx = Ax + side / 2;
            int Cy = Ay - h;
            
            if (depth == data.depth){
                AlgoVisHelper.setColor(g, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillTriangle(g, Ax, Ay, Bx, By, Cx, Cy);
                return;
            }
            
            int AB_centerX = (Ax + Bx) / 2;
            int AB_centerY = (Ay + By) / 2;
            
            int AC_centerX = (Ax + Cx) / 2;
            int AC_centerY = (Ay + Cy) / 2;
            
            drawFractal(g, Ax, Ay, side / 2, depth + 1);
            drawFractal(g, AC_centerX, AC_centerY, side / 2, depth + 1);
            drawFractal(g, AB_centerX, AB_centerY, side / 2, depth + 1);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}