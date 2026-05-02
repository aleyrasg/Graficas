import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoFormula extends JPanel {

    private BufferedImage canvas;

    public CirculoFormula(int width, int height) {
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

    // Dibujar círculo usando la fórmula
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        for (int x = xc - r; x <= xc + r; x++) {

            int dx = x - xc;
            double y = Math.sqrt(r * r - dx * dx);

            int y1 = (int)Math.round(yc + y);
            int y2 = (int)Math.round(yc - y);

            putPixel(x, y1, c); // parte superior
            putPixel(x, y2, c); // parte inferior
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        CirculoFormula panel = new CirculoFormula(500, 500);

        // Dibujar círculos
        panel.dibujarCirculo(250, 250, 100, Color.BLACK);
        panel.dibujarCirculo(150, 150, 50, Color.RED);
        panel.dibujarCirculo(350, 150, 75, Color.BLUE);

        JFrame frame = new JFrame("Círculo con fórmula matemática");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}