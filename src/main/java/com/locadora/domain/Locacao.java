package com.locadora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locacao {
    private Long id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private LocalDate devolucao;
    private double valorTotal;
    private Status status;


    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFimPrevista) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFimPrevista = dataFimPrevista;
    }

    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFimPrevista, LocalDate devolucao, double valorTotal) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFimPrevista = dataFimPrevista;
        this.devolucao = devolucao;
        this.valorTotal = valorTotal;
    }


}
