public class Subcadenas {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No se proporcionó una cadena");
            return;
        }
        
        String texto = args[0];
        int longitud = texto.length();
        
        for (int i = longitud; i > 0; i--) {
            System.out.println("\"" + texto.substring(0, i) + "\"");
        }
        
        for (int i = 1; i <= longitud; i++) {
            System.out.println("\"" + texto.substring(longitud - i, longitud) + "\"");
        }
    }
}
