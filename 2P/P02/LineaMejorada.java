import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaMejorada extends JPanel {

    private BufferedImage canvas;

    public LineaMejorada(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c) {

        // 📌 Caso 1: Línea vertical
        if (x0 == x1) {
            int yInicio = Math.min(y0, y1);
            int yFin = Math.max(y0, y1);

            for (int y = yInicio; y <= yFin; y++) {
                putPixel(x0, y, c);
            }
            return;
        }

        double m = (double)(y1 - y0) / (x1 - x0);
        double b = y0 - m * x0;

        // 📌 Si la pendiente es suave → iterar en X
        if (Math.abs(m) <= 1) {

            int xInicio = Math.min(x0, x1);
            int xFin = Math.max(x0, x1);

            for (int x = xInicio; x <= xFin; x++) {
                double y = m * x + b;
                putPixel(x, (int)Math.round(y), c);
            }

        } else {
            // 📌 Pendiente alta → iterar en Y
            int yInicio = Math.min(y0, y1);
            int yFin = Math.max(y0, y1);

            for (int y = yInicio; y <= yFin; y++) {
                double x = (y - b) / m;
                putPixel((int)Math.round(x), y, c);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        LineaMejorada panel = new LineaMejorada(500, 500);

        // Pruebas
        panel.dibujarLinea(50, 450, 450, 50, Color.BLACK);   // diagonal
        panel.dibujarLinea(50, 250, 450, 250, Color.RED);    // horizontal
        panel.dibujarLinea(250, 50, 250, 450, Color.GREEN);  // vertical
        panel.dibujarLinea(400, 50, 100, 450, Color.BLUE);   // x0 > x1
        panel.dibujarLinea(200, 50, 220, 450, Color.MAGENTA);// pendiente alta

        JFrame frame = new JFrame("Línea Mejorada — y = mx + b");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}
