package com.locadora.services;

import com.locadora.threads.GerenciadorDeTask;

/**
 * Small project bootstrap helper.
 * <p>
 * Starts the interactive task manager thread used by the console application.
 * </p>
 * <p>
 * Helper de inicialização do projeto.
 * Inicia a thread do gerenciador de tarefas utilizada pela aplicação de console.
 * </p>
 *
 * @author Locadora de Veículos
 * @since 2024
 */
public class Projeto {
    /**
     * Starts the application by launching the GerenciadorDeTask thread.
     *
     * @param palavra a placeholder parameter (not used) / parâmetro placeholder (não utilizado)
     */
    public static void start(String palavra){
        GerenciadorDeTask g = new GerenciadorDeTask();
        Thread homenzinhoTorto = new Thread(g);
        homenzinhoTorto.start();
    }
}
