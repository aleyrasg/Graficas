import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RecorteLineas extends JPanel {

    private BufferedImage canvas;

    // Región de recorte
    int xmin = 100, ymin = 100;
    int xmax = 400, ymax = 300;

    // Códigos de región
    final int INSIDE = 0;
    final int LEFT = 1;
    final int RIGHT = 2;
    final int BOTTOM = 4;
    final int TOP = 8;

    public RecorteLineas(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Dibujar ventana de recorte
        dibujarRectangulo(xmin, ymin, xmax - xmin, ymax - ymin, Color.BLACK);

        // Líneas de prueba
        dibujarLineaRecortada(50, 50, 450, 350, Color.RED);
        dibujarLineaRecortada(50, 200, 450, 200, Color.BLUE);
        dibujarLineaRecortada(200, 50, 200, 350, Color.GREEN);
        dibujarLineaRecortada(50, 150, 150, 50, Color.MAGENTA);
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

    // Dibujar rectángulo
    private void dibujarRectangulo(int x, int y, int w, int h, Color c) {
        linea(x, y, x + w, y, c);
        linea(x, y + h, x + w, y + h, c);
        linea(x, y, x, y + h, c);
        linea(x + w, y, x + w, y + h, c);
    }

    // Calcular código de región
    private int computeCode(int x, int y) {
        int code = INSIDE;

        if (x < xmin) code |= LEFT;
        else if (x > xmax) code |= RIGHT;

        if (y < ymin) code |= TOP;
        else if (y > ymax) code |= BOTTOM;

        return code;
    }

    // 🔥 Recorte de línea
    public void dibujarLineaRecortada(int x0, int y0, int x1, int y1, Color c) {

        int code0 = computeCode(x0, y0);
        int code1 = computeCode(x1, y1);

        boolean accept = false;

        while (true) {

            if ((code0 | code1) == 0) {
                // Ambos dentro
                accept = true;
                break;

            } else if ((code0 & code1) != 0) {
                // Ambos fuera
                break;

            } else {
                int codeOut = (code0 != 0) ? code0 : code1;

                int x = 0, y = 0;

                if ((codeOut & TOP) != 0) {
                    x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                    y = ymin;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                    y = ymax;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                    x = xmax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                    x = xmin;
                }

                if (codeOut == code0) {
                    x0 = x;
                    y0 = y;
                    code0 = computeCode(x0, y0);
                } else {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                }
            }
        }

        if (accept) {
            linea(x0, y0, x1, y1, c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recorte de líneas");

        RecorteLineas panel = new RecorteLineas(500, 400);
        frame.add(panel);

        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}