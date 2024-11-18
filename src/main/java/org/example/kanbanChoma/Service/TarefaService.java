package org.example.kanbanChoma.Service;

import org.example.kanbanChoma.Model.Kanban;
import org.example.kanbanChoma.Model.Prioridade;
import org.example.kanbanChoma.Model.Tarefa;
import org.example.kanbanChoma.Repository.KanbanRepository;
import org.example.kanbanChoma.Repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private KanbanRepository kanbanRepository;

    public Tarefa inserirTarefa(Tarefa novaTarefa) {

        Kanban kanban = kanbanRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum Kanban encontrado."));

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo(novaTarefa.getTitulo());
        tarefa.setDescricao(novaTarefa.getDescricao());
        tarefa.setPrioridade(novaTarefa.getPrioridade());
        tarefa.setDataCriacao(novaTarefa.getDataCriacao() != null ? novaTarefa.getDataCriacao() : new Date());
        tarefa.setDataLimite(novaTarefa.getDataLimite());

        tarefa.setKanban(kanban);
        kanban.adicionarTarefa(tarefa);

        kanbanRepository.save(kanban);
        return tarefaRepository.save(tarefa);
    }


    public List<Tarefa> selectAllTarefa() {
        return tarefaRepository.findAll();
    }

    public Tarefa updateTarefa(long Id, Tarefa novaTarefa) {
        Tarefa tarefaExistente = selectTarefaById(Id);

        tarefaExistente.setTitulo(novaTarefa.getTitulo());
        tarefaExistente.setDescricao(novaTarefa.getDescricao());
        tarefaExistente.setStatus(novaTarefa.getStatus());
        tarefaExistente.setPrioridade(novaTarefa.getPrioridade());
        tarefaExistente.setDataLimite(novaTarefa.getDataLimite());

        return tarefaRepository.save(tarefaExistente);
    }

    public void deleteTarefa(Long Id) {
        tarefaRepository.deleteById(Id);
    }

    public Tarefa moverProProximoStatus(Long id) {
        Tarefa tarefaExistente = selectTarefaById(id);

        if (tarefaExistente == null) {
            throw new RuntimeException("Essa tarefa não foi encontrada.");
        }

        tarefaExistente.setStatus(tarefaExistente.getStatus().proximo());

        return tarefaRepository.save(tarefaExistente);
    }

    public Tarefa selectTarefaById(long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

}
