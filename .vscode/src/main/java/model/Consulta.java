package model;

import interfaces.Agendavel;
import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta extends Atendimento implements Agendavel {

    public Consulta(LocalDate data,
                     LocalTime hora,
                     Sala sala,
                     Paciente paciente,
                     Profissional profissional) {

        super(data, hora, sala, paciente, profissional);
    }

    @Override
    public boolean verificarDisponibilidade() {
        return true;
    }

     @Override
public String toString() {

    return "Consulta" +
            " | Data: " + data +
            " | Hora: " + hora +
            " | Sala: " + sala +
            " | Paciente: " + paciente.getNome() +
            " | Profissional: " + profissional.getNome();

}
}