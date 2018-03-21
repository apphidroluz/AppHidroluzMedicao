package com.example.comercial_medicao.androidnovo;

public class Caixas {

    String valor1;
    String valor2;

    @Override
    public String toString() {
        return "Caixas{" +
                "valor1='" + valor1 + '\'' +
                ", valor2='" + valor2 + '\'' +
                '}';
    }

    public String getValor1() {
        return valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }
}
