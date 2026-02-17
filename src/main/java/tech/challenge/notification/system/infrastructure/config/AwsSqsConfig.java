package tech.challenge.notification.system.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsSqsConfig {

    @Bean
    public SqsClient sqsClient(@Value("${aws.region}") String region,
                               @Value("${aws.access-key-id:}") String accessKeyId,
                               @Value("${aws.secret-access-key:}") String secretAccessKey) {
        return SqsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(buildCredentialsProvider(accessKeyId, secretAccessKey))
                .build();
    }

    private AwsCredentialsProvider buildCredentialsProvider(String accessKeyId,
                                                            String secretAccessKey) {
        if (accessKeyId != null && !accessKeyId.isBlank()
                && secretAccessKey != null && !secretAccessKey.isBlank()) {
            return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretAccessKey));
        }
        return DefaultCredentialsProvider.create();
    }
}
