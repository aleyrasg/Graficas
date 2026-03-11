public class Hex2IP {
    
    public String hexToIP(String hex) {
        int[] octets = new int[4];
        for (int i = 0; i < 4; i++) {
            octets[i] = Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }
    
    public String ipToHex(String ip) {
        String[] octets = ip.split("\\.");
        StringBuilder hex = new StringBuilder();
        for (String octet : octets) {
            int value = Integer.parseInt(octet);
            hex.append(String.format("%02X", value));
        }
        return hex.toString();
    }
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Uso: java Hex2IP [-hex|-ip] [valor]");
            return;
        }
        
        Hex2IP converter = new Hex2IP();
        
        if (args[0].equals("-hex")) {
            System.out.println(converter.hexToIP(args[1]));
        } else if (args[0].equals("-ip")) {
            System.out.println(converter.ipToHex(args[1]));
        } else {
            System.out.println("Parámetro inválido. Use -hex o -ip");
        }
    }
}
