package com.locadora.exception;

/**
 * Exception thrown when an error occurs related to vehicle information.
 * <p>
 * This exception is thrown when there are issues with vehicle data validation or processing,
 * such as invalid license plate, duplicate vehicles, or missing required information.
 * It extends RuntimeException, making it an unchecked exception.
 * </p>
 * <p>
 * Exceção lançada quando um erro ocorre relacionado às informações do veículo.
 * Esta exceção é lançada quando há problemas com validação ou processamento de dados do veículo,
 * como placa inválida, veículos duplicados ou informações obrigatórias faltando.
 * Estende RuntimeException, tornando-a uma exceção não verificada.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class VeiculoInformationException extends RuntimeException {
    /**
     * Constructs a VeiculoInformationException with a specific message.
     * <p>
     * Constructs a new VeiculoInformationException with the specified detail message.
     * </p>
     * <p>
     * Constrói uma VeiculoInformationException com uma mensagem específica.
     * Constrói uma nova VeiculoInformationException com a mensagem de detalhe especificada.
     * </p>
     *
     * @param message the detail message explaining the error / mensagem de detalhe explicando o erro
     */
    public VeiculoInformationException(String message) {
        super(message);
    }
}
