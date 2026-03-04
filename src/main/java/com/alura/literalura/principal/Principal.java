package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.services.ConsumoApi;
import com.alura.literalura.services.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com";
    private Scanner leitura = new Scanner(System.in);
    private Optional<Livro> livroBusca;
    private LivroRepository repositoriolivro;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository repositoriolivro, AutorRepository autorRepositorio) {
        this.repositoriolivro = repositoriolivro;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu =" 1 - Buscar livros por titulo\n" +
                    " 2 - Adicionar livros por titulo\n" +
                    " 3- Listar Todos os livros\n" +
                    " 4- Listar Todos os autores\n" +
                    " 5- listar Todos autores por ano\n" +
                    " 6- listar Todos os livros em um idioma\n" +
                    " 0 - Sair  \\s\n" +
                    " \\s\"\"\"";
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    buscarLivroWeb();
                    break;
                case 3:
                    listarTodosLivros();
                    break;
                case 4:
                    listarTodosAutores();
                    break;
                case 5:
                    listarAutoresVivosAno();
                    break;
                case 6:
                    ListarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
            }

        }
    }

    private void ListarLivrosPorIdioma() {
        System.out.println("Digite um idioma para busca (use as siglas do idioma)");
        var idioma = leitura.nextLine().toLowerCase();
        Long quantidade = repositoriolivro.countByIdiomas(idioma);
        if(quantidade == 0){
            System.out.println("Nenhum livro encontrado nesse idioma");
        }else{
            System.out.println("Existem " + quantidade + " livros registrados nesse idioma :" + idioma);
        }
    }

    private void listarAutoresVivosAno( ) {
        System.out.println("Digite o ano de vida do autor");
        var anoAutor = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = autorRepositorio.autoresVivosEmCertoAno(anoAutor);
        if(autoresVivos.isEmpty()){
            System.out.println("Não há autores encontrados nesse ano ");
        }else{
            System.out.println("Autores vivos em: "+ anoAutor);
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarTodosAutores() {
        List<Autor> autores = autorRepositorio.findAll();
        if(autores.isEmpty()){
            System.out.println("Nenhum autor encontrado");
        }else{
            autores.forEach(System.out::println);
        }
    }

    private void listarTodosLivros() {
        List<Livro> livros = repositoriolivro.findAll();
        if(livros.isEmpty()){
            System.out.println("Nenhum livro encontrado");
        }else{
            livros.forEach(System.out::println);
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Escolha seu livro pelo titulo: ");
        var nomeLivro = leitura.nextLine();
        livroBusca = repositoriolivro.findByTituloContainingIgnoreCase(nomeLivro);

        if (livroBusca.isPresent()) {
            System.out.println("Dados do livro: " + livroBusca.get());
        } else {
            System.out.println("Livro não encontrado");
        }

    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para buscar e salvar:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + "/books/?search=" + nomeLivro.replace(" ", "%20"));
        var dados = conversor.obterDados(json, Resultados.class);

        if (dados.resultados().size() > 0) {
            DadosLivro dadosLivro = dados.resultados().get(0);
            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Autor autor = autorRepositorio.findByNomeContainingIgnoreCase(dadosAutor.nome())
                    .orElseGet(() -> {
                                Autor novoAutor = new Autor(dadosAutor);
                                return autorRepositorio.save(novoAutor);
                            });
                            Livro livro = new Livro(dadosLivro);
                            livro.setAutor(autor);
            try {
                repositoriolivro.save(livro);
                System.out.println("Livro salvo com sucesso: " + livro.getTitulo());
            } catch (Exception e) {
                System.out.println("Erro: Livro já cadastrado ou erro no banco.");
            }
        } else {
            System.out.println("Livro não encontrado.");
        }
    }
}
