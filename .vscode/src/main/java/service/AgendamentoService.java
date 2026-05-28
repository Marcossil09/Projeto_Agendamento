package service;

import model.Atendimento;
import model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoService {

    // =========================================
    // ATRIBUTOS
    // =========================================

    private FilaEspera filaEspera =
            new FilaEspera();

    private List<Atendimento> atendimentos =
            new ArrayList<>();

    private static final int LIMITE_AGENDAMENTOS = 10;

    // =========================================
    // AGENDAR ATENDIMENTO
    // =========================================

    public boolean agendar(Atendimento novo) {

        // VALIDAÇÃO
        if (novo == null) {

            System.out.println(
                    "Atendimento inválido."
            );

            return false;
        }

        // LIMITE DO SISTEMA
        if (atendimentos.size()
                >= LIMITE_AGENDAMENTOS) {

            System.out.println(
                    "Limite máximo de agendamentos atingido."
            );

            adicionarPacienteFila(novo.getPaciente());

            return false;
        }

        // VERIFICA CONFLITOS
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
                            .equalsIgnoreCase(
                                    novo.getProfissional()
                                            .getNome()
                            );

            boolean mesmaSala =
                    atendimento.getSala()
                            .getNumero()

                            == novo.getSala()
                            .getNumero();

            // =====================================
            // CONFLITO PROFISSIONAL
            // =====================================

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

                adicionarPacienteFila(
                        novo.getPaciente()
                );

                return false;
            }

            // =====================================
            // CONFLITO SALA
            // =====================================

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

                adicionarPacienteFila(
                        novo.getPaciente()
                );

                return false;
            }
        }

        // SALVA AGENDAMENTO
        atendimentos.add(novo);

        System.out.println(
                "Agendamento realizado com sucesso!"
        );

        return true;
    }

    // =========================================
    // ADICIONAR NA FILA
    // =========================================

    private void adicionarPacienteFila(
            Paciente paciente
    ) {

        filaEspera.adicionar(paciente);

        System.out.println(
                "Paciente adicionado à fila de espera: "
                        + paciente.getNome()
        );
    }

    // =========================================
    // LISTAR ATENDIMENTOS
    // =========================================

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

        for (int i = 0;
             i < atendimentos.size();
             i++) {

            System.out.println(
                    "[" + i + "] "
                            + atendimentos.get(i)
            );
        }
    }

    // =========================================
    // CANCELAR ATENDIMENTO
    // =========================================

    public void cancelarAtendimento(
            int indice
    ) {

        // VALIDAÇÃO
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

        // REMOVE
        Atendimento removido =
                atendimentos.remove(indice);

        System.out.println(
                "\nAtendimento cancelado:"
        );

        System.out.println(removido);

        // =====================================
        // CHAMAR PRÓXIMO DA FILA
        // =====================================

        chamarProximoDaFila();
    }

    // =========================================
    // CHAMAR PRÓXIMO DA FILA
    // =========================================

    private void chamarProximoDaFila() {

        if (filaEspera.estaVazia()) {

            System.out.println(
                    "Nenhum paciente na fila."
            );

            return;
        }

        Paciente proximo =
                filaEspera.chamarProximo();

        System.out.println(
                "\nPaciente chamado da fila:"
        );

        System.out.println(
                proximo.getNome()
        );
    }

    // =========================================
    // EXIBIR FILA
    // =========================================

    public void exibirFila() {

        System.out.println(
                "\n===== FILA DE ESPERA ====="
        );

        filaEspera.mostrarFila();
    }

    // =========================================
    // TOTAL DE ATENDIMENTOS
    // =========================================

    public int totalAtendimentos() {

        return atendimentos.size();
    }

    // =========================================
    // TAMANHO FILA
    // =========================================

    public int tamanhoFila() {

        return filaEspera.tamanho();
    }

    // =========================================
    // GETTERS
    // =========================================

    public List<Atendimento> getAtendimentos() {

        return atendimentos;
    }

    public FilaEspera getFilaEspera() {

        return filaEspera;
    }
}