package control;

import java.util.*;

public class RestauranteControl {
    private Map<String, Double> precios;
    private Map<String, Integer> pedidoActual;
    
    public RestauranteControl() {
        precios = new HashMap<>();
        pedidoActual = new HashMap<>();
        inicializarPrecios();
    }
    
    private void inicializarPrecios() {
        // Desayunos
        precios.put("Salad Kiwy", 75.0);
        precios.put("Som Pat", 75.0);
        precios.put("Salad Sándery", 75.0);
        precios.put("Tom Youm Soup", 85.0);
        precios.put("Curry Shrimp Soup", 85.0);
        precios.put("Pho Soup", 85.0);
        
        // Comidas
        precios.put("Wok Krabi", 95.0);
        precios.put("Wok Tai", 95.0);
        precios.put("Wok Puket", 95.0);
        precios.put("Wok Bangkok", 95.0);
        precios.put("Wok Teriyaki", 95.0);
        precios.put("Wok Pat Tai", 95.0);
        
        // Cenas
        precios.put("California Roll", 95.0);
        precios.put("Spicy Dragon Roll", 77.0);
        precios.put("Philadelphia Maki", 80.0);
        precios.put("Flamed Salmon Roll", 155.0);
        precios.put("Tempura Avocado Roll", 180.0);
        
        // Bebidas
        precios.put("Té Oolong", 35.0);
        precios.put("Té Jazmín", 35.0);
        precios.put("Té de Granada", 35.0);
        precios.put("Café Americano", 35.0);
        precios.put("Espresso", 25.0);
        precios.put("Espresso cortado doble", 35.0);
        precios.put("Frappuccino", 59.0);
        precios.put("Cookies N´ Cream", 59.0);
        precios.put("Frappé Caramelo", 59.0);
        
        // Postres
        precios.put("Sorbete de Lychi", 79.0);
        precios.put("Sticky Rice", 69.0);
        precios.put("Panquecito de Elote", 99.0);
        precios.put("Waffles con Helado", 109.0);
    }
    
    public String[] obtenerMenu(String tipo) {
        List<String> items = new ArrayList<>();
        for (String item : precios.keySet()) {
            if (aplicaFiltro(item, tipo)) {
                items.add(String.format("%s - $%.2f", item, precios.get(item)));
            }
        }
        return items.toArray(new String[0]);
    }
    
    private boolean aplicaFiltro(String item, String tipo) {
        switch (tipo) {
            case "DESAYUNOS":
                return item.contains("Salad Kiwy") || item.contains("Som Pat") || item.contains("Salad Sándery")|| item.contains("Tom Youm Soup")|| item.contains("urry Shrimp Soup")|| item.contains("Salad Sándery");
            case "COMIDAS":
                return item.contains("Wok Krabi") || item.contains("Wok Tai") || item.contains("Wok Puket")|| item.contains("Wok Bangkok")|| item.contains("Wok Teriyaki")|| item.contains("Wok Pat Tai");
            case "CENAS":
                return item.contains("California Roll") || item.contains("Spicy Dragon Roll") || item.contains("Philadelphia Maki") || item.contains("PFlamed Salmon Roll") || item.contains("Tempura Avocado Roll");
            case "BEBIDAS":
                return item.contains("Té Oolong") || item.contains("Té Jazmín") || item.contains("Té de Granada")|| item.contains("Café Americano")|| item.contains("Té de Granadaé")|| item.contains("Espresso")|| item.contains("Espresso cortado doble")|| item.contains("Frapuccino")|| item.contains("Cookies N´ Cream")|| item.contains("Frappé Caramelo");
            case "POSTRES":
                return item.contains("Sorbete de Lychi") || item.contains("Sticky Rice") || item.contains("Panquecito de Elote") || item.contains("Waffles con Helado");
            default:
                return false;
        }
    }
    
    public void agregarAlPedido(String itemCompleto, int cantidad) {
        String nombreItem = itemCompleto.split(" - ")[0];
        pedidoActual.put(nombreItem, pedidoActual.getOrDefault(nombreItem, 0) + cantidad);
    }
    
    public String generarTicket() {
        StringBuilder ticket = new StringBuilder();
        
        // Encabezado
        ticket.append("================================\n");
        ticket.append("         RESTAURANTE WOKTAI        \n");
        ticket.append("     Av. Leona Vicario #342-352, Metepec EdoMex     \n");
        ticket.append("       Tel: (722) 719 8047        \n");
        ticket.append("================================\n\n");
        
        // Cuerpo del ticket
        ticket.append("ORDEN:\n");
        double total = 0;
        
        for (Map.Entry<String, Integer> item : pedidoActual.entrySet()) {
            double precioUnitario = precios.get(item.getKey());
            double subtotal = precioUnitario * item.getValue();
            total += subtotal;
            
            ticket.append(String.format("%-20s x%d\n", item.getKey(), item.getValue()));
            ticket.append(String.format("  $%.2f c/u   Subtotal: $%.2f\n", precioUnitario, subtotal));
        }
        
        ticket.append("\n--------------------------------\n");
        ticket.append(String.format("TOTAL: $%.2f\n", total));
        
        // Pie del ticket
        ticket.append("\n================================\n");
        ticket.append("     ¡Gracias por su compra!    \n");
        ticket.append("     ¡Vuelva pronto!    \n");
        ticket.append("================================\n");
        
        return ticket.toString();
    }
}
