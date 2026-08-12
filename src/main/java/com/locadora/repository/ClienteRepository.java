package com.locadora.repository;

import com.locadora.domain.Cliente;

public class ClienteRepository {

    public static Cliente salvar(Cliente cliente){
        ClienteRepositoryJdbc.salvar(cliente);
        return cliente;
    }

    public static void buscarTodos(){
        ClienteRepositoryJdbc.buscarTodos();
    }

    public static void buscarPorId(Long id){
        ClienteRepositoryJdbc.buscarPorId(id);
    }

    public static boolean buscarPorCpf(String cpf){
        if(ClienteRepositoryJdbc.buscarPorCpf(cpf).isPresent()){
            return true;
        }
        return false;

    }

    public static void atualizar(Cliente cliente){
        ClienteRepositoryJdbc.atualizar(cliente);
    }

    public static void deletar(long id){
        ClienteRepositoryJdbc.deletar(id);
    }
}
