package com.locadora.services;

import com.locadora.domain.Status;
import com.locadora.domain.Veiculo;
import com.locadora.exception.VeiculoInformationException;
import com.locadora.repository.VeiculoRepository;

import java.util.Optional;

/**
 * Service layer for vehicle-related operations.
 * <p>
 * Provides business logic for creating, updating and deleting vehicles.
 * Methods validate input and delegate persistence to the repository layer.
 * </p>
 * <p>
 * Camada de serviço para operações relacionadas a veículos.
 * Fornece lógica de negócio para criação, atualização e remoção de veículos.
 * Os métodos validam entradas e delegam a persistência para a camada de repositório.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class VeiculoService {

    /**
     * Adiciona um novo veículo após validação dos dados.
     * <p>
     * Validates input parameters and persists a new vehicle with status DISPONIVEL.
     * </p>
     * <p>
     * Adiciona um novo veículo ao sistema após validar placa, modelo e preço por diária.
     * </p>
     *
     * @param placa         vehicle license plate / placa do veículo
     * @param modelo        vehicle model / modelo do veículo
     * @param precoPorDiaria daily price for renting / preço por diária
     * @throws VeiculoInformationException when validation fails or vehicle already exists
     *                                      / quando a validação falha ou veículo já existe
     */
    public static void adicionarVeiculo(String placa, String modelo, double precoPorDiaria){
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

        Veiculo veiculo = new Veiculo(placa.toUpperCase(),modelo,precoPorDiaria, Status.DISPONIVEL);
         VeiculoRepository.salvar(veiculo);
    }



    /**
     * Atualiza as informações de um veículo existente.
     * <p>
     * Busca o veículo pela placa e, se encontrado, delega a atualização ao repositório.
     * </p>
     *
     * @param placa the vehicle license plate / placa do veículo
     * @param status the new status for the vehicle / novo status do veículo
     * @throws VeiculoInformationException if the vehicle is not found / se o veículo não for encontrado
     */
    public static void atualizarInformacoesVeiculo(String placa,Status status){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarVeiculoporplaca(placa);

        if (!optionalVeiculo.isPresent()){
            throw new VeiculoInformationException("Veiculo nao encontrado em nosso banco de dados");
        }
        Veiculo veiculo = optionalVeiculo.get();
        VeiculoRepository.atualizar(veiculo);
    }



    /**
     * Deleta um veículo existente a partir da placa.
     * <p>
     * Procura o veículo pela placa e deleta o registro no repositório caso exista.
     * </p>
     *
     * @param placa the vehicle license plate / placa do veículo
     * @throws VeiculoInformationException if the vehicle is not found / se o veículo não for encontrado
     */
    public static void deletarVeiculo(String placa){
        Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarVeiculoporplaca(placa);

        if (!optionalVeiculo.isPresent()){
            throw new VeiculoInformationException("Veiculo nao encontrado em nosso banco de dados");
        }
        Veiculo veiculo = optionalVeiculo.get();
        VeiculoRepository.deletar(veiculo.getId());
    }
}
