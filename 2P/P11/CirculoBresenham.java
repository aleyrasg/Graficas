import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoBresenham extends JPanel {

    private BufferedImage canvas;

    public CirculoBresenham(int width, int height) {
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

    // Dibujar simetría de 8 puntos
    private void dibujarSimetria(int xc, int yc, int x, int y, Color c) {
        putPixel(xc + x, yc + y, c);
        putPixel(xc - x, yc + y, c);
        putPixel(xc + x, yc - y, c);
        putPixel(xc - x, yc - y, c);

        putPixel(xc + y, yc + x, c);
        putPixel(xc - y, yc + x, c);
        putPixel(xc + y, yc - x, c);
        putPixel(xc - y, yc - x, c);
    }

    // Algoritmo de Bresenham para circunferencia
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        dibujarSimetria(xc, yc, x, y, c);

        while (x <= y) {
            x++;

            if (d < 0) {
                d = d + 4 * x + 6;
            } else {
                y--;
                d = d + 4 * (x - y) + 10;
            }

            dibujarSimetria(xc, yc, x, y, c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        CirculoBresenham panel = new CirculoBresenham(500, 500);

        // Pruebas
        panel.dibujarCirculo(250, 250, 100, Color.BLACK);
        panel.dibujarCirculo(150, 150, 50, Color.RED);
        panel.dibujarCirculo(350, 150, 75, Color.BLUE);

        JFrame frame = new JFrame("Círculo con Bresenham");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}