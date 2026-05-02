import java.awt.*;

public class Rectangulo {

    public static void dibujar(PixelCanvas pc, int x, int y, int w, int h, Color c) {

        Linea.dibujar(pc, x, y, x + w, y, c);
        Linea.dibujar(pc, x, y + h, x + w, y + h, c);
        Linea.dibujar(pc, x, y, x, y + h, c);
        Linea.dibujar(pc, x + w, y, x + w, y + h, c);
    }
}