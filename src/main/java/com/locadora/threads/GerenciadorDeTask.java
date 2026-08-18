package com.locadora.threads;

import com.locadora.domain.Cliente;
import com.locadora.domain.Locacao;
import com.locadora.domain.Veiculo;
import com.locadora.repository.ClienteRepository;
import com.locadora.repository.LocacaoRepository;
import com.locadora.repository.VeiculoRepository;
import com.locadora.services.ClienteServices;
import com.locadora.services.LocacaoServices;
import com.locadora.services.VeiculoService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class GerenciadorDeTask implements Runnable{
private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public void run() {
        while(true){
            System.out.println("=================================================");
            System.out.println("||  Bem-Vindo, Escolha umas das opções abaixo  ||");
            System.out.println("=================================================");

            System.out.println("(1) - Entrar");
            System.out.println("(2) - Cadastre-se");
            System.out.println("(0) - Sair");
            int i = Integer.parseInt(SCANNER.nextLine());

            switch (i) {
                case 1 -> executarEntradaCliente();

                case 2 -> executarCadastroCliente();

                case 0 -> {
                    System.out.println("Ate mais!!!");
                    return;
                }

                default -> System.out.println("Digite uma das opções!\n");

            }
        }
    }



    public void executarCadastroCliente(){

        while(true){
            System.out.println("==================================================================");
            System.out.println("||    Faça o cadastro e fique por dentro dos nossos serviços.   ||");
            System.out.println("==================================================================");

            System.out.println("\nDigite seu Nome:");
            String nome = SCANNER.nextLine();

            System.out.println("\nDigite seu Cpf:");
            String cpf = SCANNER.nextLine();

            System.out.println("\nDigite seu Email:");
            String email = SCANNER.nextLine();

            try {
                System.out.print("Verificando informações");
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);
                    System.out.print(".");
                }

                System.out.println("\n");

                ClienteServices.cadastrarCliente(nome, cpf, email);

                System.out.println("\nCliente cadastrado com sucesso!\n");
                Thread.sleep(2000);
                return;

            } catch (Exception e) {
                System.out.println("\nInformações invalidas, Tente novamente!\n");
                return;
            }
        }
    }


    public void executarEntradaCliente(){
        boolean clienteE = true;
        while(clienteE){
            System.out.println("\nDigite seu CPF:");
            String cpf = SCANNER.nextLine();

            try {
                System.out.print("Verificando CPF");

                for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);
                    System.out.print(".");
                }
                System.out.println("\n");

                ClienteServices.entrarNaConta(cpf);
                clienteE = false;
                executarServicosCliente();

            } catch (Exception e) {
                System.out.println("\nCPF invalido ou não cadastrado!\n");
                return;
            }
        }
    }

    public void executarServicosCliente(){
        boolean clienteS = true;
        while(clienteS){
            System.out.println("\nDigite uma opção:\n");

            System.out.println("(1) - Aluguel de veiculo");
            System.out.println("(2) - Cadastro de veiculo");
            System.out.println("(3) - Efetuar devoluçao");
            System.out.println("(0) - Retornar");
            int i = Integer.parseInt(SCANNER.nextLine());

            switch (i){
                case 1 -> aluguelVeicular();

                case 2 -> cadastroVeicular();

                case 3 -> efetuarDevolucao();

                case 0 -> clienteS = false;

                default -> System.out.println("Escolha uma das 3 opções");
            }
        }
    }


    public void aluguelVeicular(){
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true){
            System.out.println("\nVeiculos disponiveis:\n");
            List<Veiculo> listaVeiculos = VeiculoRepository.buscarTodosVeiculos();

            if (listaVeiculos.isEmpty()){
                System.out.println("\nNão hà veiculos disponiveis no momento\n");
                return;
            }
            System.out.println(listaVeiculos);

            System.out.println("\nDigite a placa do veiculo que deseja alugar ou 'Sair' para retornar:");
            String placa = SCANNER.nextLine();

            if("SAIR".equals(placa.toUpperCase())) return;

            System.out.println("\nDigite a data que deseja dar inicio ao aluguel:");
            System.out.println("Ex: 00/00/0000");
            String dataInicio = SCANNER.nextLine();

            System.out.println("\nDigite a data fim prevista que deseja efetuar a devolução:");
            System.out.println("Ex: 00/00/0000");
            String dataFimPrevista = SCANNER.nextLine();

            try {
                LocalDate dataInicioFormat = LocalDate.parse(dataInicio,dt);
                LocalDate dataFimFormat = LocalDate.parse(dataFimPrevista,dt);
                Optional<Veiculo> optionalVeiculo = VeiculoRepository.buscarVeiculoporplaca(placa);

                    System.out.print("\nValidando informações");
                    for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);
                    System.out.print(".");

                if (!optionalVeiculo.isPresent() ||
                dataInicioFormat.isBefore(LocalDate.now()) || dataFimFormat.isBefore(dataInicioFormat)) {
                    System.out.println("Informaçoes invalidas!");
                    return;
                    }

                    System.out.println("\n");

                    System.out.println("\nInformações confirmadas!\n");
                    Thread.sleep(1000);

                    System.out.println("\nDigite seu CPF para confirmar o aluguel ou 'Sair' para cancelar e retornar:");
                    String cpf = SCANNER.nextLine();

                    if("SAIR".equals(cpf.toUpperCase())) return;

                    Optional<Cliente> clienteOptional = ClienteRepository.buscarPorCpf(cpf);
                    System.out.print("\nConfirmando aluguel");
                    for (int ii = 0; ii < 3; ii++) {
                        Thread.sleep(1000);
                        System.out.print(".");
                    }

                    System.out.print("\n");

                    if (clienteOptional.isPresent()){
                        LocacaoServices.salvarLocacao(cpf,placa,dataInicioFormat,dataFimFormat);
                    Thread.sleep(1000);
                        System.out.print("\nAluguel confirmado om sucesso!\n");
                        Thread.sleep(1000);
                        executarServicosCliente();

                    }

                }

            } catch (Exception e) {
                System.out.println("Informações invalidas, Tente novamente!");
                aluguelVeicular();
            }

        }
    }





    public void cadastroVeicular() {
        while(true){
            System.out.println("\nDigite a placa do veiculo:");
            String placa = SCANNER.nextLine();

            System.out.println("\nDigite o modelo do veiculo:");
            String modelo = SCANNER.nextLine();

            System.out.println("\nDigite o preço do aluguel por diaria:");
            System.out.println("Ex: 00.00");
            double valorDiaria = Double.parseDouble(SCANNER.nextLine());


            try {
                VeiculoService.adicionarVeiculo(placa, modelo, valorDiaria);

                System.out.println("oioioi");
                System.out.print("Validando informações do veiculo");
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(1000);
                    System.out.print(".");
                }
                System.out.println("\n");
                System.out.println("Veiculo cadastrado com sucesso!\n");
                Thread.sleep(1000);
                return;

            } catch (Exception e) {
                System.out.println("Informações invalidas!");
                cadastroVeicular();
            }
        }
    }



  public void efetuarDevolucao(){
      DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            System.out.println("\nDigite a placa do veiculo ou 'Sair' para retorar:");
            String placa = SCANNER.nextLine();

            if("SAIR".equalsIgnoreCase(placa)) return;

            System.out.println("\nDigite a data que esta efetuando a devolução:");
            System.out.println("Ex: 00/00/0000");
            LocalDate dataFormat = LocalDate.parse(SCANNER.nextLine(), dt);

            try {
                LocacaoServices.efetuarDevolucao(placa,dataFormat);

                System.out.println("\ndigite o valor que ira pagar:");
                double valorCliente = Double.parseDouble(SCANNER.nextLine());

                    System.out.print("\nConfirmando Pagamento");
                    for (int i = 0; i < 3; i++) { Thread.sleep(1000); System.out.print("."); }

                try {
                    System.out.println("\n");
                    LocacaoServices.pagamentoLocacao(placa, valorCliente);
                    Thread.sleep(1000);
                    System.out.println("\nPagamento efetuado com sucesso!");
                    executarServicosCliente();

                } catch (Exception e){
                    System.out.print("\nValor invalido, Retornando");
                    for (int i = 0; i < 3; i++) { Thread.sleep(1000); System.out.print("."); }
                    return;
                }


            } catch (Exception e) {
                System.out.println("Informações invalidas, Tente novamente!");
                efetuarDevolucao();
            }

        }
  }
}