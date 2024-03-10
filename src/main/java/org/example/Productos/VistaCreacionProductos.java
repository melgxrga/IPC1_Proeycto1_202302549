package org.example.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import org.example.Observer;
import org.example.Productos.*;
import org.example.Usuarios.UsuarioService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaCreacionProductos extends JFrame {
    
    private ProductosService productosService;
    
    private JTextField nombreProductoField;
    private JTextField precioProducto;
    private JTextField descripcionProducto;
    private JFormattedTextField cantidadProducto;
    private JComboBox<String> generoBox;
    private JButton registrarButton;

    public VistaCreacionProductos(ProductosService productosService){
        this.productosService = productosService;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nombreLabel = new JLabel("Nombre");
        nombreProductoField = new JTextField(10);
        JLabel precioLabel = new JLabel("Precio");
        precioProducto = new JTextField(10);
        JLabel descripcionLabel = new JLabel("Descripción");
        descripcionProducto = new JTextField(10);
        JLabel edadLabel = new JLabel("Cantidad producto");
        cantidadProducto = new JFormattedTextField();
        cantidadProducto.setColumns(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreProductoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(precioLabel, gbc);
        gbc.gridx = 1;
        panel.add(precioProducto, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(descripcionLabel, gbc);
        gbc.gridx = 1;
        panel.add(descripcionProducto, gbc);    
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(edadLabel, gbc);
        gbc.gridx = 1;
        panel.add(cantidadProducto, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(panel, BorderLayout.CENTER);
        registrarButton = new JButton("Registrar");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(registrarButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombreProductoStr = nombreProductoField.getText();
                    String precioProductoStr = precioProducto.getText();
                    String descripcionProductoStr = descripcionProducto.getText();
                    String cantidadProductoStr = cantidadProducto.getText();
        
                    if (nombreProductoStr.isEmpty() || precioProductoStr.isEmpty() 
                            || descripcionProductoStr.isEmpty() || cantidadProductoStr.isEmpty()){
                        throw new IllegalArgumentException("Todos los campos deben estar llenos");
                    }
        
                    int precioProductoInt = Integer.parseInt(precioProductoStr);
                    int cantidadProductoInt = Integer.parseInt(cantidadProductoStr);
        
                    Productos nuevoProducto;

                    if (VistaCreacionProductos.this.productosService != null) {
                        nuevoProducto = VistaCreacionProductos.this.productosService.generaProductos(nombreProductoStr, precioProductoInt, descripcionProductoStr, cantidadProductoInt);
                        VistaCreacionProductos.this.productosService.addProducto(nuevoProducto);
                        JOptionPane.showMessageDialog(null, "ID del nuevo producto: " + nuevoProducto.getId());
                        System.out.println(VistaCreacionProductos.this.productosService.getProductos());
                    } else {
                        ProductosService newProductosService = ProductosService.getInstance();

                        nuevoProducto = newProductosService.generaProductos(nombreProductoStr, precioProductoInt, descripcionProductoStr, cantidadProductoInt);
                        newProductosService.addProducto(nuevoProducto);
                        JOptionPane.showMessageDialog(null, "ID del nuevo producto: " + nuevoProducto.getId());
                        System.out.println(newProductosService.getProductos());
                    }
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El precio y la cantidad deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}