import java.awt.*;

public class Circulo {

    private static void simetria(PixelCanvas pc, int xc, int yc, int x, int y, Color c) {

        pc.putPixel(xc + x, yc + y, c);
        pc.putPixel(xc - x, yc + y, c);
        pc.putPixel(xc + x, yc - y, c);
        pc.putPixel(xc - x, yc - y, c);

        pc.putPixel(xc + y, yc + x, c);
        pc.putPixel(xc - y, yc + x, c);
        pc.putPixel(xc + y, yc - x, c);
        pc.putPixel(xc - y, yc - x, c);
    }

    public static void dibujar(PixelCanvas pc, int xc, int yc, int r, Color c) {

        int x = 0, y = r;
        int p = 1 - r;

        while (x <= y) {
            simetria(pc, xc, yc, x, y, c);
            x++;

            if (p < 0)
                p += 2 * x + 1;
            else {
                y--;
                p += 2 * (x - y) + 1;
            }
        }
    }
}