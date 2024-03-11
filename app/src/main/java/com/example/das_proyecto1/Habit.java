package com.example.das_proyecto1;

public class Habit {
    private int id;
    private String imagen;
    private String texto;
    private String fechaHora;

    public Habit() {
        // Constructor vacío requerido por algunas operaciones
    }

    public Habit(String imagen, String texto, String fechaHora) {
        this.imagen = imagen = "";
        this.texto = texto;
        this.fechaHora = fechaHora;
    }

    // Getters y setters para los campos de la clase Habit

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
