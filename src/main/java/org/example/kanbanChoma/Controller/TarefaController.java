package org.example.kanbanChoma.Controller;

import org.example.kanbanChoma.Model.Tarefa;
import org.example.kanbanChoma.Service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")

public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PutMapping("/{Id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable long Id, @RequestBody Tarefa tarefa) {
        Tarefa tarefaAtualizada = tarefaService.updateTarefa(Id, tarefa);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaService.inserirTarefa(tarefa);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable long id) {
        tarefaService.deleteTarefa(id);
    }

    @PutMapping("/{id}/moverStatus")
    public Tarefa moverProProximoStatus(@PathVariable long id) {
        return tarefaService.moverProProximoStatus(id);
    }
}
