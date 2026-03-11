import javax.swing.JFrame;

public class Ventana {
    
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Mi Ventana");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setVisible(true);
    }
}
