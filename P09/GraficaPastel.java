import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraficaPastel extends JFrame {

    public GraficaPastel(double[] valores) {
        setTitle("Gráfica de Pastel");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PanelPastel(valores));
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Uso: java GraficaPastel 30 20 10 40");
            System.exit(1);
        }

        double[] valores = new double[args.length];

        try {
            for (int i = 0; i < args.length; i++) {
                valores[i] = Double.parseDouble(args[i]);

                if (valores[i] < 0) {
                    System.out.println("Error: no se permiten valores negativos.");
                    System.exit(1);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: todos los argumentos deben ser números.");
            System.exit(1);
        }

        double suma = 0;
        for (double v : valores) {
            suma += v;
        }

        if (suma == 0) {
            System.out.println("Error: la suma de los valores no puede ser 0.");
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            GraficaPastel ventana = new GraficaPastel(valores);
            ventana.setVisible(true);
        });
    }
}

class PanelPastel extends JPanel {

    private final double[] valores;
    private final Color[] colores = {
        Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE,
        Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW,
        new Color(128, 0, 128), new Color(139, 69, 19)
    };

    public PanelPastel(double[] valores) {
        this.valores = valores;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = 250;
        int alto = 250;
        int x = 80;
        int y = 80;

        double suma = 0;
        for (double v : valores) {
            suma += v;
        }

        int anguloInicial = 0;

        for (int i = 0; i < valores.length; i++) {
            int angulo = (int) Math.round((valores[i] / suma) * 360);

            g2.setColor(colores[i % colores.length]);
            g2.fillArc(x, y, ancho, alto, anguloInicial, angulo);

            anguloInicial += angulo;
        }

        // Dibujar leyenda
        int leyendaX = 380;
        int leyendaY = 100;

        g2.setFont(new Font("Arial", Font.PLAIN, 14));

        for (int i = 0; i < valores.length; i++) {
            double porcentaje = (valores[i] / suma) * 100;

            g2.setColor(colores[i % colores.length]);
            g2.fillRect(leyendaX, leyendaY + i * 30, 20, 20);

            g2.setColor(Color.BLACK);
            g2.drawRect(leyendaX, leyendaY + i * 30, 20, 20);
            g2.drawString(
                "Valor " + (i + 1) + ": " + valores[i] + " (" + String.format("%.2f", porcentaje) + "%)",
                leyendaX + 30,
                leyendaY + 15 + i * 30
            );
        }

        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Gráfica de Pastel", 230, 40);
    }
}