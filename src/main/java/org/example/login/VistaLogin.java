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
        gbc.insets = new Insets(10, 10, 10, 10); // Agrega un espacio de 10 píxeles alrededor de cada componente

        // Crea los JLabels y JTextFields para ID y contraseña
        JLabel idLabel = new JLabel("ID");
        idLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(idLabel, gbc); // Añade el JLabel para ID en la columna izquierda

        idField = new JTextField(10);
        gbc.gridx = 1;
        add(idField, gbc); // Añade el JTextField para ID en la columna derecha

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc); // Añade el JLabel para contraseña en la columna izquierda

        passwordField = new JPasswordField(10);
        gbc.gridx = 1;
        add(passwordField, gbc); // Añade el JPasswordField para contraseña en la columna derecha

        // Añade el botón de iniciar sesión en la penúltima fila
        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                if (id.isEmpty() || !id.matches("\\d+")) {
                    // El ID está vacío o contiene caracteres no numéricos
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idInt = Integer.parseInt(id); // Convierte el ID a int
                Usuario usuario = loginService.login(idInt, password);
                if (usuario != null) {
                    // El login fue exitoso
                    JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("ID del usuario logueado: " + usuario.getId());
                } else {
                    // El login falló
                    JOptionPane.showMessageDialog(null, "Inicio de sesión fallido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Hace que el botón de iniciar sesión ocupe 2 columnas
        add(iniciarSesionButton, gbc); // Añade el botón de iniciar sesión

        // Añade el botón de registrar en la última fila
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new vistaRegistro(usuarioService); // Abre la ventana de registro
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Hace que el botón de registrar ocupe 2 columnas
        add(registrarButton, gbc); // Añade el botón de registrar

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura la operación de cierre por defecto
        pack();
        setResizable(false);
        setLocationRelativeTo(null); // Ajusta el tamaño del JFrame para que se ajuste a sus componentes
        setVisible(true); // Hace visible el JFrame
    }
}