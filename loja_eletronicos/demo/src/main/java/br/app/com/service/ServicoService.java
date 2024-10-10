package br.app.com.service;

import br.app.com.dao.ServicoDAO;
import br.app.com.model.Servico;

import java.util.List;

/**
 * Serviço de negócios para a entidade Servico.
 */
public class ServicoService {
    private final ServicoDAO servicoDAO;

    // Construtor que inicializa o DAO de serviço
    public ServicoService() {
        this.servicoDAO = new ServicoDAO();
    }

    /**
     * Método para cadastrar uma nova prestação de serviço.
     * @param servico Objeto Servico a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarServico(Servico servico) {
        return servicoDAO.cadastrarServico(servico);
    }

    /**
     * Método para listar todas as prestações de serviço cadastradas.
     * @return Lista de objetos Servico.
     */
    public List<Servico> listarServicos() {
        return servicoDAO.listarServicos();
    }

    /**
     * Método para buscar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço.
     * @return Objeto Servico encontrado ou null se não encontrado.
     */
    public Servico buscarServicoPorId(int id) {
        return servicoDAO.buscarServicoPorId(id);
    }

    /**
     * Método para atualizar uma prestação de serviço existente.
     * @param servico Objeto Servico com as informações atualizadas.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean atualizarServico(Servico servico) {
        return servicoDAO.atualizarServico(servico);
    }

    /**
     * Método para deletar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço a ser deletada.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deletarServico(int id) {
        return servicoDAO.deletarServico(id);
    }
}
