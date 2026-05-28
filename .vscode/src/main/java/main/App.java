package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import repository.JsonRepository;
import service.*;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // =========================
        // SERVICES
        // =========================
        AgendamentoService agendamentoService =
                new AgendamentoService();

        FilaEspera filaEspera =
                new FilaEspera();

        FaturamentoService faturamentoService =
                new FaturamentoService();

        RelatorioService relatorioService =
                new RelatorioService(
                        agendamentoService,
                        faturamentoService
                );

        // =========================
        // JSON
        // =========================
        JsonRepository repository =
                new JsonRepository();

        // =========================
        // CARREGAMENTO
        // =========================
        List<Paciente> pacientes =
                repository.carregarLista(
                        "pacientes.json",
                        Paciente.class
                );

        List<Profissional> profissionais =
                repository.carregarLista(
                        "profissionais.json",
                        Profissional.class
                );

        if (pacientes == null) {
            pacientes = new ArrayList<>();
        }

        if (profissionais == null) {
            profissionais = new ArrayList<>();
        }

        int opcao;

        do {

            System.out.println("\n===== CLÍNICA =====");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Cadastrar profissional");
            System.out.println("3 - Agendar consulta");
            System.out.println("4 - Listar atendimentos");
            System.out.println("5 - Mostrar fila de espera");
            System.out.println("6 - Finalizar atendimento");
            System.out.println("7 - Cancelar atendimento");
            System.out.println("8 - Configurar taxa");
            System.out.println("9 - Relatórios");
            System.out.println("0 - Sair");

            System.out.print("Escolha: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                // =========================
                // PACIENTE
                // =========================
                case 1: {

                    System.out.print("Nome: ");
                    String nome = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();

                    System.out.print("Idade: ");
                    int idade = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Contato: ");
                    String contato = sc.nextLine();

                    System.out.print("Convênio: ");
                    String convenio = sc.nextLine();

                    System.out.print("Prioridade (true/false): ");
                    boolean prioridade = sc.nextBoolean();
                    sc.nextLine();

                    System.out.print("Histórico: ");
                    String historico = sc.nextLine();

                    Paciente paciente =
                            new Paciente(
                                    nome,
                                    cpf,
                                    idade,
                                    contato,
                                    convenio,
                                    prioridade,
                                    historico
                            );

                    pacientes.add(paciente);

                    repository.salvar(
                            pacientes,
                            "pacientes.json"
                    );

                    System.out.println(
                            "Paciente cadastrado!"
                    );

                    break;
                }

                // =========================
                // PROFISSIONAL
                // =========================
                case 2: {

                    System.out.print("Nome: ");
                    String nomeProf = sc.nextLine();

                    System.out.print("CPF: ");
                    String cpfProf = sc.nextLine();

                    System.out.print("Idade: ");
                    int idadeProf = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Especialidade: ");
                    String especialidade = sc.nextLine();

                    System.out.print("Valor consulta: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Horário: ");
                    String horario = sc.nextLine();

                    System.out.print("Duração: ");
                    int duracao = sc.nextInt();
                    sc.nextLine();

                    Profissional profissional =
                            new Profissional(
                                    nomeProf,
                                    cpfProf,
                                    idadeProf,
                                    especialidade,
                                    valor,
                                    horario,
                                    duracao
                            );

                    profissionais.add(profissional);

                    repository.salvar(
                            profissionais,
                            "profissionais.json"
                    );

                    System.out.println(
                            "Profissional cadastrado!"
                    );

                    break;
                }

                // =========================
                // AGENDAR
                // =========================
                case 3: {

                    if (
                            pacientes.isEmpty()
                                    ||
                                    profissionais.isEmpty()
                    ) {

                        System.out.println(
                                "Cadastre pacientes e profissionais primeiro."
                        );

                        break;
                    }

                    System.out.println(
                            "\n===== PACIENTES ====="
                    );

                    for (int i = 0; i < pacientes.size(); i++) {

                        System.out.println(
                                i + " - " + pacientes.get(i)
                        );
                    }

                    System.out.print("Paciente: ");
                    int iPac = sc.nextInt();

                    System.out.println(
                            "\n===== PROFISSIONAIS ====="
                    );

                    for (int i = 0; i < profissionais.size(); i++) {

                        System.out.println(
                                i + " - " + profissionais.get(i)
                        );
                    }

                    System.out.print("Profissional: ");
                    int iProf = sc.nextInt();

                    System.out.print("Sala: ");
                    int salaNum = sc.nextInt();

                    System.out.print("Hora: ");
                    int hora = sc.nextInt();
                    sc.nextLine();

                    Paciente pacienteSelecionado =
                            pacientes.get(iPac);

                    Profissional profissionalSelecionado =
                            profissionais.get(iProf);

                    Consulta consulta =
                            new Consulta(
                                    LocalDate.now(),
                                    LocalTime.of(hora, 0),
                                    new Sala(salaNum),
                                    pacienteSelecionado,
                                    profissionalSelecionado
                            );

                    boolean agendado =
                            agendamentoService.agendar(
                                    consulta
                            );
                            
                    if (!agendado) {

                        filaEspera.adicionar(
                                pacienteSelecionado
                        );

                    } 

                    break;
                }

                // =========================
                // LISTAR
                // =========================
                case 4: {

                    agendamentoService
                            .listarAtendimentos();

                    break;
                }

                // =========================
                // FILA
                // =========================
                case 5: {

                    filaEspera
                            .mostrarFila();

                    break;
                }

                // =========================
                // FINALIZAR
                // =========================
                case 6: {

                    List<Atendimento> atendimentos =
                            agendamentoService
                                    .getAtendimentos();

                    if (atendimentos.isEmpty()) {

                        System.out.println(
                                "Nenhum atendimento."
                        );

                        break;
                    }

                    System.out.println(
                            "\n===== ATENDIMENTOS ====="
                    );

                    for (int i = 0; i < atendimentos.size(); i++) {

                        System.out.println(
                                i + " - " + atendimentos.get(i)
                        );
                    }

                    System.out.print(
                            "Escolha: "
                    );

                    int indice =
                            sc.nextInt();
                    sc.nextLine();

                    Atendimento atendimento =
                            atendimentos.get(indice);

                    // =========================
                    // TIPO ATENDIMENTO
                    // =========================

                    System.out.println(
                            "\n===== TIPO ATENDIMENTO ====="
                    );

                    for (
                            int i = 0;
                            i < TipoAtendimento.values().length;
                            i++
                    ) {

                        System.out.println(
                                i + " - " +
                                        TipoAtendimento.values()[i]
                        );
                    }

                    System.out.print("Escolha: ");

                    int opTipo =
                            sc.nextInt();
                    sc.nextLine();

                    TipoAtendimento tipo =
                            TipoAtendimento.values()[opTipo];

                    // =========================
                    // TIPO COBRANÇA
                    // =========================

                    System.out.println(
                            "\n===== TIPO COBRANÇA ====="
                    );

                    for (
                            int i = 0;
                            i < TipoCobranca.values().length;
                            i++
                    ) {

                        System.out.println(
                                i + " - " +
                                        TipoCobranca.values()[i]
                        );
                    }

                    System.out.print("Escolha: ");

                    int opCobranca =
                            sc.nextInt();
                    sc.nextLine();

                    TipoCobranca cobranca =
                            TipoCobranca.values()[opCobranca];

                    // =========================
                    // VALOR
                    // =========================

                    System.out.print(
                            "Valor base: "
                    );

                    double valorBase =
                            sc.nextDouble();
                    sc.nextLine();

                    faturamentoService
                            .finalizarAtendimento(
                                    atendimento,
                                    tipo,
                                    cobranca,
                                    valorBase
                            );

                    break;
                }

                // =========================
                // CANCELAR
                // =========================
                case 7: {

                    List<Atendimento> lista =
                            agendamentoService
                                    .getAtendimentos();

                    if (lista.isEmpty()) {

                        System.out.println(
                                "Nenhum atendimento."
                        );

                        break;
                    }

                    System.out.println(
                            "\n===== ATENDIMENTOS ====="
                    );

                    for (int i = 0; i < lista.size(); i++) {

                        System.out.println(
                                i + " - " + lista.get(i)
                        );
                    }

                    System.out.print(
                            "Escolha o atendimento: "
                    );

                    int indiceCancelamento =
                            sc.nextInt();
                    sc.nextLine();

                    Atendimento atendimentoCancelado =
                            lista.get(indiceCancelamento);

                    // =========================
                    // TIPO ATENDIMENTO
                    // =========================

                    System.out.println(
                            "\n===== TIPO DE ATENDIMENTO ====="
                    );

                    for (
                            int i = 0;
                            i < TipoAtendimento.values().length;
                            i++
                    ) {

                        System.out.println(
                                i + " - " +
                                        TipoAtendimento.values()[i]
                        );
                    }

                    System.out.print("Escolha: ");

                    int opTipo =
                            sc.nextInt();
                    sc.nextLine();

                    TipoAtendimento tipoCancelamento =
                            TipoAtendimento.values()[opTipo];

                    // =========================
                    // COBRANÇA
                    // =========================

                    System.out.println(
                            "\n===== TIPO DE COBRANÇA ====="
                    );

                    for (
                            int i = 0;
                            i < TipoCobranca.values().length;
                            i++
                    ) {

                        System.out.println(
                                i + " - " +
                                        TipoCobranca.values()[i]
                        );
                    }

                    System.out.print("Escolha: ");

                    int opCobranca =
                            sc.nextInt();
                    sc.nextLine();

                    TipoCobranca cobrancaCancelamento =
                            TipoCobranca.values()[opCobranca];

                    // =========================
                    // VALOR
                    // =========================

                    System.out.print(
                            "Valor base: "
                    );

                    double valorBaseCancelamento =
                            sc.nextDouble();
                    sc.nextLine();

                    faturamentoService
                            .aplicarCancelamento(
                                    atendimentoCancelado,
                                    tipoCancelamento,
                                    cobrancaCancelamento,
                                    valorBaseCancelamento,
                                    LocalDate.now()
                            );

                    lista.remove(
                            indiceCancelamento
                    );

                    System.out.println(
                            "Atendimento cancelado!"
                    );

                    break;
                }

                // =========================
                // CONFIGURAÇÃO
                // =========================
                case 8: {

                    System.out.print(
                            "Nova taxa: "
                    );

                    faturamentoService
                            .setTaxaCancelamento(
                                    sc.nextDouble()
                            );

                    System.out.print(
                            "Novo prazo: "
                    );

                    faturamentoService
                            .setPrazoIsencaoDias(
                                    sc.nextInt()
                            );

                    sc.nextLine();

                    break;
                }

                // =========================
                // RELATÓRIOS
                // =========================
                case 9: {

                    int sub;

                    do {

                        System.out.println(
                                "\n===== RELATÓRIOS ====="
                        );

                        System.out.println(
                                "1 - Profissional"
                        );

                        System.out.println(
                                "2 - Período"
                        );

                        System.out.println(
                                "3 - Convênio"
                        );

                        System.out.println(
                                "4 - Mensal"
                        );

                        System.out.println(
                                "5 - Indicadores"
                        );

                        System.out.println(
                                "0 - Voltar"
                        );

                        System.out.print(
                                "Escolha: "
                        );

                        sub = sc.nextInt();
                        sc.nextLine();

                        switch (sub) {

                            case 1: {

                                System.out.print(
                                        "Nome do profissional: "
                                );

                                String nomeProfissional =
                                        sc.nextLine();

                                relatorioService
                                        .listarPorProfissional(
                                                nomeProfissional
                                        );

                                break;
                            }

                            case 2: {

                                System.out.print(
                                        "Data início (AAAA-MM-DD): "
                                );

                                LocalDate inicio =
                                        LocalDate.parse(
                                                sc.nextLine()
                                        );

                                System.out.print(
                                        "Data fim (AAAA-MM-DD): "
                                );

                                LocalDate fim =
                                        LocalDate.parse(
                                                sc.nextLine()
                                        );

                                relatorioService
                                        .listarPorPeriodo(
                                                inicio,
                                                fim
                                        );

                                break;
                            }

                            case 3: {

                                System.out.print(
                                        "Nome do convênio: "
                                );

                                String convenioBusca =
                                        sc.nextLine();

                                relatorioService
                                        .listarPorConvenio(
                                                convenioBusca
                                        );

                                break;
                            }

                            case 4: {

                                System.out.print(
                                        "Mês: "
                                );

                                int mes =
                                        sc.nextInt();

                                System.out.print(
                                        "Ano: "
                                );

                                int ano =
                                        sc.nextInt();
                                sc.nextLine();

                                relatorioService
                                        .relatorioMensal(
                                                mes,
                                                ano
                                        );

                                break;
                            }

                            case 5: {

                                relatorioService
                                        .exibirIndicadores();

                                break;
                            }

                            case 0: {

                                System.out.println(
                                        "Voltando..."
                                );

                                break;
                            }

                            default: {

                                System.out.println(
                                        "Opção inválida."
                                );
                            }
                        }

                    } while (sub != 0);

                    break;
                }

                // =========================
                // SAIR
                // =========================
                case 0: {

                    System.out.println(
                            "Saindo..."
                    );

                    break;
                }

                default: {

                    System.out.println(
                            "Opção inválida."
                    );
                }
            }

        } while (opcao != 0);

        sc.close();
    }
}