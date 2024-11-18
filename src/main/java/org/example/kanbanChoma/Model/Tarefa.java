package org.example.kanbanChoma.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;
    private String descricao;
    private Date dataCriacao;
    @Enumerated(EnumType.STRING)
    private Status status = Status.A_FAZER;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade = Prioridade.BAIXA;
    private Date dataLimite;

    @ManyToOne
    @JoinColumn(name = "id_kanban", nullable = false)
    private Kanban kanban;

    public void setKanban(Kanban kanban) {
        this.kanban = kanban;
    }

    public void moverProProximoStatus() {
        this.status = this.status.proximo();
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

}
