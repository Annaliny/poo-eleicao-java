package com.example.eleicao.modelos;

import java.util.Objects;

public class Candidato {

    private String nome;
    private int numero;
    private String cpf;
    private Partido partido;

    public Candidato(String nome, int numero, String cpf, Partido partido) {
        this.nome = nome;
        this.numero = numero;
        this.cpf = cpf;
        this.partido = partido;
    }

    public Candidato() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return numero == candidato.numero && Objects.equals(nome, candidato.nome) && Objects.equals(cpf, candidato.cpf) && Objects.equals(partido, candidato.partido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, numero, cpf, partido);
    }

    @Override
    public String toString() {
        return "Candidato [nome=" + nome + ", numero=" + numero + ", cpf=" + cpf + ", partido=" + partido + "]";
    }

}
