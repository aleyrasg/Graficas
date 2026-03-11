import java.util.Arrays;

public class OrderList {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No se proporcionaron números");
            return;
        }
        
        int[] numeros = new int[args.length];
        
        for (int i = 0; i < args.length; i++) {
            numeros[i] = Integer.parseInt(args[i]);
        }
        
        Arrays.sort(numeros);
        
        System.out.println("Lista ordenada:");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
