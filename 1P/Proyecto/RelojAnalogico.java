import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;

public class RelojAnalogico extends JFrame {

    public RelojAnalogico() {
        setTitle("Reloj Analógico con Threads y Doble Buffer");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        RelojPanel panel = new RelojPanel();
        add(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.detener();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RelojAnalogico::new);
    }
}

class RelojPanel extends JPanel {

    // Ángulos de las manecillas
    private volatile double anguloSegundos;
    private volatile double anguloMinutos;
    private volatile double anguloHoras;

    // Buffer para doble bufereo manual
    private BufferedImage buffer;
    
    // Imagen de fondo
    private Image imagenFondo;
    
    // Imágenes para manecillas
    private Image imagenPeine;  // minutero
    private Image imagenLabial; // horario

    // Control de hilos
    private volatile boolean ejecutando = true;

    // Formato de fecha y hora digital
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelojPanel() {
        setBackground(new Color(211, 233, 229)); // #d3e9e5 - fondo suave
        
        // Cargar imagen de fondo
        try {
            imagenFondo = ImageIO.read(new File("BRATZ_Wallpaper.jpg"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }
        
        // Cargar imágenes de manecillas
        try {
            imagenPeine = ImageIO.read(new File("Peine.png"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen del peine: " + e.getMessage());
        }
        
        try {
            imagenLabial = ImageIO.read(new File("Labial.png"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar la imagen del labial: " + e.getMessage());
        }

        // Aunque Swing ya maneja doble buffering, aquí lo desactivamos
        // para implementar manualmente el doble buffer con BufferedImage.
        setDoubleBuffered(false);

        iniciarHilos();
    }

    public void detener() {
        ejecutando = false;
    }

    private void iniciarHilos() {
        Thread hiloSegundos = new Thread(() -> {
            while (ejecutando) {
                LocalDateTime ahora = LocalDateTime.now();
                int segundos = ahora.getSecond();
                int nanos = ahora.getNano();

                anguloSegundos = -90 + (segundos * 6) + (nanos / 1_000_000_000.0) * 6.0;

                solicitarRepintado();
                dormir(16); // ~60 FPS para movimiento suave
            }
        });

        Thread hiloMinutos = new Thread(() -> {
            while (ejecutando) {
                LocalDateTime ahora = LocalDateTime.now();
                int minutos = ahora.getMinute();
                int segundos = ahora.getSecond();
                int nanos = ahora.getNano();

                anguloMinutos = -90 + (minutos * 6)
                        + (segundos * 0.1)
                        + (nanos / 1_000_000_000.0) * 0.1;

                solicitarRepintado();
                dormir(50);
            }
        });

        Thread hiloHoras = new Thread(() -> {
            while (ejecutando) {
                LocalDateTime ahora = LocalDateTime.now();
                int horas = ahora.getHour() % 12;
                int minutos = ahora.getMinute();
                int segundos = ahora.getSecond();

                anguloHoras = -90 + (horas * 30)
                        + (minutos * 0.5)
                        + (segundos / 120.0);

                solicitarRepintado();
                dormir(100);
            }
        });

        hiloSegundos.setDaemon(true);
        hiloMinutos.setDaemon(true);
        hiloHoras.setDaemon(true);

        hiloSegundos.start();
        hiloMinutos.start();
        hiloHoras.start();
    }

    private void solicitarRepintado() {
        SwingUtilities.invokeLater(this::repaint);
    }

    private void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Crear o recrear el buffer si cambia el tamaño
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2 = buffer.createGraphics();

        // Mejorar calidad visual
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Limpiar el buffer
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        dibujarEscena(g2);

        g2.dispose();

        // Dibujar el buffer completo en pantalla
        g.drawImage(buffer, 0, 0, null);
    }

    private void dibujarEscena(Graphics2D g2) {
        int ancho = getWidth();
        int alto = getHeight();

        int cx = ancho / 2;
        int cy = alto / 2;

        int radio = Math.min(ancho, alto) / 2 - 60;

        // Fondo decorativo con gradiente de la paleta
        GradientPaint fondo = new GradientPaint(
                0, 0, new Color(61, 178, 222),      // #3db2de
                ancho, alto, new Color(211, 233, 229) // #d3e9e5
        );
        g2.setPaint(fondo);
        g2.fillRect(0, 0, ancho, alto);

        // Sombra exterior
        g2.setColor(new Color(87, 35, 100, 120)); // #572364 con transparencia
        g2.fillOval(cx - radio - 18, cy - radio - 10, radio * 2 + 36, radio * 2 + 36);

        // Marco exterior con colores de la paleta
        RadialGradientPaint marco = new RadialGradientPaint(
                new Point2D.Double(cx, cy),
                radio + 20,
                new float[]{0f, 0.5f, 1f},
                new Color[]{
                    new Color(150, 100, 160),  // morado claro
                    new Color(87, 35, 100),    // #572364 - morado medio
                    new Color(60, 25, 70)      // morado oscuro
                }
        );
        g2.setPaint(marco);
        g2.fillOval(cx - radio - 20, cy - radio - 20, (radio + 20) * 2, (radio + 20) * 2);

        // Carátula con imagen recortada en círculo
        if (imagenFondo != null) {
            // Guardar el clip original
            Shape clipOriginal = g2.getClip();
            
            // Crear clip circular para la carátula
            Ellipse2D clipCircular = new Ellipse2D.Double(cx - radio, cy - radio, radio * 2, radio * 2);
            g2.setClip(clipCircular);
            
            // Dibujar la imagen escalada dentro del círculo
            g2.drawImage(imagenFondo, cx - radio, cy - radio, radio * 2, radio * 2, null);
            
            // Restaurar el clip original
            g2.setClip(clipOriginal);
        } else {
            // Si no hay imagen, usar el gradiente original
            RadialGradientPaint caratula = new RadialGradientPaint(
                    new Point2D.Double(cx, cy),
                    radio,
                    new float[]{0f, 1f},
                    new Color[]{new Color(250, 250, 250), new Color(210, 220, 235)}
            );
            g2.setPaint(caratula);
            g2.fillOval(cx - radio, cy - radio, radio * 2, radio * 2);
        }

        // Anillo interior
        g2.setStroke(new BasicStroke(4f));
        g2.setColor(new Color(87, 35, 100)); // #572364
        g2.drawOval(cx - radio, cy - radio, radio * 2, radio * 2);

        // Marcas y números
        dibujarMarcasYNumeros(g2, cx, cy, radio);

        // Fecha y hora digital
        LocalDateTime ahora = LocalDateTime.now();
        g2.setColor(new Color(255, 115, 255)); // Rosa pastel
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(18, radio / 10)));
        String horaTexto = ahora.format(formatoHora);
        String fechaTexto = ahora.format(formatoFecha);

        FontMetrics fmHora = g2.getFontMetrics();
        g2.drawString(horaTexto, cx - fmHora.stringWidth(horaTexto) / 2, cy + radio / 2);

        g2.setFont(new Font("SansSerif", Font.PLAIN, Math.max(14, radio / 15)));
        FontMetrics fmFecha = g2.getFontMetrics();
        g2.drawString(fechaTexto, cx - fmFecha.stringWidth(fechaTexto) / 2, cy + radio / 2 + 28);

        // Texto decorativo
        g2.setFont(new Font("Serif", Font.BOLD, Math.max(20, radio / 9)));
        String marca = "ALE CLOCK";
        FontMetrics fmMarca = g2.getFontMetrics();
        g2.setColor(new Color(255, 115, 255)); // #572364 - morado para marca
        g2.drawString(marca, cx - fmMarca.stringWidth(marca) / 2, cy - radio / 3);

        // Dibujar manecillas
        dibujarManecillaImagen(g2, cx, cy, radio * 0.50, anguloHoras, imagenLabial);   // Labial como horario
        dibujarManecillaImagen(g2, cx, cy, radio * 0.72, anguloMinutos, imagenPeine);  // Peine como minutero
        dibujarManecilla(g2, cx, cy, radio * 0.82, anguloSegundos, 3f, new Color(61, 178, 222)); // #3db2de

        // Contrapeso de segundero
        dibujarContrapesoSegundero(g2, cx, cy, radio * 0.22, anguloSegundos);

        // Centro del reloj
        g2.setColor(new Color(87, 35, 100)); // #572364
        g2.fillOval(cx - 11, cy - 11, 22, 22);
        g2.setColor(new Color(211, 233, 229)); // #d3e9e5
        g2.fillOval(cx - 5, cy - 5, 10, 10);
    }

    private void dibujarMarcasYNumeros(Graphics2D g2, int cx, int cy, int radio) {
        for (int i = 0; i < 60; i++) {
            double angulo = Math.toRadians(i * 6 - 90);

            int x1, y1, x2, y2;

            if (i % 5 == 0) {
                x1 = (int) (cx + Math.cos(angulo) * (radio - 25));
                y1 = (int) (cy + Math.sin(angulo) * (radio - 25));
                x2 = (int) (cx + Math.cos(angulo) * (radio - 3));
                y2 = (int) (cy + Math.sin(angulo) * (radio - 3));

                g2.setStroke(new BasicStroke(4f));
                g2.setColor(new Color(61, 178, 222)); // #3db2de - azul para marcas
                g2.drawLine(x1, y1, x2, y2);

                int numero = i / 5 == 0 ? 12 : i / 5;
                String texto = String.valueOf(numero);

                int xn = (int) (cx + Math.cos(angulo) * (radio - 55));
                int yn = (int) (cy + Math.sin(angulo) * (radio - 55));

                g2.setFont(new Font("Serif", Font.BOLD, Math.max(22, radio / 9)));
                FontMetrics fm = g2.getFontMetrics();
                g2.setColor(new Color(61, 178, 222)); // #3db2de - azul para números
                g2.drawString(texto, xn - fm.stringWidth(texto) / 2, yn + fm.getAscent() / 3);

            } else {
                x1 = (int) (cx + Math.cos(angulo) * (radio - 15));
                y1 = (int) (cy + Math.sin(angulo) * (radio - 15));
                x2 = (int) (cx + Math.cos(angulo) * (radio - 3));
                y2 = (int) (cy + Math.sin(angulo) * (radio - 3));

                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(new Color(192, 123, 160)); // #c07ba0
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    private void dibujarManecilla(Graphics2D g2, int cx, int cy, double longitud,
                                  double anguloGrados, float grosor, Color color) {
        double rad = Math.toRadians(anguloGrados);
        int x = (int) (cx + Math.cos(rad) * longitud);
        int y = (int) (cy + Math.sin(rad) * longitud);

        g2.setStroke(new BasicStroke(grosor, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(color);
        g2.drawLine(cx, cy, x, y);
    }
    
    private void dibujarManecillaImagen(Graphics2D g2, int cx, int cy, double longitud,
                                        double anguloGrados, Image imagen) {
        if (imagen == null) {
            // Si no hay imagen, dibujar manecilla normal
            dibujarManecilla(g2, cx, cy, longitud, anguloGrados, 7f, new Color(87, 35, 100));
            return;
        }
        
        // Guardar transformación original
        AffineTransform transformOriginal = g2.getTransform();
        
        // Obtener dimensiones originales de la imagen
        int anchoOriginal = imagen.getWidth(null);
        int altoOriginal = imagen.getHeight(null);
        
        // Calcular escala para que el alto de la imagen sea igual a la longitud de la manecilla
        double escala = longitud / altoOriginal;
        
        // Calcular nuevas dimensiones manteniendo proporción
        int anchoImagen = (int) (anchoOriginal * escala * 0.8); // Reducir un poco el ancho
        int altoImagen = (int) (longitud * 0.95); // Usar casi toda la longitud
        
        // Trasladar al centro del reloj
        g2.translate(cx, cy);
        
        // Rotar según el ángulo
        g2.rotate(Math.toRadians(anguloGrados + 90)); // +90 para ajustar orientación
        
        // Dibujar la imagen centrada horizontalmente y que empiece desde el centro del reloj
        g2.drawImage(imagen, -anchoImagen / 2, -altoImagen, anchoImagen, altoImagen, null);
        
        // Restaurar transformación original
        g2.setTransform(transformOriginal);
    }

    private void dibujarContrapesoSegundero(Graphics2D g2, int cx, int cy, double longitud, double anguloGrados) {
        double rad = Math.toRadians(anguloGrados + 180);
        int x = (int) (cx + Math.cos(rad) * longitud);
        int y = (int) (cy + Math.sin(rad) * longitud);

        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(new Color(61, 178, 222)); // #3db2de
        g2.drawLine(cx, cy, x, y);
    }
}