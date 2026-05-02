import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaGrosor extends JPanel {

    private BufferedImage canvas;

    public LineaGrosor(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Dibujar líneas con distintos grosores
        dibujarLinea(50, 50, 400, 50, Color.BLACK, 1);
        dibujarLinea(50, 100, 400, 100, Color.RED, 3);
        dibujarLinea(50, 150, 400, 150, Color.BLUE, 6);
        dibujarLinea(50, 200, 400, 300, Color.MAGENTA, 8);
    }

    // Dibujar píxel
    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    // Dibujar grosor alrededor del punto
    private void dibujarGrosor(int x, int y, Color c, int grosor) {
        int mitad = grosor / 2;

        for (int i = -mitad; i <= mitad; i++) {
            for (int j = -mitad; j <= mitad; j++) {
                putPixel(x + i, y + j, c);
            }
        }
    }

    // Línea con Bresenham + grosor
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c, int grosor) {

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {

            // en lugar de un pixel → dibujamos un bloque
            dibujarGrosor(x0, y0, c, grosor);

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
        JFrame frame = new JFrame("Líneas con grosor");
        LineaGrosor panel = new LineaGrosor(500, 400);

        frame.add(panel);
        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}