package tech.challenge.notification.system.presentation.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.challenge.notification.system.infrastructure.queue.FeedbackQueueService;
import tech.challenge.notification.system.presentation.dtos.feedback.AvaliacaoDTO;

import java.time.Instant;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final FeedbackQueueService feedbackQueueService;

    public AvaliacaoController(FeedbackQueueService feedbackQueueService) {
        this.feedbackQueueService = feedbackQueueService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> receberAvaliacao(
            @Valid @RequestBody AvaliacaoDTO avaliacao) {

        // Enviar feedback para a fila SQS
        feedbackQueueService.sendFeedback(
                0L, // lessonId não aplicável para avaliação geral
                avaliacao.descricao(),
                avaliacao.nota()
        );

        AvaliacaoResponse response = new AvaliacaoResponse(
                "Avaliação recebida com sucesso",
                Instant.now().toString()
        );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }

    public record AvaliacaoResponse(
            String mensagem,
            String dataRecebimento
    ) {}
}
