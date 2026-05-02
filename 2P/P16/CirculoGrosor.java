import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoGrosor extends JPanel {

    private BufferedImage canvas;

    public CirculoGrosor(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Dibujar círculos con distinto grosor
        dibujarCirculoConGrosor(200, 200, 100, 1, Color.BLACK);
        dibujarCirculoConGrosor(200, 200, 70, 4, Color.RED);
        dibujarCirculoConGrosor(200, 200, 40, 8, Color.BLUE);
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    private void simetria(int xc, int yc, int x, int y, Color c) {
        putPixel(xc + x, yc + y, c);
        putPixel(xc - x, yc + y, c);
        putPixel(xc + x, yc - y, c);
        putPixel(xc - x, yc - y, c);

        putPixel(xc + y, yc + x, c);
        putPixel(xc - y, yc + x, c);
        putPixel(xc + y, yc - x, c);
        putPixel(xc - y, yc - x, c);
    }

    // Punto medio
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        int x = 0, y = r;
        int p = 1 - r;

        while (x <= y) {
            simetria(xc, yc, x, y, c);
            x++;

            if (p < 0)
                p += 2 * x + 1;
            else {
                y--;
                p += 2 * (x - y) + 1;
            }
        }
    }

    // 🔥 Grosor: múltiples radios
    public void dibujarCirculoConGrosor(int xc, int yc, int r, int grosor, Color c) {

        int mitad = grosor / 2;

        for (int i = -mitad; i <= mitad; i++) {
            dibujarCirculo(xc, yc, r + i, c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circunferencia con grosor");
        CirculoGrosor panel = new CirculoGrosor(400, 400);

        frame.add(panel);
        frame.setSize(420, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}