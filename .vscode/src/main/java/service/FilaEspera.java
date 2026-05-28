package service;

import java.util.PriorityQueue;

import model.Paciente;

public class FilaEspera {
    private PriorityQueue<Paciente> fila;

    public FilaEspera() {

        fila = new PriorityQueue<>(

                (p1, p2) -> {

                    if (
                            p1.isPrioridade()
                                    &&
                                    !p2.isPrioridade()
                    ) {

                        return -1;
                    }

                    if (
                            !p1.isPrioridade()
                                    &&
                                    p2.isPrioridade()
                    ) {

                        return 1;
                    }

                    return 0;
                }
        );
    }


    public void adicionar(Paciente paciente) {
        fila.add(paciente);
    }


    public Paciente chamarProximo() {

        if (fila.isEmpty()) {

            System.out.println(
                    "Fila vazia."
            );

            return null;
        }

        return fila.poll();
    }

    public void mostrarFila() {

        if (fila.isEmpty()) {

            System.out.println(
                    "Nenhum paciente na fila."
            );

            return;
        }

        System.out.println(
                "\n===== FILA DE ESPERA ====="
        );

        for (Paciente paciente : fila) {

            System.out.println(paciente);
        }
    }

    public boolean estaVazia() {

        return fila.isEmpty();
    }

    public int tamanho() {
    return fila.size();
}

}