package com.locadora.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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


    public Veiculo(Long id, String placa, String modelo, double valorDiaria, Status status) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("🚗 [%s] %-7s | Diária: R$ %6.2f | Status: %s\n",
                placa, modelo, valorDiaria, status);
    }
}
