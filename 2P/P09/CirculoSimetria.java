import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoSimetria extends JPanel {

    private BufferedImage canvas;

    public CirculoSimetria(int width, int height) {
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

    // Dibuja los 8 puntos simétricos
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

    // Dibujar círculo usando fórmula + simetría
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        for (int x = 0; x <= r; x++) {
            int y = (int)Math.round(Math.sqrt(r * r - x * x));
            dibujarSimetria(xc, yc, x, y, c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        CirculoSimetria panel = new CirculoSimetria(500, 500);

        // Dibujar círculos
        panel.dibujarCirculo(250, 250, 100, Color.BLACK);
        panel.dibujarCirculo(150, 150, 50, Color.RED);
        panel.dibujarCirculo(350, 150, 75, Color.BLUE);

        JFrame frame = new JFrame("Círculo con Simetría de 8 lados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}