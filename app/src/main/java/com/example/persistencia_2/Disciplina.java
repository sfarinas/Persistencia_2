package com.example.persistencia_2;

public class Disciplina {
    private String nome;
    private double a1, a2, a3;

    public Disciplina(){
        nome = "Nome Disciplica";
        a1 = 0.0;
        a2 = 0.0;
        a3 = 0.0;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (!nome.isEmpty()){
            this.nome = nome;
        }
    }
    public double getA1() {
        return a1;
    }
    public void setA1(double a1) {
        if (a1 >= 0){
            this.a1 = a1;
        }
    }
    public double getA2() {
        return a2;
    }
    public void setA2(double a2) {
        if (a2 >= 0){
            this.a2 = a2;
        }
    }
    public double getA3() {
        return a3;
    }
    public void setA3(double a3) {
        if (a3 >= 0){
            this.a3 = a3;
        }
    }
    public String textoLista(){ // METODO
        String item;
        item = getNome();
        item += "\nA1: " + String.format("%3.1f", getA1());
        item += "\nA2: " + String.format("%3.1f", getA2());
        item += "\nA3: " + String.format("%3.1f", getA3());
        return item;
    }
}
