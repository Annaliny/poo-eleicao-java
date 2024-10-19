package com.example.eleicao.controle;

import com.example.eleicao.exceptions.CandidatoJaExisteException;
import com.example.eleicao.exceptions.CandidatoNaoEncontradoException;
import com.example.eleicao.exceptions.PartidoJaExisteException;
import com.example.eleicao.exceptions.PartidoNaoEncontradoException;
import com.example.eleicao.modelos.Candidato;
import com.example.eleicao.modelos.Partido;
import com.example.eleicao.servicos.ISistemaEleicaoFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eleicao")
@CrossOrigin(origins = "http://localhost:4200")
public class SistemaEleicaoControle {

    private final ISistemaEleicaoFacade sistemaEleicaoService;

    public SistemaEleicaoControle(ISistemaEleicaoFacade sistemaEleicaoService) {
        this.sistemaEleicaoService = sistemaEleicaoService;
    }

    // Partido
    @PostMapping("/partido")
    public ResponseEntity<String> criarPartido(@RequestBody Partido partido) {
        try {
            sistemaEleicaoService.criarPartido(partido.getNome(), partido.getNumero(), partido.getCnpj());
            return ResponseEntity.ok("Partido criado com sucesso.");
        } catch (PartidoJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/partidos")
    public List<Partido> listarTodosPartidos() {
        return sistemaEleicaoService.listarTodosPartidos();
    }

    @GetMapping("/partido/{nome}")
    public Partido listarPartidoPorNome(@PathVariable String nome) {
        return sistemaEleicaoService.listarPartidoPorNome(nome);
    }

    @PutMapping("/partido")
    public ResponseEntity<String> editarPartido(@RequestBody Partido partido, @RequestParam String nomeAntigo) {
        System.out.println(partido.getNome());
        try {
            sistemaEleicaoService.editarPartido(nomeAntigo, partido.getNome(), partido.getNumero(), partido.getCnpj());
            return ResponseEntity.ok("Partido editado com sucesso.");
        } catch (PartidoNaoEncontradoException | PartidoJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/partido/{nome}")
    public ResponseEntity<String> removerPartido(@PathVariable String nome) {
        try {
            sistemaEleicaoService.removerPartido(nome);
            return ResponseEntity.ok("Partido removido com sucesso.");
        } catch (PartidoNaoEncontradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Candidato
    @PostMapping("/candidato")
    public ResponseEntity<String> criarCandidato(@RequestBody Candidato candidato) {
        try {
            sistemaEleicaoService.criarCandidato(candidato.getNome(), candidato.getNumero(), candidato.getCpf(), candidato.getPartido());
            return ResponseEntity.ok("Candidato criado com sucesso.");
        } catch (CandidatoJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/candidatos")
    public List<Candidato> listarTodosCandidatos() {
        return sistemaEleicaoService.listarTodosCandidatos();
    }

    @GetMapping("/candidato/{nome}")
    public ResponseEntity<Candidato> listarCandidatoPorNome(@PathVariable String nome) {
        Candidato candidato = sistemaEleicaoService.listarCandidatoPorNome(nome);
        if (candidato != null) {
            return ResponseEntity.ok(candidato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/candidato")
    public ResponseEntity<String> editarCandidato(@RequestBody Candidato candidato, @RequestParam String nomeAntigo) {
        try {
            sistemaEleicaoService.editarCandidato(nomeAntigo, candidato.getNome(), candidato.getNumero(), candidato.getCpf(), candidato.getPartido());
            return ResponseEntity.ok("Candidato editado com sucesso.");
        } catch (CandidatoNaoEncontradoException | CandidatoJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/candidato/{nome}")
    public ResponseEntity<String> removerCandidato(@PathVariable String nome) {
        try {
            sistemaEleicaoService.removerCandidato(nome);
            return ResponseEntity.ok("Candidato removido com sucesso.");
        } catch (CandidatoNaoEncontradoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Gravação e recuperação
    @PostMapping("/gravar-partidos")
    public ResponseEntity<String> gravarPartidos(@RequestParam String nomeArquivo) {
        try {
            sistemaEleicaoService.gravarPartidos(nomeArquivo);
            return ResponseEntity.ok("Partidos gravados com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gravar partidos: " + e.getMessage());
        }
    }

    @PostMapping("/gravar-candidatos")
    public ResponseEntity<String> gravarCandidatos(@RequestParam String nomeArquivo) {
        try {
            sistemaEleicaoService.gravarCandidatos(nomeArquivo);
            return ResponseEntity.ok("Candidatos gravados com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gravar candidatos: " + e.getMessage());
        }
    }

    @GetMapping("/recuperar-partidos")
    public ResponseEntity<Map<String, Partido>> recuperarPartidos(@RequestParam String nomeArquivo) {
        try {
            Map<String, Partido> partidos = sistemaEleicaoService.recuperarPartidos(nomeArquivo);
            return ResponseEntity.ok(partidos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/recuperar-candidatos")
    public ResponseEntity<Map<String, Candidato>> recuperarCandidatos(@RequestParam String nomeArquivo, @RequestParam String arquivoPartidos) {
        try {
            Map<String, Partido> partidos = sistemaEleicaoService.recuperarPartidos(arquivoPartidos); // Primeiro recupera os partidos
            Map<String, Candidato> candidatos = sistemaEleicaoService.recuperarCandidatos(nomeArquivo, partidos);
            return ResponseEntity.ok(candidatos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}