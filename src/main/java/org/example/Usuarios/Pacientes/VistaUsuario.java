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
import org.example.Citas.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.stream.Collectors;

public class VistaUsuario extends JFrame implements Observer {
    private UsuarioService usuarioService;
    private ProductosService productoService;
    private CitasService citasService;
    private JComboBox<String> especialidadesComboBox;
    JComboBox<Date> horarioComboBox = new JComboBox<>();
    private List<Productos> productos;
    private JPanel cards;
    private Usuario usuarioLogueado;

    public VistaUsuario(UsuarioService usuarioService, ProductosService productoService, CitasService citasService) {
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.citasService = citasService;
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
        c.gridy = 3;
        c.gridx = 0;
        JLabel otroLabel = new JLabel("Doctor:");
        solicitarCitaPanel.add(otroLabel, c);

        c.gridx = 1;

        JComboBox<String> doctoresComboBox = new JComboBox<>();

        List<String> nombresDoctores = usuarioService.obtenerNombresDoctores();

        for (String nombreDoctor : nombresDoctores) {
            doctoresComboBox.addItem(nombreDoctor);
        }

        JPanel botonesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cBotones = new GridBagConstraints();
        JButton filtrarButton = new JButton("Filtrar");
        cBotones.gridx = 0;
        botonesPanel.add(filtrarButton, cBotones);

        filtrarButton.addActionListener(e -> {
            String especialidadSeleccionada = (String) especialidadesComboBox.getSelectedItem();
            String doctorSeleccionado = (String) doctoresComboBox.getSelectedItem();
            if (especialidadSeleccionada != null && doctorSeleccionado != null) {
                List<Date> horariosFiltrados = usuarioService.obtenerHorariosFiltrados(especialidadSeleccionada,
                        doctorSeleccionado);
                horarioComboBox.removeAllItems();
                for (Date horario : horariosFiltrados) {
                    horarioComboBox.addItem(horario);
                }
            }
        });
        JButton generarCitaButton = new JButton("Generar Cita");
        cBotones.gridx = 1;
        botonesPanel.add(generarCitaButton, cBotones);

        botonesPanel.add(generarCitaButton, cBotones);

        generarCitaButton.addActionListener(e -> {
            String nombreDoctor = (String) doctoresComboBox.getSelectedItem();
            Date horarioDoctor = (Date) horarioComboBox.getSelectedItem();
            String estado = "En espera";
            System.out.println("nombreDoctor: " + nombreDoctor);
            System.out.println("horarioDoctor: " + horarioDoctor);
            System.out.println("estado: " + estado);
            if (nombreDoctor == null || horarioDoctor == null) {
                throw new IllegalArgumentException("El nombre del doctor y el horario no pueden ser nulos");
            } else {
                if (citasService == null) {
                    this.citasService = CitasService.getInstancia();
                    System.out.println("citasService fue null, se creó una nueva instancia" + citasService);
                } else {
                    this.citasService = citasService;
                    System.out.println("citasService no fue null, se usó la instancia pasada");
                }
                if (nombreDoctor == null) {
                    throw new IllegalArgumentException("nombreDoctor no puede ser null");
                }
                if (horarioDoctor == null) {
                    throw new IllegalArgumentException("horarioDoctor no puede ser null");
                }
                if (estado == null) {
                    throw new IllegalArgumentException("estado no puede ser null");
                }

                Cita nuevaCita = new Cita(nombreDoctor, horarioDoctor, estado);
                System.out.println("Antes de agregarCita, citasService es: " + citasService);
                citasService.agregarCita(nuevaCita);
                JOptionPane.showMessageDialog(null, "Cita generada con éxito!");
            }
        });
        doctoresComboBox.addActionListener(e -> {
            String nombreDoctorSeleccionado = (String) doctoresComboBox.getSelectedItem();
            if (nombreDoctorSeleccionado != null) {
                Usuario doctorSeleccionado = usuarioService.obtenerDoctorPorNombre(nombreDoctorSeleccionado);
                if (doctorSeleccionado != null) {
                    List<Date> horariosDoctor = doctorSeleccionado.getHorarios();
                    horarioComboBox.removeAllItems();
                    for (Date horario : horariosDoctor) {
                        horarioComboBox.addItem(horario);
                    }
                }
            }
        });

        solicitarCitaPanel.add(doctoresComboBox, c);

        c.gridx = 2;
        JButton mostrarHorariosButton = new JButton("Mostrar Horarios");
        mostrarHorariosButton.addActionListener(e -> {
            String nombreDoctorSeleccionado = (String) doctoresComboBox.getSelectedItem();
            System.out.println("Doctor seleccionado: " + nombreDoctorSeleccionado);
            if (nombreDoctorSeleccionado != null) {
                Usuario doctorSeleccionado = usuarioService.obtenerDoctorPorNombre(nombreDoctorSeleccionado);
                if (doctorSeleccionado != null) {
                    List<Date> horariosAsignados = doctorSeleccionado.getHorarios();
                    System.out.println("Horarios obtenidos: " + horariosAsignados);
                    if (horariosAsignados.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay horarios asignados para el doctor seleccionado.");
                        doctoresComboBox.setSelectedIndex(-1);
                    } else {
                        String mensaje = horariosAsignados.stream()
                                .map(Date::toString)
                                .collect(Collectors.joining(", "));
                        JOptionPane.showMessageDialog(null, mensaje);
                    }
                } else {
                    System.out.println("Doctor no encontrado: " + nombreDoctorSeleccionado);
                }
            }
        });
        solicitarCitaPanel.add(mostrarHorariosButton, c);

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
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.add(botonesPanel, BorderLayout.SOUTH);
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
            }
        });


        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> dispose());
        titleBar.add(editarUsuarioButton);
        titleBar.add(closeButton);
        this.add(botonesPanel, BorderLayout.SOUTH);
        setUndecorated(true);
        getContentPane().add(titleBar, BorderLayout.NORTH);
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
        String[] columnNames = { "Número", "Estado", "Nombre del Doctor", "Especialidad" };
        Object[][] data = {
                { 1, "Pendiente", "Dr. Juan", "Cardiología" },
                { 2, "Rechazada", "Dr. Ana", "Neurología" },
                { 3, "Aceptada", "Dr. Pedro", "Pediatría" }
        };
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        verEstadoCitaPanel.add(scrollPane);
        verEstadoCitaPanel.validate();
        verEstadoCitaPanel.repaint();
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
        especialidadesComboBox.setSelectedIndex(-1);
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