package br.app.com.ui;

import br.app.com.model.Produto; // Importa a classe Produto que representa o modelo de um produto
import br.app.com.service.ProdutoService; // Importa a classe ProdutoService para operações de serviço relacionadas ao produto

import javax.swing.*; // Importa classes da biblioteca Swing para interface gráfica
import java.awt.*; // Importa classes para layout e gerenciamento de componentes
import java.util.List; // Importa a interface List para trabalhar com listas de produtos

public class ProdutoListPanel extends JPanel { // A classe estende JPanel para criar um painel de exibição de produtos
    private final ProdutoService produtoService; // Objeto que irá manipular operações de serviço para os produtos
    private JList<Produto> produtoList; // Componente que exibe uma lista de produtos
    private DefaultListModel<Produto> produtoListModel; // Modelo que armazena e manipula os dados da lista de produtos

    // Construtor da classe
    public ProdutoListPanel() {
        this.produtoService = new ProdutoService(); // Inicializa o serviço de produtos
        this.produtoListModel = new DefaultListModel<>(); // Inicializa o modelo da lista de produtos
        this.produtoList = new JList<>(produtoListModel); // Inicializa a JList com o modelo

        // Define o layout do painel como BorderLayout, que facilita a distribuição dos componentes
        setLayout(new BorderLayout());

        // Adiciona a lista de produtos dentro de um JScrollPane, permitindo rolagem
        add(new JScrollPane(produtoList), BorderLayout.CENTER);

        // Carrega os produtos ao iniciar o painel
        loadProdutos();
    }

    // Método responsável por carregar os produtos da base de dados ou do serviço
    private void loadProdutos() {
        // Obtém a lista de produtos através do serviço
        List<Produto> produtos = produtoService.listarProdutos();
        
        // Limpa o modelo da lista antes de adicionar novos itens
        produtoListModel.clear();
        
        // Itera sobre os produtos e adiciona cada um ao modelo da lista
        for (Produto produto : produtos) {
            produtoListModel.addElement(produto); // Adiciona cada produto à lista de exibição
        }
    }
}
