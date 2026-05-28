package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonRepository {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // =========================================
    // SALVAR GENÉRICO
    // =========================================
    public void salvar(List<?> lista, String nomeArquivo) {

        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            gson.toJson(lista, writer);

            System.out.println("✔ Dados salvos em: " + nomeArquivo);

        } catch (IOException e) {

            System.out.println("❌ Erro ao salvar JSON: " + nomeArquivo);
            e.printStackTrace();
        }
    }

    // =========================================
    // CARREGAR GENÉRICO
    // =========================================
    public <T> List<T> carregarLista(String nomeArquivo, Class<T> classe) {

        try (FileReader reader = new FileReader(nomeArquivo)) {

            Type tipoLista = TypeToken.getParameterized(List.class, classe).getType();

            List<T> lista = gson.fromJson(reader, tipoLista);

            return (lista != null) ? lista : new ArrayList<>();

        } catch (IOException e) {

            System.out.println("⚠ Arquivo não encontrado: " + nomeArquivo);
            return new ArrayList<>();
        }
    }

    // =========================================
    // SALVAR PACIENTES
    // =========================================
    public void salvarPacientes(List<?> pacientes) {
        salvar(pacientes, "pacientes.json");
    }

    // =========================================
    // CARREGAR PACIENTES
    // =========================================
    public <T> List<T> carregarPacientes(Class<T> classe) {
        return carregarLista("pacientes.json", classe);
    }

    // =========================================
    // SALVAR PROFISSIONAIS
    // =========================================
    public void salvarProfissionais(List<?> profissionais) {
        salvar(profissionais, "profissionais.json");
    }

    // =========================================
    // CARREGAR PROFISSIONAIS
    // =========================================
    public <T> List<T> carregarProfissionais(Class<T> classe) {
        return carregarLista("profissionais.json", classe);
    }

    // =========================================
    // SALVAR ATENDIMENTOS
    // =========================================
    public void salvarAtendimentos(List<?> atendimentos) {
        salvar(atendimentos, "atendimentos.json");
    }

    // =========================================
    // CARREGAR ATENDIMENTOS
    // =========================================
    public <T> List<T> carregarAtendimentos(Class<T> classe) {
        return carregarLista("atendimentos.json", classe);
    }
    
}