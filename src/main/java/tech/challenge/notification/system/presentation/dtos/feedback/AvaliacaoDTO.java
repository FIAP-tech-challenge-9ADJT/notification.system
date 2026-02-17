package tech.challenge.notification.system.presentation.dtos.feedback;

import jakarta.validation.constraints.*;

public record AvaliacaoDTO(

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        String descricao,

        @NotNull(message = "Nota é obrigatória")
        @Min(value = 0, message = "Nota deve ser no mínimo 0")
        @Max(value = 10, message = "Nota deve ser no máximo 10")
        Integer nota
) {
}
