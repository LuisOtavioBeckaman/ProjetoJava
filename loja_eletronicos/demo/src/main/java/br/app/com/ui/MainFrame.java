package br.app.com.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principal que representa a janela principal da aplicação.
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Gestão"); // Título da janela
        setSize(800, 600); // Tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Operação de fechamento
        setLocationRelativeTo(null); // Centraliza a janela na tela

        tabbedPane = new JTabbedPane(); // Cria o JTabbedPane para abas

        // Criação dos painéis existentes (Clientes, Produtos, Vendas, Fornecedores)
        ClientListPanel clientListPanel = new ClientListPanel();
        ClientRegistrationPanel clientRegistrationPanel = new ClientRegistrationPanel(clientListPanel);

        ProdutoListPanel produtoListPanel = new ProdutoListPanel();
        ProdutoRegistrationPanel produtoRegistrationPanel = new ProdutoRegistrationPanel();

        SupplierListPanel supplierListPanel = new SupplierListPanel();
        SupplierRegistrationPanel supplierRegistrationPanel = new SupplierRegistrationPanel(supplierListPanel);

        VendasPanel vendasPanel = new VendasPanel();

        // Adiciona os painéis ao JTabbedPane
        tabbedPane.addTab("Clientes", clientRegistrationPanel); // Aba de Cadastro de Clientes
        tabbedPane.addTab("Listar Clientes", clientListPanel); // Aba de Listagem de Clientes

        tabbedPane.addTab("Produtos", produtoRegistrationPanel); // Aba de Cadastro de Produtos
        tabbedPane.addTab("Listar Produtos", produtoListPanel); // Aba de Listagem de Produtos

        tabbedPane.addTab("Fornecedores", supplierRegistrationPanel); // Aba de Cadastro de Fornecedores
        tabbedPane.addTab("Listar Fornecedores", supplierListPanel); // Aba de Listagem de Fornecedores

        tabbedPane.addTab("Vendas", vendasPanel); // Aba de Vendas

        // Integração da nova aba de Serviços
        ServicoListPanel servicoListPanel = new ServicoListPanel(); // Cria o painel de listagem de serviços
        ServicoRegistrationPanel servicoRegistrationPanel = new ServicoRegistrationPanel(servicoListPanel); // Cria o painel de cadastro de serviços
        tabbedPane.addTab("Serviços", servicoRegistrationPanel); // Aba de Cadastro de Serviços
        tabbedPane.addTab("Listar Serviços", servicoListPanel); // Aba de Listagem de Serviços

        add(tabbedPane, BorderLayout.CENTER); // Adiciona o JTabbedPane à janela principal
    }

    /**
     * Método principal para iniciar a aplicação.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        // Garante que a interface gráfica seja criada na thread de despacho de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(); // Cria a instância do frame
            frame.setVisible(true); // Torna o frame visível
        });
    }
}
