import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LineaConMascara extends JPanel {

    private BufferedImage canvas;

    public LineaConMascara(int width, int height) {
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

    // Línea con Bresenham + máscara
    public void dibujarLinea(int x0, int y0, int x1, int y1, Color c, int[] mascara) {

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;

        int err = dx - dy;

        int i = 0; // índice de la máscara

        while (true) {

            // aplicar máscara
            if (mascara[i % mascara.length] == 1) {
                putPixel(x0, y0, c);
            }

            if (x0 == x1 && y0 == y1)
                break;

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }

            i++; // avanzar en la máscara
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Máscaras
        int[] continua = {1,1,1,1,1,1,1,1};
        int[] punteada = {1,0,1,0,1,0,1,0};
        int[] segmentada = {1,1,1,1,0,0,0,0};

        // Dibujos
        dibujarLinea(50, 50, 400, 50, Color.BLACK, continua);
        dibujarLinea(50, 100, 400, 100, Color.RED, punteada);
        dibujarLinea(50, 150, 400, 150, Color.BLUE, segmentada);

        dibujarLinea(50, 200, 400, 350, Color.MAGENTA, punteada);

        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Líneas con máscara");
        LineaConMascara panel = new LineaConMascara(500, 400);

        frame.add(panel);
        frame.setSize(520, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}