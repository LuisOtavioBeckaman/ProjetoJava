package br.app.com.ui;

import br.app.com.controller.ServicoController;
import br.app.com.model.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Painel para listar e gerenciar as prestações de serviço cadastradas.
 */
public class ServicoListPanel extends JPanel {
    private final ServicoController servicoController; // Controlador para operações de serviço
    private JTable servicosTable; // Tabela para exibir os serviços

    /**
     * Construtor do painel de listagem de serviços.
     */
    public ServicoListPanel() {
        this.servicoController = new ServicoController(); // Inicializa o controlador de serviços
        setLayout(new BorderLayout()); // Define o layout como BorderLayout
        initializeComponents(); // Inicializa os componentes do painel
    }

    /**
     * Método para inicializar os componentes do painel.
     */
    private void initializeComponents() {
        // Painel que contém a tabela de serviços
        JPanel servicosPanel = new JPanel(new BorderLayout());
        servicosPanel.setBorder(BorderFactory.createTitledBorder("Serviços Registrados")); // Define o título do painel

        // Criação da tabela de serviços
        servicosTable = new JTable();
        atualizarTabelaServicos(); // Preenche a tabela com os dados atuais

        // Adiciona a tabela de serviços dentro de um JScrollPane para permitir rolagem
        servicosPanel.add(new JScrollPane(servicosTable), BorderLayout.CENTER);

        // Criação dos botões de ação
        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarTabelaServicos()); // Atualiza a tabela quando clicado

        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> excluirServico()); // Chama o método para excluir o serviço

        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarServico()); // Chama o método para editar o serviço

        JButton exportarButton = new JButton("Exportar para .txt");
        exportarButton.addActionListener(e -> exportarServicosParaTxt()); // Chama o método para exportar os serviços

        // Painel que contém os botões na parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(atualizarButton); // Adiciona o botão de atualizar
        buttonPanel.add(excluirButton); // Adiciona o botão de excluir
        buttonPanel.add(editarButton); // Adiciona o botão de editar
        buttonPanel.add(exportarButton); // Adiciona o botão de exportar

        // Adiciona os componentes ao painel principal
        add(servicosPanel, BorderLayout.CENTER); // Adiciona o painel de serviços ao centro
        add(buttonPanel, BorderLayout.SOUTH); // Adiciona o painel de botões na parte inferior
    }

    /**
     * Método para atualizar a tabela de serviços com os dados mais recentes.
     */
    public void atualizarTabelaServicos() {
        List<Servico> servicos = servicoController.listarServicos(); // Obtém a lista de serviços do controlador
        Object[][] data = new Object[servicos.size()][5]; // Define o número de colunas

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato para exibir a data

        // Popula o array de dados com as informações dos serviços
        for (int i = 0; i < servicos.size(); i++) {
            Servico servico = servicos.get(i);

            data[i][0] = servico.getId(); // ID do serviço

            // Formata a data do serviço, se disponível
            if (servico.getData() != null) {
                data[i][1] = servico.getData().formatted(dateFormatter); // Converte LocalDate para String formatada
            } else {
                data[i][1] = ""; // Se não houver data, deixa em branco
            }

            data[i][2] = String.format("R$ %.2f", servico.getValor()); // Formata o valor do serviço
            data[i][3] = servico.getDescricao(); // Descrição do serviço
            data[i][4] = servico.getClienteId(); // ID do cliente associado
        }

        // Define o modelo da tabela com os dados dos serviços
        servicosTable.setModel(new DefaultTableModel(
                data,
                new String[]{"ID", "Data", "Valor", "Descrição", "ID Cliente"}
        ));
        servicosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Define a seleção para uma única linha
    }

    /**
     * Método para excluir um serviço selecionado na tabela.
     */
    private void excluirServico() {
        int selectedRow = servicosTable.getSelectedRow(); // Obtém a linha selecionada na tabela
        if (selectedRow >= 0) { // Verifica se há uma linha selecionada
            int servicoId = (int) servicosTable.getValueAt(selectedRow, 0); // Obtém o ID do serviço a ser excluído
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja excluir este serviço?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            ); // Solicita confirmação do usuário

            if (confirmacao == JOptionPane.YES_OPTION) { // Se o usuário confirmar
                if (servicoController.deletarServico(servicoId)) { // Tenta excluir o serviço
                    JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!"); // Mensagem de sucesso
                    atualizarTabelaServicos(); // Atualiza a tabela após exclusão
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir serviço.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE); // Solicita seleção
        }
    }

    /**
     * Método para editar um serviço selecionado na tabela.
     */
    private void editarServico() {
        int selectedRow = servicosTable.getSelectedRow(); // Obtém a linha selecionada na tabela
        if (selectedRow >= 0) { // Verifica se há uma linha selecionada
            int servicoId = (int) servicosTable.getValueAt(selectedRow, 0); // Obtém o ID do serviço
            Servico servico = servicoController.buscarServicoPorId(servicoId); // Busca o serviço pelo ID

            if (servico != null) { // Verifica se o serviço foi encontrado
                // Solicita novos valores ao usuário
                String novaDescricao = JOptionPane.showInputDialog(this, "Editar Descrição:", servico.getDescricao());
                String novoValorStr = JOptionPane.showInputDialog(this, "Editar Valor (R$):", servico.getValor());
                String novoClienteIdStr = JOptionPane.showInputDialog(this, "Editar ID do Cliente:", servico.getClienteId());
                String novaDataStr = JOptionPane.showInputDialog(this, "Editar Data (dd/MM/yyyy):", servico.getData().formatted(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                // Validações básicas
                if (novaDescricao == null || novaDescricao.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "A descrição é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (novoValorStr == null || novoValorStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O valor é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (novoClienteIdStr == null || novoClienteIdStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O ID do cliente é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (novaDataStr == null || novaDataStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "A data é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double novoValor;
                int novoClienteId;

                // Validação e conversão do valor
                try {
                    novoValor = Double.parseDouble(novoValorStr.replace(",", ".")); // Substitui vírgula por ponto para parsing
                    if (novoValor < 0) {
                        JOptionPane.showMessageDialog(this, "O valor não pode ser negativo.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "O valor deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validação e conversão do ID do cliente
                try {
                    novoClienteId = Integer.parseInt(novoClienteIdStr);
                    if (novoClienteId <= 0) {
                        JOptionPane.showMessageDialog(this, "O ID do cliente deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "O ID do cliente deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validação e conversão da data
                LocalDate novaData;
                try {
                    novaData = LocalDate.parse(novaDataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Atualiza os dados do serviço com os novos valores
                servico.setDescricao(novaDescricao);
                servico.setValor(novoValor);
                servico.setClienteId(novoClienteId);
                servico.setData(novaData); // Atualiza a data como LocalDate

                // Tenta atualizar o serviço no controlador
                boolean sucesso = servicoController.atualizarServico(servico);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!"); // Mensagem de sucesso
                    atualizarTabelaServicos(); // Atualiza a tabela após a atualização
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar serviço.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
                }
            } else {
                JOptionPane.showMessageDialog(this, "Serviço não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); // Solicita seleção
        }
    }

    /**
     * Método para exportar as prestações de serviço para um arquivo .txt.
     */
    private void exportarServicosParaTxt() {
        List<Servico> servicos = servicoController.listarServicos(); // Obtém a lista de serviços
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato para exibir a data

        // Abre um diálogo para o usuário escolher o local e o nome do arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar arquivo de serviços");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) { // Se o usuário escolheu um local
            String caminhoArquivo = fileChooser.getSelectedFile().getAbsolutePath(); // Obtém o caminho completo do arquivo

            // Garante que o arquivo tenha a extensão .txt
            if (!caminhoArquivo.toLowerCase().endsWith(".txt")) {
                caminhoArquivo += ".txt";
            }

            // Tenta escrever os serviços no arquivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                // Escreve o cabeçalho do arquivo
                writer.write("ID | Descrição | Valor | ID Cliente | Data");
                writer.newLine();
                writer.write("---------------------------------------------------------------");
                writer.newLine();

                // Escreve os dados de cada serviço
                for (Servico servico : servicos) {
                    String dataVenda = servico.getData() != null ? servico.getData().formatted(dateFormatter) : "N/A"; // Formata a data
                    String linha = String.format("%d | %s | R$ %.2f | %d | %s",
                            servico.getId(),
                            servico.getDescricao(),
                            servico.getValor(),
                            servico.getClienteId(),
                            dataVenda);
                    writer.write(linha); // Escreve a linha no arquivo
                    writer.newLine(); // Adiciona uma nova linha
                }

                JOptionPane.showMessageDialog(this, "Serviços exportados com sucesso para " + caminhoArquivo); // Mensagem de sucesso
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar serviços: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); // Mensagem de erro
            }
        } else {
            // O usuário cancelou a operação de salvar
            JOptionPane.showMessageDialog(this, "Exportação cancelada.", "Informação", JOptionPane.INFORMATION_MESSAGE); // Mensagem informativa
        }
    }

    public void atualizarListaServicos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarListaServicos'");
    }
}
