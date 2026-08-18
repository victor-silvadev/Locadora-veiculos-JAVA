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

/**
 * Service layer for rental (Locacao) operations.
 * <p>
 * Responsible for creating rentals, processing returns and payments. Implements
 * the business rules such as date validation, status checks and price calculation.
 * </p>
 * <p>
 * Camada de serviço para operações de locação.
 * Responsável por criar locações, processar devoluções e pagamentos. Implementa
 * regras de negócio como validação de datas, verificação de status e cálculo de valores.
 * </p>
 *
 * @author Locadora de Veículos
 * @version 1.0
 * @since 2024
 */
public class LocacaoServices {

    /**
     * Creates and persists a new rental (Locacao) after validation.
     * <p>
     * Validates customer and vehicle existence, date ranges and vehicle availability.
     * Persists the Locacao and updates the vehicle status to ALUGADO.
     * </p>
     *
     * @param cpf the customer's CPF / CPF do cliente
     * @param placa the vehicle license plate / placa do veículo
     * @param dataInicio the rental start date / data de início do aluguel
     * @param dataFimPrevista the expected end date / data final prevista
     * @throws LocacaoInfomationException when validation fails (missing customer, vehicle or invalid dates)
     *                                    / quando a validação falha (cliente/veículo ausente ou datas inválidas)
     */
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
        VeiculoRepository.atualizarStatusVeiculo(veiculo.getId(), Status.ALUGADO);
    }




    /**
     * Processes a return (devolução) for a given vehicle and computes the final value.
     * <p>
     * Calculates days between start and return, applies late fees if needed and
     * updates the rental status and payment information in the repository.
     * </p>
     *
     * @param placa the vehicle license plate / placa do veículo
     * @param dataDevolucao the actual return date / data real de devolução
     * @throws LocacaoInfomationException when related records are not found or business rules fail
     *                                    / quando registros relacionados não são encontrados ou regras de negócio falham
     */
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
        System.out.printf("\nNome: %s \nPlaca: %s\nData de devolução: %s\nValor total a pagar: %.2f",devolucao.getCliente().getNome(),placa,dataDevolucao,valorFinal);

    }





    /**
     * Finalizes payment for a rental.
     * <p>
     * Compares the amount provided by the customer with the rental total and
     * performs repository updates (marks as PAGO and frees the vehicle) or throws
     * when the provided amount is insufficient.
     * </p>
     *
     * @param placa the vehicle license plate / placa do veículo
     * @param valorCliente the amount provided by the customer / valor fornecido pelo cliente
     * @throws LocacaoInfomationException when the provided amount is insufficient
     *                                    / quando o valor fornecido é insuficiente
     */
    public static void pagamentoLocacao(String placa,double valorCliente){
        Status status = Status.DISPONIVEL;
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
            VeiculoRepository.atualizarStatusVeiculo(veiculo.getId(),status);

        }else if(valorTotalLocacao == valorCliente) {
            trocoOuValorFinal = valorTotalLocacao - valorCliente;
            System.out.printf(" Valor a pagar: %.2f\n Valor pago: %.2f\n Troco: %.2f",valorTotalLocacao,valorCliente,trocoOuValorFinal);
            LocacaoRepository.atualizar(locacao.getId(),Status.PAGO);
            LocacaoRepository.deletar(locacao);
            VeiculoRepository.atualizarStatusVeiculo(veiculo.getId(),status);

        } else {
            throw new LocacaoInfomationException("Dinheiro insuficiente!");
        }

    }
}
