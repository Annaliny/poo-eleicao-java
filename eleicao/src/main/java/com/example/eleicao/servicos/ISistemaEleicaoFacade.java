package com.example.eleicao.servicos;

import com.example.eleicao.exceptions.CandidatoJaExisteException;
import com.example.eleicao.exceptions.CandidatoNaoEncontradoException;
import com.example.eleicao.exceptions.PartidoJaExisteException;
import com.example.eleicao.exceptions.PartidoNaoEncontradoException;
import com.example.eleicao.modelos.Candidato;
import com.example.eleicao.modelos.Partido;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ISistemaEleicaoFacade {

    // partidos
    void criarPartido(String nome, int numero, String cnpj) throws PartidoJaExisteException;
    List<Partido> listarTodosPartidos();
    Partido listarPartidoPorNome(String nome);
    void editarPartido(String nomeAntigo, String novoNome, int novoNumero, String novoCnpj) throws PartidoNaoEncontradoException, PartidoJaExisteException;
    void removerPartido(String nome) throws PartidoNaoEncontradoException;

    // Candidatos
    void criarCandidato(String nome, int numero, String cpf, Partido partido) throws CandidatoJaExisteException;
    List<Candidato> listarTodosCandidatos();
    Candidato listarCandidatoPorNome(String nome);
    void editarCandidato(String nomeAntigo, String novoNome, int novoNumero, String novoCpf, Partido novoPartido) throws CandidatoNaoEncontradoException, CandidatoJaExisteException;
    void removerCandidato(String nome) throws CandidatoNaoEncontradoException;

    // Gravar e recuperação de partidos e candidatos
    void gravarPartidos(String nomeArquivo) throws IOException;
    void gravarCandidatos(String nomeArquivo) throws IOException;
    Map<String, Partido> recuperarPartidos(String nomeArquivo) throws IOException;
    Map<String, Candidato> recuperarCandidatos(String nomeArquivo, Map<String, Partido> partidos) throws IOException;
}
