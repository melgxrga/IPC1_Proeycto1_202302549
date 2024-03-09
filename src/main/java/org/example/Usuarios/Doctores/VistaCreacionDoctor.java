package org.example.Usuarios.Doctores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.text.NumberFormatter;

import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;

public class VistaCreacionDoctor extends JFrame {
    private JTextField nombreField;
    private JTextField apellidoField;
    private JPasswordField passwordField;
    private JFormattedTextField edadField;
    private UsuarioService usuarioService;
    private JComboBox<String> generoBox;
    private JButton registrarButton;
    private JTextField telefonoField;
    private JTextField especialidadField;


    public VistaCreacionDoctor(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nombreLabel = new JLabel("Nombre");
        nombreField = new JTextField(10);
        JLabel apellidoLabel = new JLabel("Apellido");
        apellidoField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(10);
        JLabel edadLabel = new JLabel("Edad");
        edadField = new JFormattedTextField();
        edadField.setColumns(10);
        JLabel telefonoLabel = new JLabel("Especialidad Doctor");
        telefonoField = new JTextField(10);
        JLabel especialidadLabel = new JLabel("Telefono");
        especialidadField = new JTextField(10);
        
        JLabel generoLabel = new JLabel("Género");
        generoBox = new JComboBox<>();
        generoBox.addItem("Masculino");
        generoBox.addItem("Femenino");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nombreLabel, gbc);
        gbc.gridx = 1;
        panel.add(nombreField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(apellidoLabel, gbc);
        gbc.gridx = 1;
        panel.add(apellidoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(edadLabel, gbc);
        gbc.gridx = 1;
        panel.add(edadField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(generoLabel, gbc);
        gbc.gridx = 1;
        panel.add(generoBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(telefonoLabel, gbc);
        gbc.gridx = 1;
        panel.add(telefonoField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(especialidadLabel, gbc);
        gbc.gridx = 1;
        panel.add(especialidadField, gbc);

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
                    String nombre = nombreField.getText();
                    String apellidos = apellidoField.getText();
                    String password = new String(passwordField.getPassword());
                    String genero = (String) generoBox.getSelectedItem();
                    String numeroDoctor = telefonoField.getText();
                    String especialidad = especialidadField.getText();
        
                    if (nombre.isEmpty() || apellidos.isEmpty() || password.isEmpty() || genero.isEmpty()
                            || edadField.getText().isEmpty() || telefonoField.getText().isEmpty() 
                            || especialidadField.getText().isEmpty()){
                        throw new IllegalArgumentException("Todos los campos deben estar llenos");
                    }
        
                    int edad = Integer.parseInt(edadField.getText());
        
                    Usuario nuevoUsuario;
                    
                    if (VistaCreacionDoctor.this.usuarioService != null) {
                        nuevoUsuario = VistaCreacionDoctor.this.usuarioService.generarDoc(nombre, apellidos, password, edad, genero, numeroDoctor, especialidad);
                        VistaCreacionDoctor.this.usuarioService.addUsuario(nuevoUsuario);
                    } else {
                        nuevoUsuario = new Usuario();
                        nuevoUsuario.setId(UsuarioService.generarId());
                        nuevoUsuario.setNombre(nombre);
                        nuevoUsuario.setApellidos(apellidos);
                        nuevoUsuario.setPassword(password);
                        nuevoUsuario.setEdad(edad);
                        nuevoUsuario.setGenero(genero);
                        nuevoUsuario.setNumeroDoctor(numeroDoctor);
                        nuevoUsuario.setEspecialidadDoctor(especialidad);
                        nuevoUsuario.setRol("Doctor");

                    }
                    JOptionPane.showMessageDialog(null, "ID del nuevo usuario: " + nuevoUsuario.getId());
                    System.out.println(usuarioService.getUsuarios());
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La edad debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
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