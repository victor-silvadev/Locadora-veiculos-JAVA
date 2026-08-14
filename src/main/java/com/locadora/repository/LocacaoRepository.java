package com.locadora.repository;

import com.locadora.domain.Locacao;

public class LocacaoRepository {

    public static void salvar(Locacao locacao){
        LocacaoRepositoryJdbc.salvar(locacao);
    }

    public static void deletar(Locacao locacao){
        LocacaoRepositoryJdbc.deletar(locacao);
    }

    public static void atualizar(Locacao locacao){
        LocacaoRepositoryJdbc.atualizar(locacao);
    }

    public static void buscarPorTodos(){
        LocacaoRepositoryJdbc.buscarTodos();
    }

    public static Locacao devolucao(String placa){
        return LocacaoRepositoryJdbc.devolucao(placa);

    }
}
