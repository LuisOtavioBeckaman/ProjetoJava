package br.app.com.dao;

import br.app.com.model.Venda; // Importa a classe Venda, que representa uma venda.
import br.app.com.database.DatabaseConnection; // Importa a classe para conexão com o banco de dados.

import java.sql.*; // Importa as classes necessárias para trabalhar com SQL.
import java.time.LocalDate; // Importa a classe LocalDate para manipulação de datas.
import java.util.ArrayList; // Importa a classe ArrayList para usar listas dinâmicas.
import java.util.List; // Importa a interface List para usar listas.

public class VendaDAO {

    // Método para cadastrar uma venda no banco de dados
    public boolean cadastrarVenda(Venda venda) {
        // SQL para inserir uma nova venda
        String sql = "INSERT INTO vendas (data, total, forma_pagamento, cliente_id) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Prepara o comando SQL

            // Define os parâmetros da consulta
            statement.setDate(1, java.sql.Date.valueOf(venda.getData())); // Converte LocalDate para java.sql.Date
            statement.setDouble(2, venda.getTotal()); // Define o total da venda
            statement.setString(3, venda.getFormaPagamento()); // Define a forma de pagamento
            statement.setInt(4, venda.getClienteId()); // Define o ID do cliente

            // Executa a atualização e obtém o número de linhas afetadas
            int rowsAffected = statement.executeUpdate();

            // Se a venda foi cadastrada com sucesso, obtém o ID gerado
            if (rowsAffected > 0) {
                // Obtém o ID gerado da venda e seta na venda
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        venda.setId(generatedKeys.getInt(1)); // Seta o ID na venda
                    }
                }
            }
            return rowsAffected > 0; // Retorna true se a venda foi cadastrada
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro e retorna false
            System.err.println("Erro ao cadastrar venda: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar vendas por data
    public List<Venda> buscarVendasPorData(LocalDate data) {
        List<Venda> vendas = new ArrayList<>(); // Cria uma lista para armazenar as vendas
        String sql = "SELECT * FROM vendas WHERE data = ?"; // SQL para buscar vendas pela data
        
        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql)) { // Prepara o comando SQL

            // Define o parâmetro da consulta
            statement.setDate(1, java.sql.Date.valueOf(data)); // Converte LocalDate para java.sql.Date
            ResultSet resultSet = statement.executeQuery(); // Executa a consulta

            // Itera sobre o resultado da consulta
            while (resultSet.next()) {
                Venda venda = new Venda(); // Cria uma nova instância de Venda
                venda.setId(resultSet.getInt("id")); // Seta o ID da venda
                venda.setData(resultSet.getDate("data").toLocalDate()); // Converte a data para LocalDate
                venda.setTotal(resultSet.getDouble("total")); // Seta o total da venda
                venda.setFormaPagamento(resultSet.getString("forma_pagamento")); // Seta a forma de pagamento
                venda.setClienteId(resultSet.getInt("cliente_id")); // Seta o clienteId

                // Adiciona a venda na lista
                vendas.add(venda);
            }

        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro
            System.err.println("Erro ao buscar vendas por data: " + e.getMessage());
        }

        return vendas; // Retorna a lista de vendas
    }

    // Método para cadastrar um item de venda
    public boolean cadastrarItemVenda(int vendaId, int produtoId, int quantidade) {
        String sql = "INSERT INTO itens_venda (venda_id, produto_id, quantidade) VALUES (?, ?, ?)"; // SQL para inserir item de venda
        
        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql)) { // Prepara o comando SQL

            // Define os parâmetros da consulta
            statement.setInt(1, vendaId); // Define o ID da venda
            statement.setInt(2, produtoId); // Define o ID do produto
            statement.setInt(3, quantidade); // Define a quantidade do produto

            int rowsAffected = statement.executeUpdate(); // Executa a atualização
            return rowsAffected > 0; // Retorna true se o item foi cadastrado
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro e retorna false
            System.err.println("Erro ao cadastrar item de venda: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar todas as vendas
    public List<Venda> buscarTodasVendas() {
        List<Venda> vendas = new ArrayList<>(); // Cria uma lista para armazenar as vendas
        String sql = "SELECT * FROM vendas"; // SQL para buscar todas as vendas

        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) { // Executa a consulta

            // Itera sobre o resultado da consulta
            while (resultSet.next()) {
                Venda venda = new Venda(); // Cria uma nova instância de Venda
                venda.setId(resultSet.getInt("id")); // Seta o ID da venda
                venda.setData(resultSet.getDate("data").toLocalDate()); // Converte a data para LocalDate
                venda.setTotal(resultSet.getDouble("total")); // Seta o total da venda
                venda.setFormaPagamento(resultSet.getString("forma_pagamento")); // Seta a forma de pagamento
                venda.setClienteId(resultSet.getInt("cliente_id")); // Seta o clienteId

                vendas.add(venda); // Adiciona a venda à lista
            }
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro
            System.err.println("Erro ao buscar todas as vendas: " + e.getMessage());
        }

        return vendas; // Retorna a lista de todas as vendas
    }

    // Método para excluir uma venda pelo ID
    public boolean excluirVenda(int vendaId) {
        String sql = "DELETE FROM vendas WHERE id = ?"; // SQL para excluir uma venda
        
        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql)) { // Prepara o comando SQL

            statement.setInt(1, vendaId); // Define o ID da venda a ser excluída
            int rowsAffected = statement.executeUpdate(); // Executa a atualização
            return rowsAffected > 0; // Retorna true se a venda foi excluída
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro e retorna false
            System.err.println("Erro ao excluir venda: " + e.getMessage());
            return false;
        }
    }

    // Método para atualizar uma venda
    public boolean atualizarVenda(Venda venda) {
        String sql = "UPDATE vendas SET data = ?, total = ?, forma_pagamento = ? WHERE id = ?"; // SQL para atualizar uma venda
        
        try (Connection connection = DatabaseConnection.getConnection(); // Obtém a conexão com o banco de dados
             PreparedStatement statement = connection.prepareStatement(sql)) { // Prepara o comando SQL

            // Define os parâmetros da consulta
            statement.setDate(1, java.sql.Date.valueOf(venda.getData())); // Converte LocalDate para java.sql.Date
            statement.setDouble(2, venda.getTotal()); // Define o total da venda
            statement.setString(3, venda.getFormaPagamento()); // Define a forma de pagamento
            statement.setInt(4, venda.getId()); // Define o ID da venda

            int rowsAffected = statement.executeUpdate(); // Executa a atualização
            return rowsAffected > 0; // Retorna true se a venda foi atualizada
        } catch (SQLException e) {
            // Em caso de erro, imprime a mensagem de erro e retorna false
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
            return false;
        }
    }
}
