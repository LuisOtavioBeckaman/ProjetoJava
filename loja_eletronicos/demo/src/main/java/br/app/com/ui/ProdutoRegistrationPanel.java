package br.app.com.ui;

import br.app.com.model.Produto; // Importa a classe Produto que representa o modelo de um produto
import br.app.com.service.ProdutoService; // Importa a classe ProdutoService para manipulação de serviços relacionados ao produto

import javax.swing.*; // Importa classes da biblioteca Swing para a interface gráfica
import java.awt.*; // Importa classes para layout e gerenciamento de componentes

import java.awt.event.ActionEvent; // Importa a classe para eventos de ação
import java.awt.event.ActionListener; // Importa a interface para ouvir eventos de ação

public class ProdutoRegistrationPanel extends JPanel { // A classe estende JPanel para criar um painel de registro de produtos
    private final ProdutoService produtoService; // Objeto que irá manipular as operações de serviço de produtos
    private JTextField nomeField; // Campo de texto para entrada do nome do produto
    private JTextField quantidadeField; // Campo de texto para entrada da quantidade do produto
    private JTextField precoField; // Campo de texto para entrada do preço do produto
    private JButton cadastrarButton; // Botão para cadastrar o produto

    // Construtor da classe
    public ProdutoRegistrationPanel() {
        this.produtoService = new ProdutoService(); // Inicializa o serviço de produtos
        setLayout(new GridLayout(4, 2)); // Define o layout do painel como GridLayout com 4 linhas e 2 colunas

        // Inicializa os campos de texto e o botão
        nomeField = new JTextField(); // Campo para o nome do produto
        quantidadeField = new JTextField(); // Campo para a quantidade do produto
        precoField = new JTextField(); // Campo para o preço do produto
        cadastrarButton = new JButton("Cadastrar"); // Botão com o texto "Cadastrar"

        // Adiciona um listener de ação ao botão
        cadastrarButton.addActionListener(new CadastrarAction());

        // Adiciona os componentes ao painel
        add(new JLabel("Nome:")); // Adiciona um rótulo para o nome
        add(nomeField); // Adiciona o campo para o nome
        add(new JLabel("Quantidade:")); // Adiciona um rótulo para a quantidade
        add(quantidadeField); // Adiciona o campo para a quantidade
        add(new JLabel("Preço:")); // Adiciona um rótulo para o preço
        add(precoField); // Adiciona o campo para o preço
        add(cadastrarButton); // Adiciona o botão de cadastrar
    }

    // Classe interna que implementa ActionListener para o botão de cadastro
    private class CadastrarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { // Método chamado quando o botão é clicado
            // Obtém os valores dos campos de texto
            String nome = nomeField.getText(); // Obtém o nome do produto
            int quantidade = Integer.parseInt(quantidadeField.getText()); // Obtém a quantidade do produto
            double preco = Double.parseDouble(precoField.getText()); // Obtém o preço do produto

            // Cria uma nova instância de Produto com os dados informados
            Produto produto = new Produto(quantidade, nome, quantidade, preco);

            // Chama o método para cadastrar o produto no serviço
            produtoService.cadastrarProduto(produto);

            // Exibe uma mensagem de sucesso após o cadastro
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        }
    }
}
