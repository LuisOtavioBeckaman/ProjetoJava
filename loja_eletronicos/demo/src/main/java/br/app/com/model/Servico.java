package br.app.com.model;

import java.time.LocalDate;

/**
 * Classe que representa uma Prestação de Serviço.
 */
public class Servico {
    private int id; // Identificador único do serviço
    private String descricao; // Descrição do serviço
    private double valor; // Valor do serviço
    private int clienteId; // ID do cliente que contratou o serviço
    private String data; // Data da prestação do serviço (Formato: dd/MM/yyyy)

    // Construtor padrão
    public Servico() {
    }

    // Construtor com parâmetros
    public Servico(int id, String descricao, double valor, int clienteId, String data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.clienteId = clienteId;
        this.data = data;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    } 

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getData() {
        return data;
    }

    public void setData(String string) {
        this.data = string;
    }

    // Método toString para exibição na interface gráfica
    @Override
    public String toString() {
        return descricao + " - R$ " + String.format("%.2f", valor);
    }

    public void setData(LocalDate novaData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setData'");
    }
}
