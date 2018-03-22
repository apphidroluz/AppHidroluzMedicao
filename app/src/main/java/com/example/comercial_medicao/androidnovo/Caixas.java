package com.example.comercial_medicao.androidnovo;

public class Caixas {

    String nivel;
    String nivel2;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public Caixas(String nivel, String nivel2) {
        this.nivel = nivel;
        this.nivel2 = nivel2;
    }

    @Override
    public String toString() {
        return "Caixas{" +
                "nivel='" + nivel + '\'' +
                '}';
    }

    public Caixas() {
    }
}



