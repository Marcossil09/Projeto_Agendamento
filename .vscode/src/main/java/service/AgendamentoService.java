package service;

import model.Atendimento;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoService {

    private FilaEspera filaEspera = new FilaEspera();    
    private List<Atendimento> atendimentos =
            new ArrayList<>();


    public boolean agendar(Atendimento novo) {


        if (atendimentos.size() >= 10) {

            System.out.println(
                    "Limite máximo de agendamentos atingido."
            );

            return false;
        }


        for (Atendimento atendimento : atendimentos) {

            boolean mesmaData =
                    atendimento.getData()
                            .equals(novo.getData());

            boolean mesmoHorario =
                    atendimento.getHora()
                            .equals(novo.getHora());

            boolean mesmoProfissional =
                    atendimento.getProfissional()
                            .getNome()
                            .equals(
                                    novo.getProfissional()
                                            .getNome()
                            );

            boolean mesmaSala =
                    atendimento.getSala()
                            .getNumero()

                    == novo.getSala()
                            .getNumero();


            if (
                    mesmaData
                            &&
                            mesmoHorario
                            &&
                            mesmoProfissional
            ) {

                System.out.println(
                        "Conflito: profissional já possui atendimento nesse horário."
                );

                return false;
            }


            if (
                    mesmaData
                            &&
                            mesmoHorario
                            &&
                            mesmaSala
            ) {

                System.out.println(
                        "Conflito: sala ocupada."
                );

                return false;
            }
        }

        atendimentos.add(novo);

        System.out.println(
                "Agendamento realizado com sucesso!"
        );

        return true;
    }

    public void listarAtendimentos() {

        if (atendimentos.isEmpty()) {

            System.out.println(
                    "Nenhum atendimento cadastrado."
            );

            return;
        }

        System.out.println(
                "\n===== ATENDIMENTOS ====="
        );

        for (Atendimento atendimento : atendimentos) {

            System.out.println(atendimento);
        }
    }


    public void cancelarAtendimento(int indice) {

        if (
                indice < 0
                        ||
                        indice >= atendimentos.size()
        ) {

            System.out.println(
                    "Índice inválido."
            );

            return;
        }

        Atendimento removido =
                atendimentos.remove(indice);

        System.out.println(
                "Atendimento cancelado:"
        );

        System.out.println(removido);
    }

    public int totalAtendimentos() {
        return atendimentos.size();
    }


    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }
   public int tamanhoFila() {
    return filaEspera.tamanho();
}
}