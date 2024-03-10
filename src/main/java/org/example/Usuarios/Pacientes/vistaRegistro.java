package org.example.Usuarios.Pacientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

public class vistaRegistro extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JPasswordField passwordField;
    private JFormattedTextField edadField; 
    private UsuarioService usuarioService;
    private JComboBox<String> generoBox;
    private JButton registrarButton;

    public vistaRegistro(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        JLabel nombreLabel = new JLabel("Nombre");
        nombreField = new JTextField(10);
        JLabel apellidoLabel = new JLabel("Apellido");
        apellidoField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(10);
        JLabel edadLabel = new JLabel("Edad");
    
        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(true); 
        edadField = new JFormattedTextField(formatter);
        edadField.setColumns(10);

        edadField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JFormattedTextField) input).getText();
                return text.isEmpty() || text.matches("\\d+");
            }
        });
        JLabel generoLabel = new JLabel("Género");
        generoBox = new JComboBox<>(); 
        generoBox.addItem("Masculino");
        generoBox.addItem("Femenino");
        registrarButton = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(registrarButton, gbc);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = nombreField.getText();
                    String apellidos = apellidoField.getText();
                    String password = new String(passwordField.getPassword());
                    String genero = (String) generoBox.getSelectedItem();

                    if (nombre.isEmpty() || apellidos.isEmpty() || password.isEmpty() || genero.isEmpty()
                            || edadField.getText().isEmpty()) {
                        throw new IllegalArgumentException("Todos los campos deben estar llenos");
                    }

                    int edad = Integer.parseInt(edadField.getText());

                    Usuario nuevoUsuario = usuarioService.generarUsuario(nombre, apellidos, password, edad, genero);
                    usuarioService.addUsuario(nuevoUsuario);

                    JOptionPane.showMessageDialog(null, "ID del nuevo usuario: " + nuevoUsuario.getId());

                    System.out.println("Nombre: " + nuevoUsuario.getNombre());
                    System.out.println("Apellidos: " + nuevoUsuario.getApellidos());
                    System.out.println("Password: " + nuevoUsuario.getPassword());
                    System.out.println("Edad: " + nuevoUsuario.getEdad());
                    System.out.println("Genero: " + nuevoUsuario.getGenero());
                    System.out.println("rOL: " + nuevoUsuario.getRol());

                    // Cierra la vistaRegistro
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La edad debe ser un número", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nombreLabel, gbc);
        gbc.gridx = 1;
        add(nombreField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(apellidoLabel, gbc);
        gbc.gridx = 1;
        add(apellidoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(edadLabel, gbc);
        gbc.gridx = 1;
        add(edadField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(generoLabel, gbc);
        gbc.gridx = 1;
        add(generoBox, gbc);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}