package org.example.login;

import javax.swing.*;

import org.example.Productos.ProductosService;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
import org.example.Usuarios.Doctores.DoctorHorarios;
import org.example.Usuarios.Pacientes.vistaRegistro;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaLogin extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private LoginService loginService;

    public VistaLogin() {
        ProductosService productosService = ProductosService.getInstance();
        UsuarioService usuarioService = new UsuarioService();
        DoctorHorarios doctorLogueado = new DoctorHorarios();
        LoginService loginService = new LoginService(usuarioService, productosService, doctorLogueado);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel idLabel = new JLabel("ID");
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc); 

        idField = new JTextField(10);
        gbc.gridx = 1;
        add(idField, gbc);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc); 

        passwordField = new JPasswordField(10);
        gbc.gridx = 1;
        add(passwordField, gbc); 

    
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                if (id.isEmpty() || !id.matches("\\d+")) {
                    
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idInt = Integer.parseInt(id); 
                Usuario usuario = loginService.login(idInt, password);
                if (usuario != null) {
             
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("ID del usuario logueado: " + usuario.getId());
                } else {
               
                    JOptionPane.showMessageDialog(null, "Inicio de sesión fallido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(iniciarSesionButton, gbc); 
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new vistaRegistro(usuarioService); 
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; 
        add(registrarButton, gbc);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        pack();
        setResizable(false);
        setLocationRelativeTo(null); 
        setVisible(true); 
    }
}