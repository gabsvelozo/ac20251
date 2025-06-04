package br.edu.cs.poo.ac.seguro.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.io.Serializable;

public abstract class Segurado implements Registro{
    private static final long serialVersionUID = 1L;

    private String nome;
    private Endereco endereco;
    private LocalDate dataCriacao;
    private BigDecimal bonus;
    private LocalDate atual = LocalDate.now();

    public Segurado (String nome, Endereco endereco, LocalDate dataCriacao, BigDecimal bonus){
        this.nome = nome;
        this.endereco = endereco;
        this.dataCriacao = dataCriacao;
        this.bonus = bonus;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    protected LocalDate getDataCriacao() {
        return dataCriacao;
    }

    protected void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public int getIdade(){
        return Period.between(dataCriacao, atual).getYears();
    }

    public BigDecimal creditarBonus(BigDecimal valor){
        this.bonus = this.bonus.add(valor);
        return this.bonus;
    }

    public BigDecimal debitarBonus(BigDecimal valor){
        this.bonus = this.bonus.subtract(valor);
        return this.bonus;
    }

    public abstract boolean isEmpresa();

}
