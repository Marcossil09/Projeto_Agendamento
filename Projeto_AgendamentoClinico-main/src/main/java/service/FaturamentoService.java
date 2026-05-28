package service;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaturamentoService {

    private List<Recibo> recibos = new ArrayList<>();

    private double taxaCancelamento = 50.0;
    private int prazoIsencaoDias = 1;

    // =============================================
    // FINALIZAR ATENDIMENTO
    // =============================================
    public Recibo finalizarAtendimento(
            Atendimento atendimento,
            TipoAtendimento tipo,
            TipoCobranca cobranca,
            double valorBase
    ) {

        double desconto =
                calcularDesconto(
                        cobranca,
                        atendimento.getPaciente()
                );

        Recibo recibo = new Recibo(

                atendimento.getPaciente(),
                atendimento.getProfissional(),
                atendimento.getData(),
                atendimento.getHora(),

                tipo,
                cobranca,

                valorBase,
                desconto,

                0.0,

                false // NÃO cancelado
        );

        recibos.add(recibo);

        System.out.println(recibo);

        return recibo;
    }

    // =============================================
    // CANCELAMENTO
    // =============================================
    public Recibo aplicarCancelamento(

            Atendimento atendimento,
            TipoAtendimento tipo,
            TipoCobranca cobranca,
            double valorBase,
            LocalDate dataCancelamento
    ) {

        double desconto =
                calcularDesconto(
                        cobranca,
                        atendimento.getPaciente()
                );

        double taxa = 0.0;

        long diasDeAntecedencia =
                atendimento.getData().toEpochDay()
                        - dataCancelamento.toEpochDay();

        if (diasDeAntecedencia < prazoIsencaoDias) {

            taxa = taxaCancelamento;

            System.out.println(
                    "Cancelamento fora do prazo. Taxa aplicada: R$ "
                            + String.format("%.2f", taxa)
            );

        } else {

            System.out.println(
                    "Cancelamento dentro do prazo. Sem taxa."
            );
        }

        Recibo recibo = new Recibo(

                atendimento.getPaciente(),
                atendimento.getProfissional(),
                atendimento.getData(),
                atendimento.getHora(),

                tipo,
                cobranca,

                valorBase,
                desconto,

                taxa,

                true // CANCELADO
        );

        recibos.add(recibo);

        System.out.println(recibo);

        return recibo;
    }

    // =============================================
    // DESCONTO
    // =============================================
    private double calcularDesconto(

            TipoCobranca cobranca,
            Paciente paciente
    ) {

        if (cobranca == TipoCobranca.CONVENIO) {

            return 30.0;
        }

        return 0.0;
    }

    // =============================================
    // GETTERS / SETTERS
    // =============================================

    public void setTaxaCancelamento(double taxa) {

        this.taxaCancelamento = taxa;
    }

    public void setPrazoIsencaoDias(int dias) {

        this.prazoIsencaoDias = dias;
    }

    public double getTaxaCancelamento() {

        return taxaCancelamento;
    }

    public int getPrazoIsencaoDias() {

        return prazoIsencaoDias;
    }

    public List<Recibo> getRecibos() {

        return recibos;
    }
}