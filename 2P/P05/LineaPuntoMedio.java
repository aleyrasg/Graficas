import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaPuntoMedio extends JPanel {

    private BufferedImage canvas;

    public LineaPuntoMedio(int width, int height) {
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

    // Algoritmo de punto medio (generalizado)
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {

        int dx = x1 - x0;
        int dy = y1 - y0;

        int sx = (dx >= 0) ? 1 : -1;
        int sy = (dy >= 0) ? 1 : -1;

        dx = Math.abs(dx);
        dy = Math.abs(dy);

        int x = x0;
        int y = y0;

        putPixel(x, y, c);

        // Caso 1: |m| <= 1
        if (dx > dy) {
            int d = 2 * dy - dx;

            for (int i = 0; i < dx; i++) {
                x += sx;

                if (d < 0) {
                    d += 2 * dy;
                } else {
                    y += sy;
                    d += 2 * (dy - dx);
                }

                putPixel(x, y, c);
            }
        }
        // Caso 2: |m| > 1
        else {
            int d = 2 * dx - dy;

            for (int i = 0; i < dy; i++) {
                y += sy;

                if (d < 0) {
                    d += 2 * dx;
                } else {
                    x += sx;
                    d += 2 * (dx - dy);
                }

                putPixel(x, y, c);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        LineaPuntoMedio panel = new LineaPuntoMedio(500, 500);

        // Pruebas
        panel.dibujarLinea(50, 450, 450, 50, Color.BLACK);   // diagonal
        panel.dibujarLinea(50, 250, 450, 250, Color.RED);    // horizontal
        panel.dibujarLinea(250, 50, 250, 450, Color.GREEN);  // vertical
        panel.dibujarLinea(400, 50, 100, 450, Color.BLUE);   // invertida
        panel.dibujarLinea(200, 50, 220, 450, Color.MAGENTA);// pendiente alta

        JFrame frame = new JFrame("Línea con Punto Medio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}