package com.notas.demo.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Esta anotación marca la clase como una entidad JPA (se mapea a una tabla en la BD)
@Table(name = "notas") // Especifica el nombre de la tabla en la base de datos
public class NotaModel {

    @Id // Marca este campo como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO_INCREMENT de MySQL - la BD genera automáticamente el valor
    private Long id; // ID único de cada nota

    @Column(nullable = false) // Esta columna NO puede ser NULL en la BD
    private String titulo; // Título de la nota (ej: "Compras del super")

    @Column(columnDefinition = "TEXT") // Tipo de dato en BD: TEXT (para contenido largo)
    private String contenido; // Contenido/descripción de la nota

    @Column(name = "archivada") // Mapea este campo a la columna "archivada" en la BD
    private boolean archivada = false; // Estado: si está archivada o no (false por defecto)

    @Column(name = "creada_en") // Nombre de columna en español en la BD
    private LocalDateTime creadaEn = LocalDateTime.now(); // Fecha y hora de creación

    @Column(name = "actualizada_en") // Nombre de columna en español en la BD
    private LocalDateTime actualizadaEn = LocalDateTime.now(); // Fecha y hora de última modificación

    // ===== MÉTODOS DEL CICLO DE VIDA DE JPA =====

    @PrePersist // Se ejecuta ANTES de que la entidad sea guardada por PRIMERA VEZ en la BD
    protected void alCrear() {
        creadaEn = LocalDateTime.now(); // Establece fecha actual como creación
        actualizadaEn = LocalDateTime.now(); // También como última actualización
    }

    @PreUpdate // Se ejecuta ANTES de que la entidad sea ACTUALIZADA en la BD
    protected void alActualizar() {
        actualizadaEn = LocalDateTime.now(); // Actualiza solo la fecha de modificación
    }

    // ===== CONSTRUCTORES =====

    // Constructor por defecto (OBLIGATORIO para JPA)
    public NotaModel() {}

    // Constructor para crear notas fácilmente
    public NotaModel(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
        // Las fechas se setean automáticamente con @PrePersist
    }

    // ===== GETTERS Y SETTERS (OBLIGATORIOS para JPA) =====

    // GETTER: Obtiene el ID
    public Long getId() {
        return id;
    }

    // SETTER: Establece el ID
    public void setId(Long id) {
        this.id = id;
    }

    // GETTER: Obtiene el título
    public String getTitulo() {
        return titulo;
    }

    // SETTER: Establece el título
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // GETTER: Obtiene el contenido
    public String getContenido() {
        return contenido;
    }

    // SETTER: Establece el contenido
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    // GETTER: Verifica si está archivada
    public boolean isArchivada() {
        return archivada;
    }

    // SETTER: Archiva/desarchiva la nota
    public void setArchivada(boolean archivada) {
        this.archivada = archivada;
    }

    // GETTER: Obtiene fecha de creación
    public LocalDateTime getCreadaEn() {
        return creadaEn;
    }

    // SETTER: Establece fecha de creación (usado principalmente por JPA)
    public void setCreadaEn(LocalDateTime creadaEn) {
        this.creadaEn = creadaEn;
    }

    // GETTER: Obtiene fecha de última actualización
    public LocalDateTime getActualizadaEn() {
        return actualizadaEn;
    }

    // SETTER: Establece fecha de actualización (usado principalmente por JPA)
    public void setActualizadaEn(LocalDateTime actualizadaEn) {
        this.actualizadaEn = actualizadaEn;
    }

    // ===== MÉTODO toString() ÚTIL PARA DEBUG =====
    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", archivada=" + archivada +
                ", creadaEn=" + creadaEn +
                '}';
    }
}