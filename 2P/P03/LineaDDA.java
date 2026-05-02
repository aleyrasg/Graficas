import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaDDA extends JPanel {

    private BufferedImage canvas;

    public LineaDDA(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());
    }

    // Dibujar píxel
    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    // Algoritmo DDA
    public void dibujarLineaDDA(int x0, int y0, int x1, int y1, Color c) {

        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        double xInc = (double) dx / steps;
        double yInc = (double) dy / steps;

        double x = x0;
        double y = y0;

        for (int i = 0; i <= steps; i++) {
            putPixel((int)Math.round(x), (int)Math.round(y), c);
            x += xInc;
            y += yInc;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        LineaDDA panel = new LineaDDA(500, 500);

        // Pruebas
        panel.dibujarLineaDDA(50, 450, 450, 50, Color.BLACK);   // diagonal
        panel.dibujarLineaDDA(50, 250, 450, 250, Color.RED);    // horizontal
        panel.dibujarLineaDDA(250, 50, 250, 450, Color.GREEN);  // vertical
        panel.dibujarLineaDDA(400, 50, 100, 450, Color.BLUE);   // invertida
        panel.dibujarLineaDDA(200, 50, 220, 450, Color.MAGENTA);// pendiente alta

        JFrame frame = new JFrame("Línea con DDA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}