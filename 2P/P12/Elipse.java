import java.awt.*;

public class Elipse {

    private static void simetria(PixelCanvas pc, int xc, int yc, int x, int y, Color c) {
        pc.putPixel(xc + x, yc + y, c);
        pc.putPixel(xc - x, yc + y, c);
        pc.putPixel(xc + x, yc - y, c);
        pc.putPixel(xc - x, yc - y, c);
    }

    public static void dibujar(PixelCanvas pc, int xc, int yc, int rx, int ry, Color c) {

        int x = 0, y = ry;

        double d1 = (ry * ry) - (rx * rx * ry) + (0.25 * rx * rx);
        double dx = 2 * ry * ry * x;
        double dy = 2 * rx * rx * y;

        while (dx < dy) {
            simetria(pc, xc, yc, x, y, c);

            if (d1 < 0) {
                x++;
                dx += 2 * ry * ry;
                d1 += dx + ry * ry;
            } else {
                x++; y--;
                dx += 2 * ry * ry;
                dy -= 2 * rx * rx;
                d1 += dx - dy + ry * ry;
            }
        }

        double d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) +
                    ((rx * rx) * ((y - 1) * (y - 1))) -
                    (rx * rx * ry * ry);

        while (y >= 0) {
            simetria(pc, xc, yc, x, y, c);

            if (d2 > 0) {
                y--;
                dy -= 2 * rx * rx;
                d2 += rx * rx - dy;
            } else {
                y--; x++;
                dx += 2 * ry * ry;
                dy -= 2 * rx * rx;
                d2 += dx - dy + rx * rx;
            }
        }
    }
}