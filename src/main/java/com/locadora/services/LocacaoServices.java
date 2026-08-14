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
import java.util.Optional;

public class LocacaoServices {

    public static void salvarLocacao(String cpf, String placa, LocalDate dataInicio,LocalDate dataFimPrevista){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarTodasInfoVeiculoporplaca(placa);
        Optional<Cliente> clienteOptional = ClienteRepository.buscarPorCpf(cpf);
        LocalDate localDate = LocalDate.now();

        if (!clienteOptional.isPresent()){
            throw new LocacaoInfomationException("Cliente nao encontrado, registre-se antes de seguir em frente");

        } else if (!optionalVeiculo.isPresent()){
            throw new LocacaoInfomationException("Veiculo nao encontrado, tente outra placa antes de seguir em frente");

        }else if (dataInicio.isBefore(localDate) || dataFimPrevista.isBefore(localDate)){
            throw new LocacaoInfomationException("As datas ultilizadas são incopativeis, tente outra data antes de seguir em frente");
        }

        Veiculo veiculo = optionalVeiculo.get();
        Cliente cliente = clienteOptional.get();
        if (veiculo.getStatus() == Status.ALUGADO){
            throw new LocacaoInfomationException("Selecione um veiculo com o status DISPONIVEL para seguir em frente");

        }
        Locacao locacao = new Locacao(cliente,veiculo,dataInicio,dataFimPrevista);
        LocacaoRepository.salvar(locacao);
        veiculo.setStatus(Status.ALUGADO);
    }

    public static Locacao efetuarDevoluçao(String placa,double valorPagar){
        Locacao devolucao = LocacaoRepository.devolucao(placa);
        return null;

    }
}
