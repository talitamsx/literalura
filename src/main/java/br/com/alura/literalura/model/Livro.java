package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;

    @Column(columnDefinition = "TEXT")
    private String sinopse;
    private String idioma;
    private Integer quantidadeDownload;

    public Integer getQuantidadeDownload() {
        return quantidadeDownload;
    }

    public void setQuantidadeDownload(Integer quantidadeDownload) {
        this.quantidadeDownload = quantidadeDownload;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", idioma='" + idioma + '\'' +
                ", quantidadeDownload=" + quantidadeDownload +
                '}';
    }
}
