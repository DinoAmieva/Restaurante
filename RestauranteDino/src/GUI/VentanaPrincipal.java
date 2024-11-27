package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import control.RestauranteControl;

public class VentanaPrincipal extends JFrame {
    private JPanel panelMenu;
    private JButton btnDesayuno, btnComida, btnCena, btnBebidas, btnPostres;
    private JTextArea areaTicket;
    private RestauranteControl control;
    
    // Definición de colores
    private final Color COLOR_FONDO = Color.decode("#1E8448");
    private final Color COLOR_BOTONES = Color.decode("#FFFFFF");
    private final Color COLOR_TEXTO = Color.decode("#FFFFFF");
    private final Font FUENTE_BOTONES = new Font("Arial", Font.BOLD, 14);
    private final Font FUENTE_TICKET = new Font("Monospaced", Font.PLAIN, 12);
    
    public VentanaPrincipal() {
        control = new RestauranteControl();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Woktai - Sistema de Pedidos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal con color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(COLOR_FONDO);
        
        // Panel del menú
        panelMenu = new JPanel(new GridLayout(5, 1, 15, 15));
        panelMenu.setBackground(COLOR_FONDO);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Configuración de botones
        btnDesayuno = crearBotonEstilizado("Desayunos");
        btnComida = crearBotonEstilizado("Comidas");
        btnCena = crearBotonEstilizado("Cenas");
        btnBebidas = crearBotonEstilizado("Bebidas");
        btnPostres = crearBotonEstilizado("Postres");
        
        panelMenu.add(btnDesayuno);
        panelMenu.add(btnComida);
        panelMenu.add(btnCena);
        panelMenu.add(btnBebidas);
        panelMenu.add(btnPostres);
        
        // Área del ticket con estilo
        areaTicket = new JTextArea();
        areaTicket.setEditable(false);
        areaTicket.setFont(FUENTE_TICKET);
        areaTicket.setBackground(Color.WHITE);
        areaTicket.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollTicket = new JScrollPane(areaTicket);
        scrollTicket.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        // Panel del ticket con título
        JPanel panelTicket = new JPanel(new BorderLayout());
        panelTicket.setBackground(COLOR_FONDO);
        
        JLabel tituloTicket = new JLabel("Ticket de Compra", SwingConstants.CENTER);
        tituloTicket.setFont(new Font("Arial", Font.BOLD, 16));
        tituloTicket.setForeground(COLOR_TEXTO);
        tituloTicket.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        panelTicket.add(tituloTicket, BorderLayout.NORTH);
        panelTicket.add(scrollTicket, BorderLayout.CENTER);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(panelMenu, BorderLayout.WEST);
        panelPrincipal.add(panelTicket, BorderLayout.CENTER);
        
        // Agregar panel principal al frame
        add(panelPrincipal);
        
        // Centrar la ventana en la pantalla
        setLocationRelativeTo(null);
        
        configurarEventos();
    }
    
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(COLOR_BOTONES);
        boton.setForeground(COLOR_FONDO);
        boton.setFont(FUENTE_BOTONES);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setOpaque(true);
        
        // Efectos hover
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_FONDO);
                boton.setForeground(COLOR_BOTONES);
            }
            
            public void mouseExited(MouseEvent e) {
                boton.setBackground(COLOR_BOTONES);
                boton.setForeground(COLOR_FONDO);
            }
        });
        
        return boton;
    }
    
    private void configurarEventos() {
        btnDesayuno.addActionListener(e -> mostrarMenu("DESAYUNOS"));
        btnComida.addActionListener(e -> mostrarMenu("COMIDAS"));
        btnCena.addActionListener(e -> mostrarMenu("CENAS"));
        btnBebidas.addActionListener(e -> mostrarMenu("BEBIDAS"));
        btnPostres.addActionListener(e -> mostrarMenu("POSTRES"));
    }
    
    private void mostrarMenu(String tipo) {
        String[] opciones = control.obtenerMenu(tipo);
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.setBackground(Color.WHITE);
        
        JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
        panel.add(new JLabel("Seleccione un platillo:"));
        panel.add(comboBox);
        
        int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            tipo,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            String seleccion = (String) comboBox.getSelectedItem();
            String cantidadStr = JOptionPane.showInputDialog(
                this,
                "Ingrese la cantidad deseada:",
                "Cantidad",
                JOptionPane.PLAIN_MESSAGE
            );
            
            try {
                int cantidad = Integer.parseInt(cantidadStr);
                if (cantidad > 0) {
                    control.agregarAlPedido(seleccion, cantidad);
                    actualizarTicket();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor ingrese un número válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void actualizarTicket() {
        areaTicket.setText(control.generarTicket());
    }
}