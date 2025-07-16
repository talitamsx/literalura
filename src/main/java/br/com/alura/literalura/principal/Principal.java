package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.DadosResults;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConversorDeLivro;
import br.com.alura.literalura.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                case 3:
                    buscarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivosDeAcordoComAno();
                    break;
                case 5:
                    buscarLivroPorIdioma();
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

            var dadosDoLivroAPI = resposta.results().stream().filter(d -> d.titulo().equalsIgnoreCase(nomeLivro)).findFirst();

            if (dadosDoLivroAPI.isEmpty()) {
                System.out.println("Livro não encontrado. \n");
                return;
            }

            var dados = dadosDoLivroAPI.get();

            List<Livro> livros = repositorio.findByTitulo(dados.titulo());
            if (!livros.isEmpty()) {
                System.out.println("Livro localizado: ");
                livros.forEach(this::exibirLivro);
                return;
            }

            Livro livro = ConversorDeLivro.converteLivro(dados);

            repositorio.save(livro);
            System.out.println("Livro localizado e salvo com sucesso: \n");
            exibirLivro(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar ou processar livro:: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exibirLivro(Livro livro) {

        System.out.println("Livro: " + livro.getTitulo());
        System.out.println("Autor: " + livro.getAutor().getNome());
        System.out.println("Sinopse: " + livro.getSinopse());
        System.out.println("Idioma: " + livro.getIdioma());
        System.out.println("Número de downloads: " + livro.getQuantidadeDownload() + "\n");
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

    public void buscarAutoresRegistrados() {
        List<Livro> livros = repositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado. ");
            return;
        }

        // Filtra nulos ANTES de mapear e aplicar distinct
        var listaAutores = livros.stream()
                .map(Livro::getAutor) // Pega o objeto Autor de cada livro
                .filter(Objects::nonNull) // REMOVE quaisquer autores que sejam null
                .distinct() // Garante que cada objeto Autor apareça apenas uma vez
                .collect(Collectors.toList());

        System.out.println("\n LISTA DE AUTORES ");

        listaAutores.forEach((autor -> {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Nascimento: " + autor.getDataNascimento());
            System.out.println("Falecimento: " + autor.getDataFalecimento());

            // Filtra os livros que pertencem a este autor específico
            List<Livro> livrosDoAutor = livros.stream()
                    .filter(l -> l.getAutor() != null && l.getAutor().equals(autor)) // Certifica-se de que l.getAutor() não é nulo antes de comparar
                    .collect(Collectors.toList());

                System.out.println("Livros registrados: ");
                livrosDoAutor.forEach(livro -> System.out.println(livro.getTitulo()));
                System.out.println("\n--------------------------");;

        }));
    }

    public void buscarAutoresVivosDeAcordoComAno() {
        System.out.println("Digite o ano para buscar autores vivos: ");
        Integer anoBusca;

        try {
            anoBusca = leitura.nextInt();
            leitura.nextLine();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um ano numérico.");
            leitura.nextLine();
            return; // Sai do método se a entrada for inválida
        }

        List<Livro> livros = repositorio.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        // 1. Pega todos os autores dos livros
        // 2. Filtra autores que são null
        // 3. Garante que cada autor apareça apenas uma vez
        // 4. Filtra os autores que estavam vivos no 'anoBusca'
        List<Autor> autoresVivosNoAno = livros.stream()
                .map(Livro::getAutor)
                .filter(Objects::nonNull) // Remove autores nulos
                .distinct() // Garante autores únicos
                .filter(autor -> {
                    // Critério 1: Autor nasceu no ano ou antes do ano de busca
                    boolean nasceuAntesOuNoAno = (autor.getDataNascimento() != null && autor.getDataNascimento() <= anoBusca);

                    // Critério 2: Autor não morreu ainda (dataFalecimento é null) OU morreu depois do ano de busca
                    // (autor.getDataFalecimento() != null && autor.getDataFalecimento() >= anoBusca)
                    // A condição autor.getDataFalecimento() != null` aqui é crucial para evitar NullPointerException
                    boolean aindaVivoNoAno = (autor.getDataFalecimento() == null || (autor.getDataFalecimento() != null && autor.getDataFalecimento() >= anoBusca));

                    return nasceuAntesOuNoAno && aindaVivoNoAno;
                })
                .collect(Collectors.toList());

        // Verificação final para exibir a mensagem correta
        if (autoresVivosNoAno.isEmpty()) {
            System.out.println("Não foi localizado autores vivos nessa data (" + anoBusca + ").");
        } else {
            System.out.println("=== AUTORES VIVOS NO ANO " + anoBusca + " ===");
            autoresVivosNoAno.forEach(autor -> {
                System.out.println("Autor: " + autor.getNome());
                // Trata null para exibição das datas
                System.out.println("Nascimento: " + (autor.getDataNascimento() != null ? autor.getDataNascimento() : "Desconhecido"));
                System.out.println("Falecimento: " + (autor.getDataFalecimento() != null ? autor.getDataFalecimento() : "Vivo / Desconhecido"));

                // --- Parte para listar os livros do autor ---
                // Pega todos os livros e filtra aqueles que pertencem ao autor
                List<Livro> livrosDesteAutor = livros.stream()
                        .filter(l -> l.getAutor() != null && l.getAutor().equals(autor)) // Garante que o autor do livro não é null e compara
                        .collect(Collectors.toList());

                System.out.println("Livros registrados:");
                livrosDesteAutor.forEach(livro -> System.out.println(livro.getTitulo() + "\n--------------------------"));
            });
        }
    }
    public void buscarLivroPorIdioma() {
        System.out.println("""
                Escolha o idioma para realizar a busca:
                
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);

        String idioma = leitura.nextLine().trim().toLowerCase();

        if (idioma.isBlank()){
            System.out.println("Idioma inválido! Escolha uma idioma entre as opções do menu");
            return;
        }

        List<Livro> livrosFiltrados = repositorio.findAll().stream()
                .filter(l -> idioma.equalsIgnoreCase(l.getIdioma()))
                .collect(Collectors.toList());

        if(livrosFiltrados.isEmpty()) {
            System.out.println("Não existe livros nesse idioma no banco de dados");
        } else {
            System.out.println("\n *** LIVROS " + idioma + " ***");
            livrosFiltrados.forEach(this::exibirLivro);
        }

    }
}