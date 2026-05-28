package service;

import model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RelatorioService {

    private AgendamentoService agendamentoService;
    private FaturamentoService faturamentoService;

    public RelatorioService(AgendamentoService agendamentoService,
                             FaturamentoService faturamentoService) {

        this.agendamentoService = agendamentoService;
        this.faturamentoService = faturamentoService;
    }

    // =============================================
    // LISTAR POR PROFISSIONAL
    // =============================================
    public void listarPorProfissional(String nomeProfissional) {

        System.out.println("\n===== ATENDIMENTOS - " + nomeProfissional.toUpperCase() + " =====");

        List<Atendimento> resultado = agendamentoService.getAtendimentos()
                .stream()
                .filter(a -> a.getProfissional().getNome()
                        .equalsIgnoreCase(nomeProfissional))
                .collect(Collectors.toList());

        if (resultado.isEmpty()) {
            System.out.println("Nenhum atendimento encontrado.");
            return;
        }

        resultado.forEach(System.out::println);
    }

    // =============================================
    // LISTAR POR PERIODO
    // =============================================
    public void listarPorPeriodo(LocalDate inicio, LocalDate fim) {

        System.out.println("\n===== ATENDIMENTOS DE " + inicio + " ATÉ " + fim + " =====");

        List<Atendimento> resultado = agendamentoService.getAtendimentos()
                .stream()
                .filter(a -> !a.getData().isBefore(inicio) && !a.getData().isAfter(fim))
                .collect(Collectors.toList());

        if (resultado.isEmpty()) {
            System.out.println("Nenhum atendimento encontrado.");
            return;
        }

        resultado.forEach(System.out::println);
    }

    // =============================================
    // RELATORIO MENSAL
    // =============================================
    public void relatorioMensal(int mes, int ano) {

        System.out.println("\n===== RELATÓRIO MENSAL - " + mes + "/" + ano + " =====");

        List<Atendimento> doMes = agendamentoService.getAtendimentos()
                .stream()
                .filter(a -> a.getData().getMonthValue() == mes
                        && a.getData().getYear() == ano)
                .collect(Collectors.toList());

        System.out.println("Total de atendimentos: " + doMes.size());

        // Receita total dos recibos do mesmo mes
        double receitaTotal = faturamentoService.getRecibos()
                .stream()
                .filter(r -> r.getData().getMonthValue() == mes
                        && r.getData().getYear() == ano)
                .mapToDouble(Recibo::getTotalCobrado)
                .sum();

        System.out.println("Receita total:         R$ " + String.format("%.2f", receitaTotal));

        // Profissionais mais demandados
        System.out.println("\n--- Atendimentos por profissional ---");

        Map<String, Long> porProfissional = doMes.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getProfissional().getNome(),
                        Collectors.counting()
                ));

        porProfissional.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        // Taxa de ocupacao
        int capacidadeMaxima = 10;
        double taxaOcupacao = (doMes.size() * 100.0) / capacidadeMaxima;

        System.out.println("\nTaxa de ocupação:      " +
                String.format("%.1f", taxaOcupacao) + "%");

        System.out.println("Horários ociosos:      " +
                (capacidadeMaxima - doMes.size()));
    }

    // =============================================
    // INDICADORES
    // =============================================
    public void exibirIndicadores() {

        System.out.println("\n===== INDICADORES =====");

        List<Atendimento> todos = agendamentoService.getAtendimentos();
        List<Recibo> recibos = faturamentoService.getRecibos();

        System.out.println("Total de atendimentos: " + todos.size());
        System.out.println("Total de recibos:      " + recibos.size());

        // Cancelamentos (taxa > 0 indica cancelamento com taxa)
        long cancelamentos = recibos.stream()
        .filter(Recibo::isCancelado)
        .count();

        System.out.println("Recibos emitidos:      " + recibos.size());
        System.out.println("Cancelamentos:         " + cancelamentos);

        // Retornos
        long retornos = recibos.stream()
                .filter(r -> r.getTipoAtendimento() == TipoAtendimento.RETORNO)
                .count();

        System.out.println("Retornos:              " + retornos);

        // Profissional mais demandado
        if (!todos.isEmpty()) {

            String maisDemandado = todos.stream()
                    .collect(Collectors.groupingBy(
                            a -> a.getProfissional().getNome(),
                            Collectors.counting()
                    ))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");

            System.out.println("Mais demandado:        " + maisDemandado);
        }

        // Receita total geral
        double receitaGeral = recibos.stream()
                .mapToDouble(Recibo::getTotalCobrado)
                .sum();

        System.out.println("Receita total geral:   R$ " + String.format("%.2f", receitaGeral));

        System.out.println("Fila de espera:        " + agendamentoService.tamanhoFila());
    }

    // =============================================
    // LISTAR POR CONVENIO
    // =============================================
    public void listarPorConvenio(String convenio) {

        System.out.println("\n===== ATENDIMENTOS - CONVÊNIO: " + convenio.toUpperCase() + " =====");

        List<Recibo> resultado = faturamentoService.getRecibos()
                .stream()
                .filter(r -> r.getPaciente().getConvenio()
                        .equalsIgnoreCase(convenio))
                .collect(Collectors.toList());

        if (resultado.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        resultado.forEach(System.out::println);
    }
}
