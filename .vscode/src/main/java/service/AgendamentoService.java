package service;

import model.Atendimento;
import model.Consulta;
import model.Paciente;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoService {

    // =========================================
    // ATRIBUTOS
    // =========================================

    private FilaEspera filaEspera = new FilaEspera();
    private List<Atendimento> atendimentos = new ArrayList<>();

    private static final int LIMITE_AGENDAMENTOS = 10;

    // =========================================
    // AGENDAR ATENDIMENTO
    // =========================================

    public boolean agendar(Atendimento novo) {

        if (novo == null) {
            System.out.println("Atendimento inválido.");
            return false;
        }

        if (atendimentos.size() >= LIMITE_AGENDAMENTOS) {

            System.out.println("Limite máximo de agendamentos atingido.");

            adicionarPacienteFila(novo.getPaciente());
            return false;
        }

        for (Atendimento atendimento : atendimentos) {

            boolean mesmaData = atendimento.getData().equals(novo.getData());
            boolean mesmoHorario = atendimento.getHora().equals(novo.getHora());

            boolean mesmoProfissional = atendimento.getProfissional()
                    .getNome()
                    .equalsIgnoreCase(novo.getProfissional().getNome());

            boolean mesmaSala = atendimento.getSala().getNumero()
                    == novo.getSala().getNumero();

            if (mesmaData && mesmoHorario && mesmoProfissional) {
                System.out.println("Conflito: profissional já possui atendimento nesse horário.");
                adicionarPacienteFila(novo.getPaciente());
                return false;
            }

            if (mesmaData && mesmoHorario && mesmaSala) {
                System.out.println("Conflito: sala ocupada.");
                adicionarPacienteFila(novo.getPaciente());
                return false;
            }
        }

        atendimentos.add(novo);

        System.out.println("Agendamento realizado com sucesso!");
        return true;
    }

    // =========================================
    // ADICIONAR NA FILA
    // =========================================

    private void adicionarPacienteFila(Paciente paciente) {

        if (paciente == null) return;

        filaEspera.adicionar(paciente);

        System.out.println(
                "Paciente adicionado à fila de espera: "
                        + paciente.getNome()
        );
    }

    // =========================================
    // CANCELAR / FINALIZAR ATENDIMENTO
    // =========================================

    public void cancelarAtendimento(int indice) {

        if (indice < 0 || indice >= atendimentos.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        Atendimento removido = atendimentos.remove(indice);

        System.out.println("\nAtendimento finalizado:");
        System.out.println(removido);

        // CHAMA PRÓXIMO DA FILA E SUBSTITUI
        Paciente proximo = filaEspera.chamarProximo();

        if (proximo == null) {
            System.out.println("Nenhum paciente na fila.");
            return;
        }

       Atendimento novo = new Consulta(
        removido.getData(),
        removido.getHora(),
        removido.getSala(),
        proximo,
        removido.getProfissional()
);

        atendimentos.add(novo);

        System.out.println(
                "Paciente da fila entrou no atendimento: "
                        + proximo.getNome()
        );
    }

    // =========================================
    // LISTAR ATENDIMENTOS
    // =========================================

    public void listarAtendimentos() {

        if (atendimentos.isEmpty()) {
            System.out.println("Nenhum atendimento cadastrado.");
            return;
        }

        System.out.println("\n===== ATENDIMENTOS =====");

        for (int i = 0; i < atendimentos.size(); i++) {
            System.out.println("[" + i + "] " + atendimentos.get(i));
        }
    }

    // =========================================
    // FILA
    // =========================================

    public void exibirFila() {

        System.out.println("\n===== FILA DE ESPERA =====");
        filaEspera.mostrarFila();
    }

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

    public int totalAtendimentos() {
        return atendimentos.size();
    }
}