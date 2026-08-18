package com.locadora.exception;

/**
 * Exception thrown when an error occurs related to customer information.
 * <p>
 * This exception is thrown when there are issues with customer data validation or processing,
 * such as invalid CPF, invalid email, or duplicate customer information.
 * It extends RuntimeException, making it an unchecked exception.
 * </p>
 * <p>
 * Exceção lançada quando um erro ocorre relacionado às informações do cliente.
 * Esta exceção é lançada quando há problemas com validação ou processamento de dados do cliente,
 * como CPF inválido, email inválido ou informações duplicadas do cliente.
 * Estende RuntimeException, tornando-a uma exceção não verificada.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class ClienteInformationException extends RuntimeException {
    /**
     * Constructs a ClienteInformationException with a specific message.
     * <p>
     * Constructs a new ClienteInformationException with the specified detail message.
     * </p>
     * <p>
     * Constrói uma ClienteInformationException com uma mensagem específica.
     * Constrói uma nova ClienteInformationException com a mensagem de detalhe especificada.
     * </p>
     *
     * @param message the detail message explaining the error / mensagem de detalhe explicando o erro
     */
    public ClienteInformationException(String message) {
        super(message);
    }
}
