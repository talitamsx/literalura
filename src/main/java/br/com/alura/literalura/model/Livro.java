package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    // Relacionamento muitos para um com Autor
    // Muitos livros podem ter o mesmo autor
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;

    @Column(columnDefinition = "TEXT")
    private String sinopse;
    private String idioma;
    private Integer quantidadeDownload;


    public Livro(String titulo, Autor autor, String sinopse, String idioma, Integer quantidadeDownload) {
        this.titulo = titulo;
        this.autor = autor;
        this.sinopse = sinopse;
        this.idioma = idioma;
        this.quantidadeDownload = quantidadeDownload;
    }

    public Livro(){}

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

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
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
