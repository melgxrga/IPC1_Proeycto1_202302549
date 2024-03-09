package org.example.Usuarios;

import java.util.Random;

import javax.swing.JOptionPane;

import org.example.Usuarios.Doctores.DoctorHorarios;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Usuario implements Serializable  {
    private int id;
    private String nombre;
    private String apellidos;
    private String password;
    private int edad;
    private String genero;
    private String rol;
    private String especialidadDoctor;
    private String numeroDoctor;
    public DoctorHorarios horarios = new DoctorHorarios();
    public Usuario() {
    
    }
    @Override
     public String toString() {
        return "Usuario{" +
            "id=" + id +
            ", nombre='" + nombre + '\'' +
            ", apellidos='" + apellidos + '\'' +
            ", password='" + password + '\'' +
            ", edad=" + edad +
            ", genero='" + genero + '\'' +
            '}';
    }
    public Usuario(int id, String nombre, String apellidos, String password, int edad, String genero, String rol,
            String especialidadDoctor, String numeroDoctor) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.edad = edad;
        this.genero = genero;
        this.rol = rol;
        this.especialidadDoctor = especialidadDoctor;
        this.numeroDoctor = numeroDoctor;
    }
    public List<Date> getHorarios() {
        return horarios.getHorarios();
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getPassword() {
        return password;
    }

    public int getEdad() {
        return edad;
    }

    public String getGenero() {
        return genero;
    }

    public String getRol() {
        return rol;
    }

    public String getEspecialidadDoctor() {
        return especialidadDoctor;
    }

    public String getNumeroDoctor() {
        return numeroDoctor;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setEspecialidadDoctor(String especialidadDoctor) {
        this.especialidadDoctor = especialidadDoctor;
    }

    public void setNumeroDoctor(String numeroDoctor) {
        this.numeroDoctor = numeroDoctor;
    }

}
