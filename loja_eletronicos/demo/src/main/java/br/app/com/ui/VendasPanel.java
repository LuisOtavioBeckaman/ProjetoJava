package br.app.com.ui;

// Importa classes necessárias para o funcionamento do painel de vendas
import br.app.com.model.Venda;
import br.app.com.service.VendaService;
import javax.swing.table.DefaultTableModel; 
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Classe que representa o painel de vendas
public class VendasPanel extends JPanel {
    private final VendaService vendaService; // Instância do serviço de vendas
    private JTable vendasTable; // Tabela para exibir as vendas

    // Construtor do painel
    public VendasPanel() {
        this.vendaService = new VendaService(); // Inicializa o serviço de vendas
        setLayout(new BorderLayout()); // Define o layout do painel como BorderLayout
        initializeComponents(); // Chama o método para inicializar os componentes
    }

    // Método que inicializa os componentes do painel de vendas
    private void initializeComponents() {
        // Painel para a tabela de vendas
        JPanel vendasPanel = new JPanel(new BorderLayout());
        vendasPanel.setBorder(BorderFactory.createTitledBorder("Vendas Registradas")); // Define título para o painel

        // Criação inicial da tabela de vendas
        vendasTable = new JTable();
        atualizarTabelaVendas(); // Preenche a tabela com os dados de vendas

        // Adiciona a tabela de vendas ao painel
        vendasPanel.add(new JScrollPane(vendasTable), BorderLayout.CENTER);

        // Botão para atualizar a tabela
        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.addActionListener(e -> atualizarTabelaVendas()); // Atualiza a tabela quando clicado

        // Botão para excluir uma venda
        JButton excluirButton = new JButton("Excluir");
        excluirButton.addActionListener(e -> excluirVenda()); // Chama o método para excluir a venda

        // Botão para editar uma venda
        JButton editarButton = new JButton("Editar");
        editarButton.addActionListener(e -> editarVenda()); // Chama o método para editar a venda

        // Botão para exportar as vendas para um arquivo .txt
        JButton exportarButton = new JButton("Exportar para .txt");
        exportarButton.addActionListener(e -> exportarVendasParaTxt()); // Chama o método para exportar as vendas

        // Painel para os botões na parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(atualizarButton); // Adiciona o botão de atualizar
        buttonPanel.add(excluirButton); // Adiciona o botão de excluir
        buttonPanel.add(editarButton); // Adiciona o botão de editar
        buttonPanel.add(exportarButton); // Adiciona o botão de exportar

        // Adiciona os componentes ao painel principal
        add(vendasPanel, BorderLayout.CENTER); // Adiciona o painel de vendas ao centro
        add(buttonPanel, BorderLayout.SOUTH); // Adiciona o painel de botões na parte inferior
    }

    // Método para atualizar a tabela de vendas com dados
    private void atualizarTabelaVendas() {
        List<Venda> vendas = vendaService.listarVendas(); // Obtém a lista de vendas do serviço
        Object[][] data = new Object[vendas.size()][5]; // Cria um array para armazenar os dados das vendas (5 colunas)

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formata a data

        // Preenche os dados da tabela com as informações de vendas
        for (int i = 0; i < vendas.size(); i++) {
            Venda venda = vendas.get(i); // Obtém a venda atual

            data[i][0] = venda.getId(); // Armazena o ID da venda

            // Formata a data da venda, se disponível
            if (venda.getData() != null) {
                data[i][1] = venda.getData().format(dateFormatter); 
            } else {
                data[i][1] = ""; // Se não houver data, deixa em branco
            }
            
            data[i][2] = String.format("R$ %.2f", venda.getTotal()); // Formata o total da venda
            data[i][3] = venda.getFormaPagamento(); // Armazena a forma de pagamento
            data[i][4] = venda.getClienteId(); // Armazena o ID do cliente
        }

        // Atualiza o modelo da tabela com os dados das vendas
        vendasTable.setModel(new DefaultTableModel(data, new String[]{"ID", "Data", "Total", "Forma de Pagamento", "ID Cliente"}));
        vendasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Define o modo de seleção para apenas uma linha
    }

    // Método para excluir uma venda selecionada
    private void excluirVenda() {
        int selectedRow = vendasTable.getSelectedRow(); // Obtém a linha selecionada na tabela
        if (selectedRow >= 0) { // Verifica se há uma linha selecionada
            int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); // Obtém o ID da venda a ser excluída
            if (vendaService.excluirVenda(vendaId)) { // Tenta excluir a venda
                JOptionPane.showMessageDialog(this, "Venda excluída com sucesso!"); // Mensagem de sucesso
                atualizarTabelaVendas(); // Atualiza a tabela após exclusão
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir venda."); // Mensagem de erro
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para excluir."); // Solicita que uma linha seja selecionada
        }
    }

    // Método para editar uma venda selecionada
    private void editarVenda() {
        int selectedRow = vendasTable.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow >= 0) { // Verifica se há uma linha selecionada
            int vendaId = (int) vendasTable.getValueAt(selectedRow, 0); // Obtém o ID da venda
            Venda venda = new Venda(); // Cria uma nova instância de Venda
            venda.setId(vendaId); // Define o ID da venda

            String dataString = (String) vendasTable.getValueAt(selectedRow, 1); // Obtém a data da venda
            try {
                // Tenta converter a string da data em LocalDate
                LocalDate dataVenda = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                venda.setData(dataVenda); // Define a data da venda
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao converter a data: " + e.getMessage()); // Mensagem de erro
                return; // Sai do método em caso de erro
            }

            // Obtém e formata o total, a forma de pagamento e o ID do cliente
            venda.setTotal(Double.parseDouble(((String) vendasTable.getValueAt(selectedRow, 2)).replace("R$ ", "").replace(",", ".")));
            venda.setFormaPagamento((String) vendasTable.getValueAt(selectedRow, 3));
            venda.setClienteId((int) vendasTable.getValueAt(selectedRow, 4));

            // Solicita nova forma de pagamento ao usuário
            String novaFormaPagamento = JOptionPane.showInputDialog(this, "Forma de Pagamento:", venda.getFormaPagamento());
            if (novaFormaPagamento != null) { // Se o usuário não cancelar a entrada
                venda.setFormaPagamento(novaFormaPagamento); // Define a nova forma de pagamento
                if (vendaService.atualizarVenda(venda)) { // Tenta atualizar a venda
                    JOptionPane.showMessageDialog(this, "Venda atualizada com sucesso!"); // Mensagem de sucesso
                    atualizarTabelaVendas(); // Atualiza a tabela após a atualização
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar venda."); // Mensagem de erro
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para editar."); // Solicita que uma linha seja selecionada
        }
    }

    // Método para exportar as vendas para um arquivo .txt
    private void exportarVendasParaTxt() {
        List<Venda> vendas = vendaService.listarVendas(); // Obtém a lista de vendas
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formata a data
        
        String caminhoArquivo = "vendas.txt"; // Nome do arquivo de saída

        // Tenta escrever as vendas no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            // Escreve o cabeçalho do arquivo
            writer.write("ID | Data | Total | Forma de Pagamento | ID Cliente");
            writer.newLine();
            writer.write("-----------------------------------------------------");
            writer.newLine();

            // Escreve os dados de cada venda
            for (Venda venda : vendas) {
                String dataVenda = venda.getData() != null ? venda.getData().format(dateFormatter) : "N/A"; // Formata a data
                String linha = String.format("%d | %s | R$ %.2f | %s | %d",
                        venda.getId(), // ID da venda
                        dataVenda, // Data da venda
                        venda.getTotal(), // Total da venda
                        venda.getFormaPagamento(), // Forma de pagamento
                        venda.getClienteId()); // ID do cliente
                writer.write(linha); // Escreve a linha no arquivo
                writer.newLine(); // Nova linha
            }

            JOptionPane.showMessageDialog(this, "Vendas exportadas com sucesso para " + caminhoArquivo); // Mensagem de sucesso
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar vendas: " + e.getMessage()); // Mensagem de erro
        }
    }
}
