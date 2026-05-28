package model;

public class Paciente extends Pessoa {

    private String contato;
    private String convenio;
    private boolean prioridade;
    private String historico;

    public Paciente(
            String nome,
            String cpf,
            int idade,
            String contato,
            String convenio,
            boolean prioridade,
            String historico
    ) {

        super(nome, cpf, idade);

        this.contato = contato;
        this.convenio = convenio;
        this.prioridade = prioridade;
        this.historico = historico;
    }

    public boolean isPrioridade() {

        return prioridade;
    }

    public String getNome() {

        return nome;
    }

    public String getConvenio() {

        return convenio;
    }

   @Override
public String toString() {

    return nome +
            " | CPF: " + cpf +
            " | Idade: " + idade +
            " | Contato: " + contato +
            " | Convênio: " + convenio +
            " | Historico: " + historico +
            " | Prioridade: " + (prioridade ? "SIM" : "NÃO");
}
}