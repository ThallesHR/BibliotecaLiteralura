package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "livros")
public class Livro {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String titulo;
@ManyToOne
@JoinColumn(name = "autor_id")
private Autor autor;
private String idiomas;
private Double numeroDownload;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDownload() {
        return numeroDownload;
    }

    public void setNumeroDownload(Double numeroDownload) {
        this.numeroDownload = numeroDownload;
    }

    public Livro(){

}

public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = String.join(",", dadosLivro.idiomas());
        this.numeroDownload = dadosLivro.numeroDownloads();
}

    @Override
    public String toString() {
        String nomeAutor = (autor != null) ? autor.getNome() : "Autor Desconhecido";
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + nomeAutor + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", numeroDownload=" + numeroDownload +
                '}';
    }
}


