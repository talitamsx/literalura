package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.DadosResults;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private final ConverteDados conversor = new ConverteDados();
    private final LivroRepository repositorio;

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    *** CATALAGO DE LIROS ***
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - sair
                    *************************************
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
            }
        }
    }

    private void buscarLivroPorTitulo() {
        try {
            System.out.println("Digite o nome do livro: ");
            var nomeLivro = leitura.nextLine();
            String nomeBuscado = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);
            String json = consumo.obterDados(ENDERECO + nomeBuscado);

            if (json == null || json.isBlank()) {
                System.out.println("Nenhum dado retornado pela API. Tente novamente mais tarde.");
                return;
            }

            DadosResults resposta = conversor.obterDados(json, DadosResults.class);

            if (resposta.results().isEmpty()) {
                System.out.println("Livro não encontrado.");
                return;
            }
            var dados = resposta.results().get(0);
            Livro livro = new Livro();

            livro.setTitulo(dados.titulo());
            livro.setAutor(dados.authors().isEmpty() ? "Autor desconhecido" : dados.authors().get(0).nome());
            livro.setSinopse(dados.summaries().isEmpty() ? "Sem sinopse disponível" : dados.summaries().get(0));
            livro.setIdioma(dados.languages().isEmpty() ? "Desconhecido" : dados.languages().get(0));
            livro.setQuantidadeDownload(dados.quantidadeDownload());

            repositorio.save(livro);
            System.out.println("Livro salvo: " + livro);
        } catch (Exception e) {
            System.out.println(" Ocorreu um erro ao buscar o livro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}