package br.app.com.ui;

import br.app.com.controller.ClienteController; // Importa o controlador para gerenciar operações de clientes
import br.app.com.model.Cliente; // Importa a classe Cliente

import javax.swing.*; // Importa classes da biblioteca Swing para interface gráfica
import java.awt.*; // Importa classes para layout e gerenciamento de componentes

public class ClientRegistrationPanel extends JPanel { // Classe que estende JPanel para criar um painel de cadastro de clientes
    // Campos de texto para entrada de dados do cliente
    private JTextField txtNome, txtTelefone, txtEmail, txtEndereco; 
    private ClienteController clienteController; // Controlador para gerenciar operações relacionadas a clientes

    // Construtor da classe, recebe um ClientListPanel para atualizar a lista de clientes
    public ClientRegistrationPanel(ClientListPanel clientListPanel) {
        setLayout(new GridBagLayout()); // Define o layout do painel como GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Cria um objeto para gerenciar as restrições de layout

        // Inicializando o controlador de clientes
        clienteController = new ClienteController();

        // Campos para cadastro de cliente
        gbc.gridx = 0; // Coluna inicial
        gbc.gridy = 0; // Linha inicial
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre componentes
        
        // Rótulo e campo para nome
        JLabel lblNome = new JLabel("Nome:"); // Cria um rótulo para o campo de nome
        txtNome = new JTextField(20); // Cria um campo de texto para nome com largura de 20 colunas
        gbc.gridwidth = 1; // Define que o componente ocupa uma coluna
        add(lblNome, gbc); // Adiciona o rótulo ao painel
        
        gbc.gridx = 1; // Move para a coluna 1
        add(txtNome, gbc); // Adiciona o campo de texto ao painel
        
        // Rótulo e campo para telefone
        gbc.gridy++; // Move para a próxima linha
        JLabel lblTelefone = new JLabel("Telefone:"); // Cria um rótulo para o campo de telefone
        txtTelefone = new JTextField(20); // Cria um campo de texto para telefone
        gbc.gridx = 0; // Volta para a coluna 0
        add(lblTelefone, gbc); // Adiciona o rótulo ao painel
        
        gbc.gridx = 1; // Move para a coluna 1
        add(txtTelefone, gbc); // Adiciona o campo de texto ao painel
        
        // Rótulo e campo para email
        gbc.gridy++; // Move para a próxima linha
        JLabel lblEmail = new JLabel("Email:"); // Cria um rótulo para o campo de email
        txtEmail = new JTextField(20); // Cria um campo de texto para email
        gbc.gridx = 0; // Volta para a coluna 0
        add(lblEmail, gbc); // Adiciona o rótulo ao painel
        
        gbc.gridx = 1; // Move para a coluna 1
        add(txtEmail, gbc); // Adiciona o campo de texto ao painel
        
        // Rótulo e campo para endereço
        gbc.gridy++; // Move para a próxima linha
        JLabel lblEndereco = new JLabel("Endereço:"); // Cria um rótulo para o campo de endereço
        txtEndereco = new JTextField(20); // Cria um campo de texto para endereço
        gbc.gridx = 0; // Volta para a coluna 0
        add(lblEndereco, gbc); // Adiciona o rótulo ao painel
        
        gbc.gridx = 1; // Move para a coluna 1
        add(txtEndereco, gbc); // Adiciona o campo de texto ao painel
        
        // Botão de cadastro
        gbc.gridy++; // Move para a próxima linha
        JButton btnCadastrar = new JButton("Cadastrar"); // Cria um botão para cadastrar
        gbc.gridx = 0; // Volta para a coluna 0
        gbc.gridwidth = 2; // O botão ocupa duas colunas
        add(btnCadastrar, gbc); // Adiciona o botão ao painel

        // Ação do botão de cadastrar com validação
        btnCadastrar.addActionListener(e -> { // Adiciona um listener de ação ao botão
            // Obtém os valores dos campos de texto
            String nome = txtNome.getText().trim(); // Nome do cliente
            String telefone = txtTelefone.getText().trim(); // Telefone do cliente
            String email = txtEmail.getText().trim(); // Email do cliente
            String endereco = txtEndereco.getText().trim(); // Endereço do cliente

            // Validações básicas
            if (nome.isEmpty()) { // Verifica se o nome foi preenchido
                JOptionPane.showMessageDialog(this, "O nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                return; // Sai do método se houver erro
            }

            if (telefone.isEmpty()) { // Verifica se o telefone foi preenchido
                JOptionPane.showMessageDialog(this, "O telefone é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                return; // Sai do método se houver erro
            }

            if (email.isEmpty()) { // Verifica se o email foi preenchido
                JOptionPane.showMessageDialog(this, "O e-mail é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                return; // Sai do método se houver erro
            }

            // Verifica a validade do email
            if (!email.contains("@") || !email.contains(".")) { // Valida a estrutura do email
                JOptionPane.showMessageDialog(this, "O e-mail fornecido não é válido.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                return; // Sai do método se houver erro
            }

            // Criação do cliente
            Cliente cliente = new Cliente(); // Cria um novo objeto Cliente
            cliente.setNome(nome); // Define o nome
            cliente.setTelefone(telefone); // Define o telefone
            cliente.setEmail(email); // Define o email
            cliente.setEndereco(endereco); // Define o endereço

            // Tentativa de cadastro
            boolean sucesso = clienteController.cadastrarCliente(cliente); // Tenta cadastrar o cliente no controlador
            if (sucesso) { // Verifica se o cadastro foi bem-sucedido
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso: " + nome); // Mensagem de sucesso

                // Limpar campos após cadastro
                txtNome.setText(""); // Limpa o campo de nome
                txtTelefone.setText(""); // Limpa o campo de telefone
                txtEmail.setText(""); // Limpa o campo de email
                txtEndereco.setText(""); // Limpa o campo de endereço

                // Atualizar a lista de clientes no painel de listagem
                ((ClientListPanel) clientListPanel).atualizarListaClientes(); // Chama o método para atualizar a lista de clientes
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar o cliente.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
            }
        });
    }
}
