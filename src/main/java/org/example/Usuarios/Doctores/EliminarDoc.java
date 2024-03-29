package org.example.Usuarios.Doctores;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


    public class EliminarDoc extends JFrame {
        private UsuarioService usuarioService;
        private JTextField idField;
        private JTextField nombreField;
        private JTextField apellidoField;
        private JPasswordField passwordField;
        private JTextField edadField;
        private JComboBox generoBox;
        private JTextField telefonoField;
        private JTextField especialidadField;
        private JPanel buttonPanel;
    
        public EliminarDoc(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
    
            // Crea el panel de botones
            buttonPanel = new JPanel();
    
            // Crea los campos de texto y los deshabilita
            idField = new JTextField(20);
            nombreField = new JTextField(20);
            nombreField.setEditable(false);
            apellidoField = new JTextField(20);
            apellidoField.setEditable(false);
            passwordField = new JPasswordField(20);
            passwordField.setEditable(false);
            edadField = new JTextField(20);
            edadField.setEditable(false);
            generoBox = new JComboBox(new String[] {"Masculino", "Femenino"});
            generoBox.setEnabled(false);
            telefonoField = new JTextField(20);
            telefonoField.setEditable(false);
            especialidadField = new JTextField(20);
            especialidadField.setEditable(false);
    
            // Crea los labels
            JLabel nombreLabel = new JLabel("Nombre:");
            JLabel apellidoLabel = new JLabel("Apellido:");
            JLabel passwordLabel = new JLabel("Contraseña:");
            JLabel edadLabel = new JLabel("Edad:");
            JLabel generoLabel = new JLabel("Género:");
            JLabel telefonoLabel = new JLabel("Teléfono:");
            JLabel especialidadLabel = new JLabel("Especialidad:");
    
            // Crea el botón de eliminar
            JButton eliminarButton = new JButton("Eliminar");
            buttonPanel.add(eliminarButton);
    
            eliminarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = idField.getText();
                    Usuario usuario = usuarioService.buscarPorId(id);
                    if (usuario != null) {
                        int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este doctor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            usuarioService.eliminarUsuario(usuario);
                            JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito");
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                    }
                }
            });
            JButton buscarButton = new JButton("Buscar");
            buttonPanel.add(buscarButton);
            buscarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = idField.getText();
                    Usuario usuario = usuarioService.buscarPorId(id);
                    if (usuario != null) {
                        nombreField.setText(usuario.getNombre());
                        apellidoField.setText(usuario.getApellidos());
                        passwordField.setText(usuario.getPassword());
                        edadField.setText(String.valueOf(usuario.getEdad()));
                        generoBox.setSelectedItem(usuario.getGenero());
                        telefonoField.setText(usuario.getNumeroDoctor());
                        especialidadField.setText(usuario.getEspecialidadDoctor());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                    }
                }
            });
         // Crea un panel para los campos de entrada y los labels
    JPanel inputPanel = new JPanel(new GridLayout(8, 2));

    // Añade los labels y los campos de entrada al panel
    inputPanel.add(new JLabel("ID:"));
    inputPanel.add(idField);
    inputPanel.add(nombreLabel);
    inputPanel.add(nombreField);
    inputPanel.add(apellidoLabel);
    inputPanel.add(apellidoField);
    inputPanel.add(passwordLabel);
    inputPanel.add(passwordField);
    inputPanel.add(edadLabel);
    inputPanel.add(edadField);
    inputPanel.add(generoLabel);
    inputPanel.add(generoBox);
    inputPanel.add(telefonoLabel);
    inputPanel.add(telefonoField);
    inputPanel.add(especialidadLabel);
    inputPanel.add(especialidadField);

    // Añade los paneles al marco
    add(inputPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // Configura el marco
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
        }
    
