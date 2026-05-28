package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Recibo {

    private Paciente paciente;
    private Profissional profissional;
    private LocalDate data;
    private LocalTime hora;

    private TipoAtendimento tipoAtendimento;
    private TipoCobranca tipoCobranca;

    private double valorBase;
    private double desconto;
    private double taxaCancelamento;
    private double totalCobrado;

    private boolean cancelado;

    public Recibo(
            Paciente paciente,
            Profissional profissional,
            LocalDate data,
            LocalTime hora,
            TipoAtendimento tipoAtendimento,
            TipoCobranca tipoCobranca,
            double valorBase,
            double desconto,
            double taxaCancelamento,
            boolean cancelado
    ) {

        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.hora = hora;

        this.tipoAtendimento = tipoAtendimento;
        this.tipoCobranca = tipoCobranca;

        this.valorBase = valorBase;
        this.desconto = desconto;
        this.taxaCancelamento = taxaCancelamento;

        this.cancelado = cancelado;

        this.totalCobrado = calcularTotal();
    }

    private double calcularTotal() {

        double total = valorBase;

        if (tipoCobranca == TipoCobranca.CONVENIO) {

            total = total * (1 - desconto / 100);
        }

        if (tipoAtendimento == TipoAtendimento.RETORNO) {

            total = total * 0.5;
        }

        total += taxaCancelamento;

        return total;
    }

    public double getTotalCobrado() {
        return totalCobrado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public TipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    @Override
    public String toString() {

        return "\n========== RECIBO ==========\n" +
                "Paciente:       " + paciente.getNome() + "\n" +
                "Profissional:   " + profissional.getNome() + "\n" +
                "Data:           " + data + "\n" +
                "Hora:           " + hora + "\n" +
                "Tipo:           " + tipoAtendimento + "\n" +
                "Cobrança:       " + tipoCobranca + "\n" +
                "Valor base:     R$ " + String.format("%.2f", valorBase) + "\n" +
                "Desconto:       " + desconto + "%\n" +
                "Taxa cancelam.: R$ " + String.format("%.2f", taxaCancelamento) + "\n" +
                "Cancelado:      " + cancelado + "\n" +
                "TOTAL:          R$ " + String.format("%.2f", totalCobrado) + "\n" +
                "============================\n";
    }
}