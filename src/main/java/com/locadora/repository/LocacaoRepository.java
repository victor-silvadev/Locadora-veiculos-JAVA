package com.locadora.repository;

import com.locadora.domain.Locacao;
import com.locadora.domain.Status;

import java.time.LocalDate;

/**
 * Repository facade for rental transaction data access operations.
 * <p>
 * This class provides a simplified interface to LocacaoRepositoryJdbc, handling
 * all rental-related database operations such as save, update, and delete.
 * It acts as a bridge between the service layer and the JDBC implementation.
 * </p>
 * <p>
 * Fachada de repositório para operações de acesso a dados de transação de aluguel.
 * Esta classe fornece uma interface simplificada para LocacaoRepositoryJdbc, gerenciando
 * todas as operações de banco de dados relacionadas a aluguéis, como salvar, atualizar e deletar.
 * Atua como uma ponte entre a camada de serviço e a implementação JDBC.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class LocacaoRepository {

    /**
     * Saves a rental transaction to the database.
     * <p>
     * Delegates to LocacaoRepositoryJdbc to persist the rental object.
     * </p>
     * <p>
     * Salva uma transação de aluguel no banco de dados.
     * Delega para LocacaoRepositoryJdbc para persistir o objeto aluguel.
     * </p>
     *
     * @param locacao the rental transaction object to be saved / objeto aluguel a ser salvo
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void salvar(Locacao locacao) {
        LocacaoRepositoryJdbc.salvar(locacao);
    }

    /**
     * Deletes a rental transaction from the database.
     * <p>
     * Removes a rental record from the database. Only rentals with PAGO status can be deleted.
     * </p>
     * <p>
     * Deleta uma transação de aluguel do banco de dados.
     * Remove um registro de aluguel do banco de dados. Apenas aluguéis com status PAGO podem ser deletados.
     * </p>
     *
     * @param locacao the rental transaction object to be deleted / objeto aluguel a ser deletado
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void deletar(Locacao locacao) {
        LocacaoRepositoryJdbc.deletar(locacao);
    }

    /**
     * Updates a rental transaction's status in the database.
     * <p>
     * Changes the status of a rental transaction (e.g., EM_ANDAMENTO, ATRASADO, PAGO, etc.).
     * </p>
     * <p>
     * Atualiza o status de uma transação de aluguel no banco de dados.
     * Altera o status de uma transação de aluguel (ex: EM_ANDAMENTO, ATRASADO, PAGO, etc.).
     * </p>
     *
     * @param id     the rental transaction's unique identifier / identificador único da transação
     * @param status the new status for the rental / novo status para o aluguel
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizar(Long id, Status status) {
        LocacaoRepositoryJdbc.atualizar(id, status);
    }

    /**
     * Updates rental payment information in the database.
     * <p>
     * Updates the return date and total value paid for a rental transaction.
     * </p>
     * <p>
     * Atualiza as informações de pagamento de um aluguel no banco de dados.
     * Atualiza a data de devolução e valor total pago de uma transação de aluguel.
     * </p>
     *
     * @param id       the rental transaction's unique identifier / identificador único da transação
     * @param datadev  the return date / data de devolução
     * @param valor    the total rental value to be paid / valor total do aluguel a ser pago
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void atualizarPagamentoL(Long id, LocalDate datadev, double valor) {
        LocacaoRepositoryJdbc.atualizarLocacao(id, datadev, valor);
    }

    /**
     * Retrieves and displays all rental transactions from the database.
     * <p>
     * Fetches all rental transactions and prints them to the console.
     * </p>
     * <p>
     * Recupera e exibe todas as transações de aluguel do banco de dados.
     * Busca todas as transações de aluguel e as imprime no console.
     * </p>
     *
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static void buscarPorTodos() {
        LocacaoRepositoryJdbc.buscarTodos();
    }

    /**
     * Retrieves a rental transaction by its ID.
     * <p>
     * Searches for a rental transaction in the database using the vehicle's ID.
     * </p>
     * <p>
     * Recupera uma transação de aluguel por seu ID.
     * Pesquisa uma transação de aluguel no banco de dados usando o ID do veículo.
     * </p>
     *
     * @param id the vehicle's unique identifier (used to search for associated rental)
     *           / identificador único do veículo (usado para pesquisar o aluguel associado)
     * @return the rental transaction associated with the vehicle / transação de aluguel associada ao veículo
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Locacao buscarPorId(Long id) {
        return LocacaoRepositoryJdbc.buscarPorId(id);
    }

    /**
     * Retrieves rental information for a vehicle's return process.
     * <p>
     * Fetches detailed rental information including customer name, vehicle plate, and dates
     * using the vehicle's license plate.
     * </p>
     * <p>
     * Recupera informações de aluguel para o processo de devolução de um veículo.
     * Busca informações detalhadas de aluguel incluindo nome do cliente, placa do veículo e datas
     * usando a placa de licença do veículo.
     * </p>
     *
     * @param placa the vehicle's license plate / placa de licença do veículo
     * @return the rental transaction with return information / transação de aluguel com informações de devolução
     * @throws RuntimeException if an error occurs during database operation
     *                          / se um erro ocorrer durante a operação do banco de dados
     */
    public static Locacao devolucao(String placa) {
        return LocacaoRepositoryJdbc.devolucao(placa);
    }
}
