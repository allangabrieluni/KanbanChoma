package org.example.kanbanChoma.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.kanbanChoma.Model.Kanban;

public interface KanbanRepository extends JpaRepository<Kanban, Long> {
}
