package tech.challenge.notification.system.infrastructure.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.Instant;
import java.util.Map;

@Service
public class FeedbackQueueService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final String queueUrl;

    public FeedbackQueueService(SqsClient sqsClient,
                                ObjectMapper objectMapper,
                                @Value("${aws.sqs.feedback-queue-url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    public void sendFeedback(Long lessonId, String description, Integer score) {
        Map<String, Object> payload = Map.of(
                "lessonId", lessonId,
                "description", description,
                "score", score,
                "createdAt", Instant.now().toString()
        );

        String messageBody;
        try {
            messageBody = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Erro ao serializar feedback para SQS", e);
        }

        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .build());
    }
}
