package org.example.Usuarios;

import java.util.ArrayList;
import org.example.Observer;
import java.util.Date;
import java.util.List;
import org.example.Usuarios.Doctores.DoctorHorarios;

import java.util.Random;
import java.util.stream.Collectors;
import java.io.*;

public class UsuarioService {

    private List<Usuario> usuarios;
    private static Random random = new Random();
    private List<Observer> observers = new ArrayList<>();
    private static Usuario doctorLogueado;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public Usuario buscarPorId(String id) {
        List<Usuario> usuarios = getUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario != null && String.valueOf(usuario.getId()).equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public static Usuario setUsuarioLogueado(Usuario usuario) {
        doctorLogueado = usuario;
        return usuario;
    }

    public static Usuario getUsuarioLogueado() {
        return doctorLogueado;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public List<Date> obtenerHorariosDoctorLogueado() {
        if (doctorLogueado != null && doctorLogueado.horarios != null) {
            return doctorLogueado.horarios.getHorarios();
        } else {
            System.out.println("No hay un doctor logueado o el doctor no tiene horarios.");
            return new ArrayList<>();
        }
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void editarUsuario(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == (usuarioActualizado.getId())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        usuarios.removeIf(u -> u.getId() == usuario.getId());
    }

    public void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> obtenerEspecialidadesDisponibles() {
        return usuarios.stream()
                .filter(usuario -> "Doctor".equals(usuario.getRol()))
                .map(Usuario::getEspecialidadDoctor)
                .distinct()
                .collect(Collectors.toList());
    }

    public void cargarUsuarios() {
        File file = new File("usuarios.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                usuarios = (List<Usuario>) ois.readObject();
                // Imprime los usuarios después de cargarlos
                System.out.println("Usuarios cargados: " + usuarios);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            usuarios = new ArrayList<>();
        }
    }

    public Usuario getDoctorLogueado() {
        return doctorLogueado;
    }

    public void asignarHorarioADoctorLogueado(Date horario) {
        if (doctorLogueado != null) {
            doctorLogueado.horarios.asignarHorario(horario);
        } else {
            System.out.println("No hay un doctor logueado.");
        }
    }

    public List<String> obtenerDoctoresPorEspecialidad(String especialidad) {
        return usuarios.stream()
                .filter(usuario -> "Doctor".equals(usuario.getRol())
                        && especialidad.equals(usuario.getEspecialidadDoctor()))
                .map(Usuario::getNombre)
                .collect(Collectors.toList());
    }

    public List<String> obtenerNombresDoctores() {
        List<String> nombresDoctores = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals("Doctor")) {
                nombresDoctores.add(usuario.getNombre());
            }
        }
        return nombresDoctores;
    }

    public List<Date> obtenerHorariosDoctor(String nombreDoctor) {
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals("Doctor") && usuario.getNombre().equals(nombreDoctor)) {
                System.out.println("Doctor encontrado: " + usuario.getNombre()); 
                List<Date> horarios = usuario.getHorarios();
                System.out.println("Horarios: " + horarios); 
                return horarios;
            }
        }
        System.out.println("Doctor no encontrado: " + nombreDoctor); 
        return new ArrayList<>(); 
    }
    public Usuario obtenerDoctorPorNombre(String nombreDoctor) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombreDoctor) && usuario.getRol().equals("Doctor")) {
                return usuario;
            }
        }
        return null;
    }

    public void guardarUsuario(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioActualizado.getId()) {
                usuarios.set(i, usuarioActualizado);
                return;
            }
        }
        System.out.println("Usuario no encontrado: " + usuarioActualizado.getNombre()); 
    }
    public UsuarioService() {

        usuarios = new ArrayList<>();
        Usuario admin = new Usuario(1, "Admin", "Admin", "1", 0, "", "ADMIN", "", "");
        usuarios.add(admin);
    }

    public static Usuario generarUsuario(String nombre, String apellidos, String password, int edad, String genero) {
        Usuario usuario = new Usuario();
        usuario.setId(generarId());
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setPassword(password);
        usuario.setEdad(edad);
        usuario.setGenero(genero);
        usuario.setRol("Usuario");
        return usuario;
    }

    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public List<Usuario> getUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("Id  " + usuario.getId() + "Nombre: " + usuario.getNombre() + ", Rol: "
                    + usuario.getRol() + ", Número: " + usuario.getNumeroDoctor() + ", Especialidad: "
                    + usuario.getEspecialidadDoctor());
        }
        return usuarios;
    }

    public static Usuario generarDoc(String nombre, String apellidos, String password, int edad, String genero,
            String especialidadDoctor, String numeroDoctor) {
        Usuario usuario = new Usuario();
        usuario.setId(generarId());
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setPassword(password);
        usuario.setEdad(edad);
        usuario.setGenero(genero);
        usuario.setRol("Doctor");
        usuario.setEspecialidadDoctor(especialidadDoctor);
        usuario.setNumeroDoctor(numeroDoctor);
        return usuario;
    }
    public List<Date> obtenerHorariosFiltrados(String especialidad, String nombreDoctor) {
        Usuario doctor = obtenerDoctorPorNombre(nombreDoctor);
        if (doctor != null && especialidad.equals(doctor.getEspecialidadDoctor())) {
            return doctor.getHorarios();
        }

        return new ArrayList<>();
    }

    public static int generarId() {
        return 1000 + random.nextInt(1001);
    }

}
