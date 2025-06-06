package com.aidnalev.gestorideas.controller;

public class LoginRequest {
    private String nombre;
    private String contrasena;

    // Constructor vacío (necesario para que Spring pueda deserializar el JSON)
    public LoginRequest() {}

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
