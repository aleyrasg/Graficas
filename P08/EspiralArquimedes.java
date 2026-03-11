import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class EspiralArquimedes extends JFrame {

    public EspiralArquimedes(double factorCrecimiento) {
        setTitle("Espiral de Arquímedes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        EspiralPanel panel = new EspiralPanel(factorCrecimiento);
        panel.setDoubleBuffered(true);
        setContentPane(panel);
        setVisible(true);

        Thread hilo = new Thread(panel);
        hilo.start();
    }

    public static void main(String[] args) {
        new EspiralArquimedes(1.5);
    }
}

class EspiralPanel extends JPanel implements Runnable {

    private final double factorCrecimiento;
    private final List<Arc2D> arcos1 = Collections.synchronizedList(new ArrayList<>());
    private final List<Arc2D> arcos2 = Collections.synchronizedList(new ArrayList<>());
    private double anguloActual = 0;
    private volatile boolean detener = false;

    public EspiralPanel(double factorCrecimiento) {
        this.factorCrecimiento = factorCrecimiento;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f));

        synchronized (arcos1) {
            for (Arc2D arco : arcos1) {
                g2d.draw(arco);
            }
        }

        synchronized (arcos2) {
            for (Arc2D arco : arcos2) {
                g2d.draw(arco);
            }
        }
    }

    @Override
    public void run() {
        final double deltaAngulo = 0.1; // Radianes por iteración
        final double extensionArco = Math.toDegrees(deltaAngulo); // Conversión a grados

        try {
            while (!detener) {
                int ancho = getWidth();
                int alto = getHeight();
                int centroX = ancho / 2;
                int centroY = alto / 2;

                // Ecuación del espiral de Arquímedes: r = a * θ
                double radio = factorCrecimiento * anguloActual;

                // Detener cuando el espiral ya salga del panel
                if (centroX - radio <= 0 || centroX + radio >= ancho ||
                    centroY - radio <= 0 || centroY + radio >= alto) {
                    detener = true;
                    break;
                }

                double inicioArco1 = Math.toDegrees(-anguloActual);
                double inicioArco2 = Math.toDegrees(-(anguloActual + Math.PI));
                double diametro = 2.0 * radio;

                if (diametro >= 1.0) {
                    Arc2D.Double arco1 = new Arc2D.Double(
                            centroX - radio,
                            centroY - radio,
                            diametro,
                            diametro,
                            inicioArco1,
                            extensionArco,
                            Arc2D.OPEN
                    );

                    Arc2D.Double arco2 = new Arc2D.Double(
                            centroX - radio,
                            centroY - radio,
                            diametro,
                            diametro,
                            inicioArco2,
                            extensionArco,
                            Arc2D.OPEN
                    );

                    arcos1.add(arco1);
                    arcos2.add(arco2);
                }

                anguloActual += deltaAngulo;
                repaint();
                Thread.sleep(30); // Controla la velocidad de la animación
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}