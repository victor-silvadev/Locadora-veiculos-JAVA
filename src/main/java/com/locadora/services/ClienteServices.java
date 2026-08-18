package com.locadora.services;

import com.locadora.domain.Cliente;
import com.locadora.exception.ClienteInformationException;
import com.locadora.repository.ClienteRepository;

import java.util.Optional;

/**
 * Service layer for customer (Cliente) related operations.
 * <p>
 * Handles business rules for registering customers and authenticating access.
 * Delegates persistence to the ClienteRepository.
 * </p>
 * <p>
 * Camada de serviço para operações relacionadas ao cliente (Cliente).
 * Trata regras de negócio para cadastro de clientes e autenticação.
 * Delegue a persistência para o ClienteRepository.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class ClienteServices {


    /**
     * Registers a new customer after validating name, CPF and email.
     * <p>
     * Validates inputs, checks for CPF duplication and persists the new customer.
     * </p>
     *
     * @param nome  the customer's full name / nome completo do cliente
     * @param cpf   the customer's CPF (11 digits) / CPF do cliente (11 dígitos)
     * @param email the customer's email address / endereço de email do cliente
     * @return the persisted Cliente object / objeto Cliente persistido
     * @throws ClienteInformationException if validation fails or CPF already exists
     *                                     / se a validação falhar ou CPF já estiver cadastrado
     */
    public static Cliente cadastrarCliente(String nome, String cpf, String email){
        if (nome == null || nome.isBlank()) {
            throw new ClienteInformationException("O nome do cliente é obrigatório!");
        }

        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido!");
        }

        // Valida Email (Regex simples ainda é o padrão para Java puro)
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new ClienteInformationException("E-mail inválido: " + email);
        }

        Optional<Cliente> optionalCliente = ClienteRepository.buscarPorCpf(cpf);
        if (optionalCliente.isPresent()){
            throw new ClienteInformationException("Este Cpf ja esta cadastrado!");
        }

        Cliente cliente = new Cliente(nome,cpf,email);
        return ClienteRepository.salvar(cliente);
    }



    /**
     * Validates that a customer with the given CPF exists to allow access.
     * <p>
     * Validates the CPF format and throws if the customer is not registered.
     * </p>
     *
     * @param cpf the customer's CPF (11 digits) / CPF do cliente (11 dígitos)
     * @throws IllegalArgumentException for invalid CPF format / para formato de CPF inválido
     * @throws ClienteInformationException if the CPF is not registered / se o CPF não estiver cadastrado
     */
    public static void entrarNaConta(String cpf){
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido!");
        }

        Optional<Cliente> clienteOptional = ClienteRepository.buscarPorCpf(cpf);
        if (!clienteOptional.isPresent()){
            throw new ClienteInformationException("Este Cpf não esta cadastrado!");
        }
    }

}
