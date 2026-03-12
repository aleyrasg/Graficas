import java.awt.*;
import javax.swing.*;


public class VisorImagen extends JFrame {
    private final JScrollPane panel; 
    private final Pantalla pantalla;

    public VisorImagen(String archivo) {
        super("Visor imagen");

        Image img = Toolkit.getDefaultToolkit().getImage(archivo);
        pantalla = new Pantalla(img);
        panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        getContentPane().add(panel); 
        panel.setViewportView(pantalla);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new VisorImagen("image.jpg");
    }
}

class Pantalla extends JPanel {

    private final Image imagen;

    public Pantalla(Image imagen) {
        this.imagen = imagen;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //Desplegar para obtener dimensiones
        Dimension tam = new Dimension(imagen.getWidth(this),
                                      imagen.getHeight(this));
        setPreferredSize(tam);
        setMinimumSize(tam);
        setMaximumSize(tam);
        setSize(tam);
        update(g);
    }
    
    @Override
    public void update(Graphics g) {
        g.drawImage(imagen,0,0,this);
    }
}
