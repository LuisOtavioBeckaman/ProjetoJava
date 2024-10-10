package br.app.com.dao;

import br.app.com.database.DatabaseConnection;
import br.app.com.model.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para a entidade Servico.
 */
public class ServicoDAO {

    /**
     * Método para cadastrar uma nova prestação de serviço no banco de dados.
     * @param servico Objeto Servico a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarServico(Servico servico) {
        String sql = "INSERT INTO servicos (descricao, valor, cliente_id, data) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Define os parâmetros da consulta SQL
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setInt(3, servico.getClienteId());
            stmt.setString(4, servico.getData());

            // Executa a inserção e obtém o número de linhas afetadas
            int rowsAffected = stmt.executeUpdate();

            // Se o cadastro foi bem-sucedido, obtém o ID gerado
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        servico.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return false; // Retorna false se ocorreu algum erro
    }

    /**
     * Método para listar todas as prestações de serviço cadastradas.
     * @return Lista de objetos Servico.
     */
    public List<Servico> listarServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Itera sobre o resultado da consulta e cria objetos Servico
            while (rs.next()) {
                Servico servico = new Servico();
                servico.setId(rs.getInt("id"));
                servico.setDescricao(rs.getString("descricao"));
                servico.setValor(rs.getDouble("valor"));
                servico.setClienteId(rs.getInt("cliente_id"));
                servico.setData(rs.getString("data"));
                servicos.add(servico);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return servicos; // Retorna a lista de serviços
    }

    /**
     * Método para buscar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço.
     * @return Objeto Servico encontrado ou null se não encontrado.
     */
    public Servico buscarServicoPorId(int id) {
        Servico servico = null;
        String sql = "SELECT * FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o parâmetro da consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servico = new Servico();
                    servico.setId(rs.getInt("id"));
                    servico.setDescricao(rs.getString("descricao"));
                    servico.setValor(rs.getDouble("valor"));
                    servico.setClienteId(rs.getInt("cliente_id"));
                    servico.setData(rs.getString("data"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return servico; // Retorna o serviço encontrado ou null
    }

    /**
     * Método para atualizar uma prestação de serviço existente.
     * @param servico Objeto Servico com as informações atualizadas.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean atualizarServico(Servico servico) {
        String sql = "UPDATE servicos SET descricao = ?, valor = ?, cliente_id = ?, data = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define os parâmetros da consulta SQL
            stmt.setString(1, servico.getDescricao());
            stmt.setDouble(2, servico.getValor());
            stmt.setInt(3, servico.getClienteId());
            stmt.setString(4, servico.getData());
            stmt.setInt(5, servico.getId());

            // Executa a atualização e verifica o número de linhas afetadas
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se pelo menos uma linha foi atualizada
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return false; // Retorna false se ocorreu algum erro
    }

    /**
     * Método para deletar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço a ser deletada.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deletarServico(int id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o parâmetro da consulta
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se pelo menos uma linha foi deletada
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
        }
        return false; // Retorna false se ocorreu algum erro
    }
}
