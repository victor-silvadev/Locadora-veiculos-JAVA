package com.locadora.app;

import com.locadora.repository.ClienteRepository;
import com.locadora.services.ClienteServices;

public class teste {
    public static void main(String[] args) {
//        ClienteServices.cadastrarCliente("joao","21345633403","jorgealgusto@gmail.com");
//        ClienteServices.entrarNaConta("70716998602");
        ClienteRepository.buscarTodos();
    }
}
