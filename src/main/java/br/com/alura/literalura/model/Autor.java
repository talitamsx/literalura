package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer dataNascimento;
    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros;

    public Autor() {}

    public Autor (String nome, Integer dataNascimento, Integer dataFalecimento){
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataFalecimento = dataFalecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Integer dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(Integer dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }


    // --- ESSENCIAL: Sobrescrever equals() e hashCode() para que distinct() funcione corretamente ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        // Consideramos dois autores iguais se tiverem o mesmo nome.
        // Se houver um ID de API externa, seria melhor usar o ID.
        return Objects.equals(nome, autor.nome);
    }

    @Override
    public int hashCode() {
        // Gera o hash baseado no nome.
        return Objects.hash(nome);
    }



    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", dataNascimetno=" + dataNascimento +
                ", dataFalecimento=" + dataFalecimento +
                '}';
    }
}
