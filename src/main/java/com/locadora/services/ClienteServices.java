package com.locadora.services;

import com.locadora.domain.Cliente;
import com.locadora.exception.ClienteInformationException;
import com.locadora.repository.ClienteRepository;

import java.util.Optional;

public class ClienteServices {


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
