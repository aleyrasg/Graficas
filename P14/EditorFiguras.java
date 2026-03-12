import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class EditorFiguras extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    private final ButtonGroup modos;
    private final JPanel area;
    private final JLabel estado;
    private Image buffer, tmp;

    private final int PUNTOS = 1, LINEAS = 2, RECTANDULOS = 3, CIRCULOS = 4;
    private int modo;
    private int x, y;

    public EditorFiguras() {
        super("Editor de figuras");
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem opcionNuevo = new JMenuItem("Nuevo", 'N');
        opcionNuevo.addActionListener(this);
        opcionNuevo.setActionCommand("Nuevo");
        menuArchivo.add(opcionNuevo);
        menuArchivo.addSeparator();
        JMenuItem opcionSalir = new JMenuItem("Salir", 'S');
        opcionSalir.addActionListener(this);
        opcionSalir.setActionCommand("Salir");
        menuArchivo.add(opcionSalir);
        menuBar.add(menuArchivo);
        modos = new ButtonGroup();
        JMenu menuModo = new JMenu("Modo");
        JRadioButtonMenuItem opcionPuntos = new JRadioButtonMenuItem("Puntos", true);
        opcionPuntos.addActionListener(this);
        opcionPuntos.setActionCommand("Puntos");
        menuModo.add(opcionPuntos);
        modos.add(opcionPuntos);
        JRadioButtonMenuItem opcionLineas = new JRadioButtonMenuItem("Lineas");
        opcionLineas.addActionListener(this);
        opcionLineas.setActionCommand("Lineas");
        menuModo.add(opcionLineas);
        modos.add(opcionLineas);
        JRadioButtonMenuItem opcionRectangulos = new JRadioButtonMenuItem("Rectangulos");
        opcionRectangulos.addActionListener(this);
        opcionRectangulos.setActionCommand("Rectangulos");
        menuModo.add(opcionRectangulos);
        modos.add(opcionRectangulos);
        JRadioButtonMenuItem opcionCirculos = new JRadioButtonMenuItem("Círculos");
        opcionCirculos.addActionListener(this);
        opcionCirculos.setActionCommand("Circulos");
        menuModo.add(opcionCirculos);
        modos.add(opcionCirculos);
        menuBar.add(menuModo);

        area = new JPanel();
        area.addMouseListener(this);
        area.addMouseMotionListener(this);
        estado = new JLabel("Status", JLabel.LEFT);
        setJMenuBar(menuBar);
        getContentPane().add(area, BorderLayout.CENTER);
        getContentPane().add(estado, BorderLayout.SOUTH);
        modo = PUNTOS;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        buffer = area.createImage(area.getWidth(), area.getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Nuevo")) {
            area.getGraphics().clearRect(0, 0, area.getWidth(), area.getHeight());
            buffer = area.createImage(area.getWidth(), area.getHeight());
        } else if (comando.equals("Salir")) {
            if (JOptionPane.showConfirmDialog(this, "¿En verdad desea salir?",
                    "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
                System.exit(0);
            }
        } else if (comando.equals("Puntos")) {
            modo = PUNTOS;
        } else if (comando.equals("Lineas")) {
            modo = LINEAS;
        } else if (comando.equals("Rectangulos")) {
            modo = RECTANDULOS;
        } else if (comando.equals("Circulos")) {
            modo = CIRCULOS;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        tmp = area.createImage(area.getWidth(), area.getHeight());
        tmp.getGraphics().drawImage(buffer, 0, 0, this);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buffer.getGraphics().drawImage(tmp, 0, 0, this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics g = tmp.getGraphics();
        switch (modo) {
            case PUNTOS -> {
                g.drawImage(buffer, 0, 0, area);
                g.fillOval(e.getX(), e.getY(), 5, 5);
                area.getGraphics().drawImage(tmp, 0, 0, this);
            }
            case LINEAS -> {
                g.drawImage(buffer, 0, 0, area);
                g.drawLine(x, y, e.getX(), e.getY());
                area.getGraphics().drawImage(tmp, 0, 0, this);
            }
            case RECTANDULOS -> {
                g.drawImage(buffer, 0, 0, area);
                g.drawRect(x, y, e.getX() - x, e.getY() - y);
                area.getGraphics().drawImage(tmp, 0, 0, this);
            }
            case CIRCULOS -> {
                g.drawImage(buffer, 0, 0, area);
                g.drawOval(x, y, e.getX() - x, e.getY() - y);
                area.getGraphics().drawImage(tmp, 0, 0, this);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        estado.setText("x=" + e.getX() + ",y=" + e.getY());
    }

    public static void main(String[] args) {
        new EditorFiguras();
    }
    
}