package com.locadora.app;

import com.locadora.services.Projeto;

/**
 * Main application entry point for the console rental application.
 * <p>
 * Bootstraps the application by delegating to the Projeto helper.
 * </p>
 * <p>
 * Ponto de entrada principal da aplicação de console de locação.
 * Inicializa a aplicação delegando para o helper Projeto.
 * </p>
 *
 * @author Locadora de Veículos
 * @since 2024
 */
public class LocacaoApplication {
    /**
     * Main method that starts the application.
     *
     * @param args command line arguments / argumentos de linha de comando
     */
    public static void main(String[] args) {
        // Digite qualquer palavra e inicie o projeto
        Projeto.start("");
    }
}
