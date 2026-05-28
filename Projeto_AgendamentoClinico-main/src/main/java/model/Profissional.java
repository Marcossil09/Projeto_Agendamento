package model;

public class Profissional extends Pessoa {

    private String especialidade;
    private double valorConsulta;
    private String horarioDisponivel;
    private int duracaoAtendimento;

    public Profissional(String nome,
                        String cpf,
                        int idade,
                        String especialidade,
                        double valorConsulta,
                        String horarioDisponivel,
                        int duracaoAtendimento) {

        super(nome, cpf, idade);

        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.horarioDisponivel = horarioDisponivel;
        this.duracaoAtendimento = duracaoAtendimento;
    }

    public String getNome() {
        return nome;
    }

    @Override
public String toString() {

    return nome +
            " | Especialidade: " + especialidade +
            " | Valor Consulta: " + valorConsulta +
            " | Horario Disponivel: " + horarioDisponivel +
            " | Duração Atendimento(MIN): " + duracaoAtendimento ;
}
}