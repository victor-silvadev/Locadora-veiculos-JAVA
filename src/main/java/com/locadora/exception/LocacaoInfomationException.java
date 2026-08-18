package com.locadora.exception;

/**
 * Exception thrown when an error occurs related to rental information.
 * <p>
 * This exception is thrown when there are issues with rental data validation or processing,
 * such as invalid dates, unavailable vehicles, or insufficient payment.
 * It extends RuntimeException, making it an unchecked exception.
 * </p>
 * <p>
 * Exceção lançada quando um erro ocorre relacionado às informações de aluguel.
 * Esta exceção é lançada quando há problemas com validação ou processamento de dados de aluguel,
 * como datas inválidas, veículos indisponíveis ou pagamento insuficiente.
 * Estende RuntimeException, tornando-a uma exceção não verificada.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class LocacaoInfomationException extends RuntimeException {
    /**
     * Constructs a LocacaoInfomationException with a specific message.
     * <p>
     * Constructs a new LocacaoInfomationException with the specified detail message.
     * </p>
     * <p>
     * Constrói uma LocacaoInfomationException com uma mensagem específica.
     * Constrói uma nova LocacaoInfomationException com a mensagem de detalhe especificada.
     * </p>
     *
     * @param message the detail message explaining the error / mensagem de detalhe explicando o erro
     */
    public LocacaoInfomationException(String message) {
        super(message);
    }
}
