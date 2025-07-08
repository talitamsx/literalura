package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.DadosResults;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
                    
                    *** CATÁLOGOS DE LIROS ***
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
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
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

//            if (resposta.results().isEmpty()) {
//                System.out.println("Livro não encontrado.");
//                return;
//            }

            var dadosDoLivroAPI = resposta.results().stream()
                    .filter(d -> d.titulo().equalsIgnoreCase(nomeLivro))
                    .findFirst();

            if (dadosDoLivroAPI.isEmpty()) {
                System.out.println("Livro não encontrado.");
                return;
            }

         var dados = dadosDoLivroAPI.get();

            var livroExistente = repositorio.findByTitulo(dados.titulo());
            if (livroExistente.isPresent()) {
                System.out.println("Livro localizado: ");
                exibirLivro(livroExistente.get());
                return;
            }

            Livro livro = new Livro();
            livro.setTitulo(dados.titulo());
            livro.setAutor(dados.authors().isEmpty() ? "Autor desconhecido" : dados.authors().get(0).nome());
            livro.setSinopse(dados.summaries().isEmpty() ? "Sem sinopse disponível" : dados.summaries().get(0));
            livro.setIdioma(dados.languages().isEmpty() ? "Desconhecido" : dados.languages().get(0));
            livro.setQuantidadeDownload(dados.quantidadeDownload());

            repositorio.save(livro);
            System.out.println("Livro localizado e salvo com sucesso:");
            exibirLivro(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou processar livro:: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exibirLivro(Livro livro) {
        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor());
        System.out.println("Sinopse: " + livro.getSinopse());
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Número de downloads: " + livro.getQuantidadeDownload());
    }

    public void listarLivrosRegistrados() {
        List<Livro> livros = repositorio.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            System.out.println("=== LIVROS REGISTRADOS ===");
            livros.forEach(livro -> {
                exibirLivro(livro);
                System.out.println("--------------------------");
            });
        }
    }
}