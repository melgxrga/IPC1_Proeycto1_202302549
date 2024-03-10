package org.example.Citas;

import org.example.Citas.Cita;
import java.util.*;
import java.io.Serializable;
import org.example.Usuarios.Usuario;
public class CitasService  {
    private static CitasService instancia;
    private List<Cita> citas;
    private Usuario usuario;

    private CitasService() {
        this.citas = new ArrayList<>();
    }

    public static CitasService getInstancia() {
        if (instancia == null) {
            instancia = new CitasService();
        }
        return instancia;
    }

    public List<Cita> getCita() {
        return citas;
    }

    public void agregarCita(Cita cita) {
        this.citas.add(cita);
        System.out.println("Cita agregada" +  cita);
        System.out.println("cita" + usuario.getNombre()+ usuario.getEspecialidadDoctor());
    }
}