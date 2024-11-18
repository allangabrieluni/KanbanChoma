package org.example.kanbanChoma.Model;

public enum Status {
    A_FAZER,
    EM_PROGRESSO,
    CONCLUIDO;

    public Status proximo() {
        return switch (this) {
            case A_FAZER -> EM_PROGRESSO;
            case EM_PROGRESSO -> CONCLUIDO;
            case CONCLUIDO -> this; // Se já estiver "CONCLUIDO", permanece
        };
    }
}

