package com.locadora.services;

import com.locadora.domain.Cliente;
import com.locadora.domain.Locacao;
import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;
import com.locadora.exception.LocacaoInfomationException;
import com.locadora.repository.ClienteRepository;
import com.locadora.repository.LocacaoRepository;
import com.locadora.repository.VeiculoRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class LocacaoServices {

    public static void salvarLocacao(String cpf, String placa, LocalDate dataInicio,LocalDate dataFimPrevista){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarTodasInfoVeiculoporplaca(placa);
        Veiculo veiculo = optionalVeiculo.get();
        Optional<Cliente> clienteOptional = ClienteRepository.buscarPorCpf(cpf);
        Cliente cliente = clienteOptional.get();
        LocalDate localDate = LocalDate.now();

        if (!clienteOptional.isPresent()){
            throw new LocacaoInfomationException("Cliente nao encontrado, registre-se antes de seguir em frente");

        } else if (!optionalVeiculo.isPresent()){
            throw new LocacaoInfomationException("Veiculo nao encontrado, tente outra placa antes de seguir em frente");

        }else if (dataInicio.isBefore(localDate) || dataFimPrevista.isBefore(localDate)){
            throw new LocacaoInfomationException("As datas ultilizadas são incopativeis, tente outra data antes de seguir em frente");
        }


        if (veiculo.getStatus() == Status.ALUGADO){
            throw new LocacaoInfomationException("Selecione um veiculo com o status DISPONIVEL para seguir em frente");

        }
        Locacao locacao = new Locacao(cliente,veiculo,dataInicio,dataFimPrevista);
        LocacaoRepository.salvar(locacao);
        VeiculoRepository.atualizarStatusVeiculo(veiculo.getId());
    }




    public static void efetuarDevolucao(String placa,LocalDate dataDevolucao){
        double valorFinal = 0;

        Optional<Veiculo> veiculoOptional = VeiculoRepository.buscarTodasInfoVeiculoporplaca(placa);
        Veiculo veiculo = veiculoOptional.get();

        Locacao devolucao = LocacaoRepository.devolucao(placa);

        Locacao locacao = LocacaoRepository.buscarPorId(veiculo.getId());
        long diasPara = ChronoUnit.DAYS.between(devolucao.getDataInicio(), dataDevolucao);

        if (devolucao.getDataFimPrevista().isBefore(dataDevolucao)){
            double valor = veiculo.getValorDiaria() * diasPara;
            valorFinal = valor * 1.20;
            LocacaoRepository.atualizar(locacao.getId(), Status.ATRASADO);

        } else {
            valorFinal = veiculo.getValorDiaria() * diasPara;
        }

         LocacaoRepository.atualizarPagamentoL(locacao.getId(), dataDevolucao, valorFinal);
    }





    public static void pagamentoLocacao(String placa,double valorCliente){
        double trocoOuValorFinal = 0;

        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarTodasInfoVeiculoporplaca(placa);
        Veiculo veiculo = optionalVeiculo.get();

        Locacao locacao = LocacaoRepository.buscarPorId(veiculo.getId());
        double valorTotalLocacao = locacao.getValorTotal();

        if (valorTotalLocacao < valorCliente){
            trocoOuValorFinal = valorCliente - valorTotalLocacao;
            System.out.printf(" Valor a pagar: %.2f\n Valor pago: %.2f\n Troco: %.2f",valorTotalLocacao,valorCliente,trocoOuValorFinal);
            LocacaoRepository.atualizar(locacao.getId(),Status.PAGO);
            LocacaoRepository.deletar(locacao);
            VeiculoRepository.atualizarStatusVeiculo(veiculo.getId());

        }else if(valorTotalLocacao == valorCliente) {
            trocoOuValorFinal = valorTotalLocacao - valorCliente;
            System.out.printf(" Valor a pagar: %.2f\n Valor pago: %.2f\n Troco: %.2f",valorTotalLocacao,valorCliente,trocoOuValorFinal);
            LocacaoRepository.atualizar(locacao.getId(),Status.PAGO);
            LocacaoRepository.deletar(locacao);
            VeiculoRepository.atualizarStatusVeiculo(veiculo.getId());

        } else {
            throw new LocacaoInfomationException("Dinheiro insuficiente!");
        }

    }
}
