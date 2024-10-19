package com.example.eleicao.modelos;

import java.util.Objects;

public class Partido {

    private String nome;
    private int numero;
    private String cnpj;

    public Partido(String nome, int numero, String cnpj) {
        this.nome = nome;
        this.numero = numero;
        this.cnpj = cnpj;
    }

    public Partido() {}

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return numero == partido.numero && Objects.equals(nome, partido.nome) && Objects.equals(cnpj, partido.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, numero, cnpj);
    }

    @Override
    public String toString() {
        return "Partido [nome=" + nome + ", numero=" + numero + ", cnpj=" + cnpj + "]";
    }
}