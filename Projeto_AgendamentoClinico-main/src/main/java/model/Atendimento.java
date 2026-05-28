package model;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Atendimento {

    protected LocalDate data;
    protected LocalTime hora;
    protected Sala sala;
    protected Paciente paciente;
    protected Profissional profissional;

    public Atendimento(LocalDate data,
                       LocalTime hora,
                       Sala sala,
                       Paciente paciente,
                       Profissional profissional) {

        this.data = data;
        this.hora = hora;
        this.sala = sala;
        this.paciente = paciente;
        this.profissional = profissional;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Sala getSala() {
        return sala;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }
}
