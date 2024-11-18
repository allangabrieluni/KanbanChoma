package org.example.kanbanChoma.Controller;

import org.example.kanbanChoma.Model.Kanban;
import org.example.kanbanChoma.Model.Prioridade;
import org.example.kanbanChoma.Model.Status;
import org.example.kanbanChoma.Model.Tarefa;
import org.example.kanbanChoma.Service.KanbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kanban")
public class KanbanController {

    @Autowired
    private KanbanService kanbanService;

    @GetMapping("/tarefas")
    public List<Tarefa> listarTarefas(){
        return kanbanService.listarTarefas();
    }

    @GetMapping("/tarefas/relatorio")
    public ResponseEntity<Map<Status, List<Tarefa>>> gerarRelatorio() {
        Map<Status, List<Tarefa>> relatorio = kanbanService.gerarRelatorio();
        relatorio.forEach((status, tarefas) -> tarefas.forEach(tarefa -> {
            if (tarefa.getDataLimite() != null &&
                    tarefa.getDataLimite().before(new Date()) &&
                    !tarefa.getStatus().equals(Status.CONCLUIDO)) {
                tarefa.setTitulo("[ATRASADA] " + tarefa.getTitulo());
            }
        }));
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<Tarefa>> filtrarTarefas(
            @RequestParam Prioridade prioridade,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataLimite) {
        List<Tarefa> tarefas = kanbanService.filtrarTarefas(prioridade, dataLimite);
        return ResponseEntity.ok(tarefas);
    }


}
