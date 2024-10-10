package br.app.com.controller; // Define o pacote onde a classe está localizada, ajudando a organizar o código do projeto.

import br.app.com.model.Produto; // Importa a classe Produto, que representa um produto no sistema e contém atributos e comportamentos relacionados.
import br.app.com.service.ProdutoService; // Importa a classe ProdutoService, que contém a lógica de negócios para gerenciar produtos.

import java.util.List; // Importa a interface List da biblioteca Java, permitindo a manipulação de coleções de objetos.


// Classe responsável por gerenciar as operações relacionadas aos produtos
public class ProdutoController {
    // Declaração de um atributo do tipo ProdutoService, que será usado para interagir com a lógica de negócios
    private final ProdutoService produtoService; 

    // Construtor da classe ProdutoController
    public ProdutoController() {
        // Inicializa o produtoService, que será utilizado para as operações relacionadas a produtos
        this.produtoService = new ProdutoService(); 
    }

    // Método para cadastrar um novo produto
    public boolean cadastrarProduto(Produto produto) {
        // Chama o método de cadastro do serviço e armazena o resultado em uma variável 'sucesso'
        boolean sucesso = produtoService.cadastrarProduto(produto);
        
        // Verifica se o cadastro foi bem-sucedido e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Produto cadastrado com sucesso: " + produto.getNome());
        } else {
            System.out.println("Erro ao cadastrar produto: operação falhou.");
        }
        
        // Retorna o resultado da operação (true se bem-sucedido, false caso contrário)
        return sucesso; 
    }

    // Método para listar todos os produtos cadastrados
    public List<Produto> listarProdutos() {
        // Chama o método para listar produtos do serviço
        List<Produto> produtos = produtoService.listarProdutos();
        
        // Verifica se a lista de produtos está vazia e exibe uma mensagem adequada
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            // Itera sobre a lista de produtos e imprime suas informações
            for (Produto produto : produtos) {
                System.out.println(produto); // Exibe a representação do produto, utilizando o método toString() da classe Produto
            }
        }
        
        // Retorna a lista de produtos
        return produtos; 
    }

    // Método para buscar um produto pelo ID
    public Produto buscarProduto(int id) {
        // Chama o método de busca no serviço e armazena o resultado em uma variável 'produto'
        Produto produto = produtoService.buscarProduto(id);
        
        // Verifica se o produto foi encontrado e exibe uma mensagem apropriada
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
        
        // Retorna o produto encontrado ou null se não encontrado
        return produto; 
    }

    // Método para editar um produto existente
    public boolean editarProduto(Produto produto) {
        // Chama o método de edição do serviço e armazena o resultado em uma variável 'sucesso'
        boolean sucesso = produtoService.editarProduto(produto);
        
        // Verifica se a edição foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Produto editado com sucesso: " + produto.getNome());
        } else {
            System.out.println("Erro ao editar produto: operação falhou.");
        }
        
        // Retorna o resultado da operação
        return sucesso; 
    }

    // Método para deletar um produto pelo ID
    public boolean deletarProduto(int id) {
        // Chama o método de deleção do serviço e armazena o resultado em uma variável 'sucesso'
        boolean sucesso = produtoService.deletarProduto(id);
        
        // Verifica se a deleção foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Produto deletado com sucesso: ID " + id);
        } else {
            System.out.println("Erro ao deletar produto: operação falhou.");
        }
        
        // Retorna o resultado da operação
        return sucesso; 
    }
}
