package br.app.com.ui;

import br.app.com.controller.ServicoController;
import br.app.com.model.Servico;

import javax.swing.*;
import java.awt.*;

/**
 * Painel para cadastro de novas prestações de serviço.
 */
public class ServicoRegistrationPanel extends JPanel {
    private JTextField txtDescricao, txtValor, txtClienteId, txtData;
    private ServicoController servicoController;

    // Referência para o painel de listagem para atualizar após cadastro
    private ServicoListPanel servicoListPanel;

    /**
     * Construtor do painel de cadastro de serviço.
     * @param servicoListPanel Referência ao painel de listagem de serviços para atualizar após cadastro.
     */
    public ServicoRegistrationPanel(ServicoListPanel servicoListPanel) {
        this.servicoController = new ServicoController(); // Inicializa o controlador de serviços
        this.servicoListPanel = servicoListPanel; // Define a referência para atualizar a lista após cadastro

        setLayout(new GridBagLayout()); // Define o layout como GridBagLayout para flexibilidade
        GridBagConstraints gbc = new GridBagConstraints(); // Para especificar restrições de layout

        // Definições iniciais de GridBagConstraints
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes

        // Rótulo e campo para Descrição do Serviço
        JLabel lblDescricao = new JLabel("Descrição:");
        txtDescricao = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblDescricao, gbc); // Adiciona o rótulo

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtDescricao, gbc); // Adiciona o campo de texto

        // Rótulo e campo para Valor do Serviço
        JLabel lblValor = new JLabel("Valor (R$):");
        txtValor = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblValor, gbc); // Adiciona o rótulo

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtValor, gbc); // Adiciona o campo de texto

        // Rótulo e campo para ID do Cliente
        JLabel lblClienteId = new JLabel("ID do Cliente:");
        txtClienteId = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblClienteId, gbc); // Adiciona o rótulo

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtClienteId, gbc); // Adiciona o campo de texto

        // Rótulo e campo para Data da Prestação de Serviço
        JLabel lblData = new JLabel("Data (dd/MM/yyyy):");
        txtData = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(lblData, gbc); // Adiciona o rótulo

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(txtData, gbc); // Adiciona o campo de texto

        // Botão de Cadastro
        JButton btnCadastrar = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCadastrar, gbc); // Adiciona o botão

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> {
            String descricao = txtDescricao.getText().trim();
            String valorStr = txtValor.getText().trim();
            String clienteIdStr = txtClienteId.getText().trim();
            String data = txtData.getText().trim();

            // Validações básicas
            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A descrição é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (valorStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O valor é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clienteIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O ID do cliente é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A data é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valor;
            int clienteId;

            // Validação e conversão de valor
            try {
                valor = Double.parseDouble(valorStr);
                if (valor < 0) {
                    JOptionPane.showMessageDialog(this, "O valor não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O valor deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validação e conversão de ID do cliente
            try {
                clienteId = Integer.parseInt(clienteIdStr);
                if (clienteId <= 0) {
                    JOptionPane.showMessageDialog(this, "O ID do cliente deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O ID do cliente deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validação da data (opcional: você pode adicionar mais validações de formato)
            if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "A data deve estar no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Criação do objeto Servico
            Servico servico = new Servico();
            servico.setDescricao(descricao);
            servico.setValor(valor);
            servico.setClienteId(clienteId);
            servico.setData(data);

            // Tentativa de cadastro
            boolean sucesso = servicoController.cadastrarServico(servico);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso: " + descricao);

                // Limpar campos após cadastro
                txtDescricao.setText("");
                txtValor.setText("");
                txtClienteId.setText("");
                txtData.setText("");

                // Atualizar a lista de serviços no painel de listagem
                servicoListPanel.atualizarListaServicos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o serviço.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
