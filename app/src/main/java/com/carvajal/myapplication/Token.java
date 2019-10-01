package com.carvajal.myapplication;

public class Token {
    private String dni;
    private String estado;


    public Token(String dni, String estado) {
        this.dni = dni;
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String isEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
