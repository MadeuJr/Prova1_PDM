package com.amadeu.avaliacao_1_amadeu.model;

public class Texto {

    private int id;
    private String texto_exibir;
    private int cor_selecionada;

    public Texto(int id, String texto_exibir, int cor_selecionada) {
        this.id = id;
        this.texto_exibir = texto_exibir;
        this.cor_selecionada = cor_selecionada;
    }
    public String getTexto_exibir() {
        return texto_exibir;
    }

    public void setTexto_exibir(String texto_exibir) {
        this.texto_exibir = texto_exibir;
    }

    public int getCor_selecionada() {
        return cor_selecionada;
    }

    public void setCor_selecionada(int cor_selecionada) {
        this.cor_selecionada = cor_selecionada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
