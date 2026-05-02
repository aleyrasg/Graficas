import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MMLR extends JPanel {

    private BufferedImage canvas;

    public MMLR(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());
    }

    // Pinta un píxel directamente en el buffer
    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    // Dibuja una línea usando únicamente y = mx + b
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {
        float m = (float)(y1 - y0) / (x1 - x0);
        float b = y0 - m * x0;
        for (int x = x0; x <= x1; x++) {
            float y = (m * x) + b;
            putPixel(x, Math.round(y), c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        MMLR panel = new MMLR(500, 500);

        // Ejemplos de líneas
        panel.dibujarLinea(50,  450, 450,  50,  Color.BLACK);   // diagonal
        panel.dibujarLinea(50,  250, 450, 250,  Color.RED);     // horizontal
        panel.dibujarLinea(50,  100, 450, 400,  Color.BLUE);    // pendiente positiva

        JFrame frame = new JFrame("Línea Recta — y = mx + b");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}
