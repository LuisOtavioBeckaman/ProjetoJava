package br.app.com.controller; // Define o pacote onde a classe está localizada

import br.app.com.model.Fornecedor; // Importa a classe Fornecedor, que representa um fornecedor no sistema
import br.app.com.service.FornecedorService; // Importa a classe FornecedorService, que contém a lógica de negócios para gerenciar fornecedores

import java.util.List; // Importa a interface List para manipulação de coleções

// Classe responsável por gerenciar as operações relacionadas ao fornecedor
public class FornecedorController {
    private final FornecedorService fornecedorService; // Declaração de um atributo do tipo FornecedorService para interagir com a lógica de negócios

    // Construtor da classe FornecedorController
    public FornecedorController() {
        this.fornecedorService = new FornecedorService(); // Inicializa o fornecedorService, que será utilizado para as operações relacionadas ao fornecedor
    }

    // Método para cadastrar um fornecedor
    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        // Chama o método de cadastro no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = fornecedorService.cadastrarFornecedor(fornecedor);
        // Verifica se o cadastro foi bem-sucedido e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Fornecedor cadastrado com sucesso: " + fornecedor.getNome());
        } else {
            System.out.println("Erro ao cadastrar fornecedor: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }

    // Método para listar todos os fornecedores
    public List<Fornecedor> listarFornecedores() {
        // Chama o método para listar fornecedores do serviço
        List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
        // Verifica se a lista de fornecedores está vazia e exibe uma mensagem adequada
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado.");
        } else {
            // Itera sobre a lista de fornecedores e imprime suas informações
            for (Fornecedor fornecedor : fornecedores) {
                System.out.println(fornecedor);
            }
        }
        return fornecedores; // Retorna a lista de fornecedores
    }

    // Método para buscar um fornecedor pelo ID
    public Fornecedor buscarFornecedor(int id) {
        // Chama o método de busca no serviço e armazena o resultado em uma variável fornecedor
        Fornecedor fornecedor = fornecedorService.buscarFornecedor(id);
        // Verifica se o fornecedor foi encontrado e exibe uma mensagem apropriada
        if (fornecedor != null) {
            System.out.println("Fornecedor encontrado: " + fornecedor);
        } else {
            System.out.println("Fornecedor não encontrado.");
        }
        return fornecedor; // Retorna o fornecedor encontrado ou null se não encontrado
    }

    // Método para editar um fornecedor
    public boolean editarFornecedor(Fornecedor fornecedor) {
        // Chama o método de edição no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = fornecedorService.editarFornecedor(fornecedor);
        // Verifica se a edição foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Fornecedor editado com sucesso: " + fornecedor.getNome());
        } else {
            System.out.println("Erro ao editar fornecedor: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }

    // Método para deletar um fornecedor
    public boolean deletarFornecedor(int id) {
        // Chama o método de deleção no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = fornecedorService.deletarFornecedor(id);
        // Verifica se a deleção foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Fornecedor deletado com sucesso: ID " + id);
        } else {
            System.out.println("Erro ao deletar fornecedor: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }
}
