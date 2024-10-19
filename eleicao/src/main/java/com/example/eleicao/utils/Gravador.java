package com.example.eleicao.utils;

import com.example.eleicao.modelos.Candidato;
import com.example.eleicao.modelos.Partido;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Gravador {

    public void gravarCandidatos(Map<String, Candidato> candidatos, String nomeArquivo) throws IOException {
        BufferedWriter gravador = null;
        try {
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            gravador.write(candidatos.size() + "\n");
            for (Candidato c : candidatos.values()) {
                gravador.write(c.getCpf() + "\n");
                gravador.write(c.getNome() + "\n");
                gravador.write(c.getNumero() + "\n");
                gravador.write(c.getPartido().getCnpj() + "\n");
            }
        } finally {
            if (gravador != null) {
                gravador.close();
            }
        }
    }

    public Map<String, Candidato> recuperarCandidatos(String nomeArquivo, Map<String, Partido> partidos) throws IOException {
        BufferedReader leitor = null;
        Map<String, Candidato> candidatosLidos = new HashMap<>();
        try {
            leitor = new BufferedReader(new FileReader(nomeArquivo));
            String texto = leitor.readLine();
            if (texto != null) {
                int quantidadeCandidatos = Integer.parseInt(texto);
                for (int i = 0; i < quantidadeCandidatos; i++) {
                    String cpf = leitor.readLine();
                    String nome = leitor.readLine();
                    int numero = Integer.parseInt(leitor.readLine());
                    String cnpjPartido = leitor.readLine();
                    Partido partido = partidos.get(cnpjPartido);
                    Candidato candidato = new Candidato(nome, numero, cpf, partido);
                    candidatosLidos.put(cpf, candidato);
                }
            }
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
        return candidatosLidos;
    }

    public void gravarPartidos(Map<String, Partido> partidos, String nomeArquivo) throws IOException {
        BufferedWriter gravador = null;
        try {
            gravador = new BufferedWriter(new FileWriter(nomeArquivo));
            gravador.write(partidos.size() + "\n");
            for (Partido p : partidos.values()) {
                gravador.write(p.getCnpj() + "\n");
                gravador.write(p.getNome() + "\n");
                gravador.write(p.getNumero() + "\n");
            }
        } finally {
            if (gravador != null) {
                gravador.close();
            }
        }
    }

    public Map<String, Partido> recuperarPartidos(String nomeArquivo) throws IOException {
        BufferedReader leitor = null;
        Map<String, Partido> partidosLidos = new HashMap<>();
        try {
            leitor = new BufferedReader(new FileReader(nomeArquivo));
            String texto = leitor.readLine();
            if (texto != null) {
                int quantidadePartidos = Integer.parseInt(texto);
                for (int i = 0; i < quantidadePartidos; i++) {
                    String cnpj = leitor.readLine();
                    String nome = leitor.readLine();
                    int numero = Integer.parseInt(leitor.readLine());
                    Partido partido = new Partido(nome, numero, cnpj);
                    partidosLidos.put(cnpj, partido);
                }
            }
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }
        return partidosLidos;
    }
}
