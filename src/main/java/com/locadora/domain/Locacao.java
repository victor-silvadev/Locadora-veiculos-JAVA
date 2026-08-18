package com.locadora.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Represents a rental transaction (Locacao) in the vehicle rental system.
 * <p>
 * This class encapsulates rental information including the customer, vehicle, rental dates,
 * return date, total value, and status. It uses Lombok annotations to generate getters,
 * setters, equals, hashCode, and toString methods.
 * </p>
 * <p>
 * Representa uma transação de aluguel no sistema de aluguel de veículos.
 * Esta classe encapsula informações de aluguel incluindo cliente, veículo, datas de aluguel,
 * data de devolução, valor total e status.
 * Utiliza anotações Lombok para gerar getters, setters, equals, hashCode e método toString.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locacao {
    /**
     * Unique identifier for the rental transaction.
     * <br>Identificador único da transação de aluguel.
     */
    private Long id;

    /**
     * The customer associated with this rental.
     * <br>O cliente associado a este aluguel.
     */
    private Cliente cliente;

    /**
     * The vehicle being rented.
     * <br>O veículo sendo alugado.
     */
    private Veiculo veiculo;

    /**
     * The start date of the rental.
     * <br>A data de início do aluguel.
     */
    private LocalDate dataInicio;

    /**
     * The expected return date of the rental.
     * <br>A data de devolução prevista do aluguel.
     */
    private LocalDate dataFimPrevista;

    /**
     * The actual return date of the rental.
     * <br>A data real de devolução do aluguel.
     */
    private LocalDate devolucao;

    /**
     * The total value to be paid for the rental in Brazilian Reais (R$).
     * <br>O valor total a ser pago pelo aluguel em Reais (R$).
     */
    private double valorTotal;

    /**
     * Current status of the rental (EM_ANDAMENTO, ATRASADO, PAGO, etc.).
     * <br>Status atual do aluguel (EM_ANDAMENTO, ATRASADO, PAGO, etc.).
     */
    private Status status;

    /**
     * Constructs a Locacao with customer, vehicle, start date, and expected end date.
     * <p>
     * Constructs a new Locacao object for a new rental transaction.
     * The ID, actual return date, total value, and status are not set in this constructor.
     * </p>
     * <p>
     * Constrói uma Locacao com cliente, veículo, data de início e data final prevista.
     * Constrói um novo objeto Locacao para uma nova transação de aluguel.
     * O ID, data real de devolução, valor total e status não são definidos neste construtor.
     * </p>
     *
     * @param cliente            the customer renting the vehicle / cliente alugando o veículo
     * @param veiculo            the vehicle being rented / veículo sendo alugado
     * @param dataInicio         the rental start date / data de início do aluguel
     * @param dataFimPrevista    the expected rental end date / data final prevista do aluguel
     */
    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFimPrevista) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFimPrevista = dataFimPrevista;
    }

    /**
     * Constructs a Locacao with all rental details including return information.
     * <p>
     * Constructs a new Locacao object with complete rental information.
     * This constructor includes the actual return date and total value.
     * </p>
     * <p>
     * Constrói uma Locacao com todos os detalhes de aluguel incluindo informações de devolução.
     * Constrói um novo objeto Locacao com informações completas de aluguel.
     * Este construtor inclui a data real de devolução e valor total.
     * </p>
     *
     * @param cliente            the customer renting the vehicle / cliente alugando o veículo
     * @param veiculo            the vehicle being rented / veículo sendo alugado
     * @param dataInicio         the rental start date / data de início do aluguel
     * @param dataFimPrevista    the expected rental end date / data final prevista do aluguel
     * @param devolucao          the actual return date / data real de devolução
     * @param valorTotal         the total rental value / valor total do aluguel
     */
    public Locacao(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFimPrevista, LocalDate devolucao, double valorTotal) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFimPrevista = dataFimPrevista;
        this.devolucao = devolucao;
        this.valorTotal = valorTotal;
    }

}
