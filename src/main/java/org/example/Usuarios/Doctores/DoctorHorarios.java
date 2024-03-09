package org.example.Usuarios.Doctores;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;

public class DoctorHorarios implements Serializable {
    private List<Date> horarios;

    public DoctorHorarios() {
        this.horarios = new ArrayList<>();
    }

    public List<Date> getHorarios() {
        return horarios;
    }

    public void asignarHorario(Date horario) {
        this.horarios.add(horario);
    }
}