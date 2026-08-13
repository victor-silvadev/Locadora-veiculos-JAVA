package com.locadora.repository;

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

    public static Optional<Veiculo> buscarVeiculoPorId(Long id){
        return VeiculoRepositoryJdbc.buscarPorId(id);
    }

    public static Optional<Veiculo> buscarVeiculoporplaca(String placa){
        return VeiculoRepositoryJdbc.buscarPorPlaca(placa);
    }

    public static void atualizar(Veiculo veiculo){
        VeiculoRepositoryJdbc.atualizar(veiculo);
    }

    public static void deletar(Long id){
        VeiculoRepositoryJdbc.deletar(id);
    }
}
