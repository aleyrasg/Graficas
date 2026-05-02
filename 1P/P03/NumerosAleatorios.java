package P03;
import java.util.Random;

public class NumerosAleatorios {
    public static void main(String[] args) {
        Random rand = new Random();
        int num1 = rand.nextInt(100);
        int num2 = rand.nextInt(100);
        
        System.out.println("Número 1: " + num1);
        System.out.println("Número 2: " + num2);
        
        if (num1 > num2) {
            System.out.println("El mayor es: " + num1);
        } else if (num2 > num1) {
            System.out.println("El mayor es: " + num2);
        } else {
            System.out.println("Los números son iguales");
        }
    }
}
