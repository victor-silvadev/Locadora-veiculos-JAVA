package com.locadora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Veiculo {
    private Long id;
    private String placa;
    private String modelo;
    private double valorDiaria;
    private Status status;

    public Veiculo(String placa, String modelo, double valorDiaria, Status status) {
        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.status = status;
    }
}
