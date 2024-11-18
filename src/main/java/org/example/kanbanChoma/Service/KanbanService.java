package org.example.kanbanChoma.Service;

import jakarta.annotation.PostConstruct;
import org.example.kanbanChoma.Model.Kanban;
import org.example.kanbanChoma.Model.Prioridade;
import org.example.kanbanChoma.Model.Status;
import org.example.kanbanChoma.Model.Tarefa;
import org.example.kanbanChoma.Repository.KanbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KanbanService {

    @Autowired
    private KanbanRepository kanbanRepository;

    public List<Tarefa> listarTarefas() {
        Kanban kanban = kanbanRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum Kanban encontrado."));


        return kanban.getTarefas().stream()
                .sorted((t1, t2) -> {
                    int statusComparison = t1.getStatus().compareTo(t2.getStatus());
                    if (statusComparison != 0) {
                        return statusComparison;
                    }
                    return t1.getPrioridade().compareTo(t2.getPrioridade());
                })
                .toList();
    }

    public List<Tarefa> filtrarTarefas(Prioridade prioridade, Date dataLimite) {
        Kanban kanban = kanbanRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum Kanban encontrado."));

        return kanban.getTarefas().stream()
                .filter(tarefa -> tarefa.getPrioridade() == prioridade &&
                        (tarefa.getDataLimite() != null && tarefa.getDataLimite().before(dataLimite)))
                .toList();
    }

    public Map<Status, List<Tarefa>> gerarRelatorio() {
        Kanban kanban = kanbanRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum Kanban encontrado."));

        return kanban.getTarefas().stream()
                .collect(Collectors.groupingBy(Tarefa::getStatus, Collectors.toList()));
    }



    @PostConstruct
    public void init() {
        if (kanbanRepository.count() == 0) {
            Kanban defaultKanban = new Kanban();

            Tarefa tarefa1 = new Tarefa();
            tarefa1.setTitulo("Compar um ganso");
            tarefa1.setDescricao("Choma deve ir até um lugar e deve compra vc sabe oq");
            tarefa1.setDataCriacao(new Date());
            tarefa1.setStatus(Status.A_FAZER);
            tarefa1.setKanban(defaultKanban);

            Tarefa tarefa2 = new Tarefa();
            tarefa2.setTitulo("Dar palestriha");
            tarefa2.setDescricao("O aluno (des)favorito -  ou não -  do Choma deve palestrar.");
            tarefa2.setDataCriacao(new Date());
            tarefa2.setStatus(Status.EM_PROGRESSO);
            tarefa2.setKanban(defaultKanban);

            defaultKanban.getTarefas().add(tarefa1);
            defaultKanban.getTarefas().add(tarefa2);

            kanbanRepository.save(defaultKanban);
        }
    }



}

