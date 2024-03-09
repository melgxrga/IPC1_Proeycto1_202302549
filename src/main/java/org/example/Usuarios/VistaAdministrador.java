package org.example.Usuarios;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.Collectors;
import java.util.List;
import org.example.Observer;
import org.example.Productos.*;
import org.example.Usuarios.Doctores.CrudDoctorVista;
import org.example.Usuarios.Doctores.VistaCreacionDoctor;
import org.example.Usuarios.Pacientes.CrudPacientesVista;

public class VistaAdministrador extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private UsuarioService usuarioService;
    private ProductosService productosService;

    public class TableObserver implements Observer {
        @Override
        public void update() {
            updateTable(getData("Doctor"));
        }
    }

    public VistaAdministrador(UsuarioService usuarioService, ProductosService productosService) {
        this.usuarioService = usuarioService;
        this.productosService = productosService;
        setLayout(new BorderLayout());
        setBounds(50, 50, 800, 600);
        String[] columnNames = { "ID", "Nombre", "Apellidos", "Edad", "Genero", "Telefono", "Especialidad" };
        Object[][] data = {};
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50)); // Añade un margen de 50 por cada lado
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(e -> {
            VistaCreacionDoctor vistaCreacionDoctor = new VistaCreacionDoctor(usuarioService);
            vistaCreacionDoctor.setVisible(true);
        });

        // Crea el panel de botones superior
        JPanel topButtonPanel = new JPanel(new FlowLayout());
        JButton doctoresButton = new JButton("Doctores");
        doctoresButton.addActionListener(e -> {
            updateTable(getData("Doctor"));

            CrudDoctorVista crudDoctorVista = new CrudDoctorVista(usuarioService);
            crudDoctorVista.setVisible(true);
        });
        JButton productosButton = new JButton("Productos");
        productosButton.addActionListener(e -> {
            // Crea una variable final que haga referencia a productosService
            final ProductosService finalProductosService;
        
            // Verifica si productosService es null y, de ser así, lo inicializa
            if (productosService == null) {
                finalProductosService = ProductosService.getInstance();
            } else {
                finalProductosService = productosService;
            }
        
            // Ahora puedes llamar a getProductos() sin obtener un NullPointerException
            List<Productos> productos = finalProductosService.getProductos();
            CrudProductos crudProductos = new CrudProductos(finalProductosService); // Pasa finalProductosService en lugar de productosService
            crudProductos.setVisible(true);
        
            // Actualiza la tabla con los datos de los productos
            updateTableProductos();
        });
        add(productosButton);
        JButton pacientesButton = new JButton("Pacientes");
        pacientesButton.addActionListener(e -> {
            updateTable(getData("Usuario"));
            updateTable("Usuario");
            CrudPacientesVista crudPacientesVista = new CrudPacientesVista(usuarioService);
            crudPacientesVista.setVisible(true);
        });
        topButtonPanel.add(doctoresButton);
        topButtonPanel.add(productosButton);
        topButtonPanel.add(pacientesButton);

        // Añade la tabla y los paneles de botones al JFrame
        add(topButtonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        setResizable(false);

        // Centra el JFrame en la pantalla
        setLocationRelativeTo(null);
    }

    private void updateTable(Object[][] data) {
        tableModel.setDataVector(data,
                new String[] { "ID", "Nombre", "Apellidos", "Edad", "Genero", "Telefono", "Especialidad" });
    }

    private void updateTable(String rol) {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        List<Usuario> usuariosRol = usuarios.stream()
                .filter(usuario -> rol.equals(usuario.getRol()))
                .collect(Collectors.toList());

        tableModel.setRowCount(0); // Limpia la tabla

        // Define las columnas en función del rol
        if ("Usuario".equals(rol)) {
            tableModel.setColumnIdentifiers(new Object[] { "ID", "Nombre", "Apellidos", "Edad", "Genero" });
        } else if ("Doctor".equals(rol)) {
            tableModel.setColumnIdentifiers(new Object[] { "ID", "Nombre", "Apellidos", "Teléfono", "Especialidad" });
        } else {
            // maneja la excepción
        }

        // Añade los usuarios a la tabla
        if ("Usuario".equals(rol)) {
            for (Usuario usuario : usuariosRol) {
                tableModel.addRow(new Object[] { usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
                        usuario.getEdad(), usuario.getGenero() });
            }
        } else if ("Doctor".equals(rol)) {
            for (Usuario usuario : usuariosRol) {
                tableModel.addRow(new Object[] { usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
                        usuario.getNumeroDoctor(), usuario.getEspecialidadDoctor() /* , ... otros campos ... */ });
            }
        } 
        }
        private void updateTableProductos() {
            // Crea una variable final que haga referencia a productosService si no es null,
            // o a ProductosService.getInstance() si es null
            final ProductosService finalProductosService = (productosService != null) ? productosService : ProductosService.getInstance();
        
            // Ahora puedes llamar a getProductos() sin obtener un NullPointerException
            List<Productos> productos = finalProductosService.getProductos();
        
            tableModel.setRowCount(0); // Limpia la tabla
        
            // Define las columnas para los productos
            tableModel.setColumnIdentifiers(new Object[] { "ID", "Nombre", "Precio", "Descripción", "Cantidad" });
        
            for (Productos producto : productos) {
                tableModel.addRow(new Object[] { producto.getId(), producto.getNombreProducto(), producto.getPrecioProducto(),
                        producto.getDescripcionProducto(), producto.getCantidadProducto() /* , ... otros campos ... */ });
            }
        }
    private Object[][] getData(String rol) {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        System.out.println("Todos los usuarios: " + usuarios);

        List<Usuario> filteredUsuarios = usuarios.stream()
                .filter(usuario -> usuario.getRol().equals(rol))
                .collect(Collectors.toList());

        // Imprime la lista de usuarios filtrados
        System.out.println("Usuarios filtrados: " + filteredUsuarios);

        Object[][] data = new Object[filteredUsuarios.size()][7];
        for (int i = 0; i < filteredUsuarios.size(); i++) {
            Usuario usuario = filteredUsuarios.get(i);
            data[i][0] = usuario.getId();
            data[i][1] = usuario.getNombre();
            data[i][2] = usuario.getApellidos();
            data[i][3] = usuario.getEdad();
            data[i][4] = usuario.getGenero();
            data[i][5] = usuario.getNumeroDoctor();
            data[i][6] = usuario.getEspecialidadDoctor();
        }

        return data;

    }

}
