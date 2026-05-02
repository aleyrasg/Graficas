import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ScanLineFill extends JPanel {

    private BufferedImage canvas;

    public ScanLineFill(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Definir polígono (ejemplo)
        int[] xPoints = {200, 300, 350, 250, 150};
        int[] yPoints = {100, 150, 250, 300, 200};

        // Dibujar borde
        dibujarPoligono(xPoints, yPoints, Color.BLACK);

        // Rellenar
        scanLineFill(xPoints, yPoints, Color.CYAN);
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    // Bresenham
    private void linea(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            putPixel(x0, y0, c);
            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;

            if (e2 > -dy) { err -= dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }
    }

    // Dibujar polígono
    private void dibujarPoligono(int[] x, int[] y, Color c) {
        for (int i = 0; i < x.length; i++) {
            int j = (i + 1) % x.length;
            linea(x[i], y[i], x[j], y[j], c);
        }
    }

    // 🔥 Scan-Line Fill
    private void scanLineFill(int[] xPoints, int[] yPoints, Color fillColor) {

        int n = xPoints.length;

        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (int y : yPoints) {
            yMin = Math.min(yMin, y);
            yMax = Math.max(yMax, y);
        }

        for (int y = yMin; y <= yMax; y++) {

            ArrayList<Integer> intersecciones = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int j = (i + 1) % n;

                int x1 = xPoints[i], y1 = yPoints[i];
                int x2 = xPoints[j], y2 = yPoints[j];

                // Verificar intersección
                if ((y1 < y && y2 >= y) || (y2 < y && y1 >= y)) {
                    int x = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                    intersecciones.add(x);
                }
            }

            Collections.sort(intersecciones);

            // Rellenar pares
            for (int i = 0; i < intersecciones.size(); i += 2) {
                int xStart = intersecciones.get(i);
                int xEnd = intersecciones.get(i + 1);

                for (int x = xStart; x <= xEnd; x++) {
                    putPixel(x, y, fillColor);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Scan-Line Fill");

        ScanLineFill panel = new ScanLineFill(500, 400);
        frame.add(panel);

        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}