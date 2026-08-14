package com.locadora.repository;

import com.locadora.domain.Locacao;
import com.locadora.domain.Status;

import java.time.LocalDate;

public class LocacaoRepository {

    public static void salvar(Locacao locacao){
        LocacaoRepositoryJdbc.salvar(locacao);
    }

    public static void deletar(Locacao locacao){
        LocacaoRepositoryJdbc.deletar(locacao);
    }

    public static void atualizar(Long id, Status status){
        LocacaoRepositoryJdbc.atualizar(id,status);
    }

    public static void atualizarPagamentoL(Long id, LocalDate localdate, double valor ){
         LocacaoRepositoryJdbc.atualizarLocacao(id,localdate,valor);
    }

    public static void buscarPorTodos(){
        LocacaoRepositoryJdbc.buscarTodos();
    }

    public static Locacao buscarPorId(Long id){
       return LocacaoRepositoryJdbc.buscarPorId(id);
    }

    public static Locacao devolucao(String placa){
        return LocacaoRepositoryJdbc.devolucao(placa);

    }
}
