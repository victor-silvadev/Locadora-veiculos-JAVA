package com.locadora.repository;

import com.locadora.domain.Cliente;

public class ClienteRepository {

    public static Cliente salvar(Cliente cliente){
        ClienteRepositoryJdbc.salvar(cliente);
        return cliente;
    }

    public static void buscarTodos(){
        System.out.println( ClienteRepositoryJdbc.buscarTodos());
    }

    public static void buscarPorId(Long id){
        ClienteRepositoryJdbc.buscarPorId(id);
    }

    public static boolean buscarPorCpf(String cpf){
        return ClienteRepositoryJdbc.buscarPorCpf(cpf).isPresent();

    }

    public static void atualizar(Cliente cliente){
        ClienteRepositoryJdbc.atualizar(cliente);
    }

    public static void deletar(long id){
        ClienteRepositoryJdbc.deletar(id);
    }

}
