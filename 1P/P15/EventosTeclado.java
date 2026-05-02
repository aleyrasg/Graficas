import java.awt.event.*;
import javax.swing.*;

public class EventosTeclado extends JFrame implements KeyListener {

    public EventosTeclado() {
        setTitle("Eventos del Teclado");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // IMPORTANTE: agregar el listener
        addKeyListener(this);

        // Para asegurar que reciba eventos del teclado
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);
    }

    public static void main(String[] args) {
        new EventosTeclado();
    }

    // -------- Eventos del teclado --------

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("TECLA PRESIONADA: " + e.getKeyChar() +
                           " | Código: " + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("TECLA LIBERADA: " + e.getKeyChar() +
                           " | Código: " + e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("TECLA ESCRITA: " + e.getKeyChar());
    }
}