import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaBresenham extends JPanel {

    private BufferedImage canvas;

    public LineaBresenham(int width, int height) {
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

    // Algoritmo de Bresenham (general)
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, c);

            if (x0 == x1 && y0 == y1)
                break;

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        LineaBresenham panel = new LineaBresenham(500, 500);

        // Pruebas
        panel.dibujarLinea(50, 450, 450, 50, Color.BLACK);   // diagonal
        panel.dibujarLinea(50, 250, 450, 250, Color.RED);    // horizontal
        panel.dibujarLinea(250, 50, 250, 450, Color.GREEN);  // vertical
        panel.dibujarLinea(400, 50, 100, 450, Color.BLUE);   // invertida
        panel.dibujarLinea(200, 50, 220, 450, Color.MAGENTA);// pendiente alta

        JFrame frame = new JFrame("Línea con Bresenham");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}