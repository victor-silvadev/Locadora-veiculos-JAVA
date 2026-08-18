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

/**
 * Interactive task manager that runs the console UI loop.
 * <p>
 * This class implements Runnable and provides a simple console-based menu for
 * user registration, login and operations related to vehicle rental, such as
 * renting, registering vehicles and returning vehicles.
 * </p>
 * <p>
 * Gerenciador de tarefas interativo que executa o loop da interface de console.
 * Implementa Runnable e fornece um menu para cadastro, login e operações de locação
 * como alugar, cadastrar e devolver veículos.
 * </p>
 *
 * @author Locadora de Veículos
 * @since 2024
 */
public class GerenciadorDeTask implements Runnable{
    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    /**
     * Main loop executed by the thread.
     * <p>
     * Presents a simple menu for access, registration and exits when requested.
     * </p>
     *
     * @throws RuntimeException for unexpected runtime errors during console interaction
     */
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



    /**
     * Performs the interactive customer registration flow.
     * <p>
     * Reads user input from console and calls ClienteServices.cadastrarCliente.
     * </p>
     *
     * @throws RuntimeException if registration validation fails
     */
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


    /**
     * Performs the interactive customer login flow.
     * <p>
     * Prompts for CPF and delegates validation to ClienteServices. On success
     * it navigates to the customer services menu.
     * </p>
     *
     * @throws RuntimeException if CPF is invalid or not registered
     */
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

    /**
     * Presents the logged-in customer's service menu and routes choices.
     *
     * @throws RuntimeException for invalid menu input
     */
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


    /**
     * Interactive flow to perform vehicle rental.
     * <p>
     * Shows available vehicles, reads dates and CPF and delegates to LocacaoServices.
     * </p>
     *
     * @throws RuntimeException when validation fails or data parsing is incorrect
     */
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





    /**
     * Interactive flow to register a new vehicle.
     * <p>
     * Reads vehicle data from console and calls VeiculoService.adicionarVeiculo.
     * </p>
     *
     * @throws RuntimeException when vehicle data is invalid or persistence fails
     */
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



  /**
   * Interactive flow to process vehicle return and payment.
   * <p>
   * Reads plate and return date then delegates to LocacaoServices for processing.
   * </p>
   *
   * @throws RuntimeException when return processing or payment fails
   */
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