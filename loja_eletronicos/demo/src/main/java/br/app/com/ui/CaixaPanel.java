package br.app.com.ui;

// Importa as classes necessárias do modelo e serviços
import br.app.com.model.Cliente;
import br.app.com.model.Produto;
import br.app.com.service.ClienteService;
import br.app.com.service.ProdutoService;
import br.app.com.service.VendaService;

// Importa as bibliotecas do Swing para criar a interface gráfica
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Classe que representa o painel da caixa de venda
public class CaixaPanel extends JPanel {
    // Declaração dos serviços utilizados para interagir com clientes, produtos e vendas
    private final ProdutoService produtoService;
    private final ClienteService clienteService;
    private final VendaService vendaService;

    // Tabelas para exibir produtos disponíveis e itens no carrinho
    private JTable produtosTable;
    private JTable carrinhoTable;

    // ComboBox para selecionar um cliente
    private JComboBox<Cliente> clienteComboBox;

    // Lista para armazenar produtos adicionados ao carrinho
    private List<Produto> carrinho;

    // Label para mostrar o total da compra
    private JLabel totalLabel;

    // Componente para selecionar a quantidade de produtos
    private JSpinner quantidadeSpinner;

    // Construtor da classe
    public CaixaPanel() {
        // Inicializa os serviços e a lista do carrinho
        this.produtoService = new ProdutoService();
        this.clienteService = new ClienteService();
        this.vendaService = new VendaService();
        this.carrinho = new ArrayList<>();

        // Define o layout do painel
        setLayout(new BorderLayout());
        initializeComponents(); // Chama o método para inicializar os componentes
    }

    // Método para inicializar os componentes da interface
    private void initializeComponents() {
        // Painel para seleção de cliente
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clienteComboBox = new JComboBox<>(); // Inicializa o ComboBox para clientes
        carregarClientes(); // Carrega os clientes disponíveis no ComboBox
        clientePanel.add(new JLabel("Selecione o Cliente:")); // Label de seleção
        clientePanel.add(clienteComboBox); // Adiciona o ComboBox ao painel

        // Painel para exibir produtos disponíveis
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis")); // Título do painel
        atualizarTabelaProdutos(); // Atualiza a tabela de produtos

        // Painel para seleção de quantidade
        JPanel quantidadePanel = new JPanel();
        quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // Spinner para quantidade
        quantidadePanel.add(new JLabel("Quantidade:")); // Label de quantidade
        quantidadePanel.add(quantidadeSpinner); // Adiciona o spinner ao painel

        // Botão para adicionar produto ao carrinho
        JButton addProdutoButton = new JButton("Adicionar ao Carrinho");
        addProdutoButton.addActionListener(e -> adicionarProdutoAoCarrinho()); // Ação do botão
        produtosPanel.add(new JScrollPane(produtosTable), BorderLayout.CENTER); // Adiciona a tabela de produtos ao painel
        produtosPanel.add(quantidadePanel, BorderLayout.NORTH); // Adiciona o painel de quantidade ao painel de produtos
        produtosPanel.add(addProdutoButton, BorderLayout.SOUTH); // Adiciona o botão ao painel de produtos

        // Painel para exibir o carrinho de compras
        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.setBorder(BorderFactory.createTitledBorder("Carrinho de Compras")); // Título do painel do carrinho
        carrinhoTable = new JTable(); // Inicializa a tabela do carrinho
        totalLabel = new JLabel("Total: R$ 0.00"); // Label para exibir o total

        // Botão para remover item do carrinho
        JButton removerProdutoButton = new JButton("Remover do Carrinho");
        removerProdutoButton.addActionListener(e -> removerProdutoDoCarrinho()); // Ação do botão de remoção

        // Adiciona componentes ao painel do carrinho
        carrinhoPanel.add(new JScrollPane(carrinhoTable), BorderLayout.CENTER); // Tabela de carrinho
        carrinhoPanel.add(totalLabel, BorderLayout.SOUTH); // Label de total
        carrinhoPanel.add(removerProdutoButton, BorderLayout.NORTH); // Botão de remoção

        // Botão para finalizar a compra
        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(e -> finalizarCompra()); // Ação do botão para finalizar a compra

        // Botão para atualizar as informações
        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarInformacoes()); // Ação do botão de atualização

        // Painel para os botões de ação
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(finalizarCompraButton); // Adiciona botão de finalizar
        buttonPanel.add(atualizarButton); // Adiciona botão de atualizar

        // Adiciona todos os painéis ao painel principal
        add(clientePanel, BorderLayout.NORTH); // Painel de cliente
        add(produtosPanel, BorderLayout.WEST); // Painel de produtos
        add(carrinhoPanel, BorderLayout.EAST); // Painel de carrinho
        add(buttonPanel, BorderLayout.SOUTH); // Painel de botões
    }

    // Método para carregar os clientes no ComboBox
    private void carregarClientes() {
        clienteComboBox.removeAllItems(); // Remove itens antigos
        List<Cliente> clientes = clienteService.listarClientes(); // Obtém lista de clientes
        for (Cliente cliente : clientes) {
            clienteComboBox.addItem(cliente); // Adiciona cliente ao ComboBox
        }
    }

    // Método para atualizar a tabela de produtos disponíveis
    private void atualizarTabelaProdutos() {
        List<Produto> produtos = produtoService.listarProdutos(); // Obtém lista de produtos
        Object[][] data = new Object[produtos.size()][4]; // Cria matriz para os dados da tabela
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i); // Obtém produto da lista
            data[i][0] = produto.getId(); // ID do produto
            data[i][1] = produto.getNome(); // Nome do produto
            data[i][2] = produto.getQuantidade(); // Quantidade do produto
            data[i][3] = produto.getPreco(); // Preço do produto
        }
        // Inicializa a tabela com os dados dos produtos
        produtosTable = new JTable(data, new String[]{"ID", "Nome", "Quantidade", "Preço"});
        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite selecionar uma única linha
    }

    // Método para adicionar produto ao carrinho
    private void adicionarProdutoAoCarrinho() {
        int selectedRow = produtosTable.getSelectedRow(); // Obtém a linha selecionada na tabela de produtos
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            Produto produto = produtoService.buscarProduto((int) produtosTable.getValueAt(selectedRow, 0)); // Busca produto selecionado
            int quantidadeComprada = (int) quantidadeSpinner.getValue(); // Obtém a quantidade selecionada

            // Verifica se há quantidade suficiente em estoque
            if (produto.getQuantidade() < quantidadeComprada) {
                JOptionPane.showMessageDialog(this, "Quantidade insuficiente em estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Retorna se não houver estoque
            }

            // Adiciona o produto ao carrinho
            for (int i = 0; i < quantidadeComprada; i++) {
                carrinho.add(produto); // Adiciona o produto à lista do carrinho
            }

            // Atualiza a quantidade do produto em estoque
            produto.setQuantidade(produto.getQuantidade() - quantidadeComprada);
            produtoService.editarProduto(produto); // Atualiza produto no serviço
            atualizarTabelaCarrinho(); // Atualiza a tabela do carrinho
            atualizarTabelaProdutos(); // Atualiza a tabela de produtos
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro se nenhum produto for selecionado
        }
    }

    // Método para remover produto do carrinho
    private void removerProdutoDoCarrinho() {
        int selectedRow = carrinhoTable.getSelectedRow(); // Obtém a linha selecionada na tabela do carrinho
        if (selectedRow >= 0) { // Verifica se alguma linha foi selecionada
            Produto produtoRemovido = carrinho.get(selectedRow); // Obtém o produto do carrinho
            carrinho.remove(selectedRow); // Remove o produto do carrinho
            produtoRemovido.setQuantidade(produtoRemovido.getQuantidade() + 1);  // Devolvendo o produto ao estoque
            produtoService.editarProduto(produtoRemovido); // Atualiza produto no serviço
            atualizarTabelaCarrinho(); // Atualiza a tabela do carrinho
            atualizarTabelaProdutos(); // Atualiza a tabela de produtos
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro se nenhum produto for selecionado
        }
    }

    // Método para atualizar a tabela do carrinho
    private void atualizarTabelaCarrinho() {
        Object[][] data = new Object[carrinho.size()][3]; // Cria matriz para os dados da tabela do carrinho
        double total = 0; // Inicializa variável para o total
        for (int i = 0; i < carrinho.size(); i++) {
            Produto produto = carrinho.get(i); // Obtém produto da lista do carrinho
            data[i][0] = produto.getId(); // ID do produto
            data[i][1] = produto.getNome(); // Nome do produto
            data[i][2] = produto.getPreco(); // Preço do produto
            total += produto.getPreco(); // Adiciona preço ao total
        }
        // Inicializa a tabela do carrinho com os dados
        carrinhoTable = new JTable(data, new String[]{"ID", "Nome", "Preço"});
        totalLabel.setText("Total: R$ " + String.format("%.2f", total)); // Atualiza label de total
        carrinhoTable.revalidate(); // Revalida a tabela
        carrinhoTable.repaint(); // Repinta a tabela
    }

    // Método para finalizar a compra
    private void finalizarCompra() {
        if (carrinho.isEmpty()) { // Verifica se o carrinho está vazio
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro se o carrinho estiver vazio
            return; // Retorna se o carrinho estiver vazio
        }

        Cliente clienteSelecionado = (Cliente) clienteComboBox.getSelectedItem(); // Obtém cliente selecionado
        if (clienteSelecionado == null) { // Verifica se um cliente foi selecionado
            JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro se nenhum cliente for selecionado
            return; // Retorna se nenhum cliente foi selecionado
        }

        // Cria a venda e registra
        vendaService.registrarVenda(clienteSelecionado, carrinho, getName()); // Registra a venda com cliente e carrinho
        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE); // Mensagem de sucesso
        carrinho.clear(); // Limpa o carrinho após finalizar
        atualizarTabelaCarrinho(); // Atualiza a tabela do carrinho
    }

    // Método para atualizar as informações
    private void atualizarInformacoes() {
        carregarClientes(); // Recarrega clientes
        atualizarTabelaProdutos(); // Atualiza produtos
        atualizarTabelaCarrinho(); // Atualiza carrinho
    }
}
