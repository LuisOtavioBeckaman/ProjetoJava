package br.app.com.dao;

// Importa a classe de conexão com o banco de dados.
import br.app.com.database.DatabaseConnection;
// Importa a classe Produto que representa a entidade de produtos.
import br.app.com.model.Produto;

import java.sql.*; // Importa classes relacionadas ao SQL.
import java.util.ArrayList; // Importa a classe ArrayList para manipulação de listas.
import java.util.List; // Importa a interface List.

public class ProdutoDAO {
    // Método para criar um novo produto no banco de dados.
    public void create(Produto produto) {
        // Comando SQL para inserir um novo produto na tabela "produtos".
        String sql = "INSERT INTO produtos (nome, quantidade, preco) VALUES (?, ?, ?)";
        // Tentativa de conexão e execução do comando SQL.
        try (Connection conn = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados.
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara o comando SQL.
            // Define os parâmetros do comando SQL.
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            // Executa a atualização (inserção) no banco de dados.
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime o stack trace em caso de erro durante a execução do comando SQL.
            e.printStackTrace();
        }
    }

    // Método para buscar todos os produtos no banco de dados.
    public List<Produto> findAll() {
        // Cria uma lista para armazenar todos os produtos.
        List<Produto> produtos = new ArrayList<>();
        // Comando SQL para selecionar todos os produtos da tabela "produtos".
        String sql = "SELECT * FROM produtos";
        // Tentativa de conexão e execução do comando SQL.
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta e obtém os resultados.
            // Itera sobre o ResultSet para preencher a lista de produtos.
            while (rs.next()) {
                Produto produto = new Produto(); // Cria um novo objeto Produto.
                produto.setId(rs.getInt("id")); // Obtém o ID do produto.
                produto.setNome(rs.getString("nome")); // Obtém o nome do produto.
                produto.setQuantidade(rs.getInt("quantidade")); // Obtém a quantidade do produto.
                produto.setPreco(rs.getDouble("preco")); // Obtém o preço do produto.
                produtos.add(produto); // Adiciona o produto à lista.
            }
        } catch (SQLException e) {
            // Imprime o stack trace em caso de erro durante a execução da consulta.
            e.printStackTrace();
        }
        return produtos; // Retorna a lista de produtos.
    }

    // Método para buscar um produto pelo ID.
    public Produto findById(int id) {
        Produto produto = null; // Inicializa a variável como null.
        // Comando SQL para selecionar um produto específico pelo ID.
        String sql = "SELECT * FROM produtos WHERE id = ?";
        // Tentativa de conexão e execução do comando SQL.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); // Define o ID do produto a ser buscado.
            try (ResultSet rs = stmt.executeQuery()) { // Executa a consulta.
                // Verifica se um resultado foi encontrado.
                if (rs.next()) {
                    produto = new Produto(); // Cria um novo objeto Produto.
                    produto.setId(rs.getInt("id")); // Obtém o ID do produto.
                    produto.setNome(rs.getString("nome")); // Obtém o nome do produto.
                    produto.setQuantidade(rs.getInt("quantidade")); // Obtém a quantidade do produto.
                    produto.setPreco(rs.getDouble("preco")); // Obtém o preço do produto.
                }
            }
        } catch (SQLException e) {
            // Imprime o stack trace em caso de erro durante a execução da consulta.
            e.printStackTrace();
        }
        return produto; // Retorna o produto encontrado ou null.
    }

    // Método para atualizar as informações de um produto existente.
    public void update(Produto produto) {
        // Comando SQL para atualizar as informações do produto na tabela "produtos".
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, preco = ? WHERE id = ?";
        // Tentativa de conexão e execução do comando SQL.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os parâmetros do comando SQL.
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getId()); // Define o ID do produto a ser atualizado.
            // Executa a atualização (modificação) no banco de dados.
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime o stack trace em caso de erro durante a execução do comando SQL.
            e.printStackTrace();
        }
    }

    // Método para deletar um produto pelo ID.
    public void delete(int id) {
        // Comando SQL para deletar um produto da tabela "produtos".
        String sql = "DELETE FROM produtos WHERE id = ?";
        // Tentativa de conexão e execução do comando SQL.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id); // Define o ID do produto a ser deletado.
            // Executa a atualização (remoção) no banco de dados.
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Imprime o stack trace em caso de erro durante a execução do comando SQL.
            e.printStackTrace();
        }
    }
}
