package com.locadora.repository;

import com.locadora.domain.Cliente;
import com.locadora.domain.Veiculo;

import java.util.Optional;

public class VeiculoRepository {

    public static Veiculo salvar(Veiculo veiculo){
        VeiculoRepositoryJdbc.slavar(veiculo);
        return veiculo;
    }

    public static void buscarTodosVeiculos(){
        VeiculoRepositoryJdbc.buscarTodos();
    }

    public static void buscarVeiculoPorId(Long id){
        VeiculoRepositoryJdbc.buscarPorId(id);
    }

    public static void buscarVeiculoporplaca(String placa){
        VeiculoRepositoryJdbc.buscarPorPlaca(placa);
    }

    public static void atualizar(Veiculo veiculo){
        VeiculoRepositoryJdbc.atualizar(veiculo);
    }

    public static void deletar(long id){
        VeiculoRepositoryJdbc.deletar(id);
    }
}
