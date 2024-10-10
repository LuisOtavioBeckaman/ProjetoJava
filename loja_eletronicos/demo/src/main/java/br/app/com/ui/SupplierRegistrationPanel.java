package br.app.com.ui;

import br.app.com.controller.FornecedorController; // Importa a classe que controla a lógica de negócios relacionada aos fornecedores
import br.app.com.model.Fornecedor; // Importa a classe Fornecedor que representa o modelo de um fornecedor

import javax.swing.*; // Importa classes da biblioteca Swing para a interface gráfica
import java.awt.*; // Importa classes para layout e gerenciamento de componentes

public class SupplierRegistrationPanel extends JPanel { // A classe estende JPanel para criar um painel de registro de fornecedores
    private JTextField txtNome, txtCnpj, txtContato; // Campos de texto para entrada de dados do fornecedor
    private FornecedorController fornecedorController; // Objeto controlador que gerencia as operações relacionadas aos fornecedores

    // Construtor da classe que recebe um painel de listagem de fornecedores
    public SupplierRegistrationPanel(SupplierListPanel supplierListPanel) {
        setLayout(new GridBagLayout()); // Define o layout do painel como GridBagLayout, que permite uma disposição flexível de componentes
        GridBagConstraints gbc = new GridBagConstraints(); // Cria um objeto para especificar as restrições de layout dos componentes

        // Inicializando o controlador de fornecedores
        fornecedorController = new FornecedorController();

        // Campos para cadastro de fornecedor
        gbc.gridx = 0; // Define a coluna
        gbc.gridy = 0; // Define a linha
        gbc.insets = new Insets(10, 10, 10, 10); // Define o espaço entre os componentes

        // Rótulo e campo para o nome do fornecedor
        JLabel lblNome = new JLabel("Nome:"); // Rótulo para o campo de nome
        txtNome = new JTextField(20); // Campo de texto para o nome
        gbc.gridwidth = 1; // Define que o componente ocupará uma coluna
        add(lblNome, gbc); // Adiciona o rótulo ao painel

        gbc.gridx = 1; // Move para a próxima coluna
        add(txtNome, gbc); // Adiciona o campo de texto ao painel

        // Rótulo e campo para o CNPJ do fornecedor
        gbc.gridy++; // Move para a próxima linha
        JLabel lblCnpj = new JLabel("CNPJ:"); // Rótulo para o campo de CNPJ
        txtCnpj = new JTextField(20); // Campo de texto para o CNPJ
        gbc.gridx = 0; // Retorna para a coluna da esquerda
        add(lblCnpj, gbc); // Adiciona o rótulo ao painel

        gbc.gridx = 1; // Move para a próxima coluna
        add(txtCnpj, gbc); // Adiciona o campo de texto ao painel

        // Rótulo e campo para o contato do fornecedor
        gbc.gridy++; // Move para a próxima linha
        JLabel lblContato = new JLabel("Contato:"); // Rótulo para o campo de contato
        txtContato = new JTextField(20); // Campo de texto para o contato
        gbc.gridx = 0; // Retorna para a coluna da esquerda
        add(lblContato, gbc); // Adiciona o rótulo ao painel

        gbc.gridx = 1; // Move para a próxima coluna
        add(txtContato, gbc); // Adiciona o campo de texto ao painel

        // Botão para cadastrar o fornecedor
        gbc.gridy++; // Move para a próxima linha
        JButton btnCadastrar = new JButton("Cadastrar"); // Cria o botão "Cadastrar"
        gbc.gridx = 0; // Retorna para a coluna da esquerda
        gbc.gridwidth = 2; // Define que o botão ocupará ambas as colunas
        add(btnCadastrar, gbc); // Adiciona o botão ao painel

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> { // Adiciona um listener de ação ao botão
            String nome = txtNome.getText().trim(); // Obtém o nome do fornecedor
            String cnpj = txtCnpj.getText().trim(); // Obtém o CNPJ do fornecedor
            String contato = txtContato.getText().trim(); // Obtém o contato do fornecedor

            // Validações básicas
            if (nome.isEmpty()) { // Verifica se o nome está vazio
                JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Retorna caso a validação falhe
            }

            if (cnpj.isEmpty()) { // Verifica se o CNPJ está vazio
                JOptionPane.showMessageDialog(this, "O CNPJ é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Retorna caso a validação falhe
            }

            if (contato.isEmpty()) { // Verifica se o contato está vazio
                JOptionPane.showMessageDialog(this, "O contato é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Retorna caso a validação falhe
            }

            // Criação do fornecedor
            Fornecedor fornecedor = new Fornecedor(); // Cria uma nova instância de Fornecedor
            fornecedor.setNome(nome); // Define o nome do fornecedor
            fornecedor.setCnpj(cnpj); // Define o CNPJ do fornecedor
            fornecedor.setContato(contato); // Define o contato do fornecedor

            // Tentativa de cadastro
            boolean sucesso = fornecedorController.cadastrarFornecedor(fornecedor); // Chama o método para cadastrar o fornecedor no controlador
            if (sucesso) { // Se o cadastro for bem-sucedido
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso: " + nome); // Exibe mensagem de sucesso

                // Limpar campos após cadastro
                txtNome.setText(""); // Limpa o campo de nome
                txtCnpj.setText(""); // Limpa o campo de CNPJ
                txtContato.setText(""); // Limpa o campo de contato

                // Atualizar a lista de fornecedores no painel de listagem
                supplierListPanel.atualizarLista(); // Chama o método para atualizar a lista de fornecedores
            } else { // Se o cadastro falhar
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o fornecedor.", "Erro", JOptionPane.ERROR_MESSAGE); // Exibe mensagem de erro
            }
        });
    }
}
