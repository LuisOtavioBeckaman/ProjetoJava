package br.app.com.dao;

// Importa a conexão com o banco de dados e o modelo Fornecedor.
import br.app.com.database.DatabaseConnection;
import br.app.com.model.Fornecedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO {

    // Método para criar um novo fornecedor no banco de dados
    public void create(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedores (nome, cnpj, contato) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os valores dos parâmetros da consulta SQL
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getContato());
            // Executa a atualização (inserção)
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Captura e imprime exceções SQL
            e.printStackTrace();
        }
    }

    // Método para buscar todos os fornecedores
    public List<Fornecedor> findAll() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // Itera sobre o resultado da consulta e cria objetos Fornecedor
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getInt("id"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setContato(rs.getString("contato"));
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            // Captura e imprime exceções SQL
            e.printStackTrace();
        }
        return fornecedores; // Retorna a lista de fornecedores
    }

    // Método para buscar um fornecedor pelo ID
    public Fornecedor findById(int id) {
        Fornecedor fornecedor = null;
        String sql = "SELECT * FROM fornecedores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            // Executa a consulta e verifica se um resultado foi encontrado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    fornecedor = new Fornecedor();
                    fornecedor.setId(rs.getInt("id"));
                    fornecedor.setNome(rs.getString("nome"));
                    fornecedor.setCnpj(rs.getString("cnpj"));
                    fornecedor.setContato(rs.getString("contato"));
                }
            }
        } catch (SQLException e) {
            // Captura e imprime exceções SQL
            e.printStackTrace();
        }
        return fornecedor; // Retorna o fornecedor encontrado ou null
    }

    // Método para atualizar os dados de um fornecedor existente
    public void update(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedores SET nome = ?, cnpj = ?, contato = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os valores dos parâmetros da consulta SQL
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getContato());
            stmt.setInt(4, fornecedor.getId());
            // Executa a atualização
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Captura e imprime exceções SQL
            e.printStackTrace();
        }
    }

    // Método para deletar um fornecedor pelo ID
    public void delete(int id) {
        String sql = "DELETE FROM fornecedores WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            // Executa a exclusão
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Captura e imprime exceções SQL
            e.printStackTrace();
        }
    }
}
