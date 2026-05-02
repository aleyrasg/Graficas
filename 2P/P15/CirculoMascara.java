import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoMascara extends JPanel {

    private BufferedImage canvas;

    public CirculoMascara(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Máscaras
        int[] continua = {1,1,1,1,1,1};
        int[] punteada = {1,0,1,0,1,0};
        int[] segmentada = {1,1,1,0,0,0};

        // Dibujos
        dibujarCirculo(250, 200, 100, Color.BLACK, continua);
        dibujarCirculo(250, 200, 70, Color.RED, punteada);
        dibujarCirculo(250, 200, 40, Color.BLUE, segmentada);
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    // Dibuja los 8 puntos simétricos con máscara
    private void dibujarSimetria(int xc, int yc, int x, int y, Color c, int[] mask, int i) {

        if (mask[i % mask.length] == 1) {
            putPixel(xc + x, yc + y, c);
            putPixel(xc - x, yc + y, c);
            putPixel(xc + x, yc - y, c);
            putPixel(xc - x, yc - y, c);

            putPixel(xc + y, yc + x, c);
            putPixel(xc - y, yc + x, c);
            putPixel(xc + y, yc - x, c);
            putPixel(xc - y, yc - x, c);
        }
    }

    // Algoritmo punto medio + máscara
    public void dibujarCirculo(int xc, int yc, int r, Color c, int[] mask) {

        int x = 0, y = r;
        int p = 1 - r;
        int i = 0;

        while (x <= y) {
            dibujarSimetria(xc, yc, x, y, c, mask, i);

            x++;
            i++;

            if (p < 0)
                p += 2 * x + 1;
            else {
                y--;
                p += 2 * (x - y) + 1;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circunferencia con máscara");
        CirculoMascara panel = new CirculoMascara(500, 400);

        frame.add(panel);
        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}