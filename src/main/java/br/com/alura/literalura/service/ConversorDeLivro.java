package br.com.alura.literalura.service;

import br.com.alura.literalura.model.DadosAutor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;

public class ConversorDeLivro {
    public static Livro converteLivro(DadosLivro dados){
        return new Livro(
                dados.titulo(),
                dados.authors().stream()
                .findFirst()
                .map(DadosAutor::nome)
                .orElse("Autor Desconhecido"),
                dados.summaries().stream()
                        .findFirst()
                        .orElse("Sem sinopse disponível"),
                dados.languages().stream()
                        .findFirst()
                    .orElse("Desconhecido"),
                dados.quantidadeDownload()
        );
    }
}
