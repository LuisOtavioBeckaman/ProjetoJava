package br.app.com.ui;

import br.app.com.model.Produto;
import br.app.com.service.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProdutoListPanel extends JPanel {
    private final ProdutoService produtoService;
    private JList<Produto> produtoList;
    private DefaultListModel<Produto> produtoListModel;

    public ProdutoListPanel() {
        this.produtoService = new ProdutoService();
        this.produtoListModel = new DefaultListModel<>();
        this.produtoList = new JList<>(produtoListModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(produtoList), BorderLayout.CENTER);
        loadProdutos();
    }

    private void loadProdutos() {
        List<Produto> produtos = produtoService.listarProdutos();
        produtoListModel.clear();
        for (Produto produto : produtos) {
            produtoListModel.addElement(produto);
        }
    }
}
