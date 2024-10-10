package br.app.com.dao; // Define o pacote onde a classe está localizada, ajudando a organizar o código do projeto.

import br.app.com.database.DatabaseConnection; // Importa a classe DatabaseConnection, que gerencia a conexão com o banco de dados.
import br.app.com.model.Cliente; // Importa a classe Cliente, que representa um cliente no sistema.

import java.sql.*; // Importa as classes necessárias para trabalhar com JDBC (Java Database Connectivity).
import java.util.ArrayList; // Importa a classe ArrayList, que será usada para armazenar a lista de clientes.
import java.util.List; // Importa a interface List, que representa uma coleção de objetos.


// Classe responsável pela interação com o banco de dados para operações relacionadas a clientes
public class ClienteDAO {
    
    // Método para criar um novo cliente no banco de dados
    public void create(Cliente cliente) {
        // SQL para inserir um novo cliente na tabela 'clientes'
        String sql = "INSERT INTO clientes (nome, telefone, email, endereco) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            // Define os parâmetros da consulta
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());
            stmt.executeUpdate(); // Executa a inserção no banco de dados
        } catch (SQLException e) { // Captura e trata exceções relacionadas ao banco de dados
            e.printStackTrace(); // Exibe a pilha de erros no console
        }
    }

    // Método para buscar todos os clientes cadastrados no banco de dados
    public List<Cliente> findAll() {
        List<Cliente> clientes = new ArrayList<>(); // Cria uma lista para armazenar os clientes
        String sql = "SELECT * FROM clientes"; // SQL para selecionar todos os clientes
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             Statement stmt = conn.createStatement(); // Cria um Statement para executar a consulta
             ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta e obtém os resultados
            // Itera sobre os resultados da consulta
            while (rs.next()) {
                // Cria um novo cliente e define seus atributos a partir do ResultSet
                Cliente cliente = new Cliente(0, sql, sql, sql, sql); // Inicializa com valores placeholder
                cliente.setId(rs.getInt("id")); // Obtém o ID do cliente
                cliente.setNome(rs.getString("nome")); // Obtém o nome do cliente
                cliente.setTelefone(rs.getString("telefone")); // Obtém o telefone do cliente
                cliente.setEmail(rs.getString("email")); // Obtém o email do cliente
                cliente.setEndereco(rs.getString("endereco")); // Obtém o endereço do cliente
                clientes.add(cliente); // Adiciona o cliente à lista
            }
        } catch (SQLException e) { // Captura e trata exceções relacionadas ao banco de dados
            e.printStackTrace(); // Exibe a pilha de erros no console
        }
        return clientes; // Retorna a lista de clientes
    }

    // Método para buscar um cliente pelo ID
    public Cliente findById(int id) {
        Cliente cliente = null; // Inicializa o cliente como null
        String sql = "SELECT * FROM clientes WHERE id = ?"; // SQL para buscar um cliente específico pelo ID
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            stmt.setInt(1, id); // Define o ID do cliente a ser buscado
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta
                // Verifica se um resultado foi encontrado
                if (rs.next()) {
                    cliente = new Cliente(id, sql, sql, sql, sql); // Inicializa com valores placeholder
                    cliente.setId(rs.getInt("id")); // Obtém o ID do cliente
                    cliente.setNome(rs.getString("nome")); // Obtém o nome do cliente
                    cliente.setTelefone(rs.getString("telefone")); // Obtém o telefone do cliente
                    cliente.setEmail(rs.getString("email")); // Obtém o email do cliente
                    cliente.setEndereco(rs.getString("endereco")); // Obtém o endereço do cliente
                }
            }
        } catch (SQLException e) { // Captura e trata exceções relacionadas ao banco de dados
            e.printStackTrace(); // Exibe a pilha de erros no console
        }
        return cliente; // Retorna o cliente encontrado ou null se não encontrado
    }

    // Método para atualizar as informações de um cliente
    public void update(Cliente cliente) {
        // SQL para atualizar um cliente existente
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, email = ?, endereco = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            // Define os parâmetros da consulta
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getEndereco());
            stmt.setInt(5, cliente.getId()); // Define o ID do cliente a ser atualizado
            stmt.executeUpdate(); // Executa a atualização no banco de dados
        } catch (SQLException e) { // Captura e trata exceções relacionadas ao banco de dados
            e.printStackTrace(); // Exibe a pilha de erros no console
        }
    }

    // Método para deletar um cliente pelo ID
    public void delete(int id) {
        // SQL para deletar um cliente específico pelo ID
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a instrução SQL
            stmt.setInt(1, id); // Define o ID do cliente a ser deletado
            stmt.executeUpdate(); // Executa a deleção no banco de dados
        } catch (SQLException e) { // Captura e trata exceções relacionadas ao banco de dados
            e.printStackTrace(); // Exibe a pilha de erros no console
        }
    }
}
