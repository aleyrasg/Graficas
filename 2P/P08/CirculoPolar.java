import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CirculoPolar extends JPanel {

    private BufferedImage canvas;

    public CirculoPolar(int width, int height) {
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

    // Dibujar círculo con coordenadas polares
    public void dibujarCirculo(int xc, int yc, int r, Color c) {

        double paso = 0.01; // control de suavidad

        for (double t = 0; t <= 2 * Math.PI; t += paso) {

            int x = (int)Math.round(xc + r * Math.sin(t));
            int y = (int)Math.round(yc + r * Math.cos(t));

            putPixel(x, y, c);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    public static void main(String[] args) {

        CirculoPolar panel = new CirculoPolar(500, 500);

        // Dibujar círculos
        panel.dibujarCirculo(250, 250, 100, Color.BLACK);
        panel.dibujarCirculo(150, 150, 50, Color.RED);
        panel.dibujarCirculo(350, 150, 75, Color.BLUE);

        JFrame frame = new JFrame("Círculo con coordenadas polares");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(520, 540);
        frame.setVisible(true);
    }
}