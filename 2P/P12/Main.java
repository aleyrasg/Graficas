import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {

    private PixelCanvas pc;

    public Main() {
        pc = new PixelCanvas(800, 500);

        // Líneas
        Linea.dibujar(pc, 50, 50, 150, 150, Color.BLACK);
        Linea.dibujar(pc, 200, 100, 350, 100, Color.BLACK);
        Linea.dibujar(pc, 400, 150, 500, 50, Color.BLACK);
        Linea.dibujar(pc, 550, 100, 650, 100, Color.BLACK);

        // Círculos
        Circulo.dibujar(pc, 150, 350, 80, Color.BLACK);
        Circulo.dibujar(pc, 150, 350, 60, Color.BLACK);
        Circulo.dibujar(pc, 150, 350, 40, Color.BLACK);
        Circulo.dibujar(pc, 150, 350, 20, Color.BLACK);

        // Rectángulos
        Rectangulo.dibujar(pc, 300, 300, 200, 120, Color.BLACK);
        Rectangulo.dibujar(pc, 350, 340, 100, 60, Color.BLACK);

        // Elipses
        Elipse.dibujar(pc, 600, 350, 120, 60, Color.BLACK);
        Elipse.dibujar(pc, 600, 350, 90, 45, Color.BLACK);
        Elipse.dibujar(pc, 600, 350, 60, 30, Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pc.getCanvas(), 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Figuras separadas en clases");

        Main panel = new Main();
        frame.add(panel);

        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}