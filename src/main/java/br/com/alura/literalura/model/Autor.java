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


    // Verifica se o autor atual é igual a outro autor, considerando o nome
    //quals() compara se dois objetos Autor são considerados iguais evitando duplicidade
    @Override
    public boolean equals(Object a) {
        if (this == a) return true;
        if (a == null || getClass() != a.getClass()) return false;
        Autor autor = (Autor) a;
        return Objects.equals(nome, autor.nome);
    }

    // hashCode() método que gera um número inteiro (int) a partir dos dados de um objeto.
    // Serve para otimizar buscas e comparações em coleções ou ao usar .distinct() em streams.
    // Se dois autores são iguais pelo equals(), obrigatoriamente precisam ter o mesmo hashCode.
    @Override
    public int hashCode() {
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
