package org.example.Usuarios.Pacientes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.example.Usuarios.Usuario;
import org.example.Usuarios.UsuarioService;
import org.example.Observer;
import org.example.Productos.Productos;
import org.example.Productos.ProductosService;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.stream.Collectors;

public class VistaUsuario extends JFrame implements Observer {
    private UsuarioService usuarioService;
    private ProductosService productoService;
    private JComboBox<String> especialidadesComboBox;
    private JComboBox<String> horarioComboBox;
    private List<Productos> productos;
    private JPanel cards;
    private Usuario usuarioLogueado;

    public VistaUsuario(UsuarioService usuarioService, ProductosService productoService) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.usuarioService.addObserver(this);

        setTitle("Vista Usuario");
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel solicitarCitaPanel = new JPanel(new GridBagLayout());

        JPanel verEstadoCitaPanel = new JPanel();
        JPanel farmaciaPanel = new JPanel();

        solicitarCitaPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        verEstadoCitaPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        farmaciaPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel motivoCitaLabel = new JLabel("Motivo de la cita:");
        JTextArea motivoCitaField = new JTextArea();
        motivoCitaField.setPreferredSize(new Dimension(100, 100));
        motivoCitaField.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        solicitarCitaPanel.add(motivoCitaLabel, c);

        c.gridy = 1;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        solicitarCitaPanel.add(new JScrollPane(motivoCitaField), c);

        JLabel especialidadesLabel = new JLabel("Especialidades:");
        especialidadesComboBox = new JComboBox<>(getEspecialidadesDisponibles().toArray(new String[0]));
        especialidadesComboBox.setPreferredSize(new Dimension(100, 20));

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 10);
        solicitarCitaPanel.add(especialidadesLabel, c);

        c.gridx = 1;
        solicitarCitaPanel.add(Box.createHorizontalStrut(15), c);

        c.gridx = 2;
        solicitarCitaPanel.add(especialidadesComboBox, c);

        // Agregar nuevo JLabel, JComboBox y JButton
        // Continuando desde donde lo dejaste...
        c.gridy = 3;
        c.gridx = 0;
        JLabel otroLabel = new JLabel("Doctor:");
        solicitarCitaPanel.add(otroLabel, c);

        c.gridx = 1;
        JComboBox<String> doctoresComboBox = new JComboBox<>();
        doctoresComboBox.setPreferredSize(new Dimension(100, 20));
        // Obtener la lista de nombres de doctores
        List<String> nombresDoctores = usuarioService.obtenerNombresDoctores();
        for (String nombreDoctor : nombresDoctores) {
            doctoresComboBox.addItem(nombreDoctor);
        }
        solicitarCitaPanel.add(doctoresComboBox, c);

        c.gridx = 2;
        JButton mostrarHorariosButton = new JButton("Mostrar Horarios");
        mostrarHorariosButton.addActionListener(e -> {
            String nombreDoctorSeleccionado = (String) doctoresComboBox.getSelectedItem();
            System.out.println("Doctor seleccionado: " + nombreDoctorSeleccionado); // Depuración
            if (nombreDoctorSeleccionado != null) {
                List<Date> horariosAsignados = usuarioService.obtenerHorariosDoctor(nombreDoctorSeleccionado);
                System.out.println("Horarios obtenidos: " + horariosAsignados); // Depuración
                if (horariosAsignados.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "No hay horarios asignados para el doctor seleccionado.");
                    doctoresComboBox.setSelectedIndex(-1); // Resetea el valor seleccionado
                } else {
                    String mensaje = horariosAsignados.stream()
                            .map(Date::toString)
                            .collect(Collectors.joining(", "));
                    JOptionPane.showMessageDialog(null, mensaje);
                }
            }
        });
        solicitarCitaPanel.add(mostrarHorariosButton, c);

        // Continuar con el código original
        JLabel horarioLabel = new JLabel("Seleccionar horario:");
        c.gridy = 4;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 10);
        solicitarCitaPanel.add(horarioLabel, c);

        horarioComboBox = new JComboBox<>();
        c.gridx = 1;
        c.insets = new Insets(0, 0, 0, 10);
        horarioComboBox.setPreferredSize(new Dimension(100, 20));
        solicitarCitaPanel.add(horarioComboBox, c);

        // Resto del código...
        // Crear un nuevo JPanel para la barra de título
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Crear un nuevo JButton para editar usuario
        JButton editarUsuarioButton = new JButton("Editar Usuario");
        editarUsuarioButton.addActionListener(e -> {
            this.usuarioLogueado = UsuarioService.getUsuarioLogueado();
            if (usuarioLogueado != null) {
                EditarPropioPerfil editarPropioPerfil = new EditarPropioPerfil(usuarioService);
                editarPropioPerfil.setNombreField(usuarioLogueado.getNombre());
                editarPropioPerfil.setApellidoField(usuarioLogueado.getApellidos());
                editarPropioPerfil.setPasswordField(usuarioLogueado.getPassword());
                editarPropioPerfil.setEdadField(usuarioLogueado.getEdad());
                editarPropioPerfil.setGeneroBox(usuarioLogueado.getGenero());
                editarPropioPerfil.setVisible(true);
            } else {
                // Manejar el caso en que no hay usuario logueado
            }
        });
        // Crear un nuevo JButton para cerrar la ventana
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> dispose());

        // Agregar los JButtons al JPanel
        titleBar.add(editarUsuarioButton);
        titleBar.add(closeButton);

        // Configurar el JPanel para que sea la barra de título
        setUndecorated(true);
        getContentPane().add(titleBar, BorderLayout.NORTH);

        // Agregar un MouseListener al JPanel para que puedas mover la ventana
        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    Point p = getLocation();
                    setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                origin = null;
            }
        };
        titleBar.addMouseListener(mouseAdapter);
        titleBar.addMouseMotionListener(mouseAdapter);
        titleBar.addMouseListener(mouseAdapter);
        titleBar.addMouseMotionListener(mouseAdapter);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 1.0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 0);
        solicitarCitaPanel.add(motivoCitaLabel, c);

        c.gridy = 1;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        solicitarCitaPanel.add(new JScrollPane(motivoCitaField), c);
        especialidadesComboBox.setPreferredSize(new Dimension(100, 20));
        JButton mostrarDoctoresButton = new JButton("Mostrar Doctores");
        mostrarDoctoresButton.setPreferredSize(new Dimension(300, 20));

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 10);
        solicitarCitaPanel.add(especialidadesLabel, c);

        c.gridx = 1;
        solicitarCitaPanel.add(Box.createHorizontalStrut(15), c);

        c.gridx = 2;
        solicitarCitaPanel.add(especialidadesComboBox, c);

        c.gridy = 3;
        c.gridx = 0;
        c.insets = new Insets(0, 0, 0, 0);
        solicitarCitaPanel.add(mostrarDoctoresButton, c);
        mostrarDoctoresButton.addActionListener(e -> {
            String especialidadSeleccionada = (String) especialidadesComboBox.getSelectedItem();
            if (especialidadSeleccionada != null) {
                List<String> nombresDoctoresEspecialidad = usuarioService
                        .obtenerDoctoresPorEspecialidad(especialidadSeleccionada);
                if (nombresDoctoresEspecialidad.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "No hay doctores disponibles para la especialidad seleccionada.");
                    especialidadesComboBox.setSelectedIndex(-1);
                } else {
                    String mensaje = String.join(", ", nombresDoctoresEspecialidad);
                    JOptionPane.showMessageDialog(null, mensaje);
                }
            }
        });
        c.gridy = 2;
        solicitarCitaPanel.add(especialidadesLabel, c);

        c.gridx = 1;
        solicitarCitaPanel.add(especialidadesComboBox, c);

        c.gridx = 2;
        solicitarCitaPanel.add(mostrarDoctoresButton, c);

        tabbedPane.addTab("Solicitar Cita", solicitarCitaPanel);
        tabbedPane.addTab("Ver Estado Cita", verEstadoCitaPanel);
        cards = new JPanel();
        cards.setLayout(new BoxLayout(cards, BoxLayout.Y_AXIS));

        tabbedPane.addTab("Farmacia", new JScrollPane(cards));
        add(tabbedPane);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 2) {
                    cards.removeAll();
                    List<Productos> productos = getProductos();
                    for (Productos producto : productos) {
                        JPanel card = new JPanel();
                        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                        card.setBorder(BorderFactory.createTitledBorder(producto.getNombreProducto()));
                        card.add(new JLabel("Descripción: " + producto.getDescripcionProducto()));
                        card.add(new JLabel("Cantidad: " + producto.getCantidadProducto()));
                        card.add(Box.createVerticalGlue());
                        card.add(new JLabel("Precio: " + "Q " + producto.getPrecioProducto()));
                        card.setPreferredSize(new Dimension(200, 200));
                        cards.add(card);
                    }
                    cards.setLayout(new FlowLayout(FlowLayout.LEFT));
                    cards.revalidate();
                    cards.repaint();
                }
            }
        });
      
        setPreferredSize(new Dimension(1000, 600));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        update();
    }

    @Override
    public void update() {
        List<String> especialidadesDisponibles = getEspecialidadesDisponibles();
        System.out.println("Actualizando especialidades: " + especialidadesDisponibles);

        especialidadesComboBox.removeAllItems();
        for (String especialidad : especialidadesDisponibles) {
            especialidadesComboBox.addItem(especialidad);
        }
        especialidadesComboBox.setSelectedIndex(-1); // Resetea el valor seleccionado
    }

    private List<Productos> getProductos() {

        if (productos == null) {
            productos = productoService.getProductos();
        }

        for (Productos producto : productos) {
            System.out.println(
                    "Id  " + producto.getId() +
                            "Nombre: " + producto.getNombreProducto() +
                            ", Precio: " + producto.getPrecioProducto() +
                            ", Descripcion: " + producto.getDescripcionProducto() +
                            ", Cantidad: " + producto.getCantidadProducto());
        }
        return productos;
    }

    private List<String> getEspecialidadesDisponibles() {
        return usuarioService.obtenerEspecialidadesDisponibles();
    }
}