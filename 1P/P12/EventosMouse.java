import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EventosMouse extends JFrame 
        implements MouseListener, MouseMotionListener {

    public EventosMouse() {
        setTitle("Eventos del Mouse");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregar listeners al JFrame
        addMouseListener(this);
        addMouseMotionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        new EventosMouse();
    }

    // -------- MouseListener --------

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse CLICKED en (" + e.getX() + "," + e.getY() + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse PRESSED en (" + e.getX() + "," + e.getY() + ")");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse RELEASED en (" + e.getX() + "," + e.getY() + ")");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse ENTERED");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse EXITED");
    }

    // -------- MouseMotionListener --------

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse DRAGGED en (" + e.getX() + "," + e.getY() + ")");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse MOVED en (" + e.getX() + "," + e.getY() + ")");
    }
}