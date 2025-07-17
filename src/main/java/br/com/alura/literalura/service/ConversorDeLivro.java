package br.com.alura.literalura.service;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;

// Classe para converter dados vindos da API em um objeto Livro.
// Evita código duplicado dentro da classe Principal.

public class ConversorDeLivro {
    public static Livro converteLivro(DadosLivro dados){
        Autor autor = dados.authors().stream()
                .findFirst()
                .map(a -> new Autor(a.nome(), a.dataNascimento(), a.dataFalecimento()))
                .orElse(new Autor("Autor Desconhecido", null, null));

        return new Livro(
                dados.titulo(),
                autor,
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
