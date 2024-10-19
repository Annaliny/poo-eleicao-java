package com.example.eleicao.servicos;

import com.example.eleicao.exceptions.CandidatoJaExisteException;
import com.example.eleicao.exceptions.CandidatoNaoEncontradoException;
import com.example.eleicao.exceptions.PartidoJaExisteException;
import com.example.eleicao.exceptions.PartidoNaoEncontradoException;
import com.example.eleicao.modelos.Candidato;
import com.example.eleicao.modelos.Partido;
import com.example.eleicao.utils.Gravador;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SistemaEleicaoFacade implements ISistemaEleicaoFacade {

    private Map<String, Candidato> candidatos; // Chave: CPF
    private Map<String, Partido> partidos; // Chave: CNPJ
    private Gravador gravador;

    public SistemaEleicaoFacade() {
        this.candidatos = new HashMap<>();
        this.partidos = new HashMap<>();
        this.gravador = new Gravador();
    }


    @Override
    public void criarPartido(String nome, int numero, String cnpj) throws PartidoJaExisteException {
        for (Partido partido : partidos.values()) {
            if (partido.getNome().equalsIgnoreCase(nome)) {
                throw new PartidoJaExisteException("Já existe um partido com o nome: " + nome);
            }
            if (partido.getNumero() == numero) {
                throw new PartidoJaExisteException("Já existe um partido com o número: " + numero);
            }
        }

        if (partidos.containsKey(cnpj)) {
            throw new PartidoJaExisteException("Já existe um partido com o CNPJ: " + cnpj);
        }

        Partido partido = new Partido(nome, numero, cnpj);
        partidos.put(cnpj, partido);
    }

    @Override
    public List<Partido> listarTodosPartidos() {
        return new ArrayList<>(partidos.values());
    }


    @Override
    public Partido listarPartidoPorNome(String nome) {
        return partidos.values()
                .stream()
                .filter(partido -> partido.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);

    }

    @Override
    public void editarPartido(String nomeAntigo, String novoNome, int novoNumero, String novoCnpj)
            throws PartidoNaoEncontradoException, PartidoJaExisteException {

        Partido partido = listarPartidoPorNome(nomeAntigo);

        if (partido == null) {
            throw new PartidoNaoEncontradoException("Partido com o nome '" + nomeAntigo + "' não encontrado.");
        }

        boolean existe = partidos.values().stream()
                .filter(c -> !c.equals(partido)) // Filtra todos os partidos, exceto o próprio
                .anyMatch(c ->
                        (!c.getNome().equalsIgnoreCase(partido.getNome()) && c.getNome().equalsIgnoreCase(novoNome)) ||
                                (!c.getCnpj().equals(partido.getCnpj()) && c.getCnpj().equals(novoCnpj)) ||
                                (c.getNumero() != partido.getNumero() && c.getNumero() == novoNumero)
                );

        if (existe) {
            throw new PartidoJaExisteException("Já existe um partido com o nome, CNPJ ou numero: " + novoNome);
        }

        partidos.remove(partido.getCnpj());
        partido.setNome(novoNome);
        partido.setNumero(novoNumero);
        partido.setCnpj(novoCnpj);
        System.out.println(partido + "Edit");
        partidos.put(novoCnpj, partido);
        System.out.println(partidos.values() + "partidos.values()");
    }

    @Override
    public void removerPartido(String nome) throws PartidoNaoEncontradoException {
        Partido partido = listarPartidoPorNome(nome);
        if (partido == null) {
            throw new PartidoNaoEncontradoException("Partido com o nome '" + nome + "' não encontrado.");
        }

        partidos.remove(partido.getCnpj());
    }

    // Implementação dos métodos de Candidato

    @Override
    public void criarCandidato(String nome, int numero, String cpf, Partido partido) throws CandidatoJaExisteException {
        for (Candidato candidato : candidatos.values()) {
            if (candidato.getNome().equalsIgnoreCase(nome)) {
                throw new CandidatoJaExisteException("Já existe um candidato com o nome: " + nome);
            }
            if (candidato.getNumero() == numero) {
                throw new CandidatoJaExisteException("Já existe um candidato com o número: " + numero);
            }
        }

        if (candidatos.containsKey(cpf)) {
            throw new CandidatoJaExisteException("Já existe um candidato com o CPF: " + cpf);
        }

        Candidato candidato = new Candidato(nome, numero, cpf, partido);
        candidatos.put(cpf, candidato);
    }

    @Override
    public List<Candidato> listarTodosCandidatos() {
        return candidatos.values().stream().collect(Collectors.toList());
    }

    @Override
    public Candidato listarCandidatoPorNome(String nome) {
        return candidatos.values()
                .stream()
                .filter(candidato -> candidato.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void editarCandidato(String nomeAntigo, String novoNome, int novoNumero, String novoCpf, Partido novoPartido)
            throws CandidatoNaoEncontradoException, CandidatoJaExisteException {

        Candidato candidato = listarCandidatoPorNome(nomeAntigo);

        if (candidato == null) {
            throw new CandidatoNaoEncontradoException("Candidato com o nome '" + nomeAntigo + "' não encontrado.");
        }

        boolean existe = candidatos.values().stream()
                .filter(c -> !c.equals(candidato))
                .anyMatch(c -> c.getNome().equalsIgnoreCase(novoNome) || c.getCpf().equals(novoCpf) || c.getNumero() == novoNumero);

        if (existe) {
            throw new CandidatoJaExisteException("Já existe um candidato com o nome, CPF ou numero: " + novoNome);
        }

        candidatos.remove(candidato.getCpf());
        candidato.setNome(novoNome);
        candidato.setNumero(novoNumero);
        candidato.setCpf(novoCpf);
        candidato.setPartido(novoPartido);
        candidatos.put(novoCpf, candidato);
    }

    @Override
    public void removerCandidato(String nome) throws CandidatoNaoEncontradoException {
        Candidato candidato = listarCandidatoPorNome(nome);
        if (candidato == null) {
            throw new CandidatoNaoEncontradoException("Candidato com o nome '" + nome + "' não encontrado.");
        }

        candidatos.remove(candidato.getCpf());
    }

    @Override
    public void gravarPartidos(String nomeArquivo) throws IOException {
        gravador.gravarPartidos(partidos, nomeArquivo);
    }

    @Override
    public void gravarCandidatos(String nomeArquivo) throws IOException {
        gravador.gravarCandidatos(candidatos, nomeArquivo);
    }

    @Override
    public Map<String, Partido> recuperarPartidos(String nomeArquivo) throws IOException {
        this.partidos = gravador.recuperarPartidos(nomeArquivo);
        return this.partidos;
    }

    @Override
    public Map<String, Candidato> recuperarCandidatos(String nomeArquivo, Map<String, Partido> partidos) throws IOException {
        this.candidatos = gravador.recuperarCandidatos(nomeArquivo, partidos);
        return this.candidatos;
    }

    public Map<String, Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(Map<String, Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    public Map<String, Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(Map<String, Partido> partidos) {
        this.partidos = partidos;
    }
}
