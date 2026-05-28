package model;

import interfaces.Agendavel;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {

    private Paciente paciente;
    private Profissional profissional;
    private Agendavel local;
    private LocalDate data;
    private LocalTime horario;
    private boolean ativo;

    public Agendamento(
            Paciente paciente,
            Profissional profissional,
            Agendavel local,
            LocalDate data,
            LocalTime horario
    ) {

        this.paciente = paciente;
        this.profissional = profissional;
        this.local = local;
        this.data = data;
        this.horario = horario;
        this.ativo = true;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Agendavel getLocal() {
        return local;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void cancelar() {
        this.ativo = false;
    }
}