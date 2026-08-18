package com.locadora.domain;

/**
 * Enum representing the possible status values for vehicles and rentals.
 * <p>
 * This enumeration defines all possible states that a vehicle or rental can have
 * in the vehicle rental system:
 * - DISPONIVEL: The vehicle is available for rental
 * - ALUGADO: The vehicle is currently rented
 * - EM_ANDAMENTO: The rental is in progress
 * - ATRASADO: The rental has exceeded the expected return date
 * - PAGO: The rental has been paid
 * </p>
 * <p>
 * Enumeração que representa os valores de status possíveis para veículos e aluguéis.
 * Esta enumeração define todos os estados possíveis que um veículo ou aluguel pode ter
 * no sistema de aluguel de veículos:
 * - DISPONIVEL: O veículo está disponível para aluguel
 * - ALUGADO: O veículo está atualmente alugado
 * - EM_ANDAMENTO: O aluguel está em andamento
 * - ATRASADO: O aluguel excedeu a data de devolução prevista
 * - PAGO: O aluguel foi pago
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public enum Status {
    /**
     * Status indicating the vehicle is available for rental.
     * <br>Status indicando que o veículo está disponível para aluguel.
     */
    DISPONIVEL,

    /**
     * Status indicating the vehicle is currently rented.
     * <br>Status indicando que o veículo está atualmente alugado.
     */
    ALUGADO,

    /**
     * Status indicating the rental is in progress.
     * <br>Status indicando que o aluguel está em andamento.
     */
    EM_ANDAMENTO,

    /**
     * Status indicating the rental has exceeded the expected return date (late).
     * <br>Status indicando que o aluguel excedeu a data de devolução prevista (atrasado).
     */
    ATRASADO,

    /**
     * Status indicating the rental has been paid.
     * <br>Status indicando que o aluguel foi pago.
     */
    PAGO
}
