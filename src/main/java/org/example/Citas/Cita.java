package org.example.Citas;
import java.util.*;

public class Cita {
    private int numeroCita;
    private String nombreDoctor;
    private Date horarioDoctor;
    private String estado;

    public Cita(String nombreDoctor, Date horarioDoctor, String estado) {
        this.nombreDoctor = nombreDoctor;
        this.horarioDoctor = horarioDoctor;
        this.estado = estado;
    }

    public int getNumeroCita() {
        return numeroCita;
    }

    public void setNumeroCita(int numeroCita) {
        this.numeroCita = numeroCita;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public Date getHorarioDoctor() {
        return horarioDoctor;
    }

    public void setHorarioDoctor(Date horarioDoctor) {
        this.horarioDoctor = horarioDoctor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}