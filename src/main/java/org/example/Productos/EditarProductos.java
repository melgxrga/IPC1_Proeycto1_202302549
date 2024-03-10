package org.example.Productos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.example.Productos.*;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
public class EditarProductos extends JFrame {
      private JTextField idField;
    private JTextField nombreField;
    private JTextField precioField;
    private JTextField descripcionField;
    private JFormattedTextField cantidadField;
    private UsuarioService usuarioService;
    private JComboBox<String> generoBox;
    private JButton editarButton;
    public EditarProductos(ProductosService productosService){
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JLabel idLabel = new JLabel("ID");
        idField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
    
        JLabel nombreLabel = new JLabel("Nombre Del prodcuto");
        nombreField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1; // incrementa el valor de gridy
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);
    
        JLabel precioProducto = new JLabel("Precio Producto");
        precioField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 2; 
        panel.add(precioProducto, gbc);
        gbc.gridx = 1;
        panel.add(precioField, gbc);
    
        JLabel descripcionLabel = new JLabel("Descripcion");
        descripcionField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 3; 
        panel.add(descripcionLabel, gbc);
        gbc.gridx = 1;
        panel.add(descripcionField, gbc);
    
        JLabel cantidadLabel = new JLabel("Cantidad");
        cantidadField = new JFormattedTextField();
        cantidadField.setColumns(10);
        gbc.gridx = 0;
        gbc.gridy = 4; 
        panel.add(cantidadLabel, gbc);
        gbc.gridx = 1;
        panel.add(cantidadField, gbc);
    
        add(panel, BorderLayout.CENTER);
    
        JPanel buttonPanel = new JPanel(); 
        JButton buscarButton = new JButton("Buscar");
        buttonPanel.add(buscarButton);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                Productos producto = productosService.buscarPorId(id);
                if (producto != null) {
                   
                    idField.setEditable(false);
                    nombreField.setText(producto.getNombreProducto());
                    precioField.setText(String.valueOf(producto.getPrecioProducto()));
                    descripcionField.setText(producto.getDescripcionProducto());
                    cantidadField.setText(String.valueOf(producto.getCantidadProducto()));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID proporcionado");
                }
            }
        });
        editarButton = new JButton("Editar");
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(editarButton);
    add(buttonPanel, BorderLayout.SOUTH);
    editarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            Productos producto = productosService.buscarPorId(id);
            if (producto != null) {
                producto.setNombreProducto(nombreField.getText());
                producto.setDescripcionProducto(descripcionField.getText());
                producto.setPrecioProducto(Integer.parseInt(precioField.getText()));
                producto.setCantidadProducto(Integer.parseInt(cantidadField.getText()));
                productosService.editarProducto(producto);
                JOptionPane.showMessageDialog(null, "Producto actualizado con éxito");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID proporcionado");
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



