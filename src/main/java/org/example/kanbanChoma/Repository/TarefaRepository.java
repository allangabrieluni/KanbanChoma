package org.example.kanbanChoma.Repository;

import org.example.kanbanChoma.Model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
