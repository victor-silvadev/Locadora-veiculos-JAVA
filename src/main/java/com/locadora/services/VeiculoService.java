package com.locadora.services;

import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;
import com.locadora.exception.VeiculoInformationException;
import com.locadora.repository.VeiculoRepository;

import java.util.Optional;

public class VeiculoService {

    public static Veiculo adicionarVeiculo(String placa, String modelo, double precoPorDiaria){
        if (placa.isBlank() || modelo.isBlank()){
            throw new VeiculoInformationException("Adicione as informaçoes restantes");

        }else if(precoPorDiaria == 0.0){
            throw new VeiculoInformationException("Adicione o preço do veiculo");

        } else if (placa.length() > 7) {
            throw new VeiculoInformationException("Modelo de placa invalido!");
        }

        if(VeiculoRepository.buscarVeiculoporplaca(placa).isPresent()){
            throw new VeiculoInformationException("Veiculo com a placa: "+ placa+", Ja existe em nosso banco de dados");
        }

        Veiculo veiculo = new Veiculo(placa,modelo,precoPorDiaria, Status.DISPONIVEL);
        return VeiculoRepository.salvar(veiculo);
    }



    public static void atualizarInformacoesVeiculo(String placa){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarVeiculoporplaca(placa);

        if (!optionalVeiculo.isPresent()){
            throw new VeiculoInformationException("Veiculo nao encontrado em nosso banco de dados");
        }
        Veiculo veiculo = optionalVeiculo.get();
        VeiculoRepository.atualizar(veiculo);
    }



    public static void deletarVeiculo(String placa){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarVeiculoporplaca(placa);

        if (!optionalVeiculo.isPresent()){
            throw new VeiculoInformationException("Veiculo nao encontrado em nosso banco de dados");
        }
        Veiculo veiculo = optionalVeiculo.get();
        VeiculoRepository.deletar(veiculo.getId());
    }
}
