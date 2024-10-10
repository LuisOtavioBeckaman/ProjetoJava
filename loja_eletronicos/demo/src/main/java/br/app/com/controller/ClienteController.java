package br.app.com.controller; // Define o pacote onde a classe está localizada

import br.app.com.model.Cliente; // Importa a classe Cliente, que representa um cliente no sistema
import br.app.com.service.ClienteService; // Importa a classe ClienteService, que contém a lógica de negócios para gerenciar clientes

import java.util.List; // Importa a interface List para manipulação de coleções

// Classe responsável por gerenciar as operações relacionadas ao cliente
public class ClienteController {
    private final ClienteService clienteService; // Declaração de um atributo do tipo ClienteService para interagir com a lógica de negócios

    // Construtor da classe ClienteController
    public ClienteController() {
        this.clienteService = new ClienteService(); // Inicializa o clienteService, que será utilizado para as operações relacionadas ao cliente
    }

    // Método para cadastrar um cliente
    public boolean cadastrarCliente(Cliente cliente) {
        // Chama o método de cadastro no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = clienteService.cadastrarCliente(cliente);
        // Verifica se o cadastro foi bem-sucedido e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Cliente cadastrado com sucesso: " + cliente.getNome());
        } else {
            System.out.println("Erro ao cadastrar cliente: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }

    // Método para listar todos os clientes
    public List<Cliente> listarClientes() {
        // Chama o método para listar clientes do serviço
        List<Cliente> clientes = clienteService.listarClientes();
        // Verifica se a lista de clientes está vazia e exibe uma mensagem adequada
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            // Itera sobre a lista de clientes e imprime suas informações
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
        return clientes; // Retorna a lista de clientes
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarCliente(int id) {
        // Chama o método de busca no serviço e armazena o resultado em uma variável cliente
        Cliente cliente = clienteService.buscarCliente(id);
        // Verifica se o cliente foi encontrado e exibe uma mensagem apropriada
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
        return cliente; // Retorna o cliente encontrado ou null se não encontrado
    }

    // Método para editar um cliente
    public boolean editarCliente(Cliente cliente) {
        // Chama o método de edição no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = clienteService.editarCliente(cliente);
        // Verifica se a edição foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Cliente editado com sucesso: " + cliente.getNome());
        } else {
            System.out.println("Erro ao editar cliente: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }

    // Método para deletar um cliente
    public boolean deletarCliente(int i) {
        // Chama o método de deleção no serviço e armazena o resultado em uma variável de sucesso
        boolean sucesso = clienteService.deletarCliente(i);
        // Verifica se a deleção foi bem-sucedida e exibe uma mensagem apropriada
        if (sucesso) {
            System.out.println("Cliente deletado com sucesso: ID " + i);
        } else {
            System.out.println("Erro ao deletar cliente: operação falhou.");
        }
        return sucesso; // Retorna o resultado da operação
    }
}
