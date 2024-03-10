package org.example.Usuarios.Pacientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

public class EliminarPaciente extends JFrame {
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

    public EliminarPaciente(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        buttonPanel = new JPanel();
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

        JLabel nombreLabel = new JLabel("Nombre:");
        JLabel apellidoLabel = new JLabel("Apellido:");
        JLabel passwordLabel = new JLabel("Contraseña:");
        JLabel edadLabel = new JLabel("Edad:");
        JLabel generoLabel = new JLabel("Género:");
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
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                }
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
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
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}