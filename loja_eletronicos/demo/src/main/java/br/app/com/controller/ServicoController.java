package br.app.com.controller;

import br.app.com.model.Servico;
import br.app.com.service.ServicoService;

import java.util.List;

/**
 * Controlador para a entidade Servico.
 */
public class ServicoController {
    private final ServicoService servicoService;

    // Construtor que inicializa o serviço de serviço
    public ServicoController() {
        this.servicoService = new ServicoService();
    }

    /**
     * Método para cadastrar uma nova prestação de serviço.
     * @param servico Objeto Servico a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarServico(Servico servico) {
        // Aqui você pode adicionar validações adicionais se necessário
        return servicoService.cadastrarServico(servico);
    }

    /**
     * Método para listar todas as prestações de serviço.
     * @return Lista de objetos Servico.
     */
    public List<Servico> listarServicos() {
        return servicoService.listarServicos();
    }

    /**
     * Método para buscar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço.
     * @return Objeto Servico encontrado ou null se não encontrado.
     */
    public Servico buscarServicoPorId(int id) {
        return servicoService.buscarServicoPorId(id);
    }

    /**
     * Método para atualizar uma prestação de serviço existente.
     * @param servico Objeto Servico com as informações atualizadas.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean atualizarServico(Servico servico) {
        // Aqui você pode adicionar validações adicionais se necessário
        return servicoService.atualizarServico(servico);
    }

    /**
     * Método para deletar uma prestação de serviço pelo ID.
     * @param id ID da prestação de serviço a ser deletada.
     * @return true se a deleção for bem-sucedida, false caso contrário.
     */
    public boolean deletarServico(int id) {
        return servicoService.deletarServico(id);
    }
}
