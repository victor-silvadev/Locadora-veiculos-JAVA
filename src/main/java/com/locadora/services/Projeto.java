package com.locadora.services;

import com.locadora.threads.GerenciadorDeTask;

public class Projeto {
    public static void start(String palavra){
        GerenciadorDeTask g = new GerenciadorDeTask();
        Thread homenzinhoTorto = new Thread(g);
        homenzinhoTorto.start();
    }
}
