import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoPuntoMedio extends JPanel {

    private BufferedImage canvas;

    public CirculoPuntoMedio(int width, int height) {
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

    // Dibujar los 8 puntos simétricos
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

    // Algoritmo de punto medio para círculo
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        int x = 0;
        int y = r;
        int p = 1 - r;

        dibujarSimetria(xc, yc, x, y, c);

        while (x < y) {
            x++;

            if (p < 0) {
                p = p + 2 * x + 3;
            } else {
                y--;
                p = p + 2 * (x - y) + 5;
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

        CirculoPuntoMedio panel = new CirculoPuntoMedio(500, 500);

        // Pruebas
        panel.dibujarCirculo(250, 250, 100, Color.BLACK);
        panel.dibujarCirculo(150, 150, 50, Color.RED);
        panel.dibujarCirculo(350, 150, 75, Color.BLUE);

        JFrame frame = new JFrame("Círculo con Punto Medio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}