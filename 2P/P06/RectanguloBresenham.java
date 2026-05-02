import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RectanguloBresenham extends JPanel {

    private BufferedImage canvas;

    public RectanguloBresenham(int width, int height) {
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

    // Bresenham general
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

    // Dibujar rectángulo usando 4 líneas
    public void dibujarRectangulo(int x, int y, int ancho, int alto, Color c) {

        // Lado superior
        dibujarLinea(x, y, x + ancho, y, c);

        // Lado inferior
        dibujarLinea(x, y + alto, x + ancho, y + alto, c);

        // Lado izquierdo
        dibujarLinea(x, y, x, y + alto, c);

        // Lado derecho
        dibujarLinea(x + ancho, y, x + ancho, y + alto, c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        RectanguloBresenham panel = new RectanguloBresenham(500, 500);

        // Dibujar rectángulos
        panel.dibujarRectangulo(50, 50, 200, 100, Color.BLACK);
        panel.dibujarRectangulo(100, 200, 300, 150, Color.RED);
        panel.dibujarRectangulo(200, 100, 150, 250, Color.BLUE);

        JFrame frame = new JFrame("Rectángulos con Bresenham");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}