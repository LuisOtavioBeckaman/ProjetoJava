package br.app.com.ui;

import br.app.com.model.Produto;
import br.app.com.service.ProdutoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoRegistrationPanel extends JPanel {
    private final ProdutoService produtoService;
    private JTextField nomeField;
    private JTextField quantidadeField;
    private JTextField precoField;
    private JButton cadastrarButton;

    public ProdutoRegistrationPanel() {
        this.produtoService = new ProdutoService();
        setLayout(new GridLayout(4, 2));

        nomeField = new JTextField();
        quantidadeField = new JTextField();
        precoField = new JTextField();
        cadastrarButton = new JButton("Cadastrar");

        cadastrarButton.addActionListener(new CadastrarAction());

        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Quantidade:"));
        add(quantidadeField);
        add(new JLabel("Preço:"));
        add(precoField);
        add(cadastrarButton);
    }

    private class CadastrarAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nome = nomeField.getText();
            int quantidade = Integer.parseInt(quantidadeField.getText());
            double preco = Double.parseDouble(precoField.getText());

            Produto produto = new Produto(quantidade, nome, quantidade, preco);
            produtoService.cadastrarProduto(produto);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        }
    }
}
