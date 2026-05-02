import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FloodFill extends JPanel {

    private BufferedImage canvas;

    public FloodFill(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Fondo blanco
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                canvas.setRGB(x, y, Color.WHITE.getRGB());

        // Dibujar figura (rectángulo)
        dibujarRectangulo(100, 100, 200, 150, Color.BLACK);

        // Aplicar flood fill dentro
        floodFill(150, 150, Color.WHITE.getRGB(), Color.CYAN.getRGB());
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            canvas.setRGB(x, y, c.getRGB());
    }

    private int getPixel(int x, int y) {
        if (x >= 0 && x < canvas.getWidth() && y >= 0 && y < canvas.getHeight())
            return canvas.getRGB(x, y);
        return -1;
    }

    // Línea (Bresenham)
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

    // Rectángulo
    private void dibujarRectangulo(int x, int y, int w, int h, Color c) {
        linea(x, y, x + w, y, c);
        linea(x, y + h, x + w, y + h, c);
        linea(x, y, x, y + h, c);
        linea(x + w, y, x + w, y + h, c);
    }

    // Flood Fill (iterativo con Stack para evitar StackOverflow)
    private void floodFill(int x, int y, int colorOriginal, int colorNuevo) {
        if (colorOriginal == colorNuevo) return;

        java.util.Stack<int[]> stack = new java.util.Stack<>();
        stack.push(new int[]{x, y});

        while (!stack.isEmpty()) {
            int[] p = stack.pop();
            int px = p[0], py = p[1];

            if (px < 0 || px >= canvas.getWidth() || py < 0 || py >= canvas.getHeight()) continue;
            if (getPixel(px, py) != colorOriginal) continue;

            canvas.setRGB(px, py, colorNuevo);

            stack.push(new int[]{px + 1, py});
            stack.push(new int[]{px - 1, py});
            stack.push(new int[]{px, py + 1});
            stack.push(new int[]{px, py - 1});
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flood Fill");

        FloodFill panel = new FloodFill(500, 400);
        frame.add(panel);

        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}