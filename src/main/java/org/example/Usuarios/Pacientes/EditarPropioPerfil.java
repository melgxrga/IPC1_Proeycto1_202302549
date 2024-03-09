package org.example.Usuarios.Pacientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditarPropioPerfil extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JPasswordField passwordField;
    private JFormattedTextField edadField;
    private UsuarioService usuarioService;
    private JComboBox<String> generoBox;
    private JButton editarButton;
    private Usuario usuarioLogueado;

    public EditarPropioPerfil(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.usuarioLogueado = UsuarioService.getUsuarioLogueado();
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel nombreLabel = new JLabel("Nombre");
        nombreField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);
        JLabel apellidoLabel = new JLabel("Apellido");
        apellidoField = new JTextField(10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apellidoField, gbc);
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        JLabel edadLabel = new JLabel("Edad");
        edadField = new JFormattedTextField();
        edadField.setColumns(10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(edadLabel, gbc);
        gbc.gridx = 1;
        panel.add(edadField, gbc);
        JLabel generoLabel = new JLabel("Género");
        generoBox = new JComboBox<>();
        generoBox.addItem("Masculino");
        generoBox.addItem("Femenino");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(generoLabel, gbc);
        gbc.gridx = 1;
        panel.add(generoBox, gbc);
        JPanel buttonPanel = new JPanel();
        editarButton = new JButton("Editar");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editarButton);
        add(buttonPanel, BorderLayout.SOUTH);
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuarioLogueado != null) {
                    usuarioLogueado.setNombre(nombreField.getText());
                    usuarioLogueado.setApellidos(apellidoField.getText());
                    usuarioLogueado.setPassword(new String(passwordField.getPassword()));
                    usuarioLogueado.setEdad(Integer.parseInt(edadField.getText()));
                    usuarioLogueado.setGenero((String) generoBox.getSelectedItem());
                    usuarioService.editarUsuario(usuarioLogueado);
                    JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito");
                    System.out.println("Usuario actualizado: " + usuarioLogueado); 
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID proporcionado");
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        this.getContentPane().add(panel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void setNombreField(String nombre) {
        this.nombreField.setText(nombre);
    }

    public void setApellidoField(String apellido) {
        this.apellidoField.setText(apellido);
    }

    public void setPasswordField(String password) {
        this.passwordField.setText(password);
    }

    public void setEdadField(int edad) {
        this.edadField.setValue(edad);
    }

    public void setGeneroBox(String genero) {
        this.generoBox.setSelectedItem(genero);
    }
}
