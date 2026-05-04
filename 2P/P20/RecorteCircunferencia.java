import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RecorteCircunferencia extends JPanel {

    private BufferedImage canvas;

    // Área de recorte
    int xmin = 100, ymin = 100;
    int xmax = 400, ymax = 300;

    public RecorteCircunferencia(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Dibujar ventana de recorte
        dibujarRectangulo(xmin, ymin, xmax - xmin, ymax - ymin, Color.BLACK);

        // Circunferencias de prueba
        dibujarCirculoRecortado(250, 200, 120, Color.RED);
        dibujarCirculoRecortado(150, 150, 80, Color.BLUE);
        dibujarCirculoRecortado(350, 250, 90, Color.GREEN);
    }

    // Dibujar píxel SOLO si está dentro del área
    private void putPixelClip(int x, int y, Color c) {
        if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
            if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight()) {
                canvas.setRGB(x, y, c.getRGB());
            }
        }
    }

    // Línea (Bresenham)
    private void linea(int x0, int y0, int x1, int y1, Color c) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            canvas.setRGB(x0, y0, c.getRGB());

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;

            if (e2 > -dy) { err -= dy; x0 += sx; }
            if (e2 < dx) { err += dx; y0 += sy; }
        }
    }

    // Rectángulo
    private void dibujarRectangulo(int x, int y, int w, int h, Color c) {
        linea(x, y, x + w, y, c);
        linea(x, y + h, x + w, y + h, c);
        linea(x, y, x, y + h, c);
        linea(x + w, y, x + w, y + h, c);
    }

    // Simetría del círculo
    private void simetria(int xc, int yc, int x, int y, Color c) {

        putPixelClip(xc + x, yc + y, c);
        putPixelClip(xc - x, yc + y, c);
        putPixelClip(xc + x, yc - y, c);
        putPixelClip(xc - x, yc - y, c);

        putPixelClip(xc + y, yc + x, c);
        putPixelClip(xc - y, yc + x, c);
        putPixelClip(xc + y, yc - x, c);
        putPixelClip(xc - y, yc - x, c);
    }

    // Punto medio + recorte por puntos
    public void dibujarCirculoRecortado(int xc, int yc, int r, Color c) {

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recorte de circunferencias");

        RecorteCircunferencia panel = new RecorteCircunferencia(500, 400);
        frame.add(panel);

        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}