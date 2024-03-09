package org.example.Productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.Productos.*;
import org.example.Usuarios.UsuarioService;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EliminarProductos extends JFrame {
    private ProductosService productosService;
    private JTextField idField;
    private JTextField nombreProductoField;
    private JTextField precioField;
    private JTextField descripcionField;
    private JTextField cantidadField;
    private JPanel buttonPanel;

    public EliminarProductos(ProductosService productosService) {
        this.productosService = productosService;
        buttonPanel = new JPanel();

        // Crea los campos de texto y los deshabilita
        idField = new JTextField(20);
        nombreProductoField = new JTextField(20);
        nombreProductoField.setEditable(false);
        precioField = new JTextField(20);
        precioField.setEditable(false);
        descripcionField = new JPasswordField(20);
        descripcionField.setEditable(false);
        cantidadField = new JTextField(20);
        cantidadField.setEditable(false);

        JLabel nombreProductoLabel = new JLabel("Nombre:");
        JLabel precioLabel = new JLabel("Precio:");
        JLabel descripcionLabel = new JLabel("Descripcion:");
        JLabel cantidadLabel = new JLabel("Cantidad:");
        // Crea el botón de eliminar
        JButton eliminarButton = new JButton("Eliminar");
        buttonPanel.add(eliminarButton);

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                Productos producto = productosService.buscarPorId(id);
                if (producto != null) {
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este producto?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        productosService.eliminarProducto(producto);
                        JOptionPane.showMessageDialog(null, "Producto eliminado con éxito");
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID proporcionado");
                }
            }
        });

        JButton buscarButton = new JButton("Buscar");
        buttonPanel.add(buscarButton);
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                Productos producto = productosService.buscarPorId(id);
                if (producto != null) {
                    nombreProductoField.setText(producto.getNombreProducto());
                    precioField.setText(String.valueOf(producto.getPrecioProducto()));
                    descripcionField.setText(producto.getDescripcionProducto());
                    cantidadField.setText(String.valueOf(producto.getCantidadProducto()));
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un producto con el ID proporcionado");
                }
            }
        });
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(nombreProductoLabel);
        inputPanel.add(nombreProductoField);
        inputPanel.add(precioLabel);
        inputPanel.add(precioField);
        inputPanel.add(descripcionLabel);
        inputPanel.add(descripcionField);
        inputPanel.add(cantidadLabel);
        inputPanel.add(cantidadField);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
