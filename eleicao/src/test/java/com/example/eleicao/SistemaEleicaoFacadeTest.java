package com.example.eleicao;

import com.example.eleicao.exceptions.CandidatoJaExisteException;
import com.example.eleicao.exceptions.CandidatoNaoEncontradoException;
import com.example.eleicao.exceptions.PartidoJaExisteException;
import com.example.eleicao.exceptions.PartidoNaoEncontradoException;
import com.example.eleicao.modelos.Candidato;
import com.example.eleicao.modelos.Partido;
import com.example.eleicao.servicos.ISistemaEleicaoFacade;
import com.example.eleicao.servicos.SistemaEleicaoFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaEleicaoFacadeTest {

    private ISistemaEleicaoFacade sistemaEleicao;

    @BeforeEach
    public void setup() {
        sistemaEleicao = new SistemaEleicaoFacade();
    }

    // Testes para Partido
    @Test
    public void testCriarPartido() throws PartidoJaExisteException, PartidoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partido = sistemaEleicao.listarPartidoPorNome("Partido Verde");
        assertNotNull(partido);
        assertEquals("Partido Verde", partido.getNome());
        assertEquals(12, partido.getNumero());
        assertEquals("12.345.678/0001-99", partido.getCnpj());
    }

    @Test
    public void testCriarPartidoDuplicado() {
        assertThrows(PartidoJaExisteException.class, () -> {
            sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
            sistemaEleicao.criarPartido("Partido Verde", 45, "98.765.432/0001-88");
        });
    }

    @Test
    public void testListarTodosPartidos() throws PartidoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        sistemaEleicao.criarPartido("Partido Azul", 45, "98.765.432/0001-88");
        List<Partido> partidos = sistemaEleicao.listarTodosPartidos();
        assertEquals(2, partidos.size());
    }

    @Test
    public void testEditarPartido() throws PartidoJaExisteException, PartidoNaoEncontradoException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        sistemaEleicao.editarPartido("Partido Verde", "Partido Alterado", 99, "99.999.999/0001-99");
        Partido partido = sistemaEleicao.listarPartidoPorNome("Partido Alterado");
        assertNotNull(partido);
        assertEquals("Partido Alterado", partido.getNome());
        assertEquals(99, partido.getNumero());
        assertEquals("99.999.999/0001-99", partido.getCnpj());
    }

    @Test
    public void testEditarPartidoNaoExistente() {
        assertThrows(PartidoNaoEncontradoException.class, () -> {
            sistemaEleicao.editarPartido("Partido Inexistente", "Novo Nome", 99, "99.999.999/0001-99");
        });
    }

    @Test
    public void testRemoverPartido() throws PartidoJaExisteException, PartidoNaoEncontradoException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        sistemaEleicao.removerPartido("Partido Verde");
        assertNull(sistemaEleicao.listarPartidoPorNome("Partido Verde"));
    }

    @Test
    public void testRemoverPartidoNaoExistente() {
        assertThrows(PartidoNaoEncontradoException.class, () -> {
            sistemaEleicao.removerPartido("Partido Inexistente");
        });
    }

    // Testes para Candidato
    @Test
    public void testCriarCandidato() throws PartidoJaExisteException, CandidatoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partidoVerde = sistemaEleicao.listarPartidoPorNome("Partido Verde");
        sistemaEleicao.criarCandidato("Pessoa 1", 1234, "123.456.789-10", partidoVerde);

        Candidato candidato = sistemaEleicao.listarCandidatoPorNome("Pessoa 1");
        assertNotNull(candidato);
        assertEquals("Pessoa 1", candidato.getNome());
        assertEquals(1234, candidato.getNumero());
        assertEquals("123.456.789-10", candidato.getCpf());
        assertEquals(partidoVerde, candidato.getPartido());
    }

    @Test
    public void testCriarCandidatoDuplicado() throws PartidoJaExisteException, CandidatoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partidoVerde = sistemaEleicao.listarPartidoPorNome("Partido Verde");

        sistemaEleicao.criarCandidato("Pessoa 1", 1234, "123.456.789-10", partidoVerde);
        assertThrows(CandidatoJaExisteException.class, () -> {
            sistemaEleicao.criarCandidato("Pessoa 1", 5678, "111.111.111-11", partidoVerde); // Nome duplicado
        });

        assertThrows(CandidatoJaExisteException.class, () -> {
            sistemaEleicao.criarCandidato("Pessoa 2", 1234, "222.222.222-22", partidoVerde); // Número duplicado
        });

        assertThrows(CandidatoJaExisteException.class, () -> {
            sistemaEleicao.criarCandidato("Pessoa 3", 6789, "123.456.789-10", partidoVerde); // CPF duplicado
        });
    }

    @Test
    public void testListarTodosCandidatos() throws PartidoJaExisteException, CandidatoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partidoVerde = sistemaEleicao.listarPartidoPorNome("Partido Verde");

        sistemaEleicao.criarCandidato("Pessoa 1", 1234, "123.456.789-10", partidoVerde);
        sistemaEleicao.criarCandidato("Pessoa 2", 4321, "987.654.321-00", partidoVerde);

        List<Candidato> candidatos = sistemaEleicao.listarTodosCandidatos();
        assertEquals(2, candidatos.size());
    }

    @Test
    public void testEditarCandidato() throws PartidoJaExisteException, CandidatoNaoEncontradoException, CandidatoJaExisteException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partidoVerde = sistemaEleicao.listarPartidoPorNome("Partido Verde");

        sistemaEleicao.criarCandidato("Pessoa 1", 1234, "123.456.789-10", partidoVerde);
        sistemaEleicao.editarCandidato("Pessoa 1", "Pessoa 2", 5678, "111.111.111-11", partidoVerde);

        Candidato candidato = sistemaEleicao.listarCandidatoPorNome("Pessoa 2");
        assertNotNull(candidato);
        assertEquals("Pessoa 2", candidato.getNome());
        assertEquals(5678, candidato.getNumero());
        assertEquals("111.111.111-11", candidato.getCpf());
        assertEquals(partidoVerde, candidato.getPartido());
    }

    @Test
    public void testEditarCandidatoNaoExistente() {
        assertThrows(CandidatoNaoEncontradoException.class, () -> {
            sistemaEleicao.editarCandidato("Inexistente", "Novo Nome", 9999, "999.999.999-99", null);
        });
    }

    @Test
    public void testRemoverCandidato() throws PartidoJaExisteException, CandidatoJaExisteException, CandidatoNaoEncontradoException {
        sistemaEleicao.criarPartido("Partido Verde", 12, "12.345.678/0001-99");
        Partido partidoVerde = sistemaEleicao.listarPartidoPorNome("Partido Verde");

        sistemaEleicao.criarCandidato("Pessoa 1", 1234, "123.456.789-10", partidoVerde);
        sistemaEleicao.removerCandidato("Pessoa 1");

        assertNull(sistemaEleicao.listarCandidatoPorNome("Pessoa 1"));
    }

    @Test
    public void testRemoverCandidatoNaoExistente() {
        assertThrows(CandidatoNaoEncontradoException.class, () -> {
            sistemaEleicao.removerCandidato("Inexistente");
        });
    }
}
